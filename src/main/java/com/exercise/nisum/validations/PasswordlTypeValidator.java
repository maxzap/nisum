package com.exercise.nisum.validations;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordlTypeValidator implements ConstraintValidator<PasswordType, Object> {

    @Value(value = "${password.regex}")
    private String passwordRegex;


    private boolean validate(String emailStr) {
        Matcher matcher = Pattern.compile(passwordRegex, Pattern.CASE_INSENSITIVE).matcher(emailStr);
        return matcher.find();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value != null) {
            BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(value);
            String email = (String) wrapper.getWrappedInstance();
            return validate(email);
        }
        return true;
    }
}
