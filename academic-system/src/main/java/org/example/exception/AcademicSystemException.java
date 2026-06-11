package org.example.exception;

public class AcademicSystemException extends RuntimeException {
    public AcademicSystemException(String mensagem) {
        super(mensagem);
    }
}