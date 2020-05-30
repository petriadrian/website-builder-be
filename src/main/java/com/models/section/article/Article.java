package com.models.section.article;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.models.section.Section;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Article extends Section {
    String id;
    String title;
    String text;
    String media;
    List<Button> buttons;
}

