package it.csttech.fel.controllers.secure.fel.pages.gestione;

import com.google.common.collect.ImmutableList;
import it.csttech.fel.FelModule;
import it.csttech.fel.business.importers.service.FelImportazioneService;
import it.csttech.fel.business.services.FelFatturaXmlService;
import it.csttech.fel.controllers.secure.fel.pages.FelFattureUploaderController;
import it.csttech.fel.data.enums.FelSummaryType;
import it.csttech.fel.module.FelModuleFileTypes;
import it.phoenix.core.data.exceptions.NotFoundException;
import it.phoenix.web.annotations.MenuItem;
import it.phoenix.web.data.dtos.PhoenixResponse;
import it.phoenix.web.data.entities.ImportData;
import it.phoenix.web.data.enumerations.ImportStatus;
import it.phoenix.web.data.managers.ImportManager;
import it.phoenix.web.security.User;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static it.phoenix.web.data.dtos.PhoenixResponse.*;
import static java.lang.String.format;


@Controller
@RequestMapping("/secure/e-invoicing/pages/gestione/importazione")
@RequestScope
@MenuItem(id = "/gestione/importers", position = 1, titleIt = "Importazione", titleEn = "Import")
public class FelImportazioneController extends FelFattureUploaderController {
    private final static Logger log = LogManager.getLogger();

    private final List<ImportStatus> notDelatableStatuses = ImmutableList
            .of(ImportStatus.PROCESSED, ImportStatus.PROCESSED_KO, ImportStatus.PROCESSING);

    private final List<String> notProcessableFileTypes = ImmutableList
            .of("FEL.FATTURA_RICEVUTA_COMPLETA_XML",
                    "FEL.FATTURA_RICEVUTA_SEMPLIFICATA_XML",
                    "FEL.FEL_SDI_RICEVUTA_METADATI_XML",
                    "FEL.FEL_AGO_01B_COMPLETA_ATTACH",
                    "FEL.FEL_AGO_01C_COMPLETA_ATTACH",
                    "FEL.FEL_AGO_02A_COMPLETA_ATTACH",
                    "FEL.FEL_AGO_02B_COMPLETA_ATTACH",
                    "FEL.FEL_AGO_02C_COMPLETA_ATTACH",
                    "FEL.FEL_NUM_COMPLETA_ATTACH",
                    "FEL.FATTURA_EMESSA_ALLEGATO_XML");

    @Autowired
    private FelImportazioneService felImportazioneService;
    @Autowired
    private ImportManager importManager;
    @Autowired
    private FelModuleFileTypes fileTypes;
    @Autowired
    protected FelFatturaXmlService xmlService;

    @Transactional
    @PostMapping(value = "/elabora")
    @PreAuthorize("hasFunction('PLAY_FILE_ISSUED_INVOICES')")
    public PhoenixResponse elabora(@RequestBody ImportDataDto importDataDto, @AuthenticationPrincipal User user) {
        log.info("Elaborazione fattura avviata");

        Long importId = importDataDto.getImportDataId();
        if (importId == null) {
            throw new IllegalArgumentException("importDataId è nullo");
        }

        ImportData importData = importManager.findById(importId);
        if (importData == null) {
            throw new IllegalArgumentException("ImportData " + importId + " non trovata");
        }

        try {
            felImportazioneService.performImport(importData, user.getUsername());
            return success().withAlert("OPERAZIONE AVVIATA", DEFAULT_ALERT_TIMEOUT).withSingleton(importId);
        } catch (SecurityException e) {
            log.warn(e);
            return PhoenixResponse.warning().withAlert(e.getMessage());
        } catch (Exception ex) {
            log.error("Error uploading file", ex);
            return error().withAlert("Errore durante l'elaborazione del file: " + ex.getMessage());
        }
    }

    @Transactional
    @PostMapping(value = "/multielabora")
    @PreAuthorize("hasFunction('PLAY_FILE_ISSUED_INVOICES')")
    public PhoenixResponse multiElabora(@RequestBody ImportDataDto importDataDto, @AuthenticationPrincipal User user) {
        if (importDataDto == null || ArrayUtils.isEmpty(importDataDto.multiIds)) {
            return warning().withAlert("Nessuna fattura selezionata.");
        }

        log.info("Elaborazione fatture multiple avviata");

        List<ImportData> processableImportData = getProcessableImportData(importDataDto.multiIds, user);

        try {
            processableImportData.forEach(importData -> felImportazioneService.performImport(importData, user.getUsername()));

        } catch (SecurityException e) {
            log.warn(e.getMessage(), e);
            return warning().withAlert(e.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return error().withAlert("Errore durante l'elaborazione dei record: " + ex.getMessage());
        }

        return getPhoenixResponseForMultiProcess(importDataDto.multiIds, processableImportData);
    }


    private List<ImportData> getProcessableImportData(Long[] ids, User user) {
        return importManager.findByIds(ids).stream().filter(importData -> isImportDataProcessable(importData, user))
                .collect(Collectors.toList());
    }

    private boolean isImportDataProcessable(ImportData importData, User user) {
        return ImportStatus.ACQUIRED.equals(importData.getStatus())
                && !notProcessableFileTypes.contains(importData.getFileType())
                && importData.getCreator().equals(user.getUsername());

    }

    private PhoenixResponse getPhoenixResponseForMultiProcess(Long[] expected, List<ImportData> actual) {

        String baseMessage = format("Elaborazione avviata per %s file su %s", actual.size(), expected.length);

        if (actual.isEmpty()) {
            return error()
                    .withAlert(baseMessage, DEFAULT_ALERT_TIMEOUT)
                    .withCollection(Arrays.asList(expected));
        }

        if (actual.size() != expected.length) {
            return warning()
                    .withAlert(baseMessage, DEFAULT_ALERT_TIMEOUT)
                    .withCollection(actual.stream().map(ImportData::getId).collect(Collectors.toList()));
        }

        return success()
                .withAlert(baseMessage, DEFAULT_ALERT_TIMEOUT)
                .withCollection(actual.stream().map(ImportData::getId).collect(Collectors.toList()));
    }

    @GetMapping("/elaborato/{importId}")
    public PhoenixResponse elaborato(@PathVariable Long importId, HttpServletResponse response) {
        ImportData importData = importManager.findById(importId);
        if (importData == null) {
            return warning();
        }

        if ((importData.getStatus() == ImportStatus.ACQUIRED) || (importData.getStatus() == ImportStatus.PROCESSING)) {
            return info();
        }

        if ((importData.getStatus() == ImportStatus.REJECTED) || (importData.getStatus() == ImportStatus.AMBIGUOUS) || (importData.getStatus()
                == ImportStatus.DELETED)) {
            return error();
        }

        return success();
    }

    @GetMapping({"/download/{importId}"})
    @PreAuthorize("hasFunction('DOWNLOAD_FILE_ISSUED_INVOICES')")
    public ResponseEntity<Resource> download(@PathVariable("importId") Long importId, HttpServletRequest request,
                                             @AuthenticationPrincipal User user) {
        try {
            ImportData importData = importManager.findById(importId);
            if (importData == null) {
                throw new NotFoundException(ImportData.class, importId);
            }

            return super.downloadFile(importData.getFileRepositoryId(), FelModule.ID, request, user);
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/delete")
    public PhoenixResponse delete(@RequestBody ImportDataDto importDataDto, @AuthenticationPrincipal User user) {
        try {
            if ((importDataDto == null) || (importDataDto.getImportDataId() == null)) {
                throw new NullPointerException("importDataId");
            }

            importManager.delete(importDataDto.getImportDataId(), user.getUsername());

            //            return success().withAlert(getMessage("OPERATION_OK"), DEFAULT_ALERT_TIMEOUT);
            return success().withAlert("Cancellazione avvenuta con successo", DEFAULT_ALERT_TIMEOUT);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return error().withAlert("Errore durante l'eliminazione del record");
        }
    }

    @PostMapping("/multidelete")
    @Transactional
    @PreAuthorize("hasFunction('DELETE_ISSUED_INVOICES')")
    public PhoenixResponse multidelete(@RequestBody ImportDataDto importDataDto, @AuthenticationPrincipal User user) {
        if (importDataDto == null || ArrayUtils.isEmpty(importDataDto.multiIds)) {
            return warning().withAlert("Nessuna fattura selezionata.");
        }

        List<ImportData> deletableImportData = getDeletableImportData(importDataDto.multiIds, user);

        try {
            deletableImportData.forEach(importData -> importManager.delete(importData.getId(), user.getUsername()));

        } catch (Exception ex) {
            // in caso di eccezione -> caso nessuna cancellata
            log.error(ex.getMessage(), ex);
            return error().withAlert("Errore durante l'eliminazione dei record: " + ex.getMessage());
        }

        return getPhoenixResponseForMultiDelete(deletableImportData.size(), importDataDto.getMultiIds().length);

    }

    private List<ImportData> getDeletableImportData(Long[] ids, User user) {
        return importManager.findByIds(ids).stream().filter(importData -> isImportDataDeletable(importData, user))
                .collect(Collectors.toList());
    }

    private boolean isImportDataDeletable(ImportData importData, User user) {
        if (notDelatableStatuses.contains(importData.getStatus()))
            return false;

        if (ImportStatus.ACQUIRED.equals(importData.getStatus()) && !importData.getCreator().equals(user.getUsername()))
            return false;

        return true;

    }

    private PhoenixResponse getPhoenixResponseForMultiDelete(int importsDeleted, int importsToDelete) {
        if (importsDeleted == 0) {
            return error().withAlert(format("Cancellate %s fatture su %s", importsDeleted, importsToDelete), DEFAULT_ALERT_TIMEOUT);
        }

        String baseMessage = importsDeleted == 1 ? "Cancellata %s fattura su %s" : "Cancellate %s fatture su %s";
        if (importsDeleted != importsToDelete) {
            return warning().withAlert(format(baseMessage, importsDeleted, importsToDelete), DEFAULT_ALERT_TIMEOUT);
        }

        return success().withAlert(format(baseMessage, importsDeleted, importsToDelete), DEFAULT_ALERT_TIMEOUT);
    }

    @GetMapping("filtro/{tipoFattura}/tipoFile/{organizationId}")
    public PhoenixResponse filtroTipoFile(@PathVariable("tipoFattura") String tipoFattura, @PathVariable("organizationId") String organizationId) {
        List<String> r = new ArrayList<>();
        String[] tipoFileList;

        if (FelSummaryType.E.name().equals(tipoFattura)) {
            tipoFileList = fileTypes.getIssuedFileTypesForOrganization(
                    felOrganizationManager.getProperty(organizationId, FelModule.ID, FelModule.MODULE_CUSTOM_IDENTIFIER, String.class));
        } else if (FelSummaryType.R.name().equals(tipoFattura)) {
            tipoFileList = fileTypes.getReceivedFileTypes();
        } else {
            return info().withCollection(r);
        }

        for (String s : tipoFileList) {
            if (StringUtils.isNotBlank(s)) {
                r.add(FelModule.ID.concat(".").concat(s));
            }
        }

        return info().withCollection(r);
    }

    private static final class ImportDataDto {

        private Long importDataId;

        private Long[] multiIds;

        public Long getImportDataId() {
            return importDataId;
        }

        @SuppressWarnings("unused")
        public void setImportDataId(Long importDataId) {
            this.importDataId = importDataId;
        }

        public Long[] getMultiIds() {
            return multiIds;
        }

        public void setMultiIds(Long[] multiIds) {
            this.multiIds = multiIds;
        }
    }
}
