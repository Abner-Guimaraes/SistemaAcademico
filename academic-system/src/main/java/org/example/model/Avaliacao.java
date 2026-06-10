package org.example.model;
import org.example.exception.ExcecaoSistemaAcademico;

public abstract class Avaliacao {
	
	private String nome;
	private double valor;
	private double peso;
	

	
	public Avaliacao(String nome, double valor, double peso) {
		if(valor < 0 || peso < 0) {
			throw new ExcecaoSistemaAcademico("Inválido");
		}
		this.nome = nome;
		this.valor = valor;
		this.peso = peso;
		
	}
	
	
	
	
	
	
	public String getNome() {return nome;}
    public double getValor() { return 10.0; }
    public double getPeso() { return 0.4; }
}