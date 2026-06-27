# Registro de Decisões de Arquitetura (ADR)

Este documento registra decisões de arquitetura, design de código, padrões adotados e trade-offs do projeto. Cada entrada segue o formato **ADR (Architecture Decision Record)**.

**Documentação geral:** [contexto.md](contexto.md)  
**Histórico de tarefas:** [historico_tarefas.md](historico_tarefas.md)

---

## Índice de decisões

| # | Título | Data | Status |
|---|--------|------|--------|
| ADR-001 | Uso do Padrão Factory para Criação de Avaliações | 10/06/2026 | Aceita |
| ADR-002 | Validação de Domínio via Cláusulas de Guarda no Service (Provisória) | 11/06/2026 | Substituída (ADR-004) |
| ADR-003 | Delegação de Operações via Controller | 11/06/2026 | Evoluída (ADR-005) |
| ADR-004 | Validação com Jakarta Bean Validation | 18/06/2026 | Aceita |
| ADR-005 | Centralização de Operações no ControladorSistemaAcademico | 18/06/2026 | Aceita |
| ADR-006 | Padrão Singleton para o SistemaAcademico | 18/06/2026 | Aceita |
| ADR-007 | Hierarquia de Exceções Customizadas | 18/06/2026 | Aceita |
| ADR-008 | Abstração de Repositório para Persistência | 26/06/2026 | Aceita |
| ADR-009 | Extração da Lógica de Relatórios para ServicoRelatorio | 26/06/2026 | Aceita |
| ADR-010 | Strategy e Factory para Configuração de Persistência (ServicoPersistencia) | 26/06/2026 | Aceita |
| ADR-011 | Segregação do Fluxo de Segurança e Repository (ServicoSeguranca) | 27/06/2026 | Aceita |
| ADR-012 | Refatoração e Desmembramento da Camada de Serviços | 27/06/2026 | Aceita |
| ADR-013 | Implementação de Logging e Auditoria Descentralizada | 27/06/2026 | Aceita |

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

---

## ADR-003 — Delegação de Operações via Controller

| Campo | Conteúdo |
|-------|----------|
| **Status** | Aceita |
| **Data** | 11/06/2026 |
| **Autor(es)** | Desenvolvedor |
| **História relacionada** | US-2363 |

### Contexto / Problema

A US-2363 estabelece (no AC7) que as requisições oriundas da entrada de usuário devem passar pela camada de "controller" e "service". Fazer a chamada direta da interface de usuário (`Main.java` e `Scanner`) para o `TurmaService` uniria camadas que futuramente devem estar separadas (UI CLI/JavaFX e lógica de aplicação).

### Decisão Tomada

Criada a classe `TurmaController` para orquestrar as requisições de turmas e delegar a execução para o `TurmaService`. A injeção da dependência do Service é feita via construtor no Controller. 

### Justificativa

Em conformidade com a Arquitetura em Camadas (Seção 9 do contexto), a camada de Coordenação (`org.example.controller`) blinda a UI das regras de negócio do Service. O uso da injeção de dependência via construtor favorece os testes (facilidade para mockar o `TurmaService` futuramente).

### Consequências

- **Positivas:** Encapsulamento correto das responsabilidades; arquitetura preparada para suportar múltiplas interfaces (ex: CLI e depois JavaFX).
- **Negativas / trade-offs:** Mais classes no fluxo (overengineering para um sistema pequeno, mas necessário por propósitos educacionais da disciplina).
- **Ações de acompanhamento:** A TUS-2370 prevê refatorar operações para um `AcademicSystemController` central. O `TurmaController` deverá ser mesclado ou coordenado de acordo com as necessidades dessa futura US.


---

## ADR-004 — Validação com Jakarta Bean Validation

| Campo | Conteúdo |
|-------|----------|
| **Status** | Aceita |
| **Data** | 18/06/2026 |
| **Autor(es)** | Desenvolvedor |
| **História relacionada** | TUS-2371 |

### Contexto / Problema
As validações manuais com cláusulas de guarda (ADR-002) espalhadas pelos serviços e modelos dificultam a manutenção e não seguem um padrão declarativo. É necessário um mecanismo robusto para escalar as validações de domínio.

### Decisão Tomada
Adotar a especificação Jakarta Bean Validation acoplada ao Hibernate Validator. A validação ocorre através da classe `ValidadorDominio`, que centraliza as regras declaradas por meio de anotações (ex: `@NotBlank`, `@PositiveOrZero`) nas entidades de domínio, substituindo os ifs manuais.

### Justificativa
O uso de anotações torna as regras de negócio autoexplicativas e padroniza o retorno dos erros, reduzindo a complexidade ciclomática do código (clean code).

### Consequências
- **Positivas:** Código mais limpo; centralização das validações de entidades; facilidade para criar novas regras no futuro.
- **Negativas / trade-offs:** Adição de novas bibliotecas externas (`jakarta.validation-api` e `hibernate-validator`), aumentando o tamanho do build.

---

## ADR-005 — Centralização de Operações no ControladorSistemaAcademico

| Campo | Conteúdo |
|-------|----------|
| **Status** | Aceita |
| **Data** | 18/06/2026 |
| **Autor(es)** | Desenvolvedor |
| **História relacionada** | TUS-2370 |

### Contexto / Problema
A classe `Main` interagia diretamente com vários controladores específicos (`ControladorTurma`, `ControladorAvaliacao`), violando o princípio do menor conhecimento (Demeter) e dificultando o gerenciamento do fluxo do sistema.

### Decisão Tomada
Criar o `ControladorSistemaAcademico` para atuar como uma *Facade* (Fachada) sobre os controladores de domínio. A camada de UI agora se comunica exclusivamente com este controlador central para invocar casos de uso.

### Justificativa
Essa refatoração centraliza as chamadas de negócio em um único ponto, reduzindo o acoplamento entre a interface do usuário (`Main`) e a camada de controladores internos.

### Consequências
- **Positivas:** Isolamento e simplificação da `Main`, arquitetura mais coesa e melhor preparo para uma futura interface gráfica.

---

## ADR-006 — Padrão Singleton para o SistemaAcademico

| Campo | Conteúdo |
|-------|----------|
| **Status** | Aceita |
| **Data** | 18/06/2026 |
| **Autor(es)** | Desenvolvedor |
| **História relacionada** | US-0000 |

### Contexto / Problema
O sistema precisa de um ponto centralizado e único para inicializar e gerenciar as instâncias de serviços e controladores ao longo de toda a sua execução.

### Decisão Tomada
Implementar a classe `SistemaAcademico` usando o padrão criacional **Singleton**. A classe instancia todas as dependências e amarra os controladores e serviços.

### Justificativa
Garante que haja apenas uma instância dos serviços de domínio e controladores ativos simultaneamente na memória, evitando inconsistências de estado e fornecendo um ponto de acesso global estruturado.

### Consequências
- **Positivas:** Controle total do ciclo de vida dos componentes; injeção manual de dependências facilitada; ponto único de inicialização.
- **Negativas:** Singletons representam estado global e podem dificultar o isolamento de testes no futuro se não forem bem injetados.

---

## ADR-007 — Hierarquia de Exceções Customizadas

| Campo | Conteúdo |
|-------|----------|
| **Status** | Aceita |
| **Data** | 18/06/2026 |
| **Autor(es)** | Desenvolvedor |
| **História relacionada** | US-2367, US-2368, US-2369 |

### Contexto / Problema
O uso indiscriminado de exceções genéricas ou de sistema quebravam a execução abruptamente com stacktraces visíveis para o usuário e misturavam erros de negócio com erros de input.

### Decisão Tomada
Criar uma hierarquia semântica de exceções específicas, derivando de `RuntimeException`: `ExcecaoSistemaAcademico` (negócio), `ExcecaoSegurancaSistema` (com subclasses `ExcecaoAutenticacao` e `ExcecaoAutorizacao`) e `ExcecaoEntradaTeclado`.

### Justificativa
Essa segregação melhora a clareza do código, permitindo que a `Main` capture e trate especificamente cada cenário de erro, retornando mensagens amigáveis em português e recuperando a execução quando necessário (princípios de *clean code*).

### Consequências
- **Positivas:** Melhora da experiência do usuário ao evitar quebras bruscas; código auto-documentado.

---

## ADR-008 — Abstração de Repositório para Persistência

| Campo | Conteúdo |
|-------|----------|
| **Status** | Aceita |
| **Data** | 26/06/2026 |
| **Autor(es)** | Desenvolvedor |
| **História relacionada** | TUS-2362 |

### Contexto / Problema
O sistema precisa exportar e armazenar dados fora da memória (persistência). Misturar a lógica de manipulação de arquivos (TXT, JSON, XML) com a lógica de negócio do sistema quebraria a coesão.

### Decisão Tomada
Implementar o **Repository Pattern**, criando a interface genérica `RepositorioTurma` e, para este momento (TUS-2362), sua implementação concreta `RepositorioTurmaTxt`.

### Justificativa
Desacopla a regra de negócio da implementação de infraestrutura. Isso permitirá, de forma indolor no futuro (ex: TUS-2373, TUS-2374), plugar implementações para XML ou JSON simplesmente substituindo a injeção da dependência.

### Consequências
- **Positivas:** Isola as APIs de IO da camada Service/Model. Preparação pronta para injeção de dependência e Strategy pattern quando houver múltiplas formas de salvar.
- **Negativas:** Requer criar mais classes, mas com um bom ganho de qualidade na arquitetura a longo prazo.

---

## ADR-009 — Extração da Lógica de Relatórios para ServicoRelatorio

| Campo | Conteúdo |
|-------|----------|
| **Status** | Aceita |
| **Data** | 26/06/2026 |
| **Autor(es)** | Desenvolvedor |
| **História relacionada** | US-2375, US-2376, TUS-2399 |

### Contexto / Problema
A geração de relatórios formatados em String estava prevista inicialmente para o Controlador, o que misturaria a lógica de roteamento de requisições com a formatação e as regras pesadas de negócio e cálculo matemático (como soma de pesos das avaliações e checagem de tolerância em doubles).

### Decisão Tomada
Implementada a classe `ServicoRelatorio` na camada de serviços (`org.example.service`). O Controlador apenas repassa a lista de turmas para o serviço gerar as Strings finais dos relatórios, formatadas adequadamente.

### Justificativa
Separação de preocupações (Separation of Concerns). O Controlador permanece enxuto e age unicamente como um *Proxy/Facade*, enquanto o serviço retém as regras complexas, tornando muito mais simples escrever testes unitários independentes da interface (ex: `ServicoRelatorioTeste`).

### Consequências
- **Positivas:** Lógica perfeitamente testável via TDD sem necessidade de subir um controlador completo; fácil extensão para novos relatórios.

---

## ADR-010 — Strategy e Factory para Configuração de Persistência (ServicoPersistencia)

| Campo | Conteúdo |
|-------|----------|
| **Status** | Aceita |
| **Data** | 26/06/2026 |
| **Autor(es)** | Desenvolvedor |
| **História relacionada** | US-2372, US-2373, US-2374, TUS-2398 |

### Contexto / Problema
Com o suporte a diferentes formatos de arquivos (TXT, XML, JSON), acoplar e fixar o formato de salvamento (ex: método `salvarDadosTxt` do controlador) impossibilitava a troca da estratégia de persistência em tempo de execução e violava o princípio OCP (Open/Closed Principle).

### Decisão Tomada
Criado o `ServicoPersistencia` atuando como um "Contexto" do padrão **Strategy**. Ele mantém internamente o tipo configurado (e.g. `XML`) e, dinamicamente no momento do salvamento, resolve qual implementação concreta de `RepositorioTurma` instanciar, além de prover as checagens de autorização. O sistema não utiliza bibliotecas externas, processando JSON e XML via string builder padrão nativo do Java para os Repositórios recém-criados (`RepositorioTurmaXml` e `RepositorioTurmaJson`).

### Justificativa
Desacopla de vez a Controladora principal da infraestrutura de I/O. As requisições de salvamento são roteadas de forma invisível para a Controladora, e novas persistências (ex: Banco de Dados SQL) podem ser plugadas modificando apenas um switch-case (uma micro Factory no próprio serviço).

### Consequências
- **Positivas:** Flexibilidade absoluta no formato dos dados sem mexer no modelo ou nos outros controladores. Assegura também a restrição de perfil para a configuração do sistema em apenas um local.

---

## ADR-011 — Segregação do Fluxo de Segurança e Repository (ServicoSeguranca)

| Campo | Conteúdo |
|-------|----------|
| **Status** | Aceita |
| **Data** | 27/06/2026 |
| **Autor(es)** | Desenvolvedor |
| **História relacionada** | US-2366, US-2378, US-2379, US-2380 |

### Contexto / Problema
O sistema simulava o login apenas pedindo a "Role" (ADMIN ou PROFESSOR) sem checar credenciais. Com a implementação da US-2366, foi necessário verificar senhas reais e carregar dados de um arquivo de configuração `users.txt`. Misturar essa responsabilidade de login e parsing de TXT com os serviços de Turma ou os controladores de negócio poluiria severamente o escopo do domínio.

### Decisão Tomada
Foram criadas a entidade `Usuario`, a interface abstrata `RepositorioUsuario`, sua implementação concreta `RepositorioUsuarioTxt` (Repository Pattern) e o serviço dedicado `ServicoSeguranca` na camada de serviços. A orquestração (injeção de dependência) ocorre no construtor de `SistemaAcademico` (Singleton) e o método `autenticar()` é exposto pelo `ControladorSistemaAcademico`. A UI (`Main.java`) gerencia dinamicamente as opções do menu consultando os privilégios do `Usuario` logado.

### Justificativa
Desacopla regras de identidade e autorização (Security Domain) do domínio core Acadêmico. A adoção da interface de repositório permite, no futuro, migrar facilmente de um TXT para um banco de dados relacional sem modificar uma única linha do `ServicoSeguranca` ou do Controlador. O isolamento no serviço dedicado cumpre com o Single Responsibility Principle (SRP).

### Consequências
- **Positivas:** Clara fronteira arquitetural entre Domínio Acadêmico e Autenticação. Menu dinâmico é inteiramente governado por informações de sessão válidas.
- **Negativas:** Maior número de classes para um sistema pequeno (overengineering preventivo), mas plenamente alinhado aos princípios de qualidade estrutural exigidos.

---

## ADR-012 — Refatoração e Desmembramento da Camada de Serviços

| Campo | Conteúdo |
|-------|----------|
| **Status** | Aceita |
| **Data** | 27/06/2026 |
| **Autor(es)** | Desenvolvedor |
| **História relacionada** | TUS-2396 a TUS-2405 |

### Contexto / Problema
A classe `ServicoTurma` acumulava funções relativas ao gerenciamento de `Turma` e instanciação/validação de `Avaliacao`. Controladores secundários como `ControladorTurma` limitavam a injeção limpa de dependência.

### Decisão Tomada
Criação do `ServicoAvaliacao` separando as obrigações (SRP) e simplificação massiva do `ControladorSistemaAcademico`, que passou a orquestrar os cinco serviços principais de forma isolada.

### Justificativa
Aumento drástico de coesão, deixando a arquitetura alinhada perfeitamente com os preceitos SOLID. Facilita muito a futura implementação do JavaFX.

---

## ADR-013 — Implementação de Logging e Auditoria Descentralizada

| Campo | Conteúdo |
|-------|----------|
| **Status** | Aceita |
| **Data** | 27/06/2026 |
| **Autor(es)** | Desenvolvedor |
| **História relacionada** | TUS-2390 a TUS-2395 |

### Contexto / Problema
Débito técnico: não havia rastreabilidade no sistema para autenticações falhas, tentativas de violação de perfil, e relatórios importantes.

### Decisão Tomada
Utilização do `java.util.logging.Logger` instanciado localmente nas classes chave (`ServicoSeguranca`, `ServicoPersistencia`, `ServicoRelatorio`) emitindo os níveis `INFO` e `WARNING`.

### Justificativa
Sendo uma biblioteca built-in do Java, não inflou o POM.xml e resolve o problema de rastreabilidade de forma limpa, simples e thread-safe.

