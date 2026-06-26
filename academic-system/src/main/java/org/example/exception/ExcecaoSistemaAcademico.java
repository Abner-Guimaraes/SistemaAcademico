package org.example.exception;

public class ExcecaoSistemaAcademico extends RuntimeException {
    public ExcecaoSistemaAcademico(String mensagem) {
        super(mensagem);
    }
}