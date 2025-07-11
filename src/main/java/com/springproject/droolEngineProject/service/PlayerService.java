package com.springproject.droolEngineProject.service;

import java.util.List;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.springproject.droolEngineProject.model.Player;
import com.springproject.droolEngineProject.model.Rule;
import com.springproject.droolEngineProject.repo.DroolRulesRepo;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.KieServices;
import org.kie.internal.utils.KieHelper;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.KieBase;

@Service
public class PlayerService {

    private final KieContainer kieContainer;
    private final DroolRulesRepo rulesRepo;

    public PlayerService(KieContainer kieContainer, DroolRulesRepo rulesRepo){
        this.kieContainer = kieContainer;
        this.rulesRepo = rulesRepo;
    }

    public Player calculateCompensation(Player player) {
        System.out.println("Starting compensation calculation for player: " + player.getName() + ", position: " + player.getPosition());
        try {
            KieSession session = kieContainer.newKieSession();
            session.insert(player);
            int rulesFired = session.fireAllRules();
            session.dispose();
            System.out.println("Fired " + rulesFired + " rules, final compensation: €" + player.getTotalCompensation());
            return player;
        } catch (Exception e) {
            System.out.println("Error in calculateCompensation: " + e.getMessage());
            e.printStackTrace();
            return player;
        }
    }

    public Player calculateCompensationWithDynamicRules(Player player) throws FileNotFoundException {
        // First apply static rules
        player = calculateCompensation(player);
        
        // Then apply dynamic rules from database
        List<Rule> ruleAttributes = new ArrayList<>();
        rulesRepo.findAll().forEach(ruleAttributes::add);
        
        // Filter rules by position if applicable
        List<Rule> positionRules = new ArrayList<>();
        for(Rule rule : ruleAttributes) {
            // Include rules that match player's position or are applicable to ALL positions
            if(rule.getPosition() == null || 
               rule.getPosition().isEmpty() ||
               rule.getPosition().equals("ALL") ||
               rule.getPosition().equals(player.getPosition())) {
                positionRules.add(rule);
            }
        }
        
        // Debug logging to see the rules being loaded
        System.out.println("Loading " + positionRules.size() + " dynamic rules from database for position: " + player.getPosition());
        for(Rule rule : positionRules) {
            System.out.println("Rule ID: " + rule.getId());
            System.out.println("  Position: " + rule.getPosition());
            System.out.println("  Description: " + rule.getDescription());
            System.out.println("  If: " + rule.getIfcondition());
            System.out.println("  Then: " + rule.getThencondition());
        }

        if(positionRules.isEmpty()) {
            return player; // No dynamic rules to apply
        }

        // Prepare template that matches what Drools expects
        String templateContent = 
            "template header\n" +
            "id\n" +
            "position\n" +
            "ifcondition\n" +
            "thencondition\n" +
            "\n" +
            "import com.springproject.droolEngineProject.model.Player;\n" +
            "import com.springproject.droolEngineProject.model.Rule;\n" +
            "\n" +
            "template \"player-rules\"\n" +
            "\n" +
            "rule \"@{id}\"\n" +
            "  dialect \"java\"\n" +
            "  when\n" +
            "    player : Player(position == \"@{position}\" || \"@{position}\" == \"ALL\", @{ifcondition})\n" +
            "  then\n" +
            "    @{thencondition}\n" +
            "    System.out.println(\"Applied dynamic rule @{id} for player: \" + player.getName());\n" +
            "end\n" +
            "\n" +
            "end template";

        // Use the manually created template instead of loading from file
        ObjectDataCompiler compiler = new ObjectDataCompiler();
        InputStream is = new ByteArrayInputStream(templateContent.getBytes());
        String generatedDRL = compiler.compile(positionRules, is);

        // Debug logging to see the generated DRL
        System.out.println("Generated DRL for dynamic rules:");
        System.out.println(generatedDRL);
        
        KieServices kieServices = KieServices.Factory.get();
        KieHelper kieHelper = new KieHelper();
        byte[] b1 = generatedDRL.getBytes();
        Resource resource1 = kieServices.getResources().newByteArrayResource(b1);
        kieHelper.addResource(resource1, ResourceType.DRL);

        KieBase kieBase = kieHelper.build();
        KieSession kieSession = kieBase.newKieSession();
        kieSession.insert(player);
        int numberOfRulesFired = kieSession.fireAllRules();
        System.out.println("Number of dynamic rules fired: " + numberOfRulesFired);
        kieSession.dispose();

        // Recalculate total compensation after dynamic rules
        player.calculateTotalCompensation();
        return player;
    }
}
