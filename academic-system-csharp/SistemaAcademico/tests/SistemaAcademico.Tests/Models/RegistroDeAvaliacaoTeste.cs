using Xunit;
using SistemaAcademico.Models;
using SistemaAcademico.Exceptions;
using SistemaAcademico.Validation;

namespace SistemaAcademico.Tests.Models;

// Equivalente à classe Java: RegistroDeAvaliacaoTeste
// JUnit 5 @Test → [Fact] xUnit
// JUnit 5 @DisplayName → comentário XML <summary> (não há equivalente direto em xUnit;
//   usamos o nome do método que é preservado)
// assertEquals → Assert.Equal
// assertTrue → Assert.True
// assertThrows → Assert.Throws<T>
// assertInstanceOf → Assert.IsType<T>
public class RegistroDeAvaliacaoTeste
{
    // Tests: US-2361
    [Fact]
    // CA1, CA3, CA7 - Deve cadastrar uma avaliação válida em uma turma existente
    public void DeveCadastrarAvaliacaoValidaEmTurmaExistente()
    {
        Turma turma = new Turma("CC3A", "Orientação a Objetos");
        Avaliacao prova = new Prova("Prova 1", 10.0, 0.4);

        turma.AdicionarAvaliacao(prova);

        List<Avaliacao> avaliacoes = turma.GetAvaliacoes();
        Assert.Equal(1, avaliacoes.Count); // "A turma deve ter exatamente 1 avaliação"
        Assert.Contains(prova, avaliacoes); // "A avaliação cadastrada deve estar na lista"

        Avaliacao cadastrada = avaliacoes[0];
        Assert.Equal(10.0, cadastrada.Valor);
        Assert.Equal(0.4, cadastrada.Peso);
    }

    [Fact]
    // CA6 - Deve lançar ExcecaoSistemaAcademico ao tentar cadastrar avaliação com dados inválidos
    public void DeveLancarExcecaoQuandoDadosDaAvaliacaoForemInvalidos()
    {
        Assert.Throws<ExcecaoSistemaAcademico>(() =>
        {
            ValidadorDominio.Validate(new Prova("Prova Inválida", -5.0, 0.4));
        }); // "Deve rejeitar nota máxima negativa"

        Assert.Throws<ExcecaoSistemaAcademico>(() =>
        {
            ValidadorDominio.Validate(new Prova("Prova Inválida", 10.0, -0.1));
        }); // "Deve rejeitar peso negativo"
    }

    [Fact]
    // CA2 - Deve criar o objeto correto quando o tipo de avaliação é selecionado por texto
    public void DeveCriarObjetoDeAcordoComTipoSelecionado()
    {
        Avaliacao trabalho = AvaliacaoFactory.Criar("Trabalho Prático", "Trabalho 1", 10.0, 0.2);
        Avaliacao seminario = AvaliacaoFactory.Criar("Seminário", "Apresentação", 10.0, 0.1);
        Avaliacao tarefa = AvaliacaoFactory.Criar("Atividade", "Exercício Semana 1", 10.0, 0.3);
        Avaliacao prova = AvaliacaoFactory.Criar("Prova", "P1", 10.0, 0.4);

        Assert.IsType<TrabalhoPratico>(trabalho);
        Assert.IsType<Seminario>(seminario);
        Assert.IsType<Atividade>(tarefa);
        Assert.IsType<Prova>(prova);
    }

    [Fact]
    // CA5 - Deve lançar ExcecaoSistemaAcademico quando o tipo de avaliação selecionado for inválido
    public void DeveLancarExcecaoParaTipoAvaliacaoInvalido()
    {
        Assert.Throws<ExcecaoSistemaAcademico>(() =>
        {
            AvaliacaoFactory.Criar("Redacao", "Enem", 10.0, 0.5);
        }); // "Deve rejeitar tipos que não sejam os quatro oficiais"
    }

    [Fact]
    // CA4 - Deve lançar exceção ao tentar registrar avaliação em uma turma inexistente
    public void NaoDeveRegistrarAvaliacaoEmTurmaInexistente()
    {
        GerenciadorDeTurmas gerenciador = new GerenciadorDeTurmas();
        Avaliacao prova = AvaliacaoFactory.Criar("Prova", "P1", 10.0, 0.4);

        Assert.Throws<ExcecaoSistemaAcademico>(() =>
        {
            gerenciador.RegistrarAvaliacao("CODIGO_INEXISTENTE", prova, "PROFESSOR");
        }); // "Deve rejeitar registro em turma inexistente"
    }

    [Fact]
    // CA8 - Deve negar a operação se o usuário não for um PROFESSOR
    public void DeveNegarRegistroSemPrivilegio()
    {
        GerenciadorDeTurmas gerenciador = new GerenciadorDeTurmas();
        gerenciador.SalvarTurma(new Turma("CC3A", "Orientação a Objetos"));

        Avaliacao prova = AvaliacaoFactory.Criar("Prova", "P1", 10.0, 0.4);

        Assert.Throws<ExcecaoAutorizacao>(() =>
        {
            gerenciador.RegistrarAvaliacao("CC3A", prova, "ALUNO");
        }); // "Usuários sem privilégio não podem registrar avaliações"
    }
}
