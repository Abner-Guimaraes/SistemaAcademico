package org.example.model;
import org.example.exception.AcademicSystemException;

public abstract class Avaliacao {
	
	private String nome;
	private double valor;
	private double peso;
	

	
	public Avaliacao(String nome, double valor, double peso) {
		if(valor < 0 || peso < 0) {
			throw new AcademicSystemException("Dados inválidos: valor ou peso não podem ser negativos.");
		}
		this.nome = nome;
		this.valor = valor;
		this.peso = peso;
		
	}
	
	
	
	
	
	
	public String getNome() { return nome; }
    public double getValor() { return this.valor; }
    public double getPeso() { return this.peso; }
}