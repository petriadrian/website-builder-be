package website.builder.be.api;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;

import static java.util.Optional.ofNullable;

@RestController
public class LoadContent {

    @GetMapping("/loadContent")
    public ResponseEntity<?> loadContent(@RequestParam(value = "url") String url, HttpServletRequest request) {
        try {
            System.out.println("/loadContent url=" + url);
            System.out.println("/loadContent request.getRemoteHost=" + request.getRemoteHost());
            String contentPath = "content/" + getOriginHostname(request) + url + ".json";
            System.out.println("/loadContent fullPath=" + contentPath);
            return ResponseEntity.ok().body((JSONObject) new JSONParser().parse(
                    new FileReader(getClass().getClassLoader()
                            .getResource(contentPath)
                            .getFile())));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String getOriginHostname(HttpServletRequest request) throws MalformedURLException {
        String originUrl = new URL(getOrigin(request)).getHost();
        return originUrl.startsWith("www.") ? originUrl.substring(4) : originUrl;
    }

    private String getOrigin(HttpServletRequest request) throws MalformedURLException {
        return ofNullable(request.getHeader("origin"))
                .orElseThrow(() -> new MalformedURLException("Origin request Hostname '" + request.getHeader("origin") + "' cannot be resolved"));
    }
}
