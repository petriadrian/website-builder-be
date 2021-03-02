package com.models.section.type;

import com.models.section.Section;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Carousel extends Section {
    List<Article> items;
}
