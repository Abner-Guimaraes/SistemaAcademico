package org.example.service;

import org.example.model.Turma;
import org.example.model.Prova;
import org.example.exception.ExcecaoAutorizacao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoPersistenciaTeste {

    @Test
    @DisplayName("US-2372: AC1, AC4 - Deve alterar persistencia apenas se for ADMIN")
    void testConfigurarPersistencia() {
        ServicoPersistencia servico = new ServicoPersistencia();
        
        // ADMIN sucess
        servico.configurarPersistencia("XML", "ADMIN");
        assertEquals("XML", servico.getTipoPersistencia());

        // PROFESSOR fail
        assertThrows(ExcecaoAutorizacao.class, () -> {
            servico.configurarPersistencia("JSON", "PROFESSOR");
        });
    }

    @Test
    @DisplayName("US-2373, US-2374: AC5 - O salvamento deve utilizar a persistência configurada")
    void testSalvarDados() {
        ServicoPersistencia servico = new ServicoPersistencia();
        servico.configurarPersistencia("JSON", "ADMIN");
        
        List<Turma> turmas = new ArrayList<>();
        Turma t = new Turma("CC3A", "POO");
        t.adicionarAvaliacao(new Prova("P1", 10.0, 0.5));
        turmas.add(t);
        
        assertDoesNotThrow(() -> {
            servico.salvarDados(turmas, "ADMIN");
        });
    }
}
