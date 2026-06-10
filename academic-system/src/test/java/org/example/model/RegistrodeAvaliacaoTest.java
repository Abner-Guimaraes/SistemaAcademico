package org.example.model;

import org.example.model.*;

import org.example.exception.ExcecaoSistemaAcademico;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegistrodeAvaliacaoTest {

    @Test
    @DisplayName("CA1, CA2, CA3, CA7 - Deve cadastrar uma avaliação válida em uma turma existente")
    void deveCadastrarAvaliacaoValidaEmTurmaExistente() {
        // Given (Dado uma turma existente)
        Turma turma = new Turma("CC3A", "Orientação a Objetos");
        
        // CA2: Deve criar o objeto correspondente (Ex: Prova) com nome, nota máxima (valor) e peso
        Avaliacao prova = new Prova("Prova 1", 10.0, 0.4);

        // When (Quando a avaliação é registrada)
        turma.adicionarAvaliacao(prova);

        // Then (Então deve ser adicionada à lista da turma com seus dados)
        List<Avaliacao> avaliacoes = turma.getAvaliacoes();
        
        assertEquals(1, avaliacoes.size(), "A turma deve ter exatamente 1 avaliação");
        assertTrue(avaliacoes.contains(prova), "A avaliação cadastrada deve estar na lista");
        
        // CA3: Verificando se os valores e pesos estão guardados corretamente
        Avaliacao cadastrada = avaliacoes.get(0);
        assertEquals(10.0, cadastrada.getValor());
        assertEquals(0.4, cadastrada.getPeso());
    }

    @Test
    @DisplayName("CA6 - Deve lançar ExcecaoSistemaAcademico ao tentar cadastrar avaliação com dados inválidos")
    void deveLancarexcecaoQuandoDadosDaAvaliacaoForemInvalidos() {
        Turma turma = new Turma("CC3A", "Orientação a Objetos");

        // Testando validação de negócio básica antes do Bean Validation (nota ou peso negativos)
        assertThrows(ExcecaoSistemaAcademico.class, () -> {
            new Prova("Prova Inválida", -5.0, 0.4);
        }, "Deve rejeitar nota máxima negativa");

        assertThrows(ExcecaoSistemaAcademico.class, () -> {
            new Prova("Prova Inválida", 10.0, -0.1);
        }, "Deve rejeitar peso negativo");
    }
    
    @Test
    @DisplayName("CA2 - DeveCadastrarVariosTiposDeAvaliacoes")
    void deveCadastrarVariosTiposDeAvaliacoes() {
    	/// Escrevendo o teste com classes que ainda NÃO existem:
        Avaliacao trabalho = new TrabalhoPratico("Trabalho 1", 10.0, 0.2);
        Avaliacao seminario = new Seminario("Apresentação", 10.0, 0.1);
        Avaliacao tarefa = new Atividade("Exercício Semana 1", 10.0, 0.3);

        // Verificando se os pesos e valores foram guardados corretamente neles
        assertEquals(0.2, trabalho.getPeso());
        assertEquals(0.1, seminario.getPeso());
        assertEquals(0.3, tarefa.getPeso());
    }
}