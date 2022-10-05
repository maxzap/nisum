package com.exercise.nisum.validations;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordTypeValidator implements ConstraintValidator<PasswordType, Object> {

    @Value(value = "${password.regex}")
    private String passwordRegex;


    private boolean validate(String passwordStr) {
        Matcher matcher = Pattern.compile(passwordRegex, Pattern.CASE_INSENSITIVE).matcher(passwordStr);
        return matcher.find();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value != null) {
            BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(value);
            String password = (String) wrapper.getWrappedInstance();
            return validate(password);
        }
        return true;
    }
}
