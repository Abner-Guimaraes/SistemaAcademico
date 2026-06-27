# Sistema Acadêmico - Projeto POO 2026

Bem-vindo ao repositório final do projeto de Orientação a Objetos. Este sistema foi integralmente concebido e evoluído através das 61 Histórias de Usuário propostas pelo Professor, englobando conceitos de Segurança, Persistência, Logging, Padrões de Projeto (Strategy, Factory, Repository, Facade) e Interface Gráfica com JavaFX.

## 🚀 Como Executar o Projeto Localmente

O sistema suporta duas interfaces: a interface em Linha de Comando (CLI) e a Interface Gráfica (JavaFX).

### Requisitos:
- Java 21+
- Maven 3.9+

### Via Interface Gráfica (JavaFX):
Dentro da pasta `academic-system`:
```bash
mvn clean compile javafx:run
```

### Via Terminal (CLI Original):
```bash
mvn clean package
java -jar target/academic-system-1.0-SNAPSHOT.jar
```

---

## 🐳 Executando com Docker (Portabilidade Total)

Para garantir que o avaliador (ou você) consiga executar o sistema em **qualquer computador** sem precisar instalar o Java ou o Maven, nós preparamos uma imagem Docker (`Dockerfile`).

Devido a questões técnicas de compatibilidade visual em containers nativos, o Docker foi configurado para compilar o código de forma independente e instanciar a versão de linha de comando (CLI) interativa.

**Passo a passo para rodar com Docker:**
1. Navegue até a pasta que contém o arquivo pom.xml e o Dockerfile:
   ```bash
   cd academic-system
   ```
2. Construa a imagem (esse passo baixa as dependências e compila o `.jar` dentro de uma sandbox blindada):
   ```bash
   docker build -t sistema-academico-cli .
   ```
3. Execute o container no modo iterativo (`-it`) para habilitar o uso do teclado no terminal:
   ```bash
   docker run -it --rm sistema-academico-cli
   ```

