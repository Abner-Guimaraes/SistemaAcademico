package org.example.service;

import org.example.model.Usuario;
import org.example.repository.RepositorioUsuario;
import org.example.exception.ExcecaoAutenticacao;
import org.example.exception.ExcecaoAutorizacao;
import java.util.Optional;
import java.util.logging.Logger;

public class ServicoSeguranca {
    private static final Logger logger = Logger.getLogger(ServicoSeguranca.class.getName());
    private final RepositorioUsuario repositorio;

    public ServicoSeguranca(RepositorioUsuario repositorio) {
        this.repositorio = repositorio;
    }

    public Usuario autenticar(String username, String password) {
        Optional<Usuario> usuario = repositorio.buscarPorUsername(username);
        if (usuario.isEmpty() || !usuario.get().getPassword().equals(password)) {
            logger.warning("Falha de autenticação para o usuário: " + username);
            throw new ExcecaoAutenticacao("Credenciais inválidas.");
        }
        logger.info("Usuário autenticado com sucesso: " + username);
        return usuario.get();
    }

    public void autorizarAdmin(String role) {
        if (!"ADMIN".equals(role)) {
            logger.warning("Tentativa de acesso negada para o papel: " + role);
            throw new ExcecaoAutorizacao("Acesso negado: Requer privilégios de administrador.");
        }
        logger.info("Autorização de administrador concedida.");
    }

    public void logout(String username) {
        logger.info("Usuário efetuou logout com sucesso: " + username);
    }
}
