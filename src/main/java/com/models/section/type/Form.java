package com.models.section.type;

import com.models.section.Section;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Form extends Section {
    private String submitButton;
    private String emailSubject;
    private String successMessage;
    private String errorMessage;
    private List<Field> fields;

    @Data
    public static class Field {
        private String label;
        private String key;
        private String type;
        private String textAbove;
        private Model model;
        private Validation validations;
        private String options;
    }

    @Data
    public static class Model {
        String value;
    }

    @Data
    public static class Validation {
        private String regex;
        private String errorMessage;
    }
}
