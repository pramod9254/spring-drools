package com.springproject.droolEngineProject.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.springproject.droolEngineProject.model.Rule;
import com.springproject.droolEngineProject.repo.DroolRulesRepo;

@RestController
public class RulesController {

    @Autowired
    private DroolRulesRepo rulesRepo;

    @PostMapping("/rule")
    public Rule addRule (@RequestBody Rule rule) {
        // Add debug logging
        System.out.println("Received rule:");
        System.out.println("ifcondition: " + rule.getIfcondition());
        System.out.println("thencondition: " + rule.getThencondition());
        System.out.println("version: " + rule.getVersion());
        
        // Save and return the saved rule to verify what was stored
        return rulesRepo.save(rule);
    }

    @GetMapping("/rules")
    public List<Rule> getRules () {
        List<Rule> rules = new ArrayList<Rule>();

        rulesRepo.findAll().forEach(rules::add);
        return rules;
    }
}
