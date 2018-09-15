package com.example.demo.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.SimpleDateFormat;

public class EcDateValidator implements ConstraintValidator<EcDate, CharSequence> {
    private String pattern;

    @Override
    public void initialize(EcDate parameters) {
        this.pattern = parameters.pattern();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }

        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            format.parse(value.toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
