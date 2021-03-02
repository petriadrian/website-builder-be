package com.models.section.type;

import com.models.section.Section;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Article extends Section {
    String anchorId;
    String title;
    String text;
    String media;
    List<Button> buttons = new ArrayList<>();

    @Data
    public static class Button {
        String text;
        String link;
    }
}

