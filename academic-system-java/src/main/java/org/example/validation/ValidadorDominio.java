package org.example.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.example.exception.ExcecaoSistemaAcademico;

import java.util.Set;
import java.util.stream.Collectors;

public class ValidadorDominio {
    private static final Validator validator;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public static <T> void validate(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        
        if (!violations.isEmpty()) {
            String errorMessages = violations.stream()
                .map(v -> v.getPropertyPath() + " " + v.getMessage())
                .collect(Collectors.joining(", "));
            throw new ExcecaoSistemaAcademico("Erro de validação: " + errorMessages);
        }
    }
}
