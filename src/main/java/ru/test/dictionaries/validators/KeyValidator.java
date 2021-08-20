package ru.test.dictionaries.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class KeyValidator implements ConstraintValidator<Key,String> {
    @Override
    public void initialize(Key constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
