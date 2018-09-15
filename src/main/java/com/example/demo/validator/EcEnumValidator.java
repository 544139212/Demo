package com.example.demo.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class EcEnumValidator implements ConstraintValidator<EcEnum, Object> {

    private Class<? extends Enum<?>> clazz;
    private String method;

    @Override
    public void initialize(EcEnum parameters) {
        this.method = parameters.method();
        this.clazz = parameters.clazz();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return Boolean.TRUE;
        }

        if (clazz == null || method == null) {
            return Boolean.TRUE;
        }

        Class<?> clazz = value.getClass();

        try {
            Method method = this.clazz.getMethod(this.method, clazz);
            if (!Boolean.TYPE.equals(method.getReturnType()) && !Boolean.class.equals(method.getReturnType())) {
                throw new RuntimeException(String.format("%s method return is not boolean type in the %s class", this.method, this.clazz));
            }

            if(!Modifier.isStatic(method.getModifiers())) {
                throw new RuntimeException(String.format("%s method is not static method in the %s class", this.method, this.clazz));
            }

            Boolean result = (Boolean)method.invoke(null, value);
            return result == null ? false : result;
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(String.format("This %s(%s) method does not exist in the %s", this.method, clazz, this.clazz), e);
        }
    }
}
