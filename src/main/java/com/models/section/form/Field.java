package com.models.section.form;

import lombok.Data;

@Data
public class Field {
    private String label;
    private String key;
    private String type;
    private String textAbove;
    private Model model;
    private Validation validations;
    private String options;
}
