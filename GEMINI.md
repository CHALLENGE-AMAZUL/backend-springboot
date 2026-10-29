# Guia de Projeto: API RESTful Java Advanced — Challenge AMAZUL
> **Plataforma Educativa de Divulgação Científica sobre Tecnologia Nuclear**  
> Parceria acadêmica: **AMAZUL (Amazônia Azul Tecnologias de Defesa S.A.)** & **CTMSP (Centro Tecnológico da Marinha em São Paulo)**  
> Curso: **FIAP — Análise e Desenvolvimento de Sistemas / Engenharia de Software**  
> Disciplina: **Java Advanced & Enterprise Application Development**

---

## 1. Visão Geral e Contexto de Negócio

### 1.1. O Desafio AMAZUL
A AMAZUL tem como missão desenvolver tecnologia nuclear para a soberania nacional, incluindo o Programa Nuclear da Marinha (PNM), o Programa de Desenvolvimento de Submarinos (PROSUB) e o Reator Multipropósito Brasileiro (RMB).

O principal desafio social e de comunicação pública reside no desconhecimento e estigma da população quanto ao uso da energia nuclear. Esta API compõe o núcleo de backend de uma **Plataforma Educativa e de Divulgação Científica**, com os seguintes propósitos:
- Explicar conceitos complexos com linguagem acessível e dados científicos comprovados.
- Demonstrar as diversas áreas de aplicação da tecnologia nuclear além da geração bélica/energética:
  - **Saúde:** Produção de radiofármacos (Iodo-131, Tecnécio-99m) no IPEN para diagnóstico e tratamento de câncer.
  - **Agricultura:** Irradiação de alimentos contra pragas e técnicas isotópicas para conservação do solo.
  - **Indústria:** Gamagrafia industrial para detecção de falhas em tubulações e estruturas de aço.
  - **Meio Ambiente:** Monitoramento isotópico de aquíferos e poluição oceânica.
  - **Pesquisa e Propulsão:** Propulsão nuclear naval e reatores de pesquisa científica.
- Mapear instalações e laboratórios no Brasil e no mundo que produzem avanços nucleares pacíficos.
- Oferecer rastreabilidade científica por meio de fontes idôneas e revisadas por pares.

---

## 2. Arquitetura do Software e Padrões de Projeto

A aplicação segue uma arquitetura em camadas desacopladas (**Layered Architecture** com princípios de **Clean Code** e **Domain-Driven Design - DDD**):

```
src/main/java/com/amazul/fiap/api_springboot/
├── client/                 # Clientes HTTP externos (ex: APIs REST para dados geográficos)
├── config/                 # Configurações do ecossistema Spring (Swagger/OpenAPI, RestClient, etc.)
│   ├── GlobalExceptionHandler.java # Tratamento global de exceções (@RestControllerAdvice)
│   └── OpenApiConfig.java          # Metadados e documentação Swagger
├── controller/             # Camada de Apresentação REST (expõe DTOs, documentado com Swagger)
├── dto/                    # Data Transfer Objects (Records imutáveis com Bean Validation)
│   ├── request/            # Payloads de entrada
│   ├── response/           # Payloads de saída
│   └── errors/             # Contratos JSON padronizados de erros
├── exception/              # Exceções de negócio e de domínio (ex: ResourceNotFoundException)
├── mapper/                 # Conversores bidirecionais isolados (Entity <-> DTO)
├── model/                  # Entidades JPA com encapsulamento e regras de negócio
└── repository/             # Camada de Persistência (Spring Data JPA + Query Methods)
└── service/                # Camada de Negócio e Casos de Uso (Interface + Implementação)
```

### 2.1. Princípios Inegociáveis da Arquitetura
1. **Entidades JPA Nunca Cruzam a Fronteira do Controller:** Todas as requisições recebem `*Request` (record) e todas as respostas retornam `*Response` (record). Isso protege o domínio contra alterações acidentais e previne problemas de serialização cíclica (Jackson recursivo).
2. **Imutabilidade nos DTOs:** Utilização exclusiva de Java Records para contratos de dados.
3. **Encapsulamento Rico no Domínio:** Regras de transição de estado residem dentro das próprias entidades ou nos Services, nunca em utilitários espalhados.
4. **Sem Credenciais Hardcoded:** Strings de conexão, senhas e chaves de API externas devem ser providas via variáveis de ambiente (`.env` localmente / variáveis de ambiente do sistema).

---

## 3. Modelo de Dados Relacional (JPA <-> Banco Oracle)

O modelo relacional foi desenhado para total aderência às convenções de bancos de dados corporativos **Oracle**, respeitando limites de identificadores, tipos de dados canônicos (`VARCHAR2`, `NUMBER`, `DATE`, `CLOB`) e gerenciamento de chaves primárias via **Sequences**.

### 3.1. Dicionário de Dados e Mapeamento JPA

#### Entidade 1: Artigo / Conteudo (`TB_ARTIGO` ou `TB_CONTEUDO`)
Representa a descoberta científica ou artigo educativo publicado na plataforma.

| Campo Java (Entity) | Coluna Oracle | Tipo Java | Tipo Oracle | Restrições / Regras |
| :--- | :--- | :--- | :--- | :--- |
| `id` | `ID_ARTIGO` | `Long` | `NUMBER(19)` | **PK**, Sequence `SEQ_ARTIGO` |
| `titulo` | `DS_TITULO` | `String` | `VARCHAR2(150)` | NOT NULL, Busca textual indexada |
| `resumo` | `DS_RESUMO` | `String` | `VARCHAR2(500)` | NOT NULL |
| `aplicacaoReal` | `DS_APLICACAO_REAL` | `String` | `VARCHAR2(1500)` ou `CLOB` | NOT NULL, Exemplo prático no cotidiano |
| `impactoSociedade` | `DS_IMPACTO_SOCIEDADE` | `String` | `VARCHAR2(1500)` ou `CLOB` | NOT NULL, Benefícios e riscos mitigados |
| `areaAplicacao` | `TP_AREA_APLICACAO` | `AreaAplicacao` (Enum) | `VARCHAR2(30)` | NOT NULL (`ENERGIA`, `SAUDE`, etc.) |
| `status` | `ST_STATUS` | `StatusArtigo` (Enum) | `VARCHAR2(20)` | NOT NULL (`RASCUNHO`, `PUBLICADO`, `ARQUIVADO`) |
| `local` | `ID_LOCAL` | `Local` | `NUMBER(19)` | **FK** opcional para `TB_LOCAL(ID_LOCAL)` |
| `referencias` | — | `List<ArtigoFonte>` | — | Relacionamento 1:N com entidade associativa |

#### Entidade 2: Localizacao (`TB_LOCAL`)
Instalação física, usina, reator experimental ou laboratório associado aos estudos e aplicações.

| Campo Java (Entity) | Coluna Oracle | Tipo Java | Tipo Oracle | Restrições / Regras |
| :--- | :--- | :--- | :--- | :--- |
| `id` | `ID_LOCAL` | `Long` | `NUMBER(19)` | **PK**, Sequence `SEQ_LOCAL` |
| `nome` | `NM_LOCAL` | `String` | `VARCHAR2(120)` | NOT NULL (ex: "Centro Experimental Aramar") |
| `cidade` | `NM_CIDADE` | `String` | `VARCHAR2(100)` | Opcional (ex: "Iperó") |
| `pais` | `NM_PAIS` | `String` | `VARCHAR2(100)` | Preenchido via integração com API externa |
| `codigoPais` | `CD_PAIS` | `String` | `CHAR(2)` / `VARCHAR2(2)` | ISO-2 (ex: "BR", "FR", "US") |
| `latitude` | `NR_LATITUDE` | `String` / `Double` | `VARCHAR2(30)` | Coordenada geográfica formatada |
| `longitude` | `NR_LONGITUDE` | `String` / `Double` | `VARCHAR2(30)` | Coordenada geográfica formatada |

#### Entidade 3: Fonte Científica (`TB_FONTE`)
Repositório de referências acadêmicas primárias (IAEA, CNEN, IPEN, artigos da Nature/Science, livros).

| Campo Java (Entity) | Coluna Oracle | Tipo Java | Tipo Oracle | Restrições / Regras |
| :--- | :--- | :--- | :--- | :--- |
| `id` | `ID_FONTE` | `Long` | `NUMBER(19)` | **PK**, Sequence `SEQ_FONTE` |
| `titulo` | `DS_TITULO` | `String` | `VARCHAR2(200)` | NOT NULL |
| `url` | `DS_URL` | `String` | `VARCHAR2(500)` | NOT NULL, link válido para referência |
| `dataConsulta` | `DT_CONSULTA` | `LocalDate` | `DATE` | Data de verificação da fonte |
| `atualizadoEm` | `DT_ATUALIZACAO` | `LocalDate` | `DATE` | Opcional |

#### Entidade 4: ArtigoFonte (`TB_ARTIGO_FONTE`)
Entidade associativa do relacionamento Muitos-para-Muitos (N:M) entre `Artigo` e `Fonte`.

| Campo Java (Entity) | Coluna Oracle | Tipo Java | Tipo Oracle | Restrições / Regras |
| :--- | :--- | :--- | :--- | :--- |
| `id` | `ID_ARTIGO_FONTE` | `Long` | `NUMBER(19)` | **PK**, Sequence `SEQ_ARTIGO_FONTE` |
| `artigo` | `ID_ARTIGO` | `Artigo` | `NUMBER(19)` | **FK** NOT NULL (`TB_ARTIGO`) |
| `fonte` | `ID_FONTE` | `Fonte` | `NUMBER(19)` | **FK** NOT NULL (`TB_FONTE`) |
| `observacao` | `DS_OBSERVACAO` | `String` | `VARCHAR2(255)` | Opcional (ex: "Páginas 45-50, Gráfico de decaimento") |

### 3.2. Padrão de Chave Primária para Oracle
Para máxima compatibilidade com Oracle Database (desde 11g até 19c/21c), utiliza-se o padrão de Sequence do JPA:
```java
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_artigo")
@SequenceGenerator(name = "seq_artigo", sequenceName = "SEQ_ARTIGO", allocationSize = 1)
@Column(name = "ID_ARTIGO")
private Long id;
```
*(Nota: Para ambientes de teste local em H2 em memória, o Hibernate emula sequences perfeitamente).*

---

## 4. Regras Rígidas da Sprint 1 (Rubrica Oficial FIAP — 100 Pontos)

| Critério | Peso | Como o Projeto Cumpre Rigorosamente |
| :--- | :--- | :--- |
| **1. Ideação & Contexto Nuclear** | **40 pts** | O domínio não utiliza dados fictícios banais (ex: "Artigo 1", "Teste"). Todo payload de teste, seed e documentação referencia dados reais: radiofármacos do IPEN, complexo de Angra dos Reis, tecnologia naval do CTMSP/Aramar, normas da CNEN e relatórios da AIEA. |
| **2. Modelo de Dados & Persistência** | **20 pts** | Implementação de `JpaRepository` com CRUD completo para as entidades principais. Mapeamento relacional adequado com tabelas no padrão Oracle, `@ManyToOne`, `@OneToMany` com `orphanRemoval` e chaves estrangeiras. |
| **3. Pesquisa Textual (Query Methods)** | **10 pts** | Método `findByTituloContainingIgnoreCase(String titulo, Pageable pageable)` no `ArtigoRepository` permitindo busca de artigos por palavras-chave com paginação nativa. |
| **4. Validation & Exception Handler** | **10 pts** | DTOs de entrada validados com Jakarta Bean Validation (`@NotBlank`, `@Size`, `@NotNull`, `@Positive`). `@RestControllerAdvice` (`GlobalExceptionHandler`) captura 400 (Bad Request com lista de campos inválidos), 404 (Not Found com mensagem clara), 422 (Regra de transição de status) e 500 (Erro genérico sem stacktrace vazado). |
| **5. Swagger / OpenAPI** | **10 pts** | `springdoc-openapi-starter-webmvc-ui` documentando todos os endpoints com `@Operation`, `@ApiResponses`, `@ApiResponse`, `@Schema` e exemplos descritivos de payloads. |
| **6. Boas Práticas & Configuração** | **10 pts** | Separação estrita Entity/DTO em Java Records; `.gitignore` cobrindo artefatos de compilação, IDEs e `.env`; `application.yaml` parametrizado sem credenciais reais no repositório Git. |

---

## 5. Padrão de Documentação OpenAPI / Swagger (springdoc-openapi 2.x / 3.x)

A API adota o modelo **code-first** com a biblioteca `springdoc-openapi` (`springdoc-openapi-starter-webmvc-ui`), cumprindo todos os critérios de avaliação de Java Advanced:

### 5.1. Configuração Global (`OpenApiConfig`)
- **Metadados da Aplicação:** Configuração centralizada utilizando `@OpenAPIDefinition` com informações declarativas e institucionais:
  ```java
  @Configuration
  @OpenAPIDefinition(
      info = @Info(
          title = "AMAZUL - Plataforma Educativa de Divulgação Nuclear",
          version = "v1",
          description = "API RESTful do projeto educativo em parceria acadêmica com a AMAZUL e o CTMSP. Disponibiliza conteúdos científicos, mapeamento geográfico de instalações e acervo de referências bibliográficas.",
          contact = @Contact(name = "Equipe Challenge FIAP", email = "contato@amazul.gov.br")
      ),
      servers = {
          @Server(url = "http://localhost:8080", description = "Ambiente de Desenvolvimento Local")
      }
  )
  public class OpenApiConfig { }
  ```
- **Nota sobre Segurança (Sprint 1 vs. Sprint 2):**
  > [!NOTE]
  > Na **Sprint 1**, a documentação OpenAPI **não inclui esquemas de segurança** (`@SecurityScheme` / JWT Bearer) nem bloqueia a navegação com `@SecurityRequirement`, garantindo que todos os endpoints possam ser testados livremente no Swagger UI sem dependência de autenticação prévia.
  > A configuração de segurança (`@SecurityScheme(type = HTTP, scheme = "bearer", bearerFormat = "JWT")` e o botão *Authorize*) está catalogada e planejada para a **Sprint 2**.

### 5.2. Controllers (Camada de Apresentação REST)
Todos os controllers devem seguir um padrão rigoroso de anotações OpenAPI:
- **`@Tag(name, description)`**: Aplicada no topo da classe para agrupar e descrever semanticamente o recurso (ex: `name = "Artigo"`, `description = "Gestão de artigos educativos sobre ciência e tecnologia nuclear"`).
- **`@Operation(summary, description)`**: Aplicada em cada método HTTP:
  - `summary`: Frase curta e objetiva sobre o que a rota faz.
  - `description`: Explicação didática do impacto de negócio da operação.
- **`@ApiResponses` e `@ApiResponse`**: Mapeamento explícito de todos os retornos possíveis com schemas tipados:
  - **Status 200 / 201 / 204 (Sucesso):**
    - `@ApiResponse(responseCode = "200", description = "Operação realizada com sucesso", content = @Content(schema = @Schema(implementation = PageResponse.class)))`
    - `@ApiResponse(responseCode = "201", description = "Recurso criado com sucesso", content = @Content(schema = @Schema(implementation = ArtigoResponse.class)))`
    - `@ApiResponse(responseCode = "204", description = "Recurso excluído com sucesso")`
  - **Status 400 (Bad Request):**
    - `@ApiResponse(responseCode = "400", description = "Dados de entrada ou parâmetros inválidos", content = @Content(schema = @Schema(implementation = ErrorValidation.class)))`
  - **Status 404 (Not Found):**
    - `@ApiResponse(responseCode = "404", description = "Recurso não encontrado para o ID informado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))`
  - **Status 422 (Unprocessable Content):**
    - `@ApiResponse(responseCode = "422", description = "Regra de negócio violada (ex: transição inválida de status)", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))`
  - **Status 500 (Internal Server Error):**
    - `@ApiResponse(responseCode = "500", description = "Erro interno não tratado no servidor", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))`

### 5.3. DTOs / Records (Contratos de Dados)
Os contratos de Request e Response utilizam anotações do Swagger para documentar o significado de cada campo com **dados científicos reais**:
- **`@Schema(description, example)` em cada campo:**
  ```java
  public record ArtigoRequest(
      @Schema(description = "Título descritivo do artigo pedagógico", example = "Produção de Iodo-131 para Radioterapia e Diagnóstico de Tireoide")
      @NotBlank @Size(max = 150) String titulo,

      @Schema(description = "Resumo executivo do conteúdo", example = "Apresenta como os reatores de pesquisa sintetizam radioisótopos cruciais na medicina nuclear brasileira.")
      @NotBlank @Size(max = 500) String resumo,

      @Schema(description = "Exemplo prático e tangível da aplicação no cotidiano", example = "Tratamento de câncer de tireoide e exames de imagem nos hospitais vinculados ao SUS.")
      @NotBlank String aplicacaoReal,

      @Schema(description = "Benefícios para a sociedade e mitigação de mitos", example = "Autonomia nacional na produção de radiofármacos e redução de custos hospitalares.")
      @NotBlank String impactoSociedade,

      @Schema(description = "Setor de aplicação da tecnologia", example = "SAUDE")
      @NotNull AreaAplicacao area,

      @Schema(description = "Identificador opcional de instalação já cadastrada", example = "1")
      Long localId,

      @Schema(description = "Dados para criação e vínculo automático de instalação", nullable = true)
      LocalRequest local
  ) {}
  ```
- **`@Schema(hidden = true)`**: Utilizado em propriedades geradas pelo servidor (como metadados ou IDs internos em payloads de envio) que não devam poluir o schema da requisição no Swagger UI.

### 5.4. Acesso e Rotas Públicas do Swagger UI
Na Sprint 1, a documentação está totalmente aberta para testes locais e avaliação acadêmica:
- **Swagger UI (Interface Gráfica):** `http://localhost:8080/swagger-ui.html` ou `http://localhost:8080/swagger-ui/index.html`
- **OpenAPI JSON (Especificação OpenAPI 3.0):** `http://localhost:8080/v3/api-docs`

> [!NOTE]
> **Planejamento para a Sprint 2 (Spring Security):**
> Quando a camada de autenticação com Spring Security e JWT for introduzida na Sprint 2, as rotas do Swagger serão mantidas públicas com liberação explícita no filtro de segurança:
> ```java
> .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
> .anyRequest().authenticated()
> ```

---

## 6. Regras de Negócio e Dinâmica de Domínio

### 6.1. Ciclo de Vida do Artigo (`StatusArtigo`)
O artigo pedagógico possui ciclo de vida controlado:
```
[RASCUNHO] ──────> [PUBLICADO] ──────> [ARQUIVADO]
     │                   │
     └───────────────────┴─────── (Não permite retorno para status anterior)
```
- **RASCUNHO:** Artigo em elaboração por educadores/pesquisadores. Todos os campos podem ser editados.
- **PUBLICADO:** Artigo aprovado e visível na plataforma educativa. Seu conteúdo central torna-se protegido contra alterações acidentais.
- **ARQUIVADO:** Artigo descontinuado ou mantido apenas para histórico documental.
- Se uma transição de status inválida for solicitada, o sistema dispara `TransicaoInvalidaStatusException` tratada como HTTP 422 (Unprocessable Content).

### 6.2. A Regra do `Local`: Como Evitar Criação Desconexa sem Amarrar Exclusividade
Um dilema clássico de modelagem:
> *"Um local não deve ser criado aleatoriamente sem contexto, mas não pode ficar preso a um único artigo, devendo permitir reutilização por outros artigos posteriores."*

#### Solução Arquitetural Recomendada:
1. **Natureza da Entidade:** `Local` é uma **Entidade de Catálogo Compartilhada** (*Shared Reference Entity*). Uma instalação como o *"Centro Experimental Aramar"* ou *"Usina Nuclear Angra 1"* é um patrimônio físico único que pode sediar dezenas de descobertas e artigos educacionais.
2. **Criação e Associação nos Endpoints de Artigo:**
   - **Opção 1 (Vínculo a Local Existente):** O cliente envia `localId: 3` no payload de criação/atualização do artigo. O artigo é associado diretamente ao registro existente.
   - **Opção 2 (Criação On-Demand / "Find or Create"):** Se o cliente enviar o objeto `local: { nome: "Aramar", codigoPais: "BR" }` sem ID:
     - O serviço pesquisa se já existe um local com aquele nome e país (`localRepository.findByNomeIgnoreCaseAndCodigoPais(...)`).
     - Se **já existir**, reaproveita a entidade existente (evitando duplicar o mesmo local no banco).
     - Se **não existir**, consome o serviço de geolocalização (`PaisService` / API externa) para enriquecer coordenadas e salva o novo `Local`.
3. **Comportamento em Cascata:**
   - No relacionamento `@ManyToOne` em `Artigo`:
     ```java
     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "ID_LOCAL")
     private Local local;
     ```
   - **NUNCA** usar `CascadeType.REMOVE` ou `CascadeType.ALL` em `local`. Se um `Artigo` for excluído, a instalação física `Local` continua existindo no banco de dados para os outros artigos!
4. **Endpoints de `LocalController`:**
   - `GET /api/locais`: Lista os locais cadastrados para alimentar filtros e dropdowns no frontend.
   - `GET /api/locais/{id}`: Detalha o local e suas coordenadas.
   - `POST /api/locais`: Pode ser mantido protegido (apenas administradores/curadores) ou ser omitido se a política de negócio for estritamente via criação de artigos.

### 6.3. Relacionamento de Fontes e Citações
- As fontes (`Fonte`) são referências bibliográficas canônicas (ex: publicação do IPEN no PubMed ou documento normativo da CNEN).
- Múltiplos artigos podem citar a mesma fonte primária.
- A entidade associativa `ArtigoFonte` permite enriquecer o vínculo com uma `observacao` contextual (ex: *"Tabela 3.2 de dosimetria"* ou *"Capítulo sobre enriquecimento isotópico"*).

---

## 7. Possibilidades de Evolução e Enriquecimento do Domínio

### 7.1. Evolução Imediata (Refinamento da Sprint 1)
- **Correção de Mapeamento dos Mappers:** Garantir que o construtor do `ArtigoResponse` receba os campos `nomeLocal` e `paisLocal` com tratamento defensivo contra `NullPointerException` (quando o artigo não possuir local vinculado).
- **Ajuste de Roteamento de Busca:** Separar explicitamente `@GetMapping("/{id}")` de `@GetMapping("/busca")` com parâmetro `@RequestParam String titulo` para evitar ambiguidades de roteamento no Spring MVC.
- **Implementação do Find-or-Create para Local:** Adicionar no `ArtigoServiceImpl` a lógica inteligente de resolução de local por ID ou por dados embutidos.

### 7.2. Evolução de Domínio (Sprint 2 — Interatividade e Rastreabilidade)
- **Segurança e Perfis de Acesso (Spring Security + JWT):**
  - Implementação de autenticação via Bearer Token.
  - Habilitação do `@SecurityScheme` e do botão *Authorize* no Swagger UI.
  - Liberação de rotas públicas (`/swagger-ui/**`, `/v3/api-docs/**`) e proteção de rotas de mutação para perfis `PESQUISADOR` e `CURADOR_AMAZUL`.
- **Auditoria Automática de Entidades:**
  - Criação de classe base `@MappedSuperclass` com `@CreatedDate`, `@LastModifiedDate` do Spring Data JPA (`@EnableJpaAuditing`).
- **Soft Delete:**
  - Implementar coluna `ST_ATIVO CHAR(1) DEFAULT 'S'` com `@SQLDelete` e `@Where(clause = "ST_ATIVO = 'S'")` para que a exclusão de artigos ou locais seja lógica, preservando histórico de referências.
- **Enriquecimento Geoespacial:**
  - Adicionar suporte a polígonos ou mapas interativos (ex: integração com Leaflet/OpenStreetMap no front usando as coordenadas da entidade `Local`).

### 7.3. Evolução de Arquitetura (Sprint 3 — Escalar e Produção)
- **Spring Data Specifications (Filtros Dinâmicos):**
  - Permitir busca combinada na mesma requisição: `/api/artigos?tema=SAUDE&pais=BR&titulo=Iodo`.
- **HATEOAS (Hypermedia As The Engine Of Application State):**
  - Adicionar links hipermídia às respostas das coleções (próxima página, auto-link, links para detalhes de fontes e locais).

---

## 8. Instruções de Execução e Ambientes

### 8.1. Perfis de Execução
- **Perfil `dev` (H2 Database em memória):**
  - Execução rápida para testes e validação local sem necessidade de banco de dados instalado.
  - Console H2 habilitado em `http://localhost:8080/h2-console`.
- **Perfil `oracle` (Oracle Database FIAP / Cloud):**
  - Conexão com o banco institucional da FIAP ou container Docker Oracle XE.
  - Configuração via variáveis de ambiente:
    ```bash
    SPRING_PROFILES_ACTIVE=oracle
    DB_URL=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
    DB_USERNAME=rmXXXXXX
    DB_PASSWORD=sua_senha_segura
    ```

### 8.2. Documentação da API (Swagger UI)
Após iniciar a aplicação, a documentação interativa está disponível em:
- **Swagger UI:** `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON:** `http://localhost:8080/v3/api-docs`

---

## 9. Diretrizes para o Assistente de IA
1. Ao sugerir alterações de código, certifique-se de que o projeto compila sem erros (`./mvnw test-compile`).
2. Mantenha os DTOs desacoplados das Entidades JPA.
3. Utilize sempre dados reais da ciência nuclear brasileira (IPEN, AMAZUL, Eletronuclear, CNEN, CTMSP).
4. Preserve as validações de Jakarta Bean Validation e garanta respostas HTTP semânticas e padronizadas.
5. Siga rigorosamente o padrão de documentação OpenAPI/Swagger detalhado na Seção 5 para todos os Controllers e Records.
