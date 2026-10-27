# ⚛️ AMAZUL & CTMSP — Plataforma Educativa de Divulgação Científica sobre Tecnologia Nuclear

**API RESTful — Challenge FIAP 2026 (Java Advanced)**  
*Parceria Institucional: **AMAZUL (Amazônia Azul Tecnologias de Defesa S.A.)** & **CTMSP (Centro Tecnológico da Marinha em São Paulo)***

---

## 👥 Identificação do Grupo
| Integrante | RM |
| :--- | :--- |
| **Paulo Cavalcante Caroba** | RM566667 |
| **Renan da Silva Paulino** | RM566610 |
| **Sergio Henrique Santos** | RM567254 |

---

## 🎯 Apresentação da Solução e Contexto de Negócio

A energia e a tecnologia nuclear frequentemente enfrentam estigmas públicos associados ao desconhecimento de suas aplicações pacíficas. Este projeto compõe o núcleo de **backend** de uma **Plataforma Educativa e de Divulgação Científica**, desenhada para desmistificar a ciência nuclear por meio de dados reais, rastreabilidade bibliográfica e mapeamento geográfico de instalações no Brasil e no mundo:

- **Saúde (`SAUDE`):** Produção de radiofármacos (como Tecnécio-99m e Iodo-131) pelo IPEN/CNEN para diagnóstico oncológico e tratamentos no SUS.
- **Agricultura (`AGRICULTURA`):** Irradiação de alimentos para controle fitossanitário e técnicas isotópicas para conservação do solo.
- **Indústria (`INDUSTRIA`):** Gamagrafia industrial e ensaios não destrutivos em tubulações e estruturas metálicas.
- **Energia (`ENERGIA`):** Geração termonucleoelétrica limpa de base (Complexo Nuclear de Angra dos Reis).
- **Pesquisa e Propulsão (`PESQUISA`):** Reatores de pesquisa (RMB, IEA-R1) e o Programa Nuclear da Marinha (PNM / PROSUB no Centro Experimental Aramar).

---

## 🏛️ Arquitetura e Tecnologias Utilizadas

- **Linguagem & Framework:** Java 21 LTS + Spring Boot 4.1.1
- **Persistência:** Spring Data JPA / Hibernate + Bean Validation (`Jakarta Validation`)
- **Bancos de Dados Suportados:**
  - **H2 Database (Padrão):** Banco em memória para execução imediata e testes locais (`/h2-console`).
  - **Oracle Database:** Suporte via driver `ojdbc11` para instância Docker (`gvenzl/oracle-free`) ou servidor corporativo FIAP.
- **Integração HTTP Externa:** Spring `RestClient` consumindo a API `countrystatecity.in` para enriquecimento automático de coordenadas geográficas (`latitude`, `longitude` e nome oficial do país via código ISO-2).
- **Tratamento Global de Erros:** `@RestControllerAdvice` (`GlobalExceptionHandler`) padronizando respostas HTTP `400`, `404`, `422` e `500`.
- **Documentação Interativa:** SpringDoc OpenAPI 3 (`2.8.5`) com Swagger UI e Collections prontas para **Postman** e **Bruno**.

### Estrutura de Pacotes
```text
src/main/java/com/amazul/fiap/api_springboot/
├── client/countrystatecity/ # Cliente HTTP RestClient para API externa de países
├── config/                  # OpenApiConfig, RestClientConfig e GlobalExceptionHandler
├── controller/              # Camada REST (ArtigoController, FonteController, LocalController, PaisController)
├── dto/
│   ├── request/             # Contratos imutáveis de entrada (Java Records + Bean Validation)
│   ├── response/            # Contratos imutáveis de saída e paginação (PageResponse)
│   └── errors/              # Contratos padronizados de erro (ErrorResponse, ErrorValidation, FieldError)
├── exception/               # Exceções de domínio (ResourceNotFoundException, TransicaoInvalidaStatusException)
├── mapper/                  # Conversores isolados Entidade <-> DTO
├── model/                   # Entidades JPA (Artigo, Local, Fonte, ArtigoFonte) e Enums
├── repository/              # Interfaces Spring Data JPA + Query Methods
└── service/                 # Regras de negócio e orquestração (Interfaces + Implementações)
```

---

## 🚀 Guia de Execução Local (Sem Docker)

### Pré-requisitos
- **Java JDK 21** instalado e configurado na variável `JAVA_HOME`.
- **Git** instalado.
- *(Não é necessário instalar o Maven globalmente; o projeto inclui o Maven Wrapper `mvnw` / `mvnw.cmd`).*
- Chave de API gratuita do serviço [Country State City API](https://countrystatecity.in/) (para consulta de países e coordenadas).

### 1. Configurar Variáveis de Ambiente (`.env`)
Na raiz do projeto, crie um arquivo chamado `.env` (você pode copiar o modelo `.env.exemplo`):

```bash
# Linux / macOS
cp .env.exemplo .env

# Windows (PowerShell)
Copy-Item .env.exemplo .env
```

Preencha a variável `API_COUNTRY_KEY` no arquivo `.env`:
```env
API_COUNTRY_KEY=sua_chave_da_api_countrystatecity_aqui
```

### 2. Executar a Aplicação
Na raiz do projeto, execute:

```bash
# Windows (PowerShell / CMD)
.\mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```

A aplicação subirá na porta **`8080`** com o banco **H2 em memória** pronto para uso:
- **URL Base da API:** `http://localhost:8080`
- **Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **OpenAPI JSON:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)
- **Console H2 Database:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
  - *JDBC URL:* `jdbc:h2:mem:challenge-amazul`
  - *User Name:* `sfc`
  - *Password:* *(deixar em branco)*

---

## 🐳 Guia de Execução em Instância Docker (Para Colegas de Grupo e Deploy)

Existem **duas formas** de rodar o projeto utilizando Docker:
1. **Apenas a API em container** (usando banco H2 em memória).
2. **Ambiente Completo com Docker Compose** (API Spring Boot + Banco Oracle XE/Free em containers).

### Opção A: Gerar Imagem da API nativamente com Maven (Sem precisar de Dockerfile)
O plugin do Spring Boot permite empacotar a aplicação diretamente em uma imagem OCI/Docker usando Cloud Native Buildpacks:

```bash
# 1. Gerar a imagem Docker via Maven Wrapper
.\mvnw.cmd spring-boot:build-image "-Dspring-boot.build-image.imageName=amazul-api:v1"

# 2. Rodar o container passando a chave da API externa
docker run -d --name amazul-api -p 8080:8080 -e API_COUNTRY_KEY=sua_chave_aqui amazul-api:v1
```

### Opção B: Usando um `Dockerfile` Multi-Stage
Caso prefira realizar o build tradicional via `Dockerfile`, crie um arquivo `Dockerfile` na raiz do projeto com o conteúdo abaixo:

```dockerfile
# Estágio 1: Build com Maven e JDK 21
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Estágio 2: Runtime enxuto com JRE 21
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Em seguida, construa e execute o container:
```bash
# 1. Construir a imagem
docker build -t amazul-api:v1 .

# 2. Executar o container lendo o arquivo .env local
docker run -d --name amazul-api -p 8080:8080 --env-file .env amazul-api:v1
```

### Opção C: Subir Banco Oracle em Docker + API
Se o colega de grupo precisar testar com uma instância **Oracle Database** rodando em Docker:

```bash
# Subir container do Oracle Database Free (porta 1521)
docker run -d --name oracle-fiap \
  -p 1521:1521 \
  -e ORACLE_PASSWORD=123 \
  gvenzl/oracle-free:latest
```
Dados de conexão da instância Oracle Docker:
- **URL JDBC:** `jdbc:oracle:thin:@//localhost:1521/FREEPDB1`
- **Usuário:** `system`
- **Senha:** `123`

### Comandos Úteis de Gerenciamento Docker
```bash
# Acompanhar os logs da aplicação em tempo real
docker logs -f amazul-api

# Parar o container
docker stop amazul-api

# Remover o container
docker rm amazul-api
```

---

## 📦 Collections Prontas (Postman e Bruno)

O repositório já inclui collections pré-configuradas com todos os **22 endpoints**, exemplos de payloads científicos reais e respostas documentadas:

1. **Postman:**
   - Abra o Postman $\rightarrow$ clique em **Import**.
   - Selecione o arquivo: [`collections/postman/API Challenge SpringBoot - AMAZUL.postman_collection.json`](collections/postman/API%20Challenge%20SpringBoot%20-%20AMAZUL.postman_collection.json).
   - Certifique-se de definir a variável `baseUrl` como `http://localhost:8080` (ou selecione o environment local).

2. **Bruno:**
   - Abra o Bruno $\rightarrow$ clique em **Open Collection**.
   - Selecione a pasta: [`collections/bruno/API Challenge SpringBoot - AMAZUL`](collections/bruno/API%20Challenge%20SpringBoot%20-%20AMAZUL).
   - No canto superior direito do Bruno, selecione o ambiente **`Ambiente local`** (`baseUrl = http://localhost:8080`).

---

## 🧪 Roteiro Passo a Passo de Utilização e Testes (Guia para Avaliação)

Para validar todas as regras de negócio e relacionamentos da plataforma, recomendamos seguir a ordem abaixo:

### Passo 1: Consultar Países na Integração Externa (`GET /api/paises`)
Verifica a comunicação com a API externa *Country State City*:
```bash
# Listar todos os países (Formato: ISO2 - Nome)
curl -X GET "http://localhost:8080/api/paises"

# Consultar coordenadas de um país específico pelo código ISO-2
curl -X GET "http://localhost:8080/api/paises/BR"
```

### Passo 2: Cadastrar uma Instalação Nuclear (`POST /api/locais`)
Ao informar `codigoPais: "BR"`, a aplicação consulta automaticamente a API externa e preenche `nomePais`, `latitude` e `longitude`:
```bash
curl -X POST "http://localhost:8080/api/locais" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Complexo radiológico de Iperó/Aramar (CTMSP)",
    "codigoPais": "BR"
  }'
```

### Passo 3: Cadastrar uma Fonte Científica (`POST /api/fontes`)
Registra uma referência bibliográfica oficial (validando formato de URL e obrigatoriedade de título):
```bash
curl -X POST "http://localhost:8080/api/fontes" \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Programa Nuclear da Marinha - CTMSP",
    "url": "https://www.marinha.mil.br/ctmsp/programa-nuclear"
  }'
```

### Passo 4: Criar um Artigo Educativo (`POST /api/artigos`)
Todo artigo recém-criado inicia obrigatoriamente com o status **`RASCUNHO`**. Os setores aceitos no campo `area` são: `ENERGIA`, `SAUDE`, `AGRICULTURA`, `INDUSTRIA` e `PESQUISA`.
```bash
curl -X POST "http://localhost:8080/api/artigos" \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Produção de Radiofármacos para Diagnóstico e Tratamento Oncológico",
    "resumo": "Visão geral sobre o papel dos reatores de pesquisa na geração de radioisótopos essenciais para a medicina moderna.",
    "aplicacaoReal": "Uso do radioisótopo Tecnécio-99m (Tc-99m) produzido pelo IPEN/CNEN em procedimentos de cintilografia para mapeamento de órgãos vitais.",
    "impactoSociedade": "Possibilita diagnósticos precoces de tumores e doenças cardíacas no SUS, beneficiando milhões de pacientes anualmente com tratamentos não invasivos.",
    "area": "SAUDE",
    "localId": 1
  }'
```

### Passo 5: Vincular Local e Fonte Científica ao Artigo
- **Vincular/Atualizar Local do Artigo (`POST /api/artigos/{id}/locais/{localId}`):**
```bash
curl -X POST "http://localhost:8080/api/artigos/1/locais/1"
```

- **Associar Fonte Bibliográfica com Observação (`POST /api/artigos/{id}/fontes`):**
```bash
curl -X POST "http://localhost:8080/api/artigos/1/fontes" \
  -H "Content-Type: application/json" \
  -d '{
    "fonteId": 1,
    "observacao": "Capítulo 4, Tabela 2 - Decaimento do Mo-99"
  }'
```

### Passo 6: Ciclo de Vida do Artigo (`PUT /api/artigos/alterarStatus/{id}`)
O artigo segue uma máquina de estados controlada (`RASCUNHO` $\rightarrow$ `PUBLICADO` / `ARQUIVADO`). Uma vez `PUBLICADO` ou `ARQUIVADO`, tentativas de retorno para `RASCUNHO` disparam **`HTTP 422 Unprocessable Content`**:
```bash
# Publicar o artigo de ID 1
curl -X PUT "http://localhost:8080/api/artigos/alterarStatus/1?status=PUBLICADO"
```

### Passo 7: Consultas Paginadas e Pesquisa Textual
```bash
# Listar artigos com paginação
curl -X GET "http://localhost:8080/api/artigos?pagina=0&tamanho=10"

# Buscar artigos por palavra-chave no título (IgnoreCase)
curl -X GET "http://localhost:8080/api/artigos/busca?titulo=Radiofármacos&pagina=0&tamanho=10"
```

---

## 📋 Catálogo Completo de Endpoints

### Módulo: Artigos (`/api/artigos`)
| Método | Rota | Descrição | Status de Sucesso |
| :---: | :--- | :--- | :---: |
| `GET` | `/api/artigos` | Lista todos os artigos com paginação (`pagina`, `tamanho`) | `200 OK` |
| `GET` | `/api/artigos/{id}` | Busca um artigo detalhado por ID | `200 OK` |
| `GET` | `/api/artigos/busca` | Busca textual por título (`?titulo=...&pagina=0&tamanho=10`) | `200 OK` |
| `POST` | `/api/artigos` | Cria um novo artigo (inicia como `RASCUNHO`) | `201 Created` |
| `PUT` | `/api/artigos/{id}` | Atualiza os dados de um artigo existente | `200 OK` |
| `PUT` | `/api/artigos/alterarStatus/{id}` | Altera o status do artigo (`?status=PUBLICADO` ou `ARQUIVADO`) | `200 OK` |
| `POST` | `/api/artigos/{id}/fontes` | Associa uma fonte científica como referência do artigo | `200 OK` |
| `DELETE` | `/api/artigos/{id}/fontes/{fonteId}` | Remove o vínculo de uma fonte científica do artigo | `204 No Content` |
| `POST` | `/api/artigos/{id}/locais/{localId}` | Vincula ou atualiza a instalação/local de um artigo | `200 OK` |
| `DELETE` | `/api/artigos/{id}` | Remove permanentemente um artigo | `204 No Content` |

### Módulo: Fontes Científicas (`/api/fontes`)
| Método | Rota | Descrição | Status de Sucesso |
| :---: | :--- | :--- | :---: |
| `GET` | `/api/fontes` | Lista todas as fontes bibliográficas com paginação | `200 OK` |
| `GET` | `/api/fontes/{id}` | Busca uma fonte científica por ID e lista artigos vinculados | `200 OK` |
| `POST` | `/api/fontes` | Cadastra uma nova fonte científica | `201 Created` |
| `PUT` | `/api/fontes/{id}` | Atualiza título e URL de uma fonte existente | `200 OK` |
| `DELETE` | `/api/fontes/{id}` | Remove uma fonte científica | `204 No Content` |

### Módulo: Locais e Instalações (`/api/locais`)
| Método | Rota | Descrição | Status de Sucesso |
| :---: | :--- | :--- | :---: |
| `GET` | `/api/locais` | Lista todas as instalações e locais com paginação | `200 OK` |
| `GET` | `/api/locais/{id}` | Busca detalhes e coordenadas de um local por ID | `200 OK` |
| `POST` | `/api/locais` | Cadastra novo local (enriquece coordenadas via `codigoPais`) | `201 Created` |
| `PUT` | `/api/locais/{id}` | Atualiza dados de um local existente | `200 OK` |
| `DELETE` | `/api/locais/{id}` | Remove um local cadastrado | `204 No Content` |

### Módulo: Países — Geolocalização (`/api/paises`)
| Método | Rota | Descrição | Status de Sucesso |
| :---: | :--- | :--- | :---: |
| `GET` | `/api/paises` | Lista países e códigos ISO-2 obtidos da API externa | `200 OK` |
| `GET` | `/api/paises/{codigoPais}` | Retorna nome oficial, ISO-2, latitude e longitude do país | `200 OK` |

---

## 🛡️ Padronização de Respostas de Erro

A API possui tratamento centralizado via [`GlobalExceptionHandler`](src/main/java/com/amazul/fiap/api_springboot/config/GlobalExceptionHandler.java):
- **`400 Bad Request` (`ErrorValidation` / `ErrorResponse`):** Violação de Bean Validation (`@NotBlank`, `@Size`, `@URL`, `@Pattern`, `@Positive`) ou erro de tipo em parâmetros.
- **`404 Not Found` (`ErrorResponse`):** Recurso (`Artigo`, `Fonte` ou `Local`) não encontrado para o ID informado.
- **`422 Unprocessable Content` (`ErrorResponse`):** Violação de regra de domínio na transição de status do artigo (`TransicaoInvalidaStatusException`).
- **`500 Internal Server Error` (`ErrorResponse`):** Falha inesperada tratada de forma segura sem expor *stacktrace* interno.
