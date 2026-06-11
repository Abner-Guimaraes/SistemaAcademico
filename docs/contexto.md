# Contexto do Projeto

Documento central do trabalho semestral de Orientação a Objetos. Consolida as informações do enunciado oficial, do repositório base do professor e do estado atual deste repositório.

---

## 1. Informações Gerais

| Item | Detalhe |
|------|---------|
| **Curso** | Bacharelado em Ciência da Computação |
| **Período** | 3º |
| **Disciplina** | Orientação a Objetos |
| **Professor** | Rodrigo Martins Pagliares |
| **Instituição** | Universidade Federal de Alfenas (UNIFAL-MG) |
| **Apresentação** | 29 de junho de 2026 — Laboratório B207 |
| **Última atualização do enunciado** | 09/06/2026 |
| **Formação de equipes** | Grupos de até 5 estudantes |
| **Documento oficial** | `docs/Semester_Assignment_2026_01.pdf` |
| **Repositório base do professor** | https://github.com/pagliares/academic-system-semester-assignment-2026 |

---

## 2. Visão Geral

O trabalho consiste no desenvolvimento de um **Sistema Acadêmico** em Java, evoluído incrementalmente por meio de **histórias de usuário** e **histórias técnicas** de pequeno porte. O repositório do professor funciona como base e catálogo de requisitos; a especificação detalhada de cada história está no `README.md` do repositório base.

O sistema permite gerenciar turmas e avaliações acadêmicas, com evolução prevista para persistência (TXT, XML, JSON), segurança (RBAC), validação, relatórios, logging, interface gráfica (JavaFX), testes automatizados, Docker e CI/CD com GitHub Actions.

---

## 3. Objetivos do Trabalho

### 3.1. Evolução técnica em Orientação a Objetos

Complementar os conhecimentos de POO — antes concentrados em requisitos funcionais — com requisitos de qualidade e funcionalidades técnicas:

- Interface gráfica com o usuário (JavaFX)
- Segurança (autenticação, autorização, RBAC)
- Persistência de dados (TXT, XML, JSON)
- Logging e auditoria
- Distribuição e containerização (Docker)
- Testes automatizados (JUnit, Mockito)
- CI/CD (GitHub Actions)

Na prática, aplicar também:

- Análise de requisitos por histórias de usuário
- Design orientado a objetos com Diagramas de Sequência UML
- Princípios GRASP
- Princípios fundamentais de design de software e SOLID

### 3.2. Engenharia de Software Aprimorada por IA

- Utilizar LLMs, chatbots, IDEs e assistentes de IA no desenvolvimento das histórias
- Avaliar ganhos de produtividade com ferramentas baseadas em IA
- Manter o desenvolvedor no ciclo de decisão (**human-in-the-loop**): validação, refatoração e escolhas arquiteturais permanecem com a equipe

### 3.3. Desafio final (Exercício 3)

Refatorar e migrar a aplicação completa para uma **segunda linguagem orientada a objetos** à escolha do grupo, com apoio de LLMs.

---

## 4. Avaliação

Avaliação **presencial** no laboratório B207:

| Critério | Peso |
|----------|------|
| Código-fonte | 30% |
| Apresentação do trabalho | 30% |
| Capacidade de responder perguntas do professor | 40% |

Estudante ausente na apresentação pode obter, no máximo, a nota referente ao código-fonte.

---

## 5. Uso de Git e GitHub

- **Não obrigatório** na disciplina (Git será aprofundado em Gestão do Ciclo de Vida da Aplicação)
- **Encorajado** para trabalho colaborativo e integração de código
- Recomendação do professor: criar conta no GitHub e fazer **fork** do repositório base
- Uso de LLMs como apoio à aprendizagem é permitido quando Git for adotado

---

## 6. Exercícios do Semestre

### Exercício 1 — Leitura do repositório base

Fazer uma leitura geral do repositório com as histórias de usuário do semestre:

https://github.com/pagliares/academic-system-semester-assignment-2026

### Exercício 2 — Implementação das 61 histórias

Implementar, com apoio de LLMs/IDEs/assistentes de IA, as **61 histórias** documentadas no README do repositório base. O professor indica uma sequência sugerida de implementação (ver seção 8).

### Exercício 3 — Migração para outra linguagem

Refatorar e migrar a solução completa para outra linguagem OO à escolha do grupo.

---

## 7. Observações sobre o tamanho das histórias

As histórias foram intencionalmente definidas em **pequeno porte** (tipicamente 1–2 horas cada, sem IA). Com apoio de IA, o tempo pode ser significativamente menor. Em projetos reais, muitas dessas histórias seriam subtarefas de histórias maiores; aqui a granularidade facilita o acompanhamento e a exposição a diferentes aspectos técnicos ao longo do semestre.

---

## 8. Sequência sugerida de implementação (61 histórias)

| # | Código | Título |
|---|--------|--------|
| 01 | US-2361 | Cadastrar avaliações em turmas |
| 02 | TUS-2362 | Persist class assessments to TXT file |
| 03 | US-2363 | Register classes through keyboard input |
| 04 | US-2364 | Manage academic system through command line menu |
| 05 | TUS-2365 | Refactor domain model using Lombok |
| 06 | US-2366 | Authenticate users and authorize actions based on roles |
| 07 | US-2367 | Handle academic domain errors with custom exceptions |
| 08 | US-2368 | Handle keyboard input errors with custom exceptions |
| 09 | US-2369 | Handle authentication and authorization errors with custom exceptions |
| 10 | TUS-2370 | Refactor menu operations into AcademicSystemController |
| 11 | TUS-2371 | Validate academic domain objects using Jakarta Bean Validation |
| 12 | US-2372 | Configure persistence type as administrator |
| 13 | US-2373 | Save academic data to XML file |
| 14 | US-2374 | Save academic data to JSON file |
| 15 | US-2375 | Generate class assessment summary report |
| 16 | US-2376 | Generate assessment weight report |
| 17 | US-2377 | Generate persistence configuration report |
| 18 | US-2378 | Role-based dynamic menu rendering |
| 19 | US-2379 | Logout |
| 20 | US-2380 | Display role-specific sequential menus |
| 21 | TUS-2381 | Deliver academic system with Docker |
| 22 | TUS-2382 | Define equality for identifiable domain objects |
| 23 | TUS-2383 | Configure automated testing infrastructure |
| 24 | TUS-2384 | Test identifiable domain object equality |
| 25 | TUS-2385 | Test academic domain validation |
| 26 | US-2386 | Test authentication behavior |
| 27 | US-2387 | Test authorization behavior |
| 28 | US-2388 | Test report generation |
| 29 | US-2389 | Test persistence repositories |
| 30 | TUS-2390 | Configure application logging infrastructure |
| 31 | TUS-2391 | Log authentication and logout events |
| 32 | TUS-2392 | Log authorization failures |
| 33 | TUS-2393 | Log persistence operations |
| 34 | TUS-2394 | Log report generation |
| 35 | TUS-2395 | Verify logging infrastructure behavior |
| 36 | TUS-2396 | Introduce ClassService |
| 37 | TUS-2397 | Introduce AssessmentService |
| 38 | TUS-2398 | Introduce PersistenceService |
| 39 | TUS-2399 | Introduce ReportService |
| 40 | TUS-2400 | Simplify AcademicSystemController |
| 41 | TUS-2401 | Test ClassService behavior |
| 42 | TUS-2402 | Test AssessmentService behavior |
| 43 | TUS-2403 | Test PersistenceService behavior |
| 44 | TUS-2404 | Test ReportService behavior |
| 45 | TUS-2405 | Test AcademicSystemController delegation behavior |
| 46 | TUS-2406 | Configure JavaFX application infrastructure |
| 47 | TUS-2407 | Create JavaFX login screen |
| 48 | TUS-2414 | Introduce AuthenticationController for JavaFX login |
| 49 | TUS-2409 | Create JavaFX class registration screen |
| 50 | TUS-2408 | Create JavaFX role-based main screen |
| 51 | US-2410 | Create JavaFX assessment registration screen |
| 52 | TUS-2411 | Create JavaFX report screen |
| 53 | TUS-2412 | Create JavaFX persistence configuration screen |
| 54 | TUS-2413 | Create JavaFX class and assessment visualization screen |
| 55 | TUS-2415 | Configure CI pipeline with GitHub Actions |
| 56 | TUS-2416 | Generate test coverage reports |
| 57 | TUS-2417 | Publish Docker image automatically |
| 58 | TUS-2418 | Configure pull request validation workflow |
| 59 | TUS-2419 | Configure release workflow |

> **Status atual do grupo:** nenhuma história implementada ainda. A US-2361 será a primeira a ser desenvolvida.

---

## 9. Arquitetura prevista

Arquitetura em camadas, conforme documentação do repositório base:

| Camada | Pacote | Responsabilidade |
|--------|--------|------------------|
| Entrada | `org.example` | `Main` — ponto de entrada da aplicação |
| Apresentação | `org.example.view` | Interface CLI e JavaFX |
| Coordenação | `org.example.controller` | Orquestração de operações e menus |
| Aplicação | `org.example.service` | Casos de uso e regras de negócio |
| Domínio | `org.example.model` | Entidades e objetos de valor |
| Persistência | `org.example.repository` | Repositórios e estratégias de persistência |
| Segurança | `org.example.security` | Autenticação, autorização e sessão |
| Validação | `org.example.validation` | Validação de domínio (Jakarta Bean Validation) |
| Relatórios | `org.example.report` | Geração de relatórios |
| Erros | `org.example.exception` | Hierarquias de exceções |

### Tecnologias previstas ao longo do semestre

| Tecnologia | Finalidade |
|------------|------------|
| Java SE 25 | Linguagem principal |
| Maven 3.9+ | Build e dependências |
| Lombok | Redução de boilerplate |
| Jakarta Bean Validation | Validação de domínio |
| JUnit Jupiter | Testes unitários |
| Mockito | Mocks em testes |
| SLF4J + Logback | Logging |
| JavaFX | Interface gráfica |
| Docker | Containerização |
| GitHub Actions | CI/CD |

---

## 10. Estrutura atual deste repositório

Alinhada ao repositório base do professor, com pasta `docs/` adicional para documentação do grupo:

```text
trabalho-poo/
├── .gitignore
├── LICENSE
├── README.md
├── docs/
│   ├── contexto.md                        # este arquivo
│   └── Semester_Assignment_2026_01.pdf    # enunciado oficial
└── academic-system/                       # projeto Maven
    ├── .gitignore
    ├── pom.xml
    └── src/
        ├── main/java/org/example/
        │   ├── Main.java
        │   ├── controller/
        │   ├── exception/
        │   ├── model/
        │   ├── report/
        │   ├── repository/
        │   ├── security/
        │   ├── service/
        │   ├── validation/
        │   └── view/
        └── test/java/org/example/         # espelha produção (testes a implementar)
            ├── controller/
            ├── exception/
            ├── model/
            ├── report/
            ├── repository/
            ├── security/
            ├── service/
            ├── validation/
            └── view/
```

### Decisões de estrutura adotadas pelo grupo

- **Pacote raiz:** `org.example` (conforme repositório base)
- **Subprojeto Maven:** `academic-system/` (mesmo padrão do professor)
- **Camada de UI:** pacote `view` (nomenclatura do README do professor; antes era `ui`)
- **Testes:** estrutura de pacotes preparada; implementação prevista a partir da TUS-2383
- **Java:** versão 25 no `pom.xml` (alinhado ao repositório base)
- **JUnit:** dependência já configurada no Maven como preparação para testes futuros

---

## 11. Como compilar e executar

```bash
cd academic-system
mvn clean install
mvn exec:java -Dexec.mainClass="org.example.Main"
```

Ou, após empacotar:

```bash
mvn clean package
java -jar target/academic-system-1.0-SNAPSHOT.jar
```

---

## 12. Histórias Finalizadas

Esta seção apresenta as User Stories (US) que já foram implementadas, testadas, revisadas e aceitas:

| História | Status | Observação |
|----------|--------|------------|
| **US-2361** | Finalizada | Registrar Avaliação na Turma (Modelo de domínio e testes concluídos) |
| **US-2363** | Finalizada | Registrar Turmas por Entrada de Teclado (Validação de domínio, Controller e Scanner implementados e testados) |
| Demais (02–59) | Pendente | Seguir sequência do enunciado |

### Melhorias locais mantidas (sem conflito com o padrão do professor)

- Pasta `docs/` com este contexto e o PDF do enunciado
- `Main` com método `run()` separado, facilitando testes futuros
- JUnit 5 e Maven Surefire já configurados no `pom.xml`
- `mainClass` corrigido para `org.example.Main`
- Pacotes de arquitetura completa criados antecipadamente (`controller`, `security`, `validation`, `report`, `view`)

---

## 13. Referências

- Enunciado: `docs/Semester_Assignment_2026_01.pdf`
- Repositório base: https://github.com/pagliares/academic-system-semester-assignment-2026
- Catálogo de histórias e critérios de aceite: `README.md` do repositório base
