# 💻 TalentMind - O Futuro do Trabalho (Global Solution Java Advanced)

🌟 ## Visão Geral do Projeto

O **TalentMind** é uma plataforma inovadora desenvolvida para a Global Solution 2025 (Java Advanced). O projeto endereça o tema "O Futuro do Trabalho" ao conectar vagas, competências e cursos de requalificação (reskilling). A solução é construída com Spring Boot 3 (Java 21), adotando uma arquitetura com APIs REST (HATEOAS), WebApp (Thymeleaf) e forte ênfase em arquiteturas modernas.

🎯 ## Objetivos Chave

-   **IA Generativa**: Geração de planos de estudos personalizados, utilizando Spring AI e o modelo Llama 3.
-   **Mensageria Assíncrona**: Utilização de RabbitMQ para processamento em background do cálculo de compatibilidade de novas vagas.
-   **Segurança Robusta**: Implementação de Spring Security com autenticação Web (Form Login) e APIs stateless com JWT.
-   **Internacionalização**: Suporte a múltiplos idiomas (Português/Inglês).

---

🛠️ ## Pré-Requisitos

Para rodar o projeto localmente, é necessário ter as seguintes ferramentas instaladas:

-   Java Development Kit (JDK) 21
-   Apache Maven 3.9.x (para construção do projeto)
-   Docker (para inicializar os serviços de mensageria e IA)

### 1. Instalação do Docker

Acesse o site oficial do [Docker](https://www.docker.com/) e instale o Docker Desktop (para Windows/Mac) ou o Docker Engine (para Linux).

### 2. Configuração do Banco de Dados Oracle

As configurações de conexão para o banco de dados Oracle da FIAP são definidas em `src/main/resources/application.properties`. Certifique-se de atualizar com suas credenciais:

```properties
spring.datasource.url=jdbc:oracle:thin:@//oracle.fiap.com.br:1521/ORCL
spring.datasource.username=seu_rm
spring.datasource.password=sua_senha
```

### 3. Configuração da IA Generativa

Este projeto utiliza a API do Groq para o modelo `llama-3.1-8b-instant`. Obtenha sua chave de API e insira-a no `application.properties`:

```properties
spring.ai.openai.api-key=gsk_SUA_CHAVE_AQUI
```

---

🚀 ## Como Executar o Projeto

### 1. Inicialização das Dependências Críticas (Docker)

O RabbitMQ e o banco de dados Oracle (via Flyway) são críticos. A execução do RabbitMQ via Docker é obrigatória para o funcionamento da mensageria assíncrona.

**a) Inicializar RabbitMQ (Mensageria Assíncrona):**

Este comando inicializa o servidor RabbitMQ em modo daemon na porta padrão e com o painel de gerenciamento exposto:

```bash
docker run -d --hostname rabbitmq --name rabbitmq-server -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```
*(O Spring AMQP usará a porta `5672` e o console de administração estará em http://localhost:15672)*

### 2. Compilação e Execução do Spring Boot

Execute o projeto TalentMind via Maven Wrapper:

```bash
./mvnw spring-boot:run
```

**Migração de Banco de Dados**: O Flyway (dependência Maven) rodará automaticamente os scripts SQL (`V1` a `V4`)), criando todas as tabelas, triggers de auditoria, pacotes PL/SQL e dados de seed iniciais.

---

🔑 ## Acessos e Endpoints

### 1. Acesso Web (Thymeleaf MVC)

O Dashboard e a interface de administração estão disponíveis em: `http://localhost:8080/dashboard`.

**Login de Administrador:**
-   **E-mail**: `admin@talentmind.com`
-   **Senha**: `password` (Senha BCrypt armazenada no script V4)

### 2. API REST (Swagger UI)

A documentação da API para testes (HATEOAS, Paginação, CRUD) está em: `http://localhost:8080/swagger-ui.html`.

#### Endpoints Principais

| Endpoint                               | Verbo | Descrição                                                              |
| -------------------------------------- | ----- | ---------------------------------------------------------------------- |
| `/api/auth/login`                      | POST  | Gera o token JWT para autenticação.                                    |
| `/api/ia/plano-de-estudos/{vagaId}`      | GET   | Aciona a IA para gerar plano de estudos para a vaga especificada.      |
| `/api/usuarios`                        | CRUD  | Gerenciamento de Usuários (com Paginação e HATEOAS).                   |
| `/api/vagas`                           | CRUD  | Gerenciamento de Vagas (com Mensageria assíncrona ao criar/atualizar). |
| `/api/competencias`                    | CRUD  | Gerenciamento de Competências.                                         |
| `/api/cursos`                          | CRUD  | Gerenciamento de Cursos de Requalificação.                             |

#### Autenticação JWT (Fluxo)

1.  **Obter Token**: Faça um `POST` para `/api/auth/login` com as credenciais.
2.  **Usar Token**: Envie o token JWT retornado no cabeçalho `Authorization` para acessar qualquer endpoint REST `api/**`:
    ```
    Authorization: Bearer <SEU_TOKEN>
    ```

---

📢 ## Fluxo de Mensageria Assíncrona

A criação de uma nova **Vaga** (via API ou Web App) aciona o seguinte fluxo assíncrono (RabbitMQ):

1.  O `VagaService` persiste a nova vaga no DB.
2.  O `VagaService` publica o `ID_VAGA` na `DirectExchange` com a chave `rk.vaga-nova`.
3.  O `CompatibilidadeService` atua como listener na fila `q.talentmind.vaga-nova`.
4.  O listener realiza o cálculo de compatibilidade entre a nova vaga e todos os usuários cadastrados em background, simulando um processamento pesado.
