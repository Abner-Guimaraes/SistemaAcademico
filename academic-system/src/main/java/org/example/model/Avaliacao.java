package org.example.model;
import org.example.exception.AcademicSystemException;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

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
	
	
	
	
	
	
	public String getNome() { return nome; }
    public double getValor() { return this.valor; }
    public double getPeso() { return this.peso; }
}