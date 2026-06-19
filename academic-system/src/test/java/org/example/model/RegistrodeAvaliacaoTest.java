package org.example.model;

import org.example.exception.AcademicSystemException;
import org.example.exception.AuthorizationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegistrodeAvaliacaoTest {

	
	//Tests: US-2361
    @Test
    @DisplayName("CA1, CA3, CA7 - Deve cadastrar uma avaliação válida em uma turma existente")
    void deveCadastrarAvaliacaoValidaEmTurmaExistente() {
        Turma turma = new Turma("CC3A", "Orientação a Objetos");
        Avaliacao prova = new Prova("Prova 1", 10.0, 0.4);

        turma.adicionarAvaliacao(prova);

        List<Avaliacao> avaliacoes = turma.getAvaliacoes();
        assertEquals(1, avaliacoes.size(), "A turma deve ter exatamente 1 avaliação");
        assertTrue(avaliacoes.contains(prova), "A avaliação cadastrada deve estar na lista");
        
        Avaliacao cadastrada = avaliacoes.get(0);
        assertEquals(10.0, cadastrada.getValor());
        assertEquals(0.4, cadastrada.getPeso());
    }

    @Test
    @DisplayName("CA6 - Deve lançar AcademicSystemException ao tentar cadastrar avaliação com dados inválidos")
    void deveLancarexcecaoQuandoDadosDaAvaliacaoForemInvalidos() {
        assertThrows(org.example.exception.AcademicSystemException.class, () -> {
            org.example.validation.DomainValidator.validate(new Prova("Prova Inválida", -5.0, 0.4));
        }, "Deve rejeitar nota máxima negativa");

        assertThrows(org.example.exception.AcademicSystemException.class, () -> {
            org.example.validation.DomainValidator.validate(new Prova("Prova Inválida", 10.0, -0.1));
        }, "Deve rejeitar peso negativo");
    }
    
    @Test
    @DisplayName("CA2 - Deve criar o objeto correto quando o tipo de avaliação é selecionado por texto")
    void deveCriarObjetoDeAcordoComTipoSelecionado() {
        Avaliacao trabalho = AvaliacaoFactory.criar("Trabalho Prático", "Trabalho 1", 10.0, 0.2);
        Avaliacao seminario = AvaliacaoFactory.criar("Seminário", "Apresentação", 10.0, 0.1);
        Avaliacao tarefa = AvaliacaoFactory.criar("Atividade", "Exercício Semana 1", 10.0, 0.3);
        Avaliacao prova = AvaliacaoFactory.criar("Prova", "P1", 10.0, 0.4);

        assertInstanceOf(TrabalhoPratico.class, trabalho);
        assertInstanceOf(Seminario.class, seminario);
        assertInstanceOf(Atividade.class, tarefa);
        assertInstanceOf(Prova.class, prova);
    }

    @Test
    @DisplayName("CA5 - Deve lançar AcademicSystemException quando o tipo de avaliação selecionado for inválido")
    void deveLancarExcecaoParaTipoAvaliacaoInvalido() {
        assertThrows(org.example.exception.AcademicSystemException.class, () -> {
            AvaliacaoFactory.criar("Redacao", "Enem", 10.0, 0.5);
        }, "Deve rejeitar tipos que não sejam os quatro oficiais");
    }

    @Test
    @DisplayName("CA4 - Deve lançar exceção ao tentar registrar avaliação em uma turma inexistente")
    void naoDeveRegistrarAvaliacaoEmTurmaInexistente() {
        GerenciadorDeTurmas gerenciador = new GerenciadorDeTurmas();
        Avaliacao prova = AvaliacaoFactory.criar("Prova", "P1", 10.0, 0.4);

        assertThrows(AcademicSystemException.class, () -> {
            gerenciador.registrarAvaliacao("CODIGO_INEXISTENTE", prova, "PROFESSOR");
        }, "Deve rejeitar registro em turma inexistente");
    }

    @Test
    @DisplayName("CA8 - Deve negar a operação se o usuário não for um PROFESSOR")
    void deveNegarRegistroSemPrivilegio() {
        GerenciadorDeTurmas gerenciador = new GerenciadorDeTurmas();
        gerenciador.salvarTurma(new Turma("CC3A", "Orientação a Objetos"));
        
        Avaliacao prova = AvaliacaoFactory.criar("Prova", "P1", 10.0, 0.4);

        assertThrows(AuthorizationException.class, () -> {
            gerenciador.registrarAvaliacao("CC3A", prova, "ALUNO");
        }, "Usuários sem privilégio não podem registrar avaliações");
    }
}