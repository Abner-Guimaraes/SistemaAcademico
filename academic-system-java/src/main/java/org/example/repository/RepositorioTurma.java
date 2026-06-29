package org.example.repository;

import org.example.model.Turma;
import java.util.List;

public interface RepositorioTurma {
    void salvarTodas(List<Turma> turmas);
}
