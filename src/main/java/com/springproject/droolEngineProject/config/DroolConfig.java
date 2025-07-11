package com.springproject.droolEngineProject.config;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;

@Configuration
public class DroolConfig {
    public static final String RULES_CUSTOMER_RULES_DRL = "rules/customer-discount.drl";
    public static final String RULES_PLAYER_COMPENSATION_DRL = "rules/player-compensation.drl";
    public static final String RULES_TEMPLATE_FILE = "rules/discount-template.drl";
    public static final String RULES_PLAYER_TEMPLATE_FILE = "rules/player-compensation-template.drl";
    private static final KieServices kieServices = KieServices.Factory.get();

    @Bean
    public KieContainer kieContainer() {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        
        try {
            // Load static rules if they exist
            kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_CUSTOMER_RULES_DRL));
            kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_PLAYER_COMPENSATION_DRL));
            
            KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
            kieBuilder.buildAll();
            
            // Ignore errors since we'll be using dynamic rules
            
            KieRepository kieRepository = kieServices.getRepository();
            ReleaseId krDefaultReleaseId = kieRepository.getDefaultReleaseId();
            KieContainer kieContainer = kieServices.newKieContainer(krDefaultReleaseId);
            
            return kieContainer;
        } catch (Exception e) {
            System.out.println("Warning: Could not load static rules: " + e.getMessage());
            
            // Create a minimal KieContainer for dynamic rules
            KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
            kieBuilder.buildAll();
            
            KieRepository kieRepository = kieServices.getRepository();
            ReleaseId krDefaultReleaseId = kieRepository.getDefaultReleaseId();
            KieContainer kieContainer = kieServices.newKieContainer(krDefaultReleaseId);
            
            return kieContainer;
        }
    }
}
