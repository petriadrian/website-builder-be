package com.service;

import com.models.Site;
import com.models.section.type.Common;
import com.repository.CommonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommonService {

    private CommonRepository commonRepository;
    private PageService pageService;

    public Common findOrThrow(Site site, String id) {
        return commonRepository.findBySiteAndId(site, id)
                .orElseThrow(() -> new IllegalStateException("Common id " + id + " for site " + site.getOrigin() + " not found"));
    }

    public List<Common> findAll(Site site) {
        return commonRepository.findAllBySite(site);
    }

    public Common save(Site site, Common common) {
        if (common.getId() == null) {
            common.setSite(site);
        } else {
            pageService.updateCommonSections(site, common);
        }
        return commonRepository.save(common);
    }

    public void delete(Site site, String commonId) {
        pageService.deleteCommonSections(site, commonId);
        commonRepository.delete(findOrThrow(site, commonId));
    }
}
