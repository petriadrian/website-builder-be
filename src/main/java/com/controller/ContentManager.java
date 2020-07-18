package com.controller;

import com.models.configApp.ConfigApp;
import com.models.configApp.common.Common;
import com.models.siteArea.AreaConfig;
import lombok.Data;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

import static com.utils.Utils.*;
import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/content")
public class ContentManager {

    private static final Logger LOG = getLogger(ContentManager.class);

    @GetMapping("/load")
    public JSONObject loadContent(@RequestParam(value = "contentUrl") String contentUrl) throws IOException {
        LOG.info("get /content/load?contentUrl=" + contentUrl);
        return getContentAsObject(contentUrl, JSONObject.class);
    }

    @GetMapping("/load/configApp")
    public ConfigApp loadConfigApp(@RequestParam(value = "contentUrl") String contentUrl) throws URISyntaxException, IOException {
        LOG.info("get /content/load/configApp");
        Common common = getContentAsObject("/configAppCommon", Common.class);
        ConfigApp configApp = getContentAsObject(contentUrl, ConfigApp.class);
        configApp.setCommon(common);
        return configApp;
    }

    @PutMapping("/update")
    public void update(@RequestBody ContentUpdateDto dto) throws IOException {
        String newContentPath = getHostname(dto.getInitialContentPath()) + "/" + dto.getSiteBodyAreaConfig().getLang() + "/" + dto.getSiteBodyAreaConfig().getPath();
        LOG.info("put /content/update initialContentPath=" + dto.getInitialContentPath() + " newContentPath=" + newContentPath);
        updateFile(dto.getInitialContentPath(), newContentPath, dto.getSiteBodyContent());

        String headerContentPath = getHostnameWithLang(dto.getInitialContentPath()) + "/header";
        updateFile(headerContentPath, dto.getHeader());
        String footerContentPath = getHostnameWithLang(dto.getInitialContentPath()) + "/footer";
        updateFile(footerContentPath, dto.getFooter());
        String configContentPath = getHostname(dto.getInitialContentPath()) + "/configApp";
        updateFile(configContentPath, dto.getConfigApp());

    }

    private void updateFile(String filePath, String fileContent) throws IOException {
        updateFile(filePath, filePath, fileContent);
    }

    private void updateFile(String initialFilePath, String newFilePath, String fileContent) throws IOException {
        getContentFile(initialFilePath).delete();
        FileWriter file = new FileWriter(getContentFile(newFilePath));
        file.write(fileContent);
        file.flush();
    }

}

@Data
class ContentUpdateDto {
    private String initialContentPath;
    private String header;
    private String footer;
    private String configApp;
    private AreaConfig siteBodyAreaConfig;
    private String siteBodyContent;
}
