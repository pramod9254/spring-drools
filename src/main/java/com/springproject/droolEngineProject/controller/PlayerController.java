package com.springproject.droolEngineProject.controller;

import com.springproject.droolEngineProject.model.Player;
import com.springproject.droolEngineProject.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.io.FileNotFoundException;

@RestController
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @PostMapping("/calculateCompensation")
    public Player calculateCompensation(@RequestBody Player player) {
        return playerService.calculateCompensation(player);
    }
    
    @PostMapping("/calculateDynamicCompensation")
    public Player calculateDynamicCompensation(@RequestBody Player player) throws FileNotFoundException {
        return playerService.calculateCompensationWithDynamicRules(player);
    }
}
