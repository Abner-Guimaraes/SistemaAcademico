using System.ComponentModel.DataAnnotations;
using SistemaAcademico.Exceptions;

namespace SistemaAcademico.Validation;

// Equivalente à classe Java: ValidadorDominio
// Jakarta Validation (Validator/ValidatorFactory) → System.ComponentModel.DataAnnotations (Validator)
// A lógica é idêntica: validar o objeto contra suas anotações e lançar ExcecaoSistemaAcademico
// se houver violações, concatenando as mensagens de erro.
public class ValidadorDominio
{
    // Equivalente ao bloco static { ValidatorFactory factory = ...; validator = factory.getValidator(); }
    // Em C#, o Validator é estático e não requer inicialização prévia.

    public static void Validate<T>(T objeto)
    {
        var resultados = new List<ValidationResult>();
        var contexto = new ValidationContext(objeto!, serviceProvider: null, items: null);

        bool valido = Validator.TryValidateObject(objeto!, contexto, resultados, validateAllProperties: true);

        if (!valido)
        {
            // Equivalente ao stream().map(...).collect(Collectors.joining(", "))
            string mensagensDeErro = string.Join(", ",
                resultados.Select(r =>
                {
                    string campo = r.MemberNames.FirstOrDefault() ?? "objeto";
                    return campo + " " + r.ErrorMessage;
                }));

            throw new ExcecaoSistemaAcademico("Erro de validação: " + mensagensDeErro);
        }
    }
}
