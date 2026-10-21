# Contexto do Projeto: API Java Advanced — Challenge AMAZUL
- **Domínio:** Plataforma Educativa de Divulgação Científica sobre Tecnologia Nuclear (AMAZUL).
- **Objetivo Principal:** Desenvolver e manter uma API RESTful em Java + Spring Boot que cumpra 100% da rubrica oficial da FIAP (Java Advanced e integração com Banco de Dados Oracle).
- **Arquitetura Evolutiva:** Projetado de forma modular para atender a Sprint 1 (Catálogo/Artigos, Localizações e Fontes) e expandir facilmente nas Sprints 2 e 3 (Segurança, Mensageria, IA, etc.).

# Semântica de Domínio (Tradução Negócio <-> Banco)
Para manter legibilidade no Java e compatibilidade total com o DDL/PLSQL do Oracle:
1. **Artigo / Descoberta (`@Table(name = "conteudo")`)**: Entidade central educativa. Explica problema, aplicação, benefício e limites da tecnologia nuclear em um setor (`tema`: ENERGIA, SAUDE, INDUSTRIA, AMBIENTE, PESQUISA, AGRICULTURA) e possui ciclo de vida (`status`: RASCUNHO, PUBLICADO, ARQUIVADO).
2. **Localizacao (`@Table(name = "local")`)**: Ponto geográfico real (país ISO-2, latitude, longitude). Relacionamento `@ManyToOne` opcional a partir de Artigo/Conteudo (ex: Sonda Voyager possui localResponse nulo).
3. **Fonte (`@Table(name = "fonte")`)**: Referência científica primária com URL única e data de consulta.
4. **ConteudoFonte (`@Table(name = "conteudo_fonte")`)**: Vínculo associativo N:N com chave composta (`conteudo_id`, `fonte_id`) e `nota` comprobatória.

# Regras Rígidas da Sprint 1 (Rubrica de 100 Pontos)
1. **Ideação e Coerência Educativa (40 pts):** Todos os exemplos, payloads, seeds e documentações devem usar dados científicos reais e equilibrados sobre tecnologia nuclear (ex: Radiofármacos no IPEN, Sonda Voyager, Irradiação de Alimentos, Inseto Estéril).
2. **Modelo de Dados e Persistência (20 pts):**
    - **Leituras (GET):** Devem usar Spring Data JPA (`JpaRepository`) com paginação.
    - **Escritas (POST, PUT, DELETE):** Para conformidade simultânea com Banco de Dados Oracle, devem invocar as Stored Procedures (`pr_*_ins`, `pr_*_upd`, `pr_*_del`) via `JdbcTemplate`/`SimpleJdbcCall` (ou permitir profile de teste localResponse documentado), respeitando o controle de concorrência (`revision`).
3. **Pesquisa / Consultas (10 pts):**
    - Obrigatório implementar busca textual usando **Query Method** do Spring Data JPA (ex: `findByTituloContainingIgnoreCase` ou combinação com `tema`/`status`).
4. **Validation e Tratamento Global de Exceções (10 pts):**
    - DTOs de entrada com Jakarta Bean Validation (`@NotBlank`, `@Size`, `@Pattern`, `@NotNull`, `@Valid`).
    - `@RestControllerAdvice` (`GlobalExceptionHandler`) capturando `MethodArgumentNotValidException` (400), recurso não encontrado (404) e erros de negócio/procedures Oracle `ORA-20001` a `ORA-20007` (400/404/409), retornando JSON padronizado sem vazar stacktrace.
5. **Swagger / OpenAPI (10 pts):**
    - `springdoc-openapi` configurado com `@Operation`, `@ApiResponses`, `@Parameter` e `@Schema` documentando todos os endpoints, parâmetros e códigos HTTP.
6. **Qualidade de Código e Entrega (10 pts):**
    - Separação estrita entre `Entity` e `DTO` (Request/Response em camelCase, datas ISO-8601 UTC).
    - Repositório deve conter `.gitignore` adequado, `README.md` completo com instruções de execução, configurações (`application.yml` sem credenciais expostas) e massa de dados para teste (collection Postman/Insomnia + scripts/JSON de seed com pelo menos 10 registros por tabela).

# Comportamento do Assistente
- Ao revisar ou gerar código, sempre valide contra os 6 critérios da rubrica acima.
- Nunca exponha entidades JPA diretamente nos Controllers.
- Nunca deixe senhas reais hardcoded nos arquivos versionados (use variáveis de ambiente com fallback seguro para dev).