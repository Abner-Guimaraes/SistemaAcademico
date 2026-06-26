package org.example.model;

import org.example.exception.ExcecaoSistemaAcademico;

public class AvaliacaoFactory {

    // O método é estático para podermos chamá-lo direto pelo nome da classe, sem precisar dar "new AvaliacaoFactory()"
    public static Avaliacao criar(String tipo, String nome, double valor, double peso) {
        
        switch (tipo) {
            case "Prova":
                return new Prova(nome, valor, peso);
                
            case "Trabalho Prático":
                return new TrabalhoPratico(nome, valor, peso);
                
            case "Seminário":
                return new Seminario(nome, valor, peso);
                
            case "Atividade":
                return new Atividade(nome, valor, peso);
                
            default:
                // AC5: Se o texto digitado não for nenhum dos 4 acima, o sistema lança a exceção e barra o registro!
                throw new ExcecaoSistemaAcademico("Tipo de avaliação inválido: " + tipo);
        }
    }
}