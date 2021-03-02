package com.models.section;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.models.section.type.*;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Article.class, name = "article"),
        @JsonSubTypes.Type(value = MenuBar.class, name = "menubar"),
        @JsonSubTypes.Type(value = Cards.class, name = "cards"),
        @JsonSubTypes.Type(value = Carousel.class, name = "carousel"),
        @JsonSubTypes.Type(value = Form.class, name = "form"),
        @JsonSubTypes.Type(value = Common.class, name = "common"),
})
@Data
public abstract class Section {
    String type;

    public boolean isCommon() {
        return type.equalsIgnoreCase("common");
    }
}
