package com.rz.controller;

import com.rz.persistance.model.IssueReport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IssueController {

    @GetMapping("/issuereport")
    //@ResponseBody
    public String getReport(Model model) {
        model.addAttribute("issuereport", new IssueReport());

        return "issues/issuereport_form";
    }

    @PostMapping("/issuereport")
    //@ResponseBody
    public String submitReport(Model model, IssueReport issueReport) {
        model.addAttribute("issuereport", new IssueReport());
        model.addAttribute("submitted", true);

        return "issues/issuereport_form";
    }

    @GetMapping("/issues")
    //@ResponseBody
    public String getIssues(Model model) {
        return "issues/issuereport_list";
    }

}
