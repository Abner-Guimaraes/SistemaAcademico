# Registro de Decisões de Arquitetura (ADR)

Este documento registra decisões de arquitetura, design de código, padrões adotados e trade-offs do projeto. Cada entrada segue o formato **ADR (Architecture Decision Record)**.

**Documentação geral:** [contexto.md](contexto.md)  
**Histórico de tarefas:** [historico_tarefas.md](historico_tarefas.md)

---

## Índice de decisões

| # | Título | Data | Status |
|---|--------|------|--------|
| ADR-001 | Uso do Padrão Factory para Criação de Avaliações | 10/06/2026 | Aceita |
| ADR-002 | Validação de Domínio via Cláusulas de Guarda no Service (Provisória) | 11/06/2026 | Aceita |

---

## ADR-001 — Uso do Padrão Factory para Criação de Avaliações

| Campo | Conteúdo |
|-------|----------|
| **Status** | Aceita |
| **Data** | 10/06/2026 |
| **Autor(es)** | Desenvolvedor |
| **História relacionada** | US-2361 |

### Contexto / Problema

A US-2361 exige que o sistema registre avaliações, que podem ser de quatro tipos específicos (Prova, Trabalho Prático, Seminário, Atividade), baseando-se em uma entrada de texto (String). Fazer essa verificação condicional (`if/else` ou `switch`) diretamente dentro da classe `Turma` ou do método principal violaria o princípio de Responsabilidade Única (SRP) e o princípio Aberto/Fechado (OCP) do SOLID, acoplando a lógica de domínio à lógica de criação de objetos.

### Decisão Tomada

Adotar o padrão de projeto criacional **Simple Factory**, implementando a classe estática `AvaliacaoFactory`. O método principal de registro passa a string do tipo de avaliação para a fábrica, que se responsabiliza por instanciar a subclasse correta ou lançar uma exceção de domínio (`ExcecaoSistemaAcademico`) caso o tipo seja inválido.

### Justificativa

A fábrica isola a complexidade de criação. Se no futuro um novo tipo de avaliação for introduzido (ex: "Projeto Final"), apenas a `AvaliacaoFactory` precisará ser alterada. A classe `Turma` não precisa saber *como* uma avaliação é criada, apenas precisa saber que recebeu um objeto válido do tipo abstrato `Avaliacao`.

### Consequências

- **Positivas:** Redução do acoplamento; código mais limpo nas classes de domínio; facilidade para testar a lógica de criação isoladamente.
- **Negativas / trade-offs:** Adição de mais um arquivo/classe ao projeto, aumentando levemente a complexidade estrutural inicial.
- **Ações de acompanhamento:** Revisar na próxima vez que novos subtipos de recursos precisarem ser instanciados dinamicamente para manter o padrão.

---

## ADR-002 — Validação de Domínio via Cláusulas de Guarda no Service (Provisória)

| Campo | Conteúdo |
|-------|----------|
| **Status** | Aceita |
| **Data** | 11/06/2026 |
| **Autor(es)** | Desenvolvedor |
| **História relacionada** | US-2363 |

### Contexto / Problema

A US-2363 estabelece que classes acadêmicas não podem ser registradas com dados inválidos (código ou título em branco). A funcionalidade final preverá validação via anotações `Jakarta Bean Validation` (TUS-2371), no entanto, para satisfazer os critérios (AC3 e AC4) neste momento de forma rápida e segura durante o ciclo TDD, precisávamos adotar uma abordagem clara de validação.

### Decisão Tomada

Adotar o uso de **Cláusulas de Guarda (Guard Clauses)** dentro do método `registrarTurma` da classe `TurmaService`. Se um dado for inválido, o fluxo é interrompido imediatamente e uma `AcademicSystemException` é lançada.

### Justificativa

As cláusulas de guarda mantêm o fluxo do método ("happy path") livre de aninhamentos complexos (`if/else`), melhorando a legibilidade. Elas também centralizam a lógica de rejeição de requisições inválidas no serviço, impedindo que objetos imperfeitos cheguem à memória do sistema.

### Consequências

- **Positivas:** O código fica linear e fácil de ler; previne a alocação de objetos inválidos e facilita os testes unitários (`RegistrodeTurmasTest`).
- **Negativas / trade-offs:** A lógica de validação é hardcoded no serviço, violando parcialmente o encapsulamento do domínio ou o futuro uso do `Jakarta Bean Validation`.
- **Ações de acompanhamento:** Refatorar essa camada quando a história TUS-2371 (Jakarta Bean Validation) for iniciada, transferindo a validação para anotações nas entidades e validadores centrais.
