package com.config;

import com.models.Site;
import com.utils.Utils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static javax.validation.constraintvalidation.ValidationTarget.PARAMETERS;

@Documented
@Target(value = {METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = HasRole.RoleValidator.class)
public @interface HasRole {

    Site.Role.Type type();
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String message() default "You are not allowed to do this action.";

    @SupportedValidationTarget({PARAMETERS})
    class RoleValidator implements ConstraintValidator<HasRole, Object[]> {

        Site.Role.Type type;

        @Override
        public void initialize(HasRole constraintAnnotation) {
            this.type = constraintAnnotation.type();
        }

        @Override
        public boolean isValid(Object[] parameters, ConstraintValidatorContext constraintValidatorContext) {
            return Utils.getLoggedInUser().getRole().equals(type);
        }


    }
}
