package com.controller;

import com.models.Page;
import com.models.Site;
import com.service.PageService;
import com.service.SiteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/page")
@AllArgsConstructor
@Slf4j
public class PageController {

    private PageService pageService;
    private SiteService siteService;

    @GetMapping
    public Page get(@RequestParam(value = "origin") String origin,
                    @RequestParam(value = "path", required = false) String path) {
        log.debug("Page get for path: " + path + " origin " + origin);
        Site site = siteService.findOrThrow(origin);
        return pageService.findOrThrow(site, path);
    }

    @PutMapping
    public Page save(@RequestParam(value = "origin") String origin, @RequestBody Page page) {
        return pageService.save(page, siteService.findOrThrow(origin));
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable String id) {
        pageService.delete(id);
    }

}
