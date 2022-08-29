package com.exercise.nisum.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = EmailTypeValidator.class)
@Documented
public @interface EmailType {
    String message() default "No es un tipo de correo valido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
