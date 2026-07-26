# 🔧 Sistema de Agendamento de Serviços

<p align="center">
  <img src="https://img.shields.io/badge/Java-20-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/Spring_Boot-3.2.3-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot">
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL">
  <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" alt="Maven">
  <img src="https://img.shields.io/badge/Status-Conclu%C3%ADdo-success?style=for-the-badge" alt="Status">
</p>

<p align="center">
  API REST desenvolvida em Java com Spring Boot para gerenciar agendamentos de serviços em uma oficina,
  relacionando clientes, serviços e horários marcados.
</p>

---

## 📑 Sumário

- [Sobre o Projeto](#-sobre-o-projeto)
- [Funcionalidades](#-funcionalidades)
- [Tecnologias Utilizadas](#️-tecnologias-utilizadas)
- [Modelo de Dados](#️-modelo-de-dados)
- [Arquitetura](#️-arquitetura)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Pré-requisitos](#️-pré-requisitos)
- [Como Executar](#-como-executar)
- [Endpoints da API](#-endpoints-da-api)
- [Exemplo de Uso](#-exemplo-de-uso)
- [Dados Iniciais (Seed)](#-dados-iniciais-seed)
- [Melhorias Futuras](#-melhorias-futuras)
- [Autor](#-autor)
- [Licença](#-licença)

---

## 📖 Sobre o Projeto

O **Sistema de Agendamento de Serviços** é uma API desenvolvida em **Java** com **Spring Boot**, criada para simular o backend de uma oficina que precisa gerenciar **clientes**, **serviços oferecidos** e **agendamentos**.

A API permite cadastrar clientes e serviços de forma independente, e então criar um **agendamento** que relaciona um cliente a um serviço em uma data específica, com controle de status (por exemplo, `PENDENTE`). Os dados são persistidos em um banco **MySQL** via Spring Data JPA.

Este projeto foi desenvolvido como atividade acadêmica para praticar relacionamentos entre entidades (`@ManyToOne`), organização de uma API REST em múltiplos controllers e validações básicas de regra de negócio.

---

## ✨ Funcionalidades

- 👤 Cadastro e listagem de clientes
- 🛠️ Cadastro e listagem de serviços
- 📅 Criação de agendamentos vinculando cliente e serviço
- 🔍 Busca de agendamento por ID
- ✏️ Atualização de agendamentos (data, status, observações, cliente e/ou serviço)
- 🗑️ Exclusão de agendamentos
- ✅ Validação de existência de cliente e serviço antes de criar/atualizar um agendamento
- 💾 Persistência dos dados em banco MySQL
- 🌱 Carga inicial de dados de exemplo via `data.sql`

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Função |
|---|---|
| **Java 20** | Linguagem principal do projeto |
| **Spring Boot 3.2.3** | Framework principal da aplicação |
| **Spring Web** | Construção da API REST |
| **Spring Data JPA** | Camada de persistência de dados |
| **Spring Boot Starter Validation** | Disponível no projeto para validação de dados |
| **MySQL** | Banco de dados relacional |
| **Maven** | Gerenciamento de dependências e build |

---

## 🗂️ Modelo de Dados

O sistema é composto por três entidades principais:

**Cliente**

| Campo | Tipo | Descrição |
|---|---|---|
| `id` | `Long` (auto gerado) | Identificador único |
| `nome` | `String` | Nome do cliente |
| `email` | `String` | E-mail de contato |
| `telefone` | `String` | Telefone de contato |
| `criadoEm` | `LocalDateTime` | Data/hora de criação do registro |

**Serviço**

| Campo | Tipo | Descrição |
|---|---|---|
| `id` | `Long` (auto gerado) | Identificador único |
| `nome` | `String` | Nome do serviço |
| `descricao` | `String` | Descrição do serviço |
| `preco` | `Double` | Preço do serviço |
| `criadoEm` | `LocalDateTime` | Data/hora de criação do registro |

**Agendamento**

| Campo | Tipo | Descrição |
|---|---|---|
| `id` | `Long` (auto gerado) | Identificador único |
| `cliente` | `Cliente` (`@ManyToOne`) | Cliente vinculado ao agendamento |
| `servico` | `Servico` (`@ManyToOne`) | Serviço vinculado ao agendamento |
| `dataAgendada` | `LocalDateTime` | Data e hora marcadas para o serviço |
| `observacoes` | `String` | Observações adicionais |
| `status` | `String` | Situação do agendamento (padrão: `PENDENTE`) |
| `criadoEm` | `LocalDateTime` | Data/hora de criação do registro |

---

## 🏛️ Arquitetura

O projeto segue a arquitetura em camadas padrão do Spring Boot, com um controller e um repositório dedicados a cada entidade:

| Camada | Classe | Responsabilidade |
|---|---|---|
| **Controller** | `ClienteController` | Endpoints REST em `/clientes` |
| **Controller** | `ServicoController` | Endpoints REST em `/servicos` |
| **Controller** | `AgendamentoController` | Endpoints REST em `/agendamentos`, com validação dos vínculos |
| **Repository** | `ClienteRepository`, `ServicoRepository`, `AgendamentoRepository` | Interfaces JPA para acesso ao banco |
| **Model** | `Cliente`, `Servico`, `Agendamento` | Entidades JPA do domínio |
| **Application** | `AgendamentosApiApplication` | Classe principal que sobe a aplicação Spring Boot |

> ℹ️ O `AgendamentoController` é responsável por validar se o cliente e o serviço informados existem antes de criar ou atualizar um agendamento, retornando um erro caso não sejam encontrados.

---

## 📂 Estrutura do Projeto

```text
agendamentos-api/
│
├── src/
│   ├── main/
│   │   ├── java/com/oficina/agendamentosapi/
│   │   │   ├── AgendamentosApiApplication.java   # Classe principal
│   │   │   ├── controller/
│   │   │   │   ├── ClienteController.java
│   │   │   │   ├── ServicoController.java
│   │   │   │   └── AgendamentoController.java
│   │   │   ├── repository/
│   │   │   │   ├── ClienteRepository.java
│   │   │   │   ├── ServicoRepository.java
│   │   │   │   └── AgendamentoRepository.java
│   │   │   └── model/
│   │   │       ├── Cliente.java
│   │   │       ├── Servico.java
│   │   │       └── Agendamento.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties
│   │       └── data.sql                          # Dados iniciais de exemplo
│   └── test/
│
├── create_database.sql
├── pom.xml
└── README.md
```

---

## ⚙️ Pré-requisitos

Antes de executar o projeto, certifique-se de possuir:

- Java JDK 20 ou superior
- Maven
- MySQL Server rodando localmente
- Uma IDE compatível com Java (IntelliJ IDEA, Eclipse, VS Code, etc.)

---

## 🚀 Como Executar

**1. Clone o repositório**
```bash
git clone https://github.com/juanoliveira0020/Sistema-de-Agendamento-de-Servicos.git
```

**2. Entre na pasta do projeto**
```bash
cd Sistema-de-Agendamento-de-Servicos/agendamentos-api
```

**3. Crie o banco de dados (opcional)**

O projeto inclui um script pronto em `create_database.sql`:

```sql
CREATE DATABASE IF NOT EXISTS oficina_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

> ✅ Esse passo é opcional: a conexão já está configurada com `createDatabaseIfNotExist=true`, então o banco `oficina_db` também é criado automaticamente na primeira execução.

**4. Configure as credenciais**

O arquivo `src/main/resources/application.properties` já vem configurado com:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/oficina_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
```

Ajuste usuário e senha conforme a sua instalação do MySQL.

**5. Execute o projeto**

Pelo Maven:
```bash
mvn spring-boot:run
```

Ou execute a classe principal diretamente pela IDE:
```
AgendamentosApiApplication.java
```

**6. Acesse a aplicação**

A API estará disponível em:
```
http://localhost:8080
```

---

## 📌 Endpoints da API

### Clientes — `/clientes`

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/clientes` | Lista todos os clientes |
| `POST` | `/clientes` | Cadastra um novo cliente |

### Serviços — `/servicos`

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/servicos` | Lista todos os serviços |
| `POST` | `/servicos` | Cadastra um novo serviço |

### Agendamentos — `/agendamentos`

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/agendamentos` | Lista todos os agendamentos |
| `GET` | `/agendamentos/{id}` | Busca um agendamento pelo ID |
| `POST` | `/agendamentos` | Cria um novo agendamento (exige cliente e serviço existentes) |
| `PUT` | `/agendamentos/{id}` | Atualiza data, status, observações, cliente e/ou serviço |
| `DELETE` | `/agendamentos/{id}` | Remove um agendamento |

> ℹ️ Os controllers de Cliente e Serviço ainda não possuem busca por ID, atualização ou exclusão — apenas listagem e criação.

---

## 🧪 Exemplo de Uso

**1. Cadastrar um cliente** — `POST /clientes`
```json
{
  "nome": "Ana Souza",
  "email": "ana@exemplo.com",
  "telefone": "(11)98888-7777"
}
```

**2. Cadastrar um serviço** — `POST /servicos`
```json
{
  "nome": "Troca de Óleo",
  "descricao": "Troca de óleo e filtro",
  "preco": 150.00
}
```

**3. Criar um agendamento** — `POST /agendamentos`
```json
{
  "cliente": { "id": 1 },
  "servico": { "id": 1 },
  "dataAgendada": "2026-08-05T10:00:00",
  "observacoes": "Cliente prefere atendimento pela manhã"
}
```

**Resposta**
```json
{
  "id": 1,
  "cliente": { "id": 1, "nome": "Ana Souza", "email": "ana@exemplo.com", "telefone": "(11)98888-7777" },
  "servico": { "id": 1, "nome": "Troca de Óleo", "descricao": "Troca de óleo e filtro", "preco": 150.0 },
  "dataAgendada": "2026-08-05T10:00:00",
  "observacoes": "Cliente prefere atendimento pela manhã",
  "status": "PENDENTE"
}
```

> ⚠️ Se o `cliente.id` ou `servico.id` informado não existir no banco, a API retorna um erro informando que o registro não foi encontrado.

---

## 🌱 Dados Iniciais (Seed)

O arquivo `src/main/resources/data.sql` insere automaticamente um cliente e um serviço de exemplo na primeira execução:

- Cliente: **Carlos** (`carlos@exemplo.com`)
- Serviço: **Troca de Bateria** — R$ 350,00

Isso permite testar a criação de agendamentos imediatamente após subir a aplicação, sem precisar cadastrar dados manualmente antes.

---

## 🚀 Melhorias Futuras

- [ ] Endpoints de busca por ID, atualização e exclusão para clientes e serviços
- [ ] Validação de dados de entrada com Bean Validation (`@NotBlank`, `@Email`, etc.)
- [ ] Tratamento global de exceções (`@ControllerAdvice`), substituindo as `RuntimeException` genéricas
- [ ] Enum para o campo `status` do agendamento
- [ ] Verificação de conflito de horários na criação de agendamentos
- [ ] Documentação com Swagger/OpenAPI
- [ ] Testes unitários e de integração
- [ ] Dockerização do projeto

---

## 👨‍💻 Autor

**Juan Oliveira**

[![GitHub](https://img.shields.io/badge/GitHub-juanoliveira0020-181717?style=flat&logo=github)](https://github.com/juanoliveira0020)

---

## 📄 Licença

Este projeto foi desenvolvido para fins acadêmicos e de aprendizado.

Sinta-se à vontade para utilizá-lo como referência para estudos sobre Java, Spring Boot, APIs REST e relacionamentos JPA.
