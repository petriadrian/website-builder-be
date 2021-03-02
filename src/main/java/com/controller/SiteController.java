package com.controller;

import com.config.HasRole;
import com.models.Site;
import com.service.SiteService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.models.Site.Role.Type.ADMIN;

@RestController
@RequestMapping("/site")
@AllArgsConstructor
@Validated
public class SiteController {

    private SiteService siteService;

    @GetMapping
    public Site get(@RequestParam(value = "origin") String origin) {
        return siteService.findOrThrow(origin);
    }

    @PutMapping
    @HasRole(type = ADMIN)
    public Site put(@RequestBody Site site) {
        return siteService.save(site);
    }

    @GetMapping(value = "/create")
    public Site create() {
        return null;
    }
}
