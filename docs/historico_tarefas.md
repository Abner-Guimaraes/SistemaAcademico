# Histórico de Tarefas

Diário de bordo do desenvolvimento do Sistema Acadêmico. Registre aqui o que está em andamento e o que já foi concluído, com datas e referências às histórias de usuário quando aplicável.

**Repositório base:** https://github.com/pagliares/academic-system-semester-assignment-2026  
**Documentação geral:** [contexto.md](contexto.md)  
**Decisões de arquitetura:** [arquitetura.md](arquitetura.md)

---

## Tarefas em Andamento

| Data de início | História / Tarefa | Descrição | Responsável | Observações |
|----------------|-------------------|-----------|-------------|-------------|
| 12/06/2026 | US-2361 | Integrar AvaliacaoFactory no fluxo principal (Controller/Service) | Desenvolvedor | Refatoração de Factory |

### Detalhamento

Use esta seção para notas mais longas sobre a tarefa atual:
**Integração do Padrão Factory (US-2361)**
1. Captura de Dados (Menu/CLI): Capturar código da turma, tipo da avaliação (String), valor e peso na interface.
2. Passagem pelo Controller: Repassar os dados da UI para a camada de Serviço (`TurmaController` ou similar).
3. Processamento no Service: Validar existência da turma; chamar `AvaliacaoFactory.criarAvaliacao(tipo)`; preencher atributos e adicionar à turma.
4. Validação na Factory: Lançar `AcademicSystemException` se o tipo for inválido.
5. Validação de Domínio: Validar valor e peso.

---

## Requisitos Concluídos (com data)

| Data de conclusão | História / Requisito | Resumo do que foi entregue | Commit / PR (opcional) |
|-------------------|----------------------|----------------------------|------------------------|
| 10/06/2026        | US-2361              | Registrar Avaliação na Turma | *Aguardando push* |
| 11/06/2026        | US-2363              | Registrar Turmas por Entrada de Teclado | *Aguardando push* |

### Registro detalhado

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

---

## Legenda

| Símbolo | Significado        |
|---------|--------------------|
| US-     | User Story         |
| TUS-    | Technical User Story |
| UC-     | Use Case           |
