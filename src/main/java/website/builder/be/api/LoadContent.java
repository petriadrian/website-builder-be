package website.builder.be.api;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.web.server.WebServerException;
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
    public JSONObject loadContent(@RequestParam(value = "url") String url, HttpServletRequest request) {
        try {
            String contentPath = "content/" + getOriginHostname(request) + "/" + url + ".json";
            System.out.println("/loadContent=" + contentPath);
            return (JSONObject) new JSONParser().parse(
                    new FileReader(getClass().getClassLoader()
                            .getResource(contentPath)
                            .getFile()));
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    private String getOriginHostname(HttpServletRequest request) {
        return ofNullable(request.getHeader("origin"))
                .map(url -> {
                    try {
                        return new URL(url).getHost().startsWith("www.") ? url.substring(4) : url;
                    } catch (MalformedURLException e) {
                        throw buildException(request, e);
                    }

                })
                .orElseThrow(()->buildException(request, new MalformedURLException()));
    }

    private RuntimeException buildException(HttpServletRequest request, MalformedURLException e) {
        return new WebServerException("Origin request Hostname ' " + request.getHeader("origin") + "' cannot be resolved", e);
    }
}
