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
| 10/06/2026        | US-2361              | Registrar Avaliação na Turma | 3d63c9d |
| 11/06/2026        | US-2363              | Registrar Turmas por Entrada de Teclado | beabdaf |
| 18/06/2026        | US-2367              | Handle academic domain errors with custom exceptions | 9fa8148 |
| 18/06/2026        | US-2368              | Handle keyboard input errors with custom exceptions | 7d93f84 |
| 18/06/2026        | US-2369              | Handle authentication and authorization errors with custom exceptions | 740a413 |
| 18/06/2026        | US-0000              | Startup Academic System | 682bb74 |
| 18/06/2026        | TUS-2370             | Refactor menu operations into AcademicSystemController | e5d5b21 |
| 18/06/2026        | TUS-2371             | Validate academic domain objects using Jakarta Bean Validation | 2329f9c |
| 26/06/2026        | TUS-2362             | Persist class assessments to TXT file | - |
| 26/06/2026        | US-2364              | Manage academic system through command line menu | - |
| 26/06/2026        | TUS-2365             | Refactor domain model using Lombok | - |
| 26/06/2026        | TUS-2382             | Define equality for identifiable domain objects | - |
| 26/06/2026        | US-2375              | Generate class assessment summary report | - |
| 26/06/2026        | US-2376              | Generate assessment weight report | - |
| 26/06/2026        | US-2372              | Configure persistence type as administrator | - |
| 26/06/2026        | US-2373              | Save academic data to XML file | - |
| 26/06/2026        | US-2374              | Save academic data to JSON file | - |
| 26/06/2026        | US-2377              | Generate persistence configuration report | - |

### Registro detalhado

#### 26/06/2026 — Persistence Block (US-2372, US-2373, US-2374, US-2377)

- **Histórias:** 
  - US-2372: Configure persistence type as administrator
  - US-2373: Save academic data to XML file
  - US-2374: Save academic data to JSON file
  - US-2377: Generate persistence configuration report
- **O que foi implementado:**
  - Foi criado o `ServicoPersistencia` que consolida a responsabilidade de gerenciar como o sistema salva os dados (TUS-2398 atendido de tabela). Ele detém o tipo de persistência (`TXT`, `XML`, `JSON`) e direciona ao repositório correspondente, além de bloquear perfis não-admin.
  - Implementadas as classes `RepositorioTurmaXml` e `RepositorioTurmaJson` que assinam a interface `RepositorioTurma` e executam o *parsing* manual dos dados sem bibliotecas externas.
  - Criado o método de relatório de persistência (`gerarRelatorioPersistencia`) no `ServicoRelatorio`.
  - O `ControladorSistemaAcademico` agora delega o salvamento de dados (`salvarDados()`) para o `ServicoPersistencia`, extinguindo a limitação do TXT forçado no controlador.
  - Os menus de ADMIN na classe `Main` foram atualizados para permitir configurar a persistência e gerar o relatório da configuração.
- **Como foi validado:** Criação e execução dos testes unitários `ServicoPersistenciaTeste` abrangendo trocas de configuração e salvamento por perfis corretos. O relatório foi testado via `ServicoRelatorioTeste`. Build verde (`mvn clean test`).

#### 26/06/2026 — Generate assessment weight report (US-2376)

- **História:** US-2376 - Generate assessment weight report
- **O que foi implementado:**
  - Adição do método `gerarRelatorioPesos` na classe `ServicoRelatorio`.
  - O método varre as turmas, soma os pesos (usando `double`) e informa se o peso total resulta exatamente em 1.0 (margem de erro para double) classificando como VÁLIDA ou INVÁLIDA (AC1 a AC5).
  - Adicionada a opção correspondente "Pesos das Avaliações (Relatório)" na `Main` e integração ao Controller.
- **Como foi validado:** Via TDD. Adicionado `deveGerarRelatorioPesoValido`, `deveGerarRelatorioPesoInvalido` e `deveGerarRelatorioPesoZeroParaTurmaSemAvaliacao` na classe `ServicoRelatorioTeste`. Executado com sucesso via `mvn clean test`.
- **Observações:** O AC7 pede log (Auditoria), porém segue no aguardo da implementação da infraestrutura da história TUS-2390.

#### 26/06/2026 — Generate class assessment summary report (US-2375)

- **História:** US-2375 - Generate class assessment summary report
- **O que foi implementado:**
  - Criação do serviço `ServicoRelatorio` focado puramente na lógica de relatórios.
  - O controller principal agora atua como ponte repassando as turmas cadastradas para o serviço formatar o relatório textual.
  - Atualização nos menus iterativos da `Main` adicionando a opção de `Resumo de Avaliações` tanto para `ADMIN` quanto para `PROFESSOR`.
- **Como foi validado:** Via desenvolvimento guiado por testes (TDD). A classe `ServicoRelatorioTeste` verifica detalhadamente AC1, AC2, AC3 e AC4 garantindo a formatação. Executado `mvn clean test`.
- **Observações / débito técnico:** A auditoria (AC6) exigida não foi aplicada de forma completa por causa do débito persistente (TUS-2390) que deve configurar a infraestrutura de Log.

#### 26/06/2026 — Define equality for identifiable domain objects (TUS-2382)

- **História:** TUS-2382 - Define equality for identifiable domain objects
- **O que foi implementado:**
  - Uso da anotação `@EqualsAndHashCode(onlyExplicitlyIncluded = true)` na classe `Turma`.
  - Inclusão apenas do campo `codigo` como chave de igualdade com `@EqualsAndHashCode.Include`.
  - Paralelamente, resolvido o débito técnico em `RepositorioTurmaTxt` adicionando injeção do caminho do arquivo no construtor.
- **Como foi validado:** Build via `mvn clean test`.
- **Observações:** A entidade `User` mencionada na história ainda não foi criada. A regra será aplicada nela futuramente.

#### 26/06/2026 — Refactor domain model using Lombok (TUS-2365)

- **História:** TUS-2365 - Refactor domain model using Lombok
- **O que foi implementado:**
  - Inclusão da dependência `lombok` (versão 1.18.32) no `pom.xml` com escopo *provided*.
  - Remoção de todos os métodos de acesso verbosos (`getters` criados manualmente) das entidades `Turma` e `Avaliacao`.
  - Remoção de repetição do `getPeso()` explícito e fixo nas subclasses `Prova`, `TrabalhoPratico`, `Seminario` e `Atividade`, substituindo pelo funcionamento nativo ativado pela abstração da superclasse.
  - Inclusão da anotação `@Getter` ao nível das classes `Turma` e `Avaliacao`, reduzindo substancialmente o boilerplate code.
- **Como foi validado:** Compilado e testado usando `mvn clean test`. Os testes (que ainda referenciam os métodos get) continuaram operacionais provando que o processador de anotações funcionou corretamente.
- **Observações / débito técnico:** Próximo passo ideal seria usarmos `@EqualsAndHashCode` quando a história TUS-2382 for abordada.

#### 26/06/2026 — Manage academic system through command line menu (US-2364)

- **História:** US-2364 - Manage academic system through command line menu
- **O que foi implementado:**
  - Refatoração completa da `Main` para abandonar o "script de testes linear" e adotar um loop `while` infinito (AC1).
  - Simulação de login, onde o usuário pode escolher entrar como `ADMIN` ou `PROFESSOR`.
  - Separação de menus baseada no perfil escolhido. O ADMIN pode registrar turmas e salvar TXT, enquanto PROFESSOR pode registrar avaliações (AC2, AC3).
  - Uso de comandos `switch` para acionar o Controller correto de acordo com a opção (AC4).
  - Tratamento de falhas e opções inválidas usando nossas Exceptions personalizadas sem quebrar o loop do sistema (AC5, AC6).
  - Operação de Logout (voltar para a tela de perfis) e Sair (encerrar o sistema) (AC7, AC8).
- **Como foi validado:** Build via `mvn clean test` obteve sucesso. Foi validado mentalmente checando as ramificações de menu.
- **Observações / débito técnico:** A tela de login é apenas simulada por enquanto. A autenticação real será introduzida na US-2366.

#### 26/06/2026 — Persist class assessments to TXT file (TUS-2362)

- **História:** TUS-2362 - Persist class assessments to TXT file
- **O que foi implementado:**
  - Criação da interface `RepositorioTurma` para abstrair a persistência.
  - Implementação `RepositorioTurmaTxt` que salva dados de turmas e avaliações no formato TXT.
  - Adição do método `salvarDadosTxt(String usuarioAdmin)` no `ServicoTurma` e repasse através de `ControladorTurma` e `ControladorSistemaAcademico`.
  - Verificação de perfil `ADMIN` integrada no Serviço para autorizar o salvamento.
  - Teste manual simulado no `Main`.
- **Como foi validado:** Build e suíte de testes aprovados (`mvn clean test`). O `Main` imprime a mensagem de sucesso na simulação com perfil ADMIN.
- **Observações / débito técnico:** O caminho do arquivo TXT está fixo (`turmas.txt`). Futuramente pode ser parametrizado.

#### 18/06/2026 — Validate academic domain objects using Jakarta Bean Validation (TUS-2371)

- **História:** TUS-2371 - Validate academic domain objects using Jakarta Bean Validation
- **O que foi implementado:**
  - Adição das dependências do Jakarta Bean Validation e Hibernate Validator no `pom.xml`.
  - Anotação das classes de domínio (`Turma` e `Avaliacao`) com regras de validação (`@NotBlank`, `@PositiveOrZero`).
  - Criação do `DomainValidator` para encapsular e rodar o `Validator` oficial da API, traduzindo violações de Constraint para nossa exceção de negócio `AcademicSystemException`.
  - Remoção dos *if-statements* manuais de validação espalhados pelas lógicas (como no `TurmaService` e `Avaliacao`).
- **Como foi validado:** Suíte de testes (`mvn clean test`) rodando perfeitamente e validando as exceções através do novo componente de Validação.
- **Observações / débito técnico:** Nenhum. A base agora escala facilmente para dezenas de novos atributos validáveis.



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
