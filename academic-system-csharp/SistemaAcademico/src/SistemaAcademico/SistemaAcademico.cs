using SistemaAcademico.Controllers;
using SistemaAcademico.Repositories;
using SistemaAcademico.Services;

namespace SistemaAcademico;

// Equivalente à classe Java: SistemaAcademico
// Padrão de projeto: Singleton (preservado com lock para thread-safety, equivalente ao synchronized Java)
public class SistemaAcademico
{
    private static SistemaAcademico? instance;
    private static readonly object lockObj = new object();

    private ServicoTurma servicoTurma = null!;
    private ServicoAvaliacao servicoAvaliacao = null!;
    private ServicoRelatorio servicoRelatorio = null!;
    private ServicoPersistencia servicoPersistencia = null!;
    private ServicoSeguranca servicoSeguranca = null!;
    private ControladorSistemaAcademico controladorSistemaAcademico = null!;

    private SistemaAcademico()
    {
        InicializarComponentes();
    }

    // Equivalente ao synchronized getInstance() Java → lock em C#
    public static SistemaAcademico GetInstance()
    {
        if (instance == null)
        {
            lock (lockObj)
            {
                if (instance == null)
                {
                    instance = new SistemaAcademico();
                }
            }
        }
        return instance;
    }

    private void InicializarComponentes()
    {
        this.servicoTurma = new ServicoTurma();
        this.servicoAvaliacao = new ServicoAvaliacao(this.servicoTurma);
        this.servicoRelatorio = new ServicoRelatorio();
        this.servicoPersistencia = new ServicoPersistencia();
        this.servicoSeguranca = new ServicoSeguranca(new RepositorioUsuarioTxt("users.txt"));
        this.controladorSistemaAcademico = new ControladorSistemaAcademico(
            this.servicoTurma,
            this.servicoAvaliacao,
            this.servicoRelatorio,
            this.servicoPersistencia,
            this.servicoSeguranca
        );
    }

    public ControladorSistemaAcademico GetAcademicSystemController()
    {
        return controladorSistemaAcademico;
    }
}
