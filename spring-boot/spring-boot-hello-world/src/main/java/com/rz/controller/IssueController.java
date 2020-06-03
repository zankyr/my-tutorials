package com.rz.controller;

import com.rz.persistance.model.IssueReport;
import com.rz.persistance.repository.IssueRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class IssueController {
    IssueRepository issueRepository;

    public IssueController(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    @GetMapping("/issuereport")
    //@ResponseBody
    public String getReport(Model model, @RequestParam(name = "submitted", required = false) boolean submitted) {
        model.addAttribute("issuereport", new IssueReport());
        model.addAttribute("submitted", submitted);

        return "issues/issuereport_form";
    }

    @PostMapping("/issuereport")
    //@ResponseBody
    public String submitReport(IssueReport issueReport, RedirectAttributes redirectAttributes) {
        // With this code, if you reload the page after a submit, another submit will be requested.
        // So, we need to redirect the request after the save of the issue.
        //IssueReport result = issueRepository.save(issueReport);
        //model.addAttribute("issuereport", result);
        //model.addAttribute("submitted", true);
        //return "issues/issuereport_form";

        this.issueRepository.save(issueReport);
        redirectAttributes.addAttribute("submitted", true);
        return "redirect:/issuereport";

    }

    @GetMapping("/issues")
    //@ResponseBody
    public String getIssues(Model model) {

        model.addAttribute("totalIssues", this.issueRepository.count());

        model.addAttribute("issues", this.issueRepository.findAllButPrivate());

        return "issues/issuereport_list";
    }

}
