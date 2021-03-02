package com.service;

import com.models.Page;
import com.models.Site;
import com.models.section.type.Common;
import com.repository.PageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
@AllArgsConstructor
public class PageService {

    private PageRepository pageRepository;

    public Page findOrThrow(Site site, String path) {
        Page page = pageRepository.findBySiteAndPath(site, sanitizePath(path))
                .orElseThrow(() -> new IllegalStateException("Path " + sanitizePath(path) + " was not found for site " + site.getOrigin()));
        page.setSite(site);
        return page;
    }

    public List<Page> findAll(Site site, View view) {
        switch (view) {
            case BASIC:
                return this.pageRepository.findAllBasicBySite(site);
            case ALL:
                return this.pageRepository.findAllBySite(site);
        }
        return this.pageRepository.findAllBySite(site);
    }

    public Page save(Page page, Site site) {
        if (page.getId() != null) {
            checkPagePath(page, findOrThrow(page.getId()));
        }
        page.setSite(site);
        return pageRepository.save(page);
    }

    public Page findOrThrow(String id) {
        return pageRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Page id " + id + " was not found"));
    }

    private void checkPagePath(Page toBeSavedPage, Page initialPage) {
        if (toBeSavedPage.getPath().startsWith("/")) {
            toBeSavedPage.setPath(toBeSavedPage.getPath().substring(1));
        }
        if (initialPage.isHomePage() && toBeSavedPage.isNotHomePage()) {
            throw new IllegalStateException("Path for home page can't be changed");
        }
        if (toBeSavedPage.getPath().equals("admin") || toBeSavedPage.getPath().equals("common")) {
            throw new IllegalStateException("You can't use 'admin' or 'common' as page path");
        }
        if (isEmpty(toBeSavedPage.getPath())) {
            throw new IllegalStateException("Path can't be empty");
        }
        pageRepository.findBySiteAndPathAndIdNot(toBeSavedPage.getSite(), toBeSavedPage.getPath(), toBeSavedPage.getId())
                .ifPresent(otherPageFound -> {
                    throw new IllegalStateException("Page id: " + otherPageFound.getId() + " already have the path " + toBeSavedPage.getPath() + ". Please set a unique path.");
                });
    }

    private String sanitizePath(String path) {
        path = path.startsWith("/") ? path.substring(1) : path;
        path = isEmpty(path) ? "home" : path;
        return path;
    }

    public void delete(String id) {
        pageRepository.deleteById(id);
    }

    public void deleteCommonSections(Site site, String commonId) {
        findAll(site, View.ALL).forEach(page -> {
            if (page.getSections().removeIf(section -> section instanceof Common && ((Common) section).getId().equals(commonId))) {
                pageRepository.save(page);
            }
        });
    }

    public void updateCommonSections(Site site, Common common) {
        findAll(site, View.ALL).forEach(page -> {
            page.getSections().stream()
                    .filter(section -> section instanceof Common)
                    .map(section -> (Common) section)
                    .filter(existingCommon -> existingCommon.equals(common))
                    .forEach(existingCommon -> {
                        existingCommon.setSections(common.getSections());
                        pageRepository.save(page);
                    });
        });
    }

}
