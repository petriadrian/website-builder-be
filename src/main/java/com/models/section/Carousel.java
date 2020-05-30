package com.models.section;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.models.section.article.Article;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Carousel extends Section {
    List<Article> items;
}
