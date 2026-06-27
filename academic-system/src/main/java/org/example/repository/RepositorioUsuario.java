package org.example.repository;

import org.example.model.Usuario;
import java.util.Optional;

public interface RepositorioUsuario {
    Optional<Usuario> buscarPorUsername(String username);
}
