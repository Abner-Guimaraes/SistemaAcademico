# Histórico de Tarefas

Diário de bordo do desenvolvimento do Sistema Acadêmico. Registre aqui o que está em andamento e o que já foi concluído, com datas e referências às histórias de usuário quando aplicável.

**Repositório base:** https://github.com/pagliares/academic-system-semester-assignment-2026  
**Documentação geral:** [contexto.md](contexto.md)  
**Decisões de arquitetura:** [arquitetura.md](arquitetura.md)

---

## Tarefas em Andamento

| Data de início | História / Tarefa | Descrição | Responsável | Observações |
|----------------|-------------------|-----------|-------------|-------------|
| - | - | Nenhuma tarefa em andamento no momento | - | - |

### Detalhamento

Use esta seção para notas mais longas sobre a tarefa atual:
*(Nenhuma tarefa em andamento)*

---

## Requisitos Concluídos (com data)

| Data de conclusão | História / Requisito | Resumo do que foi entregue | Commit / PR (opcional) |
|-------------------|----------------------|----------------------------|------------------------|
| 10/06/2026        | US-2361              | Registrar Avaliação na Turma | *Aguardando push* |
| 11/06/2026        | US-2363              | Registrar Turmas por Entrada de Teclado | *Aguardando push* |
| 18/06/2026        | US-2367              | Handle academic domain errors with custom exceptions | 9fa8148 |
| 18/06/2026        | US-2368              | Handle keyboard input errors with custom exceptions | 7d93f84 |
| 18/06/2026        | US-2369              | Handle authentication and authorization errors with custom exceptions | 740a413 |
| 18/06/2026        | US-0000              | Startup Academic System | 682bb74 |
| 18/06/2026        | TUS-2370             | Refactor menu operations into AcademicSystemController | e5d5b21 |

### Registro detalhado

#### 18/06/2026 — Refactor menu operations into AcademicSystemController (TUS-2370)

- **História:** TUS-2370 - Refactor menu operations into AcademicSystemController
- **O que foi implementado:**
  - Criação da classe `AcademicSystemController` servindo como Facade.
  - O `AcademicSystem` foi atualizado para inicializar e prover este novo controller.
  - A classe `Main` foi enxugada: agora ela não fala mais diretamente com `TurmaController` ou `AvaliacaoController`, mas repassa todos os comandos de negócio para o `AcademicSystemController`.
- **Como foi validado:** Build e suíte de testes aprovados (`mvn clean test`).
- **Observações / débito técnico:** Nenhum. A Main está purificada para atuar apenas como I/O.



#### 18/06/2026 — Startup Academic System (US-0000)

- **História:** US-0000 - Startup Academic System
- **O que foi implementado:**
  - Criação da classe `AcademicSystem` aplicando o padrão Singleton.
  - Transferência da responsabilidade de inicializar serviços e controllers (como `TurmaService`, `TurmaController`, `AvaliacaoController`) para dentro do Singleton.
  - Refatoração da `Main` para iniciar os componentes requisitando `AcademicSystem.getInstance()`.
- **Como foi validado:** Execução e aprovação de todos os testes unitários via `mvn clean test`.
- **Observações / débito técnico:** Nenhum. A base para o futuro menu dinâmico está consolidada.



#### 18/06/2026 — Handle authentication and authorization errors with custom exceptions (US-2369)

- **História:** US-2369 - Handle authentication and authorization errors with custom exceptions
- **O que foi implementado:**
  - Criação da hierarquia de exceções de segurança (`SecuritySystemException`, `AuthenticationException`, `AuthorizationException`).
  - Substituição das exceções lançadas nos serviços (`TurmaService` e `GerenciadorDeTurmas`) em verificações de perfil (`ADMIN`/`PROFESSOR`) de `AcademicSystemException` para `AuthorizationException`.
  - Captura das exceções de segurança na `Main` sem comprometer o fluxo, garantindo os Critérios de Aceitação AC1-AC8.
- **Como foi validado:** Suíte de testes atualizada para esperar as novas exceções. Execução via `mvn clean test` obteve 100% de sucesso.
- **Observações / débito técnico:** Pronta para receber mecanismos reais de Login futuramente.



#### 18/06/2026 — Handle keyboard input errors with custom exceptions (US-2368)

- **História:** US-2368 - Handle keyboard input errors with custom exceptions
- **O que foi implementado:**
  - Criação da classe específica `KeyboardInputException`.
  - Envolvimento da leitura de valores numéricos (valor e peso da avaliação) em blocos `try-catch` na `Main`.
  - Tratamento da exceção formatando amigavelmente a mensagem de erro para o usuário sem estourar o stacktrace.
- **Como foi validado:** Execução e aprovação da suíte de testes unitários.
- **Observações / débito técnico:** Nenhum.



#### 18/06/2026 — Handle academic domain errors with custom exceptions (US-2367)

- **História:** US-2367 - Handle academic domain errors with custom exceptions
- **O que foi implementado:**
  - Remoção da classe duplicada `ExcecaoSistemaAcademico`.
  - Padronização do uso da exceção base de domínio `AcademicSystemException`.
  - Tratamento da exceção na classe `Main` para emitir mensagens amigáveis de erro de negócio sem estourar o stacktrace.
- **Como foi validado:** Execução e aprovação da suíte de testes (`mvn clean test`).
- **Observações / débito técnico:** Nenhum débito deixado.


#### 11/06/2026 — Registrar Turmas por Entrada de Teclado (US-2363)

- **História:** US-2363 - Register classes through keyboard input
- **O que foi implementado:** 
  - Lógica de validação de domínio em `TurmaService` para garantir que `codigo` e `titulo` da turma não sejam nulos ou vazios (AC3, AC4).
  - Criação da classe `TurmaController` para abstrair e delegar chamadas de regras de negócio, conforme exigido pelo AC7.
  - Implementação provisória no ponto de entrada (`Main.java`) com uso de `java.util.Scanner` para coletar o código e título da turma no console (AC1).
  - Testes unitários preexistentes (`RegistrodeTurmasTest`) executados e aprovados para a camada de serviço.
- **Como foi validado:** Execução da suíte de testes do JUnit 5 (`mvn clean test`). Execução e simulação do input via console com `mvn exec:java`. 
- **Observações / débito técnico:** A auditoria (logging - AC8) foi ignorada temporariamente por não existirem as ferramentas propostas na arquitetura ainda (TUS-2390). O uso de scanner no `Main` é provisório e deve ser evoluído na US-2364.

#### 10/06/2026 — Registrar Avaliação na Turma (US-2361)

- **História:** US-2361 - Registrar Avaliação
- **O que foi implementado:** - Modelo de domínio completo: `Turma`, `Avaliacao` (Classe Abstrata) e suas especializações (`Prova`, `TrabalhoPratico`, `Seminario`, `Atividade`).
  - Implementação do Design Pattern *Factory* (`AvaliacaoFactory`) para lidar com a criação dos tipos corretos a partir de strings.
  - Implementação de um `GerenciadorDeTurmas` para validar a existência da turma e os privilégios do usuário (PROFESSOR) antes do registro.
- **Como foi validado:** Testes unitários via JUnit (`RegistrodeAvaliacaoTest.java`). Cobertura de 100% dos Critérios de Aceitação (AC1 ao AC8).
- **Observações / débito técnico:** O código foi refatorado após a aprovação nos testes, isolando as regras de negócio corretamente. Aprendizado prático do ciclo Red-Green-Refactor do TDD.

#### 12/06/2026 — Integração e Refatoração do Padrão Factory (US-2361)

- **História:** US-2361 - Registrar Avaliação
- **O que foi refatorado:**
  - Código "órfão" da `AvaliacaoFactory` foi conectado à lógica principal.
  - Implementado o fluxo MVC e separação de responsabilidades (captura de dados CLI, passagem via `AvaliacaoController`, processamento e amarração final no `TurmaService`).
  - Atualizadas as validações de domínio da própria `Avaliacao` e da `AvaliacaoFactory` para garantirem que herdam da exceção padrão `AcademicSystemException`.
- **Como foi validado:** Através do teste de regressão e atualização do `RegistrodeAvaliacaoTest.java`, incluindo correções no CA5 e CA6 para a nova exceção.

---

## Legenda

| Símbolo | Significado        |
|---------|--------------------|
| US-     | User Story         |
| TUS-    | Technical User Story |
| UC-     | Use Case           |
