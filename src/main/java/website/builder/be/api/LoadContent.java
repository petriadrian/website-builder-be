package website.builder.be.api;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.FileReader;

@RestController
public class LoadContent {

    @GetMapping("/loadContent")
    public JSONObject loadContent(@RequestParam(value = "url") String url, HttpServletRequest request) {
        System.out.println("/loadContent=" + url);
        System.out.println("request.getServerName()=" + request.getServerName());
        try {
            return (JSONObject) new JSONParser().parse(
                    new FileReader(getClass().getClassLoader()
                            .getResource("content/" + request.getServerName() + "/" + url + ".json").getFile()));
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }
}
