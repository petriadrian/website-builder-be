package com.models;

import com.models.section.type.Common;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Site {
    @Id
    private String id;
    @Indexed(unique = true)
    private String origin;
    private Style style;
    private String googleAnalyticsTrackingId;
    private Facebook facebook;
    private List<Role> roles;
    @Transient
    private List<Common> commons;
    @Transient
    private List<Page> pages;

    @CreatedDate
    private LocalDateTime createdDate;

    @Data
    @Builder
    public static class Style {
        private String primaryColor;
        private String secondColor;
    }

    @Data
    @Builder
    public static class Facebook {
        private String welcomeMessage;
        private String pageId;
    }

    @Data
    @Builder
    public static class Role {
        @DBRef
        private User user;
        private Role.Type type;

        public enum Type {
            ADMIN, PAGE_MANAGER
        }
    }

    public Site.Role.Type getRoleOrThrowFor(User user) {
        return roles.stream()
                .filter(role -> role.getUser().equals(user))
                .map(Site.Role::getType)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("User " + user.getEmail() + " has no role on " + origin));
    }

}
