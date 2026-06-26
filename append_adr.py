import sys

filepath = 'docs/arquitetura.md'
with open(filepath, 'r', encoding='utf-8') as f:
    content = f.read()

# Update Index
old_index = """| ADR-001 | Uso do Padrão Factory para Criação de Avaliações | 10/06/2026 | Aceita |
| ADR-002 | Validação de Domínio via Cláusulas de Guarda no Service (Provisória) | 11/06/2026 | Aceita |
| ADR-003 | Delegação de Operações via Controller | 11/06/2026 | Aceita |"""

new_index = """| ADR-001 | Uso do Padrão Factory para Criação de Avaliações | 10/06/2026 | Aceita |
| ADR-002 | Validação de Domínio via Cláusulas de Guarda no Service (Provisória) | 11/06/2026 | Substituída (ADR-004) |
| ADR-003 | Delegação de Operações via Controller | 11/06/2026 | Evoluída (ADR-005) |
| ADR-004 | Validação com Jakarta Bean Validation | 18/06/2026 | Aceita |
| ADR-005 | Centralização de Operações no ControladorSistemaAcademico | 18/06/2026 | Aceita |
| ADR-006 | Padrão Singleton para o SistemaAcademico | 18/06/2026 | Aceita |
| ADR-007 | Hierarquia de Exceções Customizadas | 18/06/2026 | Aceita |"""

content = content.replace(old_index, new_index)

# Append new ADRs
new_adrs = """

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
"""

with open(filepath, 'w', encoding='utf-8') as f:
    f.write(content + new_adrs)

print("Updated arquitetura.md")
