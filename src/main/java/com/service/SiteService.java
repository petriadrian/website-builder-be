package com.service;

import com.models.Site;
import com.repository.SiteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.models.Site.Role.Type.ADMIN;
import static com.utils.Utils.sanitizeOrigin;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class SiteService {

    private SiteRepository siteRepository;
    private PageService pageService;
    private CommonService commonService;
    private UserService userService;

    public Site findOrThrow(String origin) {
        Site site = siteRepository.findByOrigin(sanitizeOrigin(origin))
                .orElseThrow(() -> new IllegalStateException("Site " + sanitizeOrigin(origin) + " not found"));
        site.setPages(pageService.findAll(site, View.BASIC));
        site.setCommons(commonService.findAll(site));
        return site;
    }

    public Site save(Site site) {
        findByIdOrThrow(site.getId());
        if (site.getRoles().stream().noneMatch(role -> role.getType().equals(ADMIN))) {
            throw new IllegalStateException("At least one user should be ADMIN");
        }
        userService.save(site.getRoles().stream().map(Site.Role::getUser).collect(toList()));
        siteRepository.save(site);
        return site;
    }

    public Site findByIdOrThrow(String id) {
        return siteRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Site " + id + " not found"));
    }
    
}
