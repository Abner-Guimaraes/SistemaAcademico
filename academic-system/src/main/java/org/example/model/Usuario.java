package org.example.model;

import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {
    @EqualsAndHashCode.Include
    private String username;
    private String password;
    private String role;
}
