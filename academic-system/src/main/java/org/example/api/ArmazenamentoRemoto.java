package org.example.api;

import java.util.List;
import org.example.model.Turma;

public interface ArmazenamentoRemoto {
    boolean autenticar(String token);
    void enviarDados(List<Turma> turmas);
    List<Turma> recuperarDados();
}
