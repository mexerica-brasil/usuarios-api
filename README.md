# Usuarios API

API REST para cadastro e gerenciamento de usuários, desenvolvida com Java e Spring Boot.

O projeto disponibiliza serviços HTTP para operações relacionadas ao cadastro de usuários, utilizando persistência em banco de dados MySQL e JPA/Hibernate.

---

## 📋 Funcionalidades

- Cadastro de usuários
- Consulta de usuários
- Atualização de usuários
- Exclusão de usuários
- Validação dos dados de entrada
- Persistência utilizando JPA
- Controle de versionamento do banco de dados com Flyway
- Monitoramento da aplicação através do Spring Boot Actuator
- Exposição de API REST
- Integração com banco de dados MySQL

---

## 🏗️ Arquitetura

A aplicação segue uma arquitetura baseada no padrão de camadas:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

### Camadas

**Controller**

Responsável pela exposição dos endpoints REST e pelo recebimento das requisições HTTP.

**Service**

Contém as regras de negócio da aplicação.

**Repository**

Responsável pelo acesso e persistência dos dados através do Spring Data JPA.

**Entity**

Representa as entidades persistidas no banco de dados.

**DTO**

Objetos utilizados para transportar dados entre a API e seus consumidores, evitando expor diretamente as entidades quando necessário.

---

## 🚀 Tecnologias

| Tecnologia | Versão / Utilização |
|---|---|
| Java | 26 |
| Spring Boot | 4.1.1 |
| Spring Web MVC | API REST |
| Spring Data JPA | Persistência |
| Hibernate | ORM |
| MySQL | Banco de dados |
| Flyway | Versionamento do banco |
| Bean Validation | Validação |
| Spring Boot Actuator | Monitoramento |
| Lombok | Redução de código boilerplate |
| Maven | Gerenciamento do projeto |
| JUnit / Spring Boot Test | Testes |

---

## 📦 Dependências principais

### Spring Web MVC

Responsável pela implementação da API REST e pelo processamento das requisições HTTP.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webmvc</artifactId>
</dependency>
```

### Spring Data JPA

Utilizado para persistência dos dados e integração com o banco de dados.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

### MySQL

Banco de dados utilizado pela aplicação.

```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
</dependency>
```

### Flyway

Utilizado para controle e versionamento das alterações do banco de dados.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-flyway</artifactId>
</dependency>
```

### Bean Validation

Responsável pela validação dos dados recebidos pela API.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

### Actuator

Disponibiliza endpoints para monitoramento e observabilidade da aplicação.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

---

## 📋 Pré-requisitos

Para executar o projeto localmente, é necessário possuir:

- Java 26
- Maven
- MySQL
- Git

Verifique as versões instaladas:

```bash
java -version
mvn -version
mysql --version
git --version
```

---

## 🗄️ Banco de dados

O projeto utiliza MySQL como banco de dados.

Crie o banco de dados:

```sql
CREATE DATABASE usuarios;
```

Exemplo de configuração no `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/usuarios?useSSL=false&serverTimezone=America/Sao_Paulo&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

> As informações de acesso ao banco não devem ser versionadas no repositório.

---

## 🗃️ Flyway

As alterações estruturais do banco de dados devem ser controladas através de migrations do Flyway.

Estrutura esperada:

```text
src/
└── main/
    └── resources/
        └── db/
            └── migration/
                ├── V1__create_table_usuario.sql
                ├── V2__add_column_usuario.sql
                └── V3__create_table_endereco.sql
```

As migrations são executadas automaticamente durante a inicialização da aplicação.

### Convenção

Os arquivos devem seguir o padrão:

```text
V<versão>__<descricao>.sql
```

Exemplo:

```text
V1__create_table_usuario.sql
V2__create_table_endereco.sql
V3__add_unique_constraint_cpf.sql
```

---

## ▶️ Executando o projeto

Clone o repositório:

```bash
git clone <URL_DO_REPOSITORIO>
```

Entre no diretório:

```bash
cd usuarios-api
```

Compile o projeto:

```bash
mvn clean package
```

Execute a aplicação:

```bash
mvn spring-boot:run
```

Ou execute o JAR gerado:

```bash
java -jar target/usuarios-api-1.0.0.jar
```

Por padrão, a aplicação será disponibilizada em:

```text
http://localhost:8080
```

---

## 🔌 API REST

Os endpoints da aplicação devem seguir o padrão REST.

Exemplo de estrutura:

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/usuarios` | Cadastrar usuário |
| `GET` | `/usuarios` | Listar usuários |
| `GET` | `/usuarios/{id}` | Consultar usuário |
| `PUT` | `/usuarios/{id}` | Atualizar usuário |
| `DELETE` | `/usuarios/{id}` | Excluir usuário |

### Exemplo — cadastro

```http
POST /usuarios
Content-Type: application/json
```

Exemplo de payload:

```json
{
    "nome": "João da Silva",
    "cpf": "12345678901",
    "email": "joao.silva@email.com"
}
```

---

## ✅ Validação

A API utiliza Bean Validation para validar os dados recebidos nas requisições.

Exemplo:

```java
@NotBlank
private String nome;

@NotBlank
@Email
private String email;
```

Outras validações podem ser utilizadas conforme as regras de negócio.

---

## 📊 Actuator

O Spring Boot Actuator é utilizado para disponibilizar informações de monitoramento da aplicação.

Exemplo:

```text
http://localhost:8080/actuator/health
```

Resposta esperada:

```json
{
    "status": "UP"
}
```

Os endpoints expostos pelo Actuator devem ser configurados de acordo com as necessidades e requisitos de segurança do ambiente.

---

## 🧪 Testes

Para executar os testes:

```bash
mvn test
```

Para executar o build completo:

```bash
mvn clean verify
```

Os testes devem contemplar, conforme a evolução do projeto:

- Regras de negócio
- Controllers
- Services
- Repositories
- Validações
- Integração com banco de dados

---

## 🧹 Qualidade de código

O projeto utiliza Lombok para reduzir código boilerplate.

Exemplo:

```java
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Usuario {
    // ...
}
```

O uso de Lombok deve ser aplicado de forma criteriosa, principalmente em entidades JPA e objetos utilizados pela API.

---

## 📁 Estrutura do projeto

Estrutura sugerida:

```text
usuarios-api/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── velsis/
│   │   │           └── usuarios/
│   │   │               ├── controller/
│   │   │               ├── service/
│   │   │               ├── repository/
│   │   │               ├── entity/
│   │   │               ├── dto/
│   │   │               ├── exception/
│   │   │               └── UsuariosApiApplication.java
│   │   │
│   │   └── resources/
│   │       ├── db/
│   │       │   └── migration/
│   │       ├── application.properties
│   │       └── application.yml
│   │
│   └── test/
│       └── java/
│
├── .gitignore
├── pom.xml
└── README.md
```

---

## 🔐 Configuração e segurança

Informações sensíveis não devem ser armazenadas diretamente no código-fonte ou no repositório Git.

Evite versionar:

```text
spring.datasource.password
```

senhas, tokens, chaves de API e outras credenciais.

Para ambientes diferentes, recomenda-se utilizar variáveis de ambiente ou arquivos de configuração específicos.

Exemplo:

```properties
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

---

## 🌎 Ambientes

A aplicação pode utilizar diferentes configurações para cada ambiente:

```text
application.properties
application-dev.properties
application-test.properties
application-prod.properties
```

Exemplo:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

---

## 🌿 Git

Sugestão de organização das branches:

```text
main
├── develop
├── feature/*
├── bugfix/*
└── hotfix/*
```

Exemplos:

```bash
git switch -c feature/cadastro-usuario
git switch -c feature/cadastro-endereco
git switch -c bugfix/validacao-cpf
```

Commits devem preferencialmente ser objetivos e descrever a alteração realizada.

Exemplos:

```text
feat: adiciona cadastro de usuário
feat: adiciona consulta de usuários
fix: corrige validação de CPF
refactor: reorganiza camada de serviço
test: adiciona testes do UsuarioService
chore: atualiza dependências
```

---

## 📦 Build

Gerar o artefato da aplicação:

```bash
mvn clean package
```

O artefato será gerado no diretório:

```text
target/
```

Exemplo:

```text
target/usuarios-api-1.0.0.jar
```

---

## 🚀 Deploy

A aplicação pode ser executada como um arquivo JAR:

```bash
java -jar target/usuarios-api-1.0.0.jar
```

Em ambientes de produção, recomenda-se utilizar um processo de deploy automatizado e separar as configurações de cada ambiente.

---

## 📈 Evolução do projeto

Possíveis evoluções:

- Documentação da API com OpenAPI/Swagger
- Autenticação e autorização
- OAuth 2.0 / JWT
- Paginação e ordenação
- Tratamento global de exceções
- Logs estruturados
- Testes de integração
- Docker
- Docker Compose
- CI/CD
- Monitoramento e observabilidade
- Integração com serviços externos

---

## 👨‍💻 Desenvolvimento

Projeto desenvolvido utilizando Java e Spring Boot, seguindo princípios de desenvolvimento de APIs REST, separação de responsabilidades e boas práticas de desenvolvimento de software.

---

## 📄 Licença

Este projeto possui licença definida de acordo com as políticas do repositório e da organização responsável pelo desenvolvimento.