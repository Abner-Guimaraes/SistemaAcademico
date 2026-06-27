package org.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TurmaIgualdadeTeste {

    @Test
    public void turmasComMesmoCodigoDevemSerIguais() {
        Turma t1 = new Turma("POO", "Programação Orientada a Objetos");
        Turma t2 = new Turma("POO", "Outro Título");
        
        assertEquals(t1, t2);
        assertEquals(t1.hashCode(), t2.hashCode());
    }

    @Test
    public void turmasComCodigosDiferentesDevemSerDiferentes() {
        Turma t1 = new Turma("POO1", "Programação Orientada a Objetos");
        Turma t2 = new Turma("POO2", "Programação Orientada a Objetos");
        
        assertNotEquals(t1, t2);
    }
}
