package website.builder.be.api;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileReader;
import java.util.Map;

@RestController
public class LoadContent {

    @GetMapping("/loadContent")
    public ResponseEntity<JSONObject> loadContent(@RequestParam(value = "url") String url) {
        try {
            System.out.println("/loadContent url=" + url);
            return ResponseEntity.ok().body((JSONObject) new JSONParser().parse(
                    new FileReader(getClass().getClassLoader()
                            .getResource("content/" + url + ".json")
                            .getFile())));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new JSONObject(Map.of("error", e.getMessage())));
        }
    }
}
