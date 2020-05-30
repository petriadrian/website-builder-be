package com.models.section.form;

import lombok.*;

@Data
public class Validation {
    private String requiredErrorMessage;
    private String regex;
    private String regexErrorMessage;
}
