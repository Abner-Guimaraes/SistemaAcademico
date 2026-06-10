package org.example.model;

public class Atividade extends Avaliacao{
	
	public Atividade(String nome, double valor, double peso) {
		super(nome,valor, peso);
	}

	public double getPeso() {return 0.3;}
}
