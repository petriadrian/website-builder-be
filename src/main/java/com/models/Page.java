package com.models;

import com.models.section.Section;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@CompoundIndex(def = "{'site': 1, 'path': 1}", unique = true)
public class Page {
    @Id
    private String id;
    @DBRef
    private Site site;
    private String path;
    private Metadata metadata;
    private List<? extends Section> sections;
    @CreatedDate
    private LocalDateTime createdDate;

    @Data
    @Builder
    @AllArgsConstructor
    public static class Metadata {
        private String title;
        private String text;
        private String media;
    }

    public boolean isHomePage() {
        return this.path.equals("home");
    }

    public boolean isNotHomePage() {
        return !this.isHomePage();
    }
}
