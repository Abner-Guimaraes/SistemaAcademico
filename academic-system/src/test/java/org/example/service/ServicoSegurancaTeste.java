package org.example.service;

import org.example.model.Usuario;
import org.example.repository.RepositorioUsuario;
import org.example.exception.ExcecaoAutenticacao;
import org.example.exception.ExcecaoAutorizacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ServicoSegurancaTeste {

    private ServicoSeguranca servicoSeguranca;
    
    @BeforeEach
    public void setup() {
        RepositorioUsuario mockRepo = username -> {
            if (username.equals("admin")) {
                return Optional.of(new Usuario("admin", "admin123", "ADMIN"));
            } else if (username.equals("prof")) {
                return Optional.of(new Usuario("prof", "prof123", "PROFESSOR"));
            }
            return Optional.empty();
        };
        servicoSeguranca = new ServicoSeguranca(mockRepo);
    }

    @Test
    public void deveAutenticarUsuarioValido() {
        Usuario user = servicoSeguranca.autenticar("admin", "admin123");
        assertNotNull(user);
        assertEquals("ADMIN", user.getRole());
    }

    @Test
    public void deveLancarExcecaoSenhaInvalida() {
        assertThrows(ExcecaoAutenticacao.class, () -> {
            servicoSeguranca.autenticar("admin", "senhaerrada");
        });
    }

    @Test
    public void deveLancarExcecaoUsuarioInexistente() {
        assertThrows(ExcecaoAutenticacao.class, () -> {
            servicoSeguranca.autenticar("desconhecido", "123");
        });
    }

    @Test
    public void deveAutorizarAdmin() {
        // Não deve lançar exceção
        servicoSeguranca.autorizarAdmin("ADMIN");
    }

    @Test
    public void deveNegarAutorizacaoProfessor() {
        assertThrows(ExcecaoAutorizacao.class, () -> {
            servicoSeguranca.autorizarAdmin("PROFESSOR");
        });
    }
}
