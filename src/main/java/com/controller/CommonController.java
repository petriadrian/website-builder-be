package com.controller;

import com.models.section.type.Common;
import com.service.CommonService;
import com.service.SiteService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/common")
@AllArgsConstructor
@Validated
public class CommonController {

    private SiteService siteService;
    private CommonService commonService;

    @GetMapping("/{siteId}/{commonId}")
    public Common get(@PathVariable String siteId, @PathVariable String commonId) {
        return commonService.findOrThrow(siteService.findByIdOrThrow(siteId), commonId);
    }

    @PutMapping("/{siteId}")
    public Common save(@RequestBody Common common, @PathVariable String siteId) {
        return commonService.save(siteService.findByIdOrThrow(siteId), common);
    }

    @DeleteMapping("/{siteId}/{commonId}")
    public void delete(@PathVariable String siteId, @PathVariable String commonId) {
        commonService.delete(siteService.findByIdOrThrow(siteId), commonId);
    }

}
