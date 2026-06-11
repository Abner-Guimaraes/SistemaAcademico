package org.example;

import org.example.model.Avaliacao;
import org.example.model.AvaliacaoFactory;
import org.example.model.GerenciadorDeTurmas;
import org.example.model.Turma;

public class Main {
    public static void main(String[] args) {
        GerenciadorDeTurmas gerenciador = new GerenciadorDeTurmas();
        gerenciador.salvarTurma(new Turma("CC3A", "Orientação a Objetos"));

        System.out.println("Bem-vindo ao Sistema Acadêmico!");
        System.out.println("-----------------------------------");

        System.out.println("Usuário logado: PROFESSOR");
        try {
            Avaliacao prova = AvaliacaoFactory.criar("Prova", "P1", 10.0, 0.4);
            gerenciador.registrarAvaliacao("CC3A", prova, "PROFESSOR");
            System.out.println("Sucesso: Você registrou a avaliação na turma CC3A!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

        System.out.println("-----------------------------------");

        System.out.println("Usuário logado: Abner");
        try {
            Avaliacao trabalho = AvaliacaoFactory.criar("Trabalho Prático", "T1", 10.0, 0.2);
            gerenciador.registrarAvaliacao("CC3A", trabalho, "ALUNO_HACKER");
            System.out.println("Sucesso: Avaliação registrada!");
        } catch (Exception e) {
            System.out.println("Erro ao tentar registrar: " + e.getMessage());
        }
    }
}