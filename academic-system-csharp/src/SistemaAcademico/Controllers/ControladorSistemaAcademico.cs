using SistemaAcademico.Models;
using SistemaAcademico.Services;

namespace SistemaAcademico.Controllers;

// Equivalente à classe Java: ControladorSistemaAcademico
// Padrão de projeto: Facade (fachada sobre os serviços) — preservado
// Injeção de dependência via construtor — preservada
public class ControladorSistemaAcademico
{
    private readonly ServicoTurma servicoTurma;
    private readonly ServicoAvaliacao servicoAvaliacao;
    private readonly ServicoRelatorio servicoRelatorio;
    private readonly ServicoPersistencia servicoPersistencia;
    private readonly ServicoSeguranca servicoSeguranca;

    public ControladorSistemaAcademico(
        ServicoTurma servicoTurma,
        ServicoAvaliacao servicoAvaliacao,
        ServicoRelatorio servicoRelatorio,
        ServicoPersistencia servicoPersistencia,
        ServicoSeguranca servicoSeguranca)
    {
        this.servicoTurma = servicoTurma;
        this.servicoAvaliacao = servicoAvaliacao;
        this.servicoRelatorio = servicoRelatorio;
        this.servicoPersistencia = servicoPersistencia;
        this.servicoSeguranca = servicoSeguranca;
    }

    public Usuario Autenticar(string username, string password)
    {
        return servicoSeguranca.Autenticar(username, password);
    }

    public void Logout(string username)
    {
        servicoSeguranca.Logout(username);
    }

    public void RegistrarTurma(string codigo, string titulo, string usuarioLogado)
    {
        servicoTurma.RegistrarTurma(codigo, titulo, usuarioLogado);
    }

    public List<Turma> ListarTurmas()
    {
        return servicoTurma.ListarTurmas();
    }

    public void RegistrarAvaliacao(string codigoTurma, string nome, string tipo, double valor, double peso, string usuarioLogado)
    {
        servicoAvaliacao.RegistrarAvaliacao(codigoTurma, nome, tipo, valor, peso, usuarioLogado);
    }

    public void ConfigurarPersistencia(string tipo, string usuarioLogado)
    {
        servicoPersistencia.ConfigurarPersistencia(tipo, usuarioLogado);
    }

    public void SalvarDados(string usuarioLogado)
    {
        servicoPersistencia.SalvarDados(ListarTurmas(), usuarioLogado);
    }

    public string GerarResumoAvaliacoes(string usuarioLogado)
    {
        return servicoRelatorio.GerarResumoAvaliacoes(ListarTurmas(), usuarioLogado);
    }

    public string GerarRelatorioPesos(string usuarioLogado)
    {
        return servicoRelatorio.GerarRelatorioPesos(ListarTurmas(), usuarioLogado);
    }

    public string GerarRelatorioPersistencia(string usuarioLogado)
    {
        return servicoRelatorio.GerarRelatorioPersistencia(servicoPersistencia.GetTipoPersistencia(), usuarioLogado);
    }
}
