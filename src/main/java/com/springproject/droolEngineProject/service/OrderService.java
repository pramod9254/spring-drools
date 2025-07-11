package com.springproject.droolEngineProject.service;

import java.util.List;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.springproject.droolEngineProject.model.Order;
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
public class OrderService {

    private final KieContainer kieContainer;
    private final DroolRulesRepo rulesRepo;

    public OrderService(KieContainer kieContainer, DroolRulesRepo rulesRepo){
        this.kieContainer = kieContainer;
        this.rulesRepo = rulesRepo;
    }

    public Order getDiscountForOrder(Order order) {
        System.out.println("Starting getDiscountForOrder with: " + order.getCardType() + ", price: " + order.getPrice());
        try {
            KieSession session = kieContainer.newKieSession();
            session.insert(order);
            int rulesFired = session.fireAllRules();
            session.dispose();
            System.out.println("Fired " + rulesFired + " rules, final discount: " + order.getDiscount());
            return order;
        } catch (Exception e) {
            System.out.println("Error in getDiscountForOrder: " + e.getMessage());
            e.printStackTrace();
            return order;
        }
    }

    public Order getDiscountForOrderV2(Order order) throws FileNotFoundException {
        List<Rule> ruleAttributes = new ArrayList<>();
        rulesRepo.findAll().forEach(ruleAttributes::add);
        
        // Debug logging to see the rules being loaded
        System.out.println("Loading " + ruleAttributes.size() + " rules from database");
        for(Rule rule : ruleAttributes) {
            System.out.println("Rule ID: " + rule.getId());
            System.out.println("  If: " + rule.getIfcondition());
            System.out.println("  Then: " + rule.getThencondition());
        }

        // Prepare a fixed template that matches what Drools expects
        String templateContent = 
            "template header\n" +
            "id\n" +
            "ifcondition\n" +
            "thencondition\n" +
            "\n" +
            "import com.springproject.droolEngineProject.model.Order;\n" +
            "import com.springproject.droolEngineProject.model.Rule;\n" +
            "\n" +
            "template \"tmp1\"\n" +
            "\n" +
            "rule \"@{id}\"\n" +
            "  dialect \"java\"\n" +
            "  when\n" +
            "    @{ifcondition}\n" +
            "  then\n" +
            "    @{thencondition}\n" +
            "end\n" +
            "\n" +
            "end template";

        // Use the manually created template instead of loading from file
        ObjectDataCompiler compiler = new ObjectDataCompiler();
        // Convert template content to stream
        InputStream is = new ByteArrayInputStream(templateContent.getBytes());
        String generatedDRL = compiler.compile(ruleAttributes, is);

        // Debug logging to see the generated DRL
        System.out.println("Generated DRL:");
        System.out.println(generatedDRL);
        
        KieServices kieServices = KieServices.Factory.get();

        KieHelper kieHelper = new KieHelper();

        // Add the generated rules
        byte[] b1 = generatedDRL.getBytes();
        Resource resource1 = kieServices.getResources().newByteArrayResource(b1);
        kieHelper.addResource(resource1, ResourceType.DRL);

        KieBase kieBase = kieHelper.build();

        KieSession kieSession = kieBase.newKieSession();
        kieSession.insert(order);
        int numberOfRulesFired = kieSession.fireAllRules();
        System.out.println("Number of rules fired: " + numberOfRulesFired);
        kieSession.dispose();

        return order;
    }
}
