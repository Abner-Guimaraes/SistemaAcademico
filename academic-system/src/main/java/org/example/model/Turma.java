package org.example.model;
import java.util.List;
import java.util.ArrayList;

public class Turma {
	
	private String codigo;
	private String nome;
	private ArrayList<Avaliacao> avaliacoes;

	
	

    public Turma(String codigo, String nome) {
    	this.codigo = codigo;
    	this.nome = nome;
    	this.avaliacoes = new ArrayList<>();
    	
    }

    
    //AC6: Dados inválidos de avaliação,
    //quando uma tentativa de registro de avaliação é feita,
    //então o sistema deve rejeitar a operação lançando uma AcademicSystemException.
    public void adicionarAvaliacao(Avaliacao avaliacao) { 
    	if(avaliacao == null) {
    		throw new IllegalArgumentException("A avaliação não pode ser nula");
    	}
    	this.avaliacoes.add(avaliacao);
    	
    }

    public String getCodigo() {return codigo;}
    public String getnome() {return nome;}
    
    public List<Avaliacao> getAvaliacoes() { 
        return new ArrayList<>(avaliacoes); 
    }
}