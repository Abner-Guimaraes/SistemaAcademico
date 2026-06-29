package org.example.repository;

import org.example.model.Usuario;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Optional;

public class RepositorioUsuarioTxt implements RepositorioUsuario {
    private final String caminhoArquivo;

    public RepositorioUsuarioTxt(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    @Override
    public Optional<Usuario> buscarPorUsername(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length >= 3 && partes[0].equals(username)) {
                    return Optional.of(new Usuario(partes[0], partes[1], partes[2]));
                }
            }
        } catch (Exception e) {
            // Silencioso, retorna vazio
        }
        return Optional.empty();
    }
}
