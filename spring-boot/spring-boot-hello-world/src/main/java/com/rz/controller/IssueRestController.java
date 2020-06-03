package com.rz.controller;

import com.rz.persistance.model.IssueReport;
import com.rz.persistance.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/issues")
public class IssueRestController {

    @Autowired
    private IssueRepository issueRepository;

    @GetMapping
    public List<IssueReport> getIssues() {
        return issueRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<IssueReport> getIssue(@PathVariable("id") Optional<IssueReport> optionalIssueReport) {
        if (optionalIssueReport.isPresent())
            return new ResponseEntity<>(optionalIssueReport.get(), HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
