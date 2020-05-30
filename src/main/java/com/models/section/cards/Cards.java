package com.models.section.cards;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.models.section.Section;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Cards extends Section {
    List<Item> items;
}
