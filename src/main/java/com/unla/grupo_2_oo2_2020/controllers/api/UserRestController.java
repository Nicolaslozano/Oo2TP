package com.unla.grupo_2_oo2_2020.controllers.api;

import com.unla.grupo_2_oo2_2020.services.ISecurityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    @Autowired
    @Qualifier("securityService")
    private ISecurityService securityService;

    @GetMapping("/getLoggedUser")
    public ResponseEntity<?> getLoggedUser() {
        String username = securityService.findLoggedInUsername();
        return ResponseEntity.ok(username);
    }
}