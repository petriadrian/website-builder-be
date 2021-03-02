package com;

import com.models.Page;
import com.models.Site;
import com.models.User;
import com.repository.PageRepository;
import com.repository.SiteRepository;
import com.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.List;

import static com.models.Site.Role.Type.ADMIN;

@EnableConfigurationProperties
@EnableMongoRepositories
@EnableMongoAuditing
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(App.class, args);
        init(context.getBean(SiteRepository.class), context.getBean(PageRepository.class), context.getBean(UserRepository.class));
    }

    private static void init(SiteRepository siteRepository, PageRepository pageRepository, UserRepository userRepository) {
        User user = userRepository.findByEmail("casapetrirosiamontana@gmail.com")
                .orElseGet(() -> userRepository.save(
                        User.builder()
                                .email("casapetrirosiamontana@gmail.com")
                                .build()));
        Site site = siteRepository.findByOrigin("www.casapetrirosiamontana.ro")
                .orElseGet(() -> siteRepository.save(
                        Site.builder()
                                .origin("www.casapetrirosiamontana.ro")
                                .roles(List.of(Site.Role.builder()
                                        .user(user)
                                        .type(ADMIN)
                                        .build()))
                                .style(Site.Style.builder().primaryColor("").secondColor("").build())
                                .facebook(Site.Facebook.builder().pageId("").welcomeMessage("").build())
                                .googleAnalyticsTrackingId("")
                                .build()));
        pageRepository.findBySiteAndPath(site, "home")
                .orElseGet(() -> pageRepository.save(
                        Page.builder()
                                .site(site)
                                .path("home")
                                .metadata(Page.Metadata.builder().title("").text("").media("").build())
                                .sections(new ArrayList<>())
                                .build()));
    }

}
