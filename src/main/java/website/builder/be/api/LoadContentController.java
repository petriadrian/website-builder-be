package website.builder.be.api;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileReader;

@RestController
public class LoadContentController {

    @GetMapping("/api/loadContent")
    public JSONObject loadContent(@RequestParam(value = "url") String url) {
        System.out.println("/loadContent=" + url);
        try {
            return (JSONObject) new JSONParser().parse(
                    new FileReader(getClass().getClassLoader()
                            .getResource("content/" + url + ".json").getFile()));
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }
}
