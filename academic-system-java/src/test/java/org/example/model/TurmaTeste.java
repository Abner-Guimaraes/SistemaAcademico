package org.example.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TurmaTeste {

    @Test
    @DisplayName("TUS-2384: Deve considerar turmas iguais se possuirem o mesmo código")
    void testIgualdadePorCodigo() {
        Turma turma1 = new Turma("CC3A", "Orientação a Objetos");
        Turma turma2 = new Turma("CC3A", "Algoritmos e Estruturas de Dados");
        
        assertEquals(turma1, turma2, "Turmas com o mesmo código devem ser iguais");
        assertEquals(turma1.hashCode(), turma2.hashCode(), "Turmas com o mesmo código devem ter o mesmo hashCode");
    }

    @Test
    @DisplayName("TUS-2384: Deve considerar turmas diferentes se possuirem códigos diferentes")
    void testDesigualdadePorCodigo() {
        Turma turma1 = new Turma("CC3A", "Orientação a Objetos");
        Turma turma2 = new Turma("CC3B", "Orientação a Objetos");
        
        assertNotEquals(turma1, turma2, "Turmas com códigos diferentes devem ser diferentes");
    }
}
