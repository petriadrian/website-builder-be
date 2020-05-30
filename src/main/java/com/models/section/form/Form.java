package com.models.section.form;

import lombok.*;
import com.models.section.Section;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Form extends Section {
    private String submitButton;
    private String emailSubject;
    private String successMessage;
    private String errorMessage;
    private List<Field> fields;
}
