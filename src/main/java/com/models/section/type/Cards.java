package com.models.section.type;

import com.models.section.Section;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Cards extends Section {
    List<Item> items = new ArrayList<>();

    @Data
    public static class Item {
        String title;
        String text;
        String link;
        String media;
    }
}
