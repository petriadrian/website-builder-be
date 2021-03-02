package com.models.section.type;

import com.models.section.Section;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class MenuBar extends Section {

    List<MenuBarButton> leftButtons;
    List<MenuBarButton> rightButtons;
    List<Message> messages;
    boolean fixedOnTop;

    @Data
    public static class MenuBarButton {
        String text;
        String link;
        String media;
    }

    @Data
    public static class Message {
        String key;
        String text;
        String backgroundColor;
    }
    
}

