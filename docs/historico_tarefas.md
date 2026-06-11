# Histórico de Tarefas

Diário de bordo do desenvolvimento do Sistema Acadêmico. Registre aqui o que está em andamento e o que já foi concluído, com datas e referências às histórias de usuário quando aplicável.

**Repositório base:** https://github.com/pagliares/academic-system-semester-assignment-2026  
**Documentação geral:** [contexto.md](contexto.md)  
**Decisões de arquitetura:** [arquitetura.md](arquitetura.md)

---

## Tarefas em Andamento

| Data de início | História / Tarefa | Descrição | Responsável | Observações |
|----------------|-------------------|-----------|-------------|-------------|
| 10/06/2026     | US-2363           | Registrar Turmas por Entrada de Teclado | Desenvolvedor | Domínio rascunhado no Miro. Aguardando Code Review da US-2361 para iniciar mapeamento de testes. |

### Detalhamento

Use esta seção para notas mais longas sobre a tarefa atual:

- **Objetivo:** Permitir que um professor (ADMIN) registre turmas acadêmicas por meio de entrada via teclado.
- **Critérios de aceite relevantes:** AC1 a AC8 (Foco inicial na validação de permissões de usuário, validação de dados inválidos e delegação arquitetural via Controller/Service).
- **Arquivos alterados:** `TurmaService.java` (Validação de domínio), `TurmaController.java` (Delegação arquitetural inserida).
- **Próximos passos:** 1. Integrar a leitura de teclado utilizando `java.util.Scanner` de forma provisória no ponto de entrada (`Main.java`).
  2. Finalizar a US-2363.

---

## Requisitos Concluídos (com data)

| Data de conclusão | História / Requisito | Resumo do que foi entregue | Commit / PR (opcional) |
|-------------------|----------------------|----------------------------|------------------------|
| 10/06/2026        | US-2361              | Registrar Avaliação na Turma | *Aguardando push* |

### Registro detalhado

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
