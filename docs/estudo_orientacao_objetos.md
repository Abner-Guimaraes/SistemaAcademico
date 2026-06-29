# Estudo de Orientação a Objetos — Sistema Acadêmico

Material de estudo que relaciona os conceitos teóricos de Programação Orientada a Objetos (POO) com a implementação real do projeto Java localizado em `academic-system-java/`.

**Documentação complementar:** [contexto.md](contexto.md) · [arquitetura.md](arquitetura.md) · [historico_tarefas.md](historico_tarefas.md)

---

## Introdução

### Arquitetura do projeto

O Sistema Acadêmico é uma aplicação Java 21 organizada em camadas, construída com Maven e evoluída incrementalmente por histórias de usuário. A arquitetura segue uma separação clara entre:

| Camada | Pacote | Responsabilidade |
|--------|--------|------------------|
| Apresentação | `Main`, `gui` | Captura de entrada e exibição de saída (CLI e JavaFX) |
| Controle | `controller` | Orquestração de casos de uso |
| Serviços | `service` | Regras de negócio e autorização |
| Domínio | `model` | Entidades e comportamentos do negócio acadêmico |
| Infraestrutura | `repository`, `api`, `validation`, `exception` | Persistência, integração remota, validação e tratamento de erros |

O ponto de entrada da aplicação gráfica é `MainFX`; o da linha de comando é `Main`. Ambos obtêm o controlador central por meio do Singleton `SistemaAcademico`, que inicializa e conecta todos os serviços na construção:

```java
this.servicoTurma = new ServicoTurma();
this.servicoAvaliacao = new ServicoAvaliacao(this.servicoTurma);
this.servicoRelatorio = new ServicoRelatorio();
this.servicoPersistencia = new ServicoPersistencia();
this.servicoSeguranca = new ServicoSeguranca(new RepositorioUsuarioTxt("users.txt"));
this.controladorSistemaAcademico = new ControladorSistemaAcademico(...);
```

### Objetivo deste documento

Explicar, de forma didática e técnica, como os pilares da POO, os princípios de design orientado a objetos e os princípios GRASP foram aplicados (ou não) no código-fonte do projeto. Cada conceito é ilustrado com classes reais, para servir como material de revisão da equipe e preparação para a apresentação do trabalho semestral.

---

## Os quatro pilares da Orientação a Objetos

### Encapsulamento

**Definição teórica:** Encapsulamento é o princípio de ocultar os detalhes internos de um objeto e expor apenas o necessário por meio de uma interface pública controlada (métodos de acesso). Isso protege a integridade dos dados e reduz o acoplamento entre componentes.

**Aplicação no projeto:**

A classe `Turma` encapsula seus atributos como `private` e expõe acesso controlado via Lombok (`@Getter`) e pelo método `getAvaliacoes()`, que retorna uma **cópia defensiva** da lista interna — impedindo que código externo modifique a coleção sem passar por `adicionarAvaliacao()`:

```java
public List<Avaliacao> getAvaliacoes() {
    return new ArrayList<>(avaliacoes);
}
```

Da mesma forma, `Avaliacao` mantém `nome`, `valor` e `peso` privados, com acesso somente leitura via `@Getter`. A entidade `Usuario` encapsula credenciais e papel (`role`) sem expor lógica de autenticação — essa responsabilidade pertence ao `ServicoSeguranca`.

O `ServicoPersistencia` encapsula o estado interno `tipoPersistenciaAtual`, expondo-o apenas pelo método `getTipoPersistencia()` quando necessário para relatórios.

**Exemplo concreto:** quando um professor registra uma avaliação, a `Main` ou o `GerenciadorTelas` não manipulam diretamente a lista de avaliações da turma; delegam ao `ControladorSistemaAcademico` → `ServicoAvaliacao` → `Turma.adicionarAvaliacao()`.

---

### Herança

**Definição teórica:** Herança permite que uma classe (subclasse) reutilize e especialize atributos e comportamentos de outra classe (superclasse), estabelecendo uma relação *é-um* (*is-a*).

**Aplicação no projeto:**

#### Hierarquia de avaliações

A classe abstrata `Avaliacao` concentra os atributos comuns (`nome`, `valor`, `peso`) e regras de validação Jakarta. As subclasses concretas especializam o tipo sem repetir código:

```
Avaliacao (abstrata)
├── Prova
├── TrabalhoPratico
├── Seminario
└── Atividade
```

Cada subclasse apenas invoca o construtor da superclasse:

```java
public class Prova extends Avaliacao {
    public Prova(String nome, double valor, double peso) {
        super(nome, valor, peso);
    }
}
```

#### Hierarquia de exceções

O projeto também utiliza herança para organizar erros por categoria:

```
RuntimeException
├── ExcecaoSistemaAcademico          (erros de domínio/negócio)
└── ExcecaoSegurancaSistema          (erros de segurança)
    ├── ExcecaoAutenticacao          (login inválido)
    └── ExcecaoAutorizacao           (permissão negada)
```

Isso permite que a camada de apresentação trate categorias distintas de erro de forma diferenciada — por exemplo, `Main` captura `ExcecaoAutenticacao` na tela de login sem interromper o sistema inteiro.

**Observação:** a classe `GerenciadorDeTurmas` é um resquício de uma versão anterior do domínio. Hoje a lógica equivalente está em `ServicoTurma` e `ServicoAvaliacao`; `GerenciadorDeTurmas` permanece apenas referenciada em testes (`RegistroDeAvaliacaoTeste`).

---

### Polimorfismo

**Definição teórica:** Polimorfismo é a capacidade de tratar objetos de diferentes classes por meio de uma referência comum à superclasse ou interface, permitindo que cada implementação responda de forma particular à mesma operação.

**Aplicação no projeto:**

#### 1. Factory + referência abstrata

`AvaliacaoFactory.criar()` retorna objetos concretos (`Prova`, `Seminario`, etc.) tipados como `Avaliacao`. O código consumidor não precisa conhecer a subclasse específica:

```java
Avaliacao novaAvaliacao = AvaliacaoFactory.criar(tipo, nome, valor, peso);
turmaEncontrada.adicionarAvaliacao(novaAvaliacao);
```

#### 2. Relatórios com tipo dinâmico

Em `ServicoRelatorio.gerarResumoAvaliacoes()`, o tipo concreto de cada avaliação é obtido em tempo de execução:

```java
a.getClass().getSimpleName()  // retorna "Prova", "Seminario", etc.
```

#### 3. Strategy via interface `RepositorioTurma`

`ServicoPersistencia` seleciona a implementação adequada (TXT, XML ou JSON) e invoca o mesmo método polimórfico `salvarTodas()`:

```java
RepositorioTurma repo;
switch (tipoPersistenciaAtual) {
    case "TXT":  repo = new RepositorioTurmaTxt();  break;
    case "XML":  repo = new RepositorioTurmaXml();  break;
    case "JSON": repo = new RepositorioTurmaJson(); break;
}
repo.salvarTodas(turmas);
```

#### 4. Interface `ArmazenamentoRemoto`

A sincronização remota em `ServicoPersistencia` utiliza a interface `ArmazenamentoRemoto`, implementada por `ArmazenamentoRemotoAwsS3`. O serviço de persistência depende do contrato, não da implementação concreta.

---

### Abstração

**Definição teórica:** Abstração consiste em modelar apenas os aspectos essenciais de um conceito, omitindo detalhes irrelevantes para o contexto do sistema. Em Java, abstração se materializa por meio de classes abstratas e interfaces.

**Aplicação no projeto:**

| Abstração | Tipo | Papel |
|-----------|------|-------|
| `Avaliacao` | Classe abstrata | Define o contrato comum de toda avaliação acadêmica |
| `RepositorioTurma` | Interface | Define *o que* persistir, sem expor *como* (TXT/XML/JSON) |
| `RepositorioUsuario` | Interface | Abstrai a origem dos usuários (arquivo TXT hoje) |
| `ArmazenamentoRemoto` | Interface | Abstrai comunicação com backend remoto |
| `ValidadorDominio` | Classe utilitária | Oculta a complexidade do Jakarta Bean Validation |

A classe abstrata `Avaliacao` não pode ser instanciada diretamente — apenas suas subclasses representam entidades concretas do domínio. Isso força o uso da `AvaliacaoFactory` e garante que todo objeto de avaliação possua tipo definido.

A interface `RepositorioTurma` possui um único método:

```java
void salvarTodas(List<Turma> turmas);
```

Essa abstração mínima isola `ServicoPersistencia` dos detalhes de formatação de cada formato de arquivo.

---

## Design Orientado a Objetos

### Separação de responsabilidades

Cada classe do projeto possui uma responsabilidade principal bem definida:

- **`Main` / `GerenciadorTelas`:** apenas interação com o usuário (entrada/saída).
- **`ControladorSistemaAcademico`:** coordena casos de uso, sem implementar regras de negócio.
- **`ServicoTurma`, `ServicoAvaliacao`, etc.:** concentram regras de negócio e verificações de autorização.
- **`RepositorioTurmaTxt` (e similares):** apenas leitura/escrita em arquivos.
- **`Turma`, `Avaliacao`:** representam o domínio acadêmico.

Essa separação ficou explícita na refatoração documentada em TUS-2396 a TUS-2400, quando a lógica de avaliações foi extraída de `ServicoTurma` para `ServicoAvaliacao`, aplicando o **Single Responsibility Principle (SRP)** do SOLID.

### Organização das camadas

O fluxo típico de uma operação segue a direção:

```
Apresentação → Controller → Service → Model / Repository
```

**Exemplo — cadastro de turma (ADMIN):**

1. `Main` lê código e título via `Scanner`.
2. `Main` chama `controller.registrarTurma(codigo, titulo, role)`.
3. `ControladorSistemaAcademico` delega a `servicoTurma.registrarTurma(...)`.
4. `ServicoTurma` verifica se o usuário é `ADMIN`, cria a `Turma`, valida com `ValidadorDominio` e adiciona à lista interna.

A camada de apresentação **nunca** acessa diretamente `ServicoTurma` ou `RepositorioTurmaTxt` — sempre passa pelo controlador obtido via `SistemaAcademico.getInstance()`.

### Benefícios da arquitetura utilizada

1. **Testabilidade:** serviços como `ServicoRelatorio` e `ServicoSeguranca` possuem testes unitários independentes da interface (`ServicoRelatorioTeste`, `ServicoSegurancaTeste`).
2. **Extensibilidade:** novos formatos de persistência exigem apenas uma nova implementação de `RepositorioTurma`, sem alterar `ServicoPersistencia` (além do `switch` de seleção).
3. **Manutenibilidade:** alterações na GUI (`GerenciadorTelas`) não impactam regras de negócio, desde que o contrato do controlador seja respeitado.
4. **Reutilização:** `ServicoRelatorio` é compartilhado entre CLI e GUI, pois ambas usam o mesmo `ControladorSistemaAcademico`.

### Decisões de projeto adotadas

As principais decisões estão registradas formalmente em [arquitetura.md](arquitetura.md) (ADRs). Destacam-se:

| Decisão | Padrão / princípio | Classes envolvidas |
|---------|-------------------|-------------------|
| ADR-001 | Factory | `AvaliacaoFactory` |
| ADR-004 | Bean Validation | `ValidadorDominio`, `Turma`, `Avaliacao` |
| ADR-005 | Facade | `ControladorSistemaAcademico` |
| ADR-006 | Singleton | `SistemaAcademico` |
| ADR-008 | Repository | `RepositorioTurma` e implementações |
| ADR-010 | Strategy | `ServicoPersistencia` + repositórios |
| ADR-012 | SRP / camada de serviços | `ServicoTurma`, `ServicoAvaliacao`, etc. |

---

## Princípios GRASP

GRASP (*General Responsibility Assignment Software Patterns*) são padrões de atribuição de responsabilidades que orientam *qual classe deve fazer o quê* em um design orientado a objetos.

### Controller

**Definição:** Atribuir a responsabilidade de tratar eventos do sistema a uma classe que representa o módulo de software, o dispositivo ou um agregador de casos de uso — sem implementar a lógica de negócio diretamente.

**Classe controladora do projeto:** `ControladorSistemaAcademico`

**Justificativa:** Esta classe recebe as requisições originadas da camada de apresentação (`Main`, `GerenciadorTelas`) — como `registrarTurma()`, `registrarAvaliacao()`, `salvarDados()` — e as repassa aos serviços especializados. Ela não contém regras de validação de domínio, verificação de perfil ou formatação de relatórios; limita-se a orquestrar.

```java
public void registrarTurma(String codigo, String titulo, String usuarioLogado) {
    servicoTurma.registrarTurma(codigo, titulo, usuarioLogado);
}
```

Essa escolha mantém a camada de apresentação desacoplada dos serviços e centraliza o ponto de entrada dos casos de uso, conforme ADR-005.

---

### Information Expert (Especialista da Informação)

**Definição:** Atribuir uma responsabilidade à classe que possui a informação necessária para cumpri-la.

**Exemplos no projeto:**

| Responsabilidade | Especialista | Motivo |
|-----------------|--------------|--------|
| Adicionar avaliação a uma turma | `Turma` | Possui a lista `avaliacoes` e o método `adicionarAvaliacao()` |
| Manter turmas cadastradas | `ServicoTurma` | Detém `turmasCadastradas` e conhece regras de registro |
| Validar credenciais | `ServicoSeguranca` + `RepositorioUsuarioTxt` | O repositório conhece os dados de `users.txt`; o serviço aplica a lógica de comparação |
| Formatar relatório de pesos | `ServicoRelatorio` | Recebe a lista de turmas e itera sobre suas avaliações para calcular totais |
| Serializar turmas em TXT | `RepositorioTurmaTxt` | Conhece o formato do arquivo e como mapear `Turma`/`Avaliacao` para linhas delimitadas |

Exemplo em `Turma`:

```java
public void adicionarAvaliacao(Avaliacao avaliacao) {
    if (avaliacao == null) {
        throw new IllegalArgumentException("A avaliação não pode ser nula");
    }
    this.avaliacoes.add(avaliacao);
}
```

A turma é o especialista natural para gerenciar sua própria coleção de avaliações.

---

### Creator

**Definição:** Atribuir a responsabilidade de criar uma instância de classe B a uma classe A que agrega, contém, registra ou utiliza intensivamente B, ou que possui os dados de inicialização de B.

**Aplicação:** `AvaliacaoFactory` centraliza a criação das subclasses de `Avaliacao`. Embora não seja a turma que cria diretamente, a factory concentra o conhecimento sobre qual subclasse instanciar com base no tipo informado pelo usuário — evitando lógica de `switch` espalhada em `ServicoAvaliacao`.

`SistemaAcademico` também atua como *creator* do grafo de objetos da aplicação, instanciando serviços e o controlador na inicialização.

---

### Low Coupling (Baixo Acoplamento)

**Definição:** Manter baixa dependência entre classes, de modo que alterações em uma não propaguem efeitos desnecessários em outras.

**Aplicação:**

- `ServicoSeguranca` depende da **interface** `RepositorioUsuario`, não de `RepositorioUsuarioTxt` diretamente no contrato público (a implementação concreta é injetada no construtor em `SistemaAcademico`).
- `ServicoPersistencia` depende de `RepositorioTurma`, não dos detalhes de escrita em disco de cada formato.
- `ServicoAvaliacao` recebe `ServicoTurma` por construtor, acoplando-se apenas ao serviço necessário para localizar turmas — não à camada de apresentação ou persistência.

**Limite observado:** `ServicoPersistencia.salvarDados()` instancia diretamente `ArmazenamentoRemotoAwsS3` (`new ArmazenamentoRemotoAwsS3()`), o que aumenta o acoplamento. Uma evolução seria injetar a implementação de `ArmazenamentoRemoto` pelo construtor, como já ocorre com `RepositorioUsuario` em `ServicoSeguranca`.

---

### High Cohesion (Alta Coesão)

**Definição:** Manter as responsabilidades de uma classe relacionadas e focadas, evitando classes que acumulam funções díspares.

**Aplicação:**

A refatoração que separou `ServicoAvaliacao` de `ServicoTurma` (TUS-2396/2397) é o exemplo mais claro: antes, um único serviço acumulava registro de turmas, registro de avaliações e, em versões anteriores, persistência. Hoje cada serviço possui coesão elevada:

- `ServicoTurma` → turmas
- `ServicoAvaliacao` → avaliações
- `ServicoRelatorio` → relatórios
- `ServicoPersistencia` → persistência e sincronização
- `ServicoSeguranca` → autenticação e autorização

---

### Indirection (Indireção)

**Definição:** Introduzir um objeto intermediário para mediar a comunicação entre componentes, reduzindo acoplamento direto.

**Aplicação:**

- `ControladorSistemaAcademico` é o intermediário entre `Main`/`GerenciadorTelas` e os serviços.
- `ValidadorDominio` medeia entre as entidades de domínio e a API Jakarta Validation — os serviços chamam `ValidadorDominio.validate(objeto)` sem conhecer `Validator`, `ValidatorFactory` ou `ConstraintViolation`.
- `RepositorioTurma` media entre `ServicoPersistencia` e o sistema de arquivos.

---

### Polymorphism (Polimorfismo — GRASP)

**Definição:** Atribuir responsabilidades usando interfaces ou classes abstratas para que implementações possam ser substituídas.

**Aplicação:** Vide seção de Polimorfismo acima. O princípio GRASP de Polimorfismo orienta a decisão de usar `RepositorioTurma` como interface em vez de métodos separados `salvarTxt()`, `salvarXml()` e `salvarJson()` em `ServicoPersistencia`.

---

### Pure Fabrication (Fabricação Pura)

**Definição:** Criar classes que não representam conceitos do domínio, mas são necessárias para manter baixo acoplamento e alta coesão.

**Exemplos no projeto:**

| Classe | Por que é fabricação pura |
|--------|--------------------------|
| `ServicoTurma`, `ServicoAvaliacao`, etc. | "Serviço" não é um conceito do domínio acadêmico; é uma construção arquitetural |
| `ValidadorDominio` | Validador genérico não existe no domínio de turmas e avaliações |
| `RepositorioTurmaTxt` | Repositório de arquivo não é entidade de negócio |
| `ControladorSistemaAcademico` | Controlador é artefato de arquitetura, não conceito acadêmico |
| `AvaliacaoFactory` | Fábrica é padrão de criação, não entidade do mundo real |

Essas classes existem para organizar responsabilidades que, se colocadas em `Turma` ou `Main`, violariam coesão e acoplamento.

---

### Protected Variations (Variações Protegidas)

**Definição:** Identificar pontos de instabilidade ou variação prevista e criar estruturas estáveis que protejam outros elementos dessas variações.

**Aplicação:**

- **Formato de persistência:** a interface `RepositorioTurma` protege `ServicoPersistencia` das variações TXT/XML/JSON. Adicionar um formato CSV exigiria nova implementação, não reescrita do serviço.
- **Tipo de avaliação:** a hierarquia `Avaliacao` + `AvaliacaoFactory` protege `ServicoAvaliacao` de conhecer cada subclasse individualmente.
- **Backend remoto:** `ArmazenamentoRemoto` protege o sistema da variação entre mock S3 e uma implementação real futura.

**Ponto de melhoria:** o `switch` em `ServicoPersistencia` ainda precisa ser alterado ao adicionar um novo formato. Uma evolução seria um mapa de fábricas ou injeção de estratégia, protegendo ainda mais contra variações.

---

## Considerações finais

### Contribuição dos conceitos de POO para a qualidade do projeto

A aplicação sistemática de encapsulamento, herança, polimorfismo e abstração permitiu que o Sistema Acadêmico crescesse de um protótipo com registro de avaliações (US-2361) até uma aplicação com autenticação RBAC, múltiplos formatos de persistência, relatórios, logging, interface gráfica JavaFX e sincronização remota simulada — sem colapsar em uma única classe monolítica.

A combinação de camadas (apresentação → controller → service → model/repository) com princípios GRASP tornou possível:

- testar serviços de forma isolada;
- trocar a interface de usuário (CLI ↔ JavaFX) sem reescrever regras de negócio;
- estender formatos de persistência e tipos de avaliação com impacto localizado;
- tratar erros de forma categorizada (domínio vs. segurança vs. entrada de teclado).

### Principais aprendizados

1. **Separação precoce de responsabilidades facilita refatorações.** A extração de `ServicoAvaliacao` e a simplificação do `ControladorSistemaAcademico` demonstram que investir em coesão desde cedo reduz custo de manutenção.

2. **Abstrações bem definidas pagam dividendos.** A interface `RepositorioTurma` com três implementações provou o valor do polimorfismo na prática — o mesmo método `salvarTodas()` funciona para TXT, XML e JSON.

3. **Fabricações puras são necessárias, mas devem ser conscientes.** Serviços, repositórios e validadores não existem no domínio acadêmico, mas são indispensáveis para manter o modelo de negócio (`Turma`, `Avaliacao`) enxuto e expressivo.

4. **Código legado exige atenção.** `GerenciadorDeTurmas` ilustra como classes podem ficar obsoletas após refatorações — permanecem nos testes, mas não no fluxo principal. Identificar e documentar esse tipo de resquício é parte do processo de evolução orientada a objetos.

5. **Princípios orientam, mas a implementação pode ser incremental.** Nem todo princípio GRASP ou SOLID está aplicado de forma ideal em todos os pontos (como a instanciação direta de `ArmazenamentoRemotoAwsS3`). Reconhecer essas oportunidades de melhoria faz parte do aprendizado técnico da disciplina.

---

*Documento elaborado com base no código-fonte em `academic-system-java/src/main/java/org/example/` e nas decisões registradas em `docs/arquitetura.md`.*
