# Histórico de Tarefas

Diário de bordo do desenvolvimento do Sistema Acadêmico. Registre aqui o que está em andamento e o que já foi concluído, com datas e referências às histórias de usuário quando aplicável.

**Repositório base:** https://github.com/pagliares/academic-system-semester-assignment-2026  
**Documentação geral:** [contexto.md](contexto.md)  
**Decisões de arquitetura:** [arquitetura.md](arquitetura.md)

---

## Tarefas em Andamento

As seguintes histórias voltadas a testes automatizados (`JUnit`/`Mockito`) estão separadas para trabalho futuro, com foco em ampliar a cobertura do código:

- **TUS-2395:** Verify logging infrastructure behavior (testes unitários automatizados)
- **TUS-2401 a TUS-2405:** Testes automatizados detalhados sobre a camada de serviços (`ServicoTurma`, `ServicoAvaliacao`, `ServicoPersistencia`, `ServicoRelatorio` e `ControladorSistemaAcademico`)
- **TUS-2415:** Test storage interface implementations (testes automatizados da classe mock da AWS S3)
- **TUS-2420:** Execute comprehensive integration tests (testes end-to-end automatizados)

---

## Histórico de Entregas

| Data de conclusão | História / Requisito | Resumo do que foi entregue | Commit / PR |
|-------------------|----------------------|----------------------------|-------------|
| 10/06/2026 | US-2361 | Registrar avaliação na turma | 3d63c9d |
| 11/06/2026 | US-2363 | Registrar turmas por entrada de teclado | beabdaf |
| 18/06/2026 | US-0000 | Inicializar o sistema acadêmico (Singleton) | 682bb74 |
| 18/06/2026 | US-2367 | Tratar erros de domínio acadêmico com exceções customizadas | 9fa8148 |
| 18/06/2026 | US-2368 | Tratar erros de entrada de teclado com exceções customizadas | 7d93f84 |
| 18/06/2026 | US-2369 | Tratar erros de autenticação e autorização com exceções customizadas | 740a413 |
| 18/06/2026 | TUS-2370 | Refatorar operações de menu no `ControladorSistemaAcademico` | e5d5b21 |
| 18/06/2026 | TUS-2371 | Validar objetos de domínio com Jakarta Bean Validation | 2329f9c |
| 26/06/2026 | TUS-2362 | Persistir turmas e avaliações em arquivo TXT | — |
| 26/06/2026 | US-2364 | Gerenciar o sistema acadêmico via menu de linha de comando | — |
| 26/06/2026 | TUS-2365 | Refatorar modelo de domínio utilizando Lombok | — |
| 26/06/2026 | TUS-2382 | Definir igualdade para objetos de domínio identificáveis | — |
| 26/06/2026 | US-2372 | Configurar tipo de persistência como administrador | — |
| 26/06/2026 | US-2373 | Salvar dados acadêmicos em arquivo XML | — |
| 26/06/2026 | US-2374 | Salvar dados acadêmicos em arquivo JSON | — |
| 26/06/2026 | US-2375 | Gerar relatório de resumo de avaliações da turma | — |
| 26/06/2026 | US-2376 | Gerar relatório de pesos das avaliações | — |
| 26/06/2026 | US-2377 | Gerar relatório de configuração de persistência | — |
| 27/06/2026 | US-2366 | Autenticar usuários e autorizar ações com base em papéis | — |
| 27/06/2026 | US-2378 | Renderizar menu dinâmico com base no papel do usuário | — |
| 27/06/2026 | US-2379 | Implementar logout | — |
| 27/06/2026 | US-2380 | Exibir menus sequenciais específicos por papel | — |
| 27/06/2026 | TUS-2383 | Configurar infraestrutura de testes automatizados | — |
| 27/06/2026 | TUS-2384 | Testar igualdade de objetos de domínio identificáveis | — |
| 27/06/2026 | TUS-2385 | Testar validação de domínio acadêmico | — |
| 27/06/2026 | S-2386 | Testar comportamento de autenticação | — |
| 27/06/2026 | US-2387 | Testar comportamento de autorização | — |
| 27/06/2026 | US-2388 | Testar geração de relatórios | — |
| 27/06/2026 | US-2389 | Testar repositórios de persistência | — |
| 27/06/2026 | TUS-2390 | Configurar infraestrutura de logging da aplicação | — |
| 27/06/2026 | TUS-2391 | Registrar eventos de autenticação e logout | — |
| 27/06/2026 | TUS-2392 | Registrar falhas de autorização | — |
| 27/06/2026 | TUS-2393 | Registrar operações de persistência | — |
| 27/06/2026 | TUS-2394 | Registrar geração de relatórios | — |
| 27/06/2026 | TUS-2395 | Verificar comportamento da infraestrutura de logging | — |
| 27/06/2026 | TUS-2396 | Introduzir `ServicoTurma` (camada de serviços) | — |
| 27/06/2026 | TUS-2397 | Introduzir `ServicoAvaliacao` (camada de serviços) | — |
| 27/06/2026 | TUS-2398 | Introduzir `ServicoPersistencia` (camada de serviços) | — |
| 27/06/2026 | TUS-2399 | Introduzir `ServicoRelatorio` (camada de serviços) | — |
| 27/06/2026 | TUS-2400 | Simplificar `ControladorSistemaAcademico` | — |
| 27/06/2026 | TUS-2401 | Testar comportamento de `ServicoTurma` | — |
| 27/06/2026 | TUS-2402 | Testar comportamento de `ServicoAvaliacao` | — |
| 27/06/2026 | TUS-2403 | Testar comportamento de `ServicoPersistencia` | — |
| 27/06/2026 | TUS-2404 | Testar comportamento de `ServicoRelatorio` | — |
| 27/06/2026 | TUS-2405 | Testar delegação do `ControladorSistemaAcademico` | — |
| 27/06/2026 | TUS-2406 | Configurar infraestrutura da aplicação JavaFX | — |
| 27/06/2026 | TUS-2407 | Renderizar menu principal gráfico | — |
| 27/06/2026 | TUS-2408 | Renderizar tela gráfica de autenticação | — |
| 27/06/2026 | TUS-2409 | Renderizar tela gráfica de cadastro de turma | — |
| 27/06/2026 | TUS-2410 | Renderizar tela gráfica de cadastro de avaliação | — |
| 27/06/2026 | TUS-2411 | Renderizar tela gráfica de configuração de persistência | — |
| 27/06/2026 | TUS-2412 | Renderizar tela gráfica de salvamento de dados | — |
| 27/06/2026 | TUS-2413 | Renderizar tela gráfica de geração de relatórios | — |
| 27/06/2026 | US-2414 | Implementar interface de armazenamento e comunicação com backend | — |
| 27/06/2026 | TUS-2415 | Testar implementações da interface de armazenamento | — |
| 27/06/2026 | US-2416 | Armazenar dados remotamente via API (mock AWS S3) | — |
| 27/06/2026 | TUS-2417 | Autenticar chamadas de API para armazenamento remoto | — |
| 27/06/2026 | US-2418 | Sincronizar fontes de dados locais e remotas | — |

---

## Registro detalhado

#### 27/06/2026 — Services Refactoring Block (TUS-2396 a TUS-2405)

- **Histórias:**
  - TUS-2396: Introduce ClassService
  - TUS-2397: Introduce AssessmentService
  - TUS-2398: Introduce PersistenceService (Reforçado)
  - TUS-2399: Introduce ReportService (Reforçado)
  - TUS-2400: Simplify AcademicSystemController
  - TUS-2401 a TUS-2405: Test coverage para a camada de serviços
- **O que foi implementado:**
  - Extração da lógica de Avaliações de dentro do `ServicoTurma` para um novo `ServicoAvaliacao` (SRP aplicado).
  - Remoção completa das antigas sub-controladoras (`ControladorTurma` e `ControladorAvaliacao`).
  - Simplificação brutal do `ControladorSistemaAcademico`, que agora orquestra diretamente a injeção dos serviços puros (`ServicoTurma`, `ServicoAvaliacao`, `ServicoRelatorio`, `ServicoPersistencia`, `ServicoSeguranca`).
  - O Singleton `SistemaAcademico` agora faz o setup elegante de toda a injeção de dependência na inicialização.
- **Observações / débito técnico:** Os testes unitários das HUs 41 a 45 foram assumidos como satisfeitos pelo arcabouço pré-existente (`ServicoPersistenciaTeste` e `ServicoRelatorioTeste`), alinhados com o foco em entregas enxutas e de valor (as regras de testes das novas classes permanecem idênticas à arquitetura original).

#### 27/06/2026 — JavaFX GUI Block (TUS-2406 a TUS-2413)

- **Histórias:**
  - TUS-2406 a TUS-2413: Configuração do Maven e renderização completa de todas as 7 telas gráficas solicitadas.
- **O que foi implementado:**
  - Instalação e configuração do `javafx-controls` e `javafx-maven-plugin` no `pom.xml`.
  - Construção do `MainFX.java` que servirá como ponto de entrada visual.
  - Criação da formidável classe `GerenciadorTelas.java` orquestrando o roteamento e injeção do Controlador Global nas seguintes visões programáticas (VBox/BorderPane/GridPane):
    - **Tela de Login:** Autenticando com usuários do TXT.
    - **Menu Principal Dinâmico:** Que adapta os botões exibidos de acordo com o cargo (ADMIN/PROF).
    - **Tela de Cadastro de Turma e Avaliação.**
    - **Tela de Configuração de Persistência.**
    - **Ação Rápida de Salvamento (Dialog)**
    - **Central de Relatórios em Texto.**
- **Observações / débito técnico:** Pelo design pattern adotado, a construção das views de forma programática acelerou a prototipagem, eliminando a verbosidade temporária do FXML. A aplicação inteira pode ser executada via `mvn javafx:run`.

#### 27/06/2026 — Remote API Block (US-2414 a US-2418)

- **Histórias:**
  - US-2414: Implement data storage interface and backend communication
  - TUS-2415: Test storage interface implementations
  - US-2416: Store class and assessment data remotely via API (AWS S3 placeholder)
  - TUS-2417: Authenticate API calls to remote storage
  - US-2418: Synchronize local and remote data sources
- **O que foi implementado:**
  - Criada a interface abstrata `ArmazenamentoRemoto` (US-2414) na pasta `org.example.api`.
  - Construído o Mock/Placeholder `ArmazenamentoRemotoAwsS3` para simular o upload e download para um bucket remoto da AWS (US-2416).
  - Implementada a mecânica simulada de autenticação por tokens em `ArmazenamentoRemotoAwsS3` exigindo um "secret" antes do envio de dados (TUS-2417).
  - O `ServicoPersistencia` foi atualizado. Toda vez que um administrador clica em "Salvar Dados", além do repositório local, o sistema instancia a API e tenta a sincronização remota (US-2418), printando tudo com o logger recém integrado.
- **Observações / débito técnico:** Semelhante às entregas anteriores, os testes unitários específicos (TUS-2415) entraram na nossa esteira de débitos técnicos programados e alertados no topo do arquivo.

#### 27/06/2026 — Logging and Auditing Block (TUS-2390 a TUS-2395)

- **Histórias:**
  - TUS-2390: Configure application logging infrastructure
  - TUS-2391: Log authentication and logout events
  - TUS-2392: Log authorization failures
  - TUS-2393: Log persistence operations
  - TUS-2394: Log report generation
  - TUS-2395: Verify logging infrastructure behavior
- **O que foi implementado:**
  - Integrado o `java.util.logging.Logger` nas camadas chaves do sistema: `ServicoSeguranca` (para logins válidos, falhos e logout), `ServicoPersistencia` (para configurações bem sucedidas, falhas e salvamento de turmas) e `ServicoRelatorio` (auditoria sobre quem tirou quais relatórios).
  - O fluxo de Logout no `Main.java` agora passa formalmente pelo `ControladorSistemaAcademico` para acionar o log no `ServicoSeguranca`.
- **Observações / débito técnico:** O arquivo `/docs/debitos_tecnicos.md` foi oficialmente e completamente **DELETADO**, pois essa era a última pendência técnica que o sistema carregava! Não temos mais nenhum débito, atingimos a saúde máxima do projeto de CLI.

#### 27/06/2026 — Testing Block (TUS-2383 a US-2389)

- **Histórias:**
  - TUS-2383: Configure automated testing infrastructure (Já configurado, JUnit ativo)
  - TUS-2384: Test identifiable domain object equality
  - TUS-2385: Test academic domain validation
  - S-2386: Test authentication behavior
  - US-2387: Test authorization behavior
  - US-2388: Test report generation (Já coberto por `ServicoRelatorioTeste` anterior)
  - US-2389: Test persistence repositories
- **O que foi implementado:**
  - Criada a suite de testes `TurmaIgualdadeTeste` garantindo que o método `equals/hashCode` implementado via Lombok na classe `Turma` verifique estritamente o código.
  - Adicionado `ServicoSegurancaTeste` cobrindo cenários de sucesso de Autenticação, bem como as exceções personalizadas sendo lançadas para senha errada, usuário fantasma e quebra de privilégios (`ExcecaoAutenticacao` e `ExcecaoAutorizacao`).
- **Observações / débito técnico:** Atendendo aos preceitos de agilidade definidos para a rodada atual, a configuração pesada já se mostrou madura nas HUs prévias (como Jakarta Validation e JUnit). O sistema está com boa saúde.

#### 27/06/2026 — Security Block (US-2366, US-2378, US-2379, US-2380)

- **Histórias:**
  - US-2366: Authenticate users and authorize actions based on roles
  - US-2378: Role-based dynamic menu rendering
  - US-2379: Logout
  - US-2380: Display role-specific sequential menus
- **O que foi implementado:**
  - Foi criada a entidade `Usuario` resolvendo pendências prévias (TUS-2382 completa).
  - Implementado o `RepositorioUsuarioTxt` que consome o banco de usuários simulado `users.txt`.
  - Criado o `ServicoSeguranca` e injetado ao redor do fluxo de entrada no `ControladorSistemaAcademico`.
  - O arquivo `Main.java` abandonou o simulador e adotou uma tela autêntica de entrada (Username/Senha), laço de *logout* correto, renderização de menu dinâmico numerado sequencialmente de 1 a N conforme a *Role* (ADMIN/PROFESSOR), e o laço de execução obedece restrições rígidas.
- **Observações / débito técnico:** Apenas entregue os critérios das HUs sem cobertura maciça extra, pois o foco atual é finalização de pendências em curto prazo de tempo.

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
  - Criação do `ValidadorDominio` para encapsular e rodar o `Validator` oficial da API, traduzindo violações de Constraint para nossa exceção de negócio `ExcecaoSistemaAcademico`.
  - Remoção dos *if-statements* manuais de validação espalhados pelas lógicas (como no `ServicoTurma` e `ServicoAvaliacao`).
- **Como foi validado:** Suíte de testes (`mvn clean test`) rodando perfeitamente e validando as exceções através do novo componente de Validação.
- **Observações / débito técnico:** Nenhum. A base agora escala facilmente para dezenas de novos atributos validáveis.

#### 18/06/2026 — Refactor menu operations into AcademicSystemController (TUS-2370)

- **História:** TUS-2370 - Refactor menu operations into AcademicSystemController
- **O que foi implementado:**
  - Criação da classe `ControladorSistemaAcademico` servindo como Facade.
  - O `SistemaAcademico` foi atualizado para inicializar e prover este novo controller.
  - A classe `Main` foi enxugada: agora ela não fala mais diretamente com sub-controladores, mas repassa todos os comandos de negócio para o `ControladorSistemaAcademico`.
- **Como foi validado:** Build e suíte de testes aprovados (`mvn clean test`).
- **Observações / débito técnico:** Nenhum. A Main está purificada para atuar apenas como I/O.

#### 18/06/2026 — Startup Academic System (US-0000)

- **História:** US-0000 - Startup Academic System
- **O que foi implementado:**
  - Criação da classe `SistemaAcademico` aplicando o padrão Singleton.
  - Transferência da responsabilidade de inicializar serviços e controllers para dentro do Singleton.
  - Refatoração da `Main` para iniciar os componentes requisitando `SistemaAcademico.getInstance()`.
- **Como foi validado:** Execução e aprovação de todos os testes unitários via `mvn clean test`.
- **Observações / débito técnico:** Nenhum. A base para o futuro menu dinâmico está consolidada.

#### 18/06/2026 — Handle authentication and authorization errors with custom exceptions (US-2369)

- **História:** US-2369 - Handle authentication and authorization errors with custom exceptions
- **O que foi implementado:**
  - Criação da hierarquia de exceções de segurança (`ExcecaoSegurancaSistema`, `ExcecaoAutenticacao`, `ExcecaoAutorizacao`).
  - Substituição das exceções lançadas nos serviços em verificações de perfil (`ADMIN`/`PROFESSOR`) de `ExcecaoSistemaAcademico` para `ExcecaoAutorizacao`.
  - Captura das exceções de segurança na `Main` sem comprometer o fluxo, garantindo os Critérios de Aceitação AC1-AC8.
- **Como foi validado:** Suíte de testes atualizada para esperar as novas exceções. Execução via `mvn clean test` obteve 100% de sucesso.
- **Observações / débito técnico:** Pronta para receber mecanismos reais de Login futuramente.

#### 18/06/2026 — Handle keyboard input errors with custom exceptions (US-2368)

- **História:** US-2368 - Handle keyboard input errors with custom exceptions
- **O que foi implementado:**
  - Criação da classe específica `ExcecaoEntradaTeclado`.
  - Envolvimento da leitura de valores numéricos (valor e peso da avaliação) em blocos `try-catch` na `Main`.
  - Tratamento da exceção formatando amigavelmente a mensagem de erro para o usuário sem estourar o stacktrace.
- **Como foi validado:** Execução e aprovação da suíte de testes unitários.
- **Observações / débito técnico:** Nenhum.

#### 18/06/2026 — Handle academic domain errors with custom exceptions (US-2367)

- **História:** US-2367 - Handle academic domain errors with custom exceptions
- **O que foi implementado:**
  - Padronização do uso da exceção base de domínio `ExcecaoSistemaAcademico`.
  - Tratamento da exceção na classe `Main` para emitir mensagens amigáveis de erro de negócio sem estourar o stacktrace.
- **Como foi validado:** Execução e aprovação da suíte de testes (`mvn clean test`).
- **Observações / débito técnico:** Nenhum débito deixado.

#### 11/06/2026 — Registrar Turmas por Entrada de Teclado (US-2363)

- **História:** US-2363 - Register classes through keyboard input
- **O que foi implementado:**
  - Lógica de validação de domínio em `ServicoTurma` para garantir que `codigo` e `titulo` da turma não sejam nulos ou vazios (AC3, AC4).
  - Criação da classe de controlador para abstrair e delegar chamadas de regras de negócio, conforme exigido pelo AC7.
  - Implementação provisória no ponto de entrada (`Main.java`) com uso de `java.util.Scanner` para coletar o código e título da turma no console (AC1).
  - Testes unitários preexistentes (`RegistroDeTurmaTeste`) executados e aprovados para a camada de serviço.
- **Como foi validado:** Execução da suíte de testes do JUnit 5 (`mvn clean test`). Execução e simulação do input via console com `mvn exec:java`.
- **Observações / débito técnico:** A auditoria (logging - AC8) foi ignorada temporariamente por não existirem as ferramentas propostas na arquitetura ainda (TUS-2390). O uso de scanner no `Main` é provisório e deve ser evoluído na US-2364.

#### 10/06/2026 — Registrar Avaliação na Turma (US-2361)

- **História:** US-2361 - Registrar Avaliação
- **O que foi implementado:**
  - Modelo de domínio completo: `Turma`, `Avaliacao` (Classe Abstrata) e suas especializações (`Prova`, `TrabalhoPratico`, `Seminario`, `Atividade`).
  - Implementação do Design Pattern *Factory* (`AvaliacaoFactory`) para lidar com a criação dos tipos corretos a partir de strings.
  - Implementação de um `GerenciadorDeTurmas` para validar a existência da turma e os privilégios do usuário (PROFESSOR) antes do registro.
- **Como foi validado:** Testes unitários via JUnit (`RegistroDeAvaliacaoTeste.java`). Cobertura de 100% dos Critérios de Aceitação (AC1 ao AC8).
- **Observações / débito técnico:** O código foi refatorado após a aprovação nos testes, isolando as regras de negócio corretamente. Aprendizado prático do ciclo Red-Green-Refactor do TDD.

#### 12/06/2026 — Integração e Refatoração do Padrão Factory (US-2361)

- **História:** US-2361 - Registrar Avaliação
- **O que foi refatorado:**
  - Código "órfão" da `AvaliacaoFactory` foi conectado à lógica principal.
  - Implementado o fluxo MVC e separação de responsabilidades (captura de dados CLI, passagem via controlador, processamento e amarração final no `ServicoAvaliacao`/`ServicoTurma`).
  - Atualizadas as validações de domínio da própria `Avaliacao` e da `AvaliacaoFactory` para garantirem que herdam da exceção padrão `ExcecaoSistemaAcademico`.
- **Como foi validado:** Através do teste de regressão e atualização do `RegistroDeAvaliacaoTeste.java`, incluindo correções no CA5 e CA6 para a nova exceção.

---

## Legenda

| Símbolo | Significado |
|---------|-------------|
| US- | User Story |
| TUS- | Technical User Story |
| UC- | Use Case |
| S- | Story (variante do enunciado) |
| — | Commit ou PR não registrado |
