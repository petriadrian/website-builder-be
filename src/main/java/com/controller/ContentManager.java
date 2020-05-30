package com.controller;

import com.models.configApp.ConfigApp;
import com.models.configApp.common.ConfigAppCommon;
import com.models.siteArea.AreaConfig;
import lombok.Data;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

import static com.utils.Utils.getContentAsObject;
import static com.utils.Utils.getContentFile;
import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/content")
public class ContentManager {

    private ResourceLoader resourceLoader;

    ContentManager(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    private static final Logger LOG = getLogger(ContentManager.class);

    @GetMapping("/load")
    public JSONObject loadContent(@RequestParam(value = "contentUrl") String contentUrl) throws IOException {
        LOG.info("get /content/load?contentUrl=" + contentUrl);
        return getContentAsObject(contentUrl, JSONObject.class);
    }

    @GetMapping("/load/configApp")
    public ConfigApp loadConfigApp(@RequestParam(value = "contentUrl") String contentUrl) throws URISyntaxException, IOException {
        LOG.info("get /content/load/configApp");
        ConfigAppCommon configAppCommon = getContentAsObject("/configAppCommon", ConfigAppCommon.class);
        ConfigApp configApp = getContentAsObject(contentUrl, ConfigApp.class);
        configApp.setConfigAppCommon(configAppCommon);
        return configApp;
    }

    @PutMapping("/update")
    public void update(@RequestBody ContentUpdateDto dto) throws IOException {
        String newContentPath = dto.getHostname() + "/" + dto.getAreaConfig().getLang() + "/" + dto.getAreaConfig().getPath();
        LOG.info("put /content/update initialContentPath=" + dto.getInitialContentPath() + " newContentPath=" + newContentPath);
        getContentFile(dto.getInitialContentPath()).delete();
        FileWriter file = new FileWriter(getContentFile(newContentPath));
        file.write(dto.getContent());
        file.flush();
    }

}

@Data
class ContentUpdateDto {
    private String hostname;
    private String initialContentPath;
    private AreaConfig areaConfig;
    private String content;
}
