package com.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.http.HttpHeaders.ORIGIN;

@Service
public class Utils {

    public static <T> T getContentAsObject(String path, Class<T> objectType) throws IOException {
        return new ObjectMapper().readValue(getContentFile(path), objectType);
    }

    public static File getContentFile(String path) throws MalformedURLException {
        path = path.replace("localhost", "casapetrirosiamontana.ro");
        return new File( "content/" + path + ".json");
    }

    public static String getDomainFromOrigin(HttpServletRequest request) throws URISyntaxException {
        return getDomainFromOrigin(request.getHeader(ORIGIN));
    }


    public static String getDomainFromOrigin(String originHeader) throws URISyntaxException {
        String domain = new URI(originHeader).getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }

    public static String getHostname(String path) {
        return path.split("/")[0];
    }

    public static String getHostnameWithLang(String path) {
        return path.split("/")[0] + "/" + path.split("/")[1];
    }

}
