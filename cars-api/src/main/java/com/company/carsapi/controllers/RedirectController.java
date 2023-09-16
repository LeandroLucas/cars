package com.company.carsapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Resolve erro de refresh da página nos estáticos
 */
@RestController
public class RedirectController {
    @GetMapping(path = "/login")
    public RedirectView redirectLogin()  {
        return new RedirectView("");
    }

    @GetMapping(path = "/home")
    public RedirectView redirectHome()  {
        return new RedirectView("");
    }
}
