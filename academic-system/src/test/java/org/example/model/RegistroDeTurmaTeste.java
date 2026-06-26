package org.example.model;

import org.example.service.ServicoTurma;
import org.example.exception.ExcecaoSistemaAcademico;
import org.example.exception.ExcecaoAutorizacao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RegistroDeTurmaTeste {
	
    @Test
    @DisplayName("AC1, AC2, AC6 - Deve registrar uma turma com sucesso quando o usuário for ADMIN e dados forem válidos")
    void deveRegistrarTurmaComSucesso() {
        
        // 1. DADO (Given)
        ServicoTurma service = new ServicoTurma(); 
        String codigoValido = "CC3A";
        String tituloValido = "Orientação a Objetos";
        String usuarioAdmin = "ADMIN"; 

        // 2. QUANDO (When)
        service.registrarTurma(codigoValido, tituloValido, usuarioAdmin);

        // 3. ENTÃO (Then)
        List<Turma> listaDeTurmas = service.listarTurmas();
        
        assertEquals(1, listaDeTurmas.size(), "A lista deve conter exatamente 1 turma");
        
        Turma turmaCadastrada = listaDeTurmas.get(0);
        assertEquals(codigoValido, turmaCadastrada.getCodigo(), "O código armazenado deve ser CC3A");
        assertEquals(tituloValido, turmaCadastrada.getTitulo(), "O título armazenado deve ser Orientação a Objetos");
    }
    
    @Test
    @DisplayName("AC5 - Deve impedir o registro de turma se o usuário não for ADMIN")
    void deveLancarExcecaoQuandoUsuarioNaoForAdmin() {
        // 1. DADO: Um usuário que não possui a role ADMIN (ex: PROFESSOR)
        ServicoTurma service = new ServicoTurma();
        String codigo = "CC3A";
        String titulo = "Estrutura de Dados";
        String usuarioInvalido = "PROFESSOR"; 

        // 2. QUANDO / ENTÃO: Esperamos que o método quebre com uma ExcecaoAutorizacao
        assertThrows(ExcecaoAutorizacao.class, () -> {
            service.registrarTurma(codigo, titulo, usuarioInvalido);
        }, "Deveria ter negado a operação lançando ExcecaoAutorizacao");
    }
    
    @Test
    @DisplayName("AC3, AC4 - Deve rejeitar o registro se o código da turma for vazio")
    void deveLancarExcecaoQuandoCodigoForVazio() {
        // 1. DADO: Um usuário ADMIN válido, mas o código da turma está em branco
        ServicoTurma service = new ServicoTurma();
        String codigoInvalido = ""; // Vazio
        String tituloValido = "Estrutura de Dados";
        String usuarioAdmin = "ADMIN";

        // 2. QUANDO / ENTÃO: O sistema deve barrar por dado inválido
        assertThrows(ExcecaoSistemaAcademico.class, () -> {
            service.registrarTurma(codigoInvalido, tituloValido, usuarioAdmin);
        }, "Deveria ter rejeitado o código vazio lançando ExcecaoSistemaAcademico");
    }
}