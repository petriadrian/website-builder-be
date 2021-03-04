package com.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class Utils {

    public static <T> T getContentAsObject(String path, Class<T> objectType) throws IOException {
        log.info("get content " + path);
        return new ObjectMapper().readValue(getContentFile(path), objectType);
    }

    public static File getContentFile(String path) {
        return new File("content/" + path + ".json");
    }

    public static String sanitizeOrigin(String origin) {
        return origin
                .replace("www.", "")
                .replace("http://localhost:4200", "casapetrirosiamontana.ro")
                .replace("localhost", "casapetrirosiamontana.ro");
    }

    public static boolean isUserLoggedIn() {
        return !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
    }

    public static User getLoggedInUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static String getHostname(String path) {
        return path.split("/")[0];
    }

    public static String getHostnameWithLang(String path) {
        return path.split("/")[0] + "/" + path.split("/")[1];
    }

}
