package org.example.model;
import org.example.exception.ExcecaoSistemaAcademico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

@Getter
public abstract class Avaliacao {
	
	@NotBlank(message = "O nome da avaliação não pode ser vazio.")
	private String nome;
	
	@PositiveOrZero(message = "Dados inválidos: valor não pode ser negativo.")
	private double valor;
	
	@PositiveOrZero(message = "Dados inválidos: peso não pode ser negativo.")
	private double peso;

	public Avaliacao(String nome, double valor, double peso) {
		this.nome = nome;
		this.valor = valor;
		this.peso = peso;
	}
}