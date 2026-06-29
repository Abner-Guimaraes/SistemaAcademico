using SistemaAcademico.Exceptions;

namespace SistemaAcademico.Models;

// Equivalente à classe Java: AvaliacaoFactory
// Padrão de projeto: Factory (preservado integralmente)
public class AvaliacaoFactory
{
    // Método estático equivalente ao Java static: chamado diretamente pela classe
    public static Avaliacao Criar(string tipo, string nome, double valor, double peso)
    {
        switch (tipo)
        {
            case "Prova":
                return new Prova(nome, valor, peso);

            case "Trabalho Prático":
                return new TrabalhoPratico(nome, valor, peso);

            case "Seminário":
                return new Seminario(nome, valor, peso);

            case "Atividade":
                return new Atividade(nome, valor, peso);

            default:
                // AC5: Se o tipo digitado não for nenhum dos 4 acima, lança exceção
                throw new ExcecaoSistemaAcademico("Tipo de avaliação inválido: " + tipo);
        }
    }
}
