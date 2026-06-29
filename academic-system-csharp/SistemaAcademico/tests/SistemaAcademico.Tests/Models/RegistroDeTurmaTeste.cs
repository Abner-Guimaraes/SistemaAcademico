using Xunit;
using SistemaAcademico.Models;
using SistemaAcademico.Services;
using SistemaAcademico.Exceptions;

namespace SistemaAcademico.Tests.Models;

// Equivalente à classe Java: RegistroDeTurmaTeste
public class RegistroDeTurmaTeste
{
    [Fact]
    // AC1, AC2, AC6 - Deve registrar uma turma com sucesso quando o usuário for ADMIN e dados forem válidos
    public void DeveRegistrarTurmaComSucesso()
    {
        // 1. DADO (Given)
        ServicoTurma service = new ServicoTurma();
        string codigoValido = "CC3A";
        string tituloValido = "Orientação a Objetos";
        string usuarioAdmin = "ADMIN";

        // 2. QUANDO (When)
        service.RegistrarTurma(codigoValido, tituloValido, usuarioAdmin);

        // 3. ENTÃO (Then)
        List<Turma> listaDeTurmas = service.ListarTurmas();

        Assert.Equal(1, listaDeTurmas.Count); // "A lista deve conter exatamente 1 turma"

        Turma turmaCadastrada = listaDeTurmas[0];
        Assert.Equal(codigoValido, turmaCadastrada.Codigo); // "O código armazenado deve ser CC3A"
        Assert.Equal(tituloValido, turmaCadastrada.Titulo); // "O título armazenado deve ser Orientação a Objetos"
    }

    [Fact]
    // AC5 - Deve impedir o registro de turma se o usuário não for ADMIN
    public void DeveLancarExcecaoQuandoUsuarioNaoForAdmin()
    {
        // 1. DADO: Um usuário que não possui a role ADMIN (ex: PROFESSOR)
        ServicoTurma service = new ServicoTurma();
        string codigo = "CC3A";
        string titulo = "Estrutura de Dados";
        string usuarioInvalido = "PROFESSOR";

        // 2. QUANDO / ENTÃO: Esperamos que o método quebre com ExcecaoAutorizacao
        Assert.Throws<ExcecaoAutorizacao>(() =>
        {
            service.RegistrarTurma(codigo, titulo, usuarioInvalido);
        }); // "Deveria ter negado a operação lançando ExcecaoAutorizacao"
    }

    [Fact]
    // AC3, AC4 - Deve rejeitar o registro se o código da turma for vazio
    public void DeveLancarExcecaoQuandoCodigoForVazio()
    {
        // 1. DADO: Um usuário ADMIN válido, mas o código da turma está em branco
        ServicoTurma service = new ServicoTurma();
        string codigoInvalido = ""; // Vazio
        string tituloValido = "Estrutura de Dados";
        string usuarioAdmin = "ADMIN";

        // 2. QUANDO / ENTÃO: O sistema deve barrar por dado inválido
        Assert.Throws<ExcecaoSistemaAcademico>(() =>
        {
            service.RegistrarTurma(codigoInvalido, tituloValido, usuarioAdmin);
        }); // "Deveria ter rejeitado o código vazio lançando ExcecaoSistemaAcademico"
    }
}
