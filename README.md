# AMAZUL — API educativa

Java 21 e Spring Boot 4.1.1. API de artigos sobre aplicações da tecnologia nuclear, locais e fontes bibliográficas.

| Integrante | RM |
|---|---|
| Sérgio Henrique S. Santos | 567254 |
| Paulo Cavalcante Caroba | 566667 |
| Renan da Silva Paulino | 566610 |

Turma: 2TDSPS.

## Executar localmente

```bash
./mvnw spring-boot:run
```

No Windows: ` .\mvnw.cmd spring-boot:run`.
Swagger: http://localhost:8080/swagger-ui/index.html. Health: http://localhost:8080/actuator/health.
O perfil local permite alterações sem autenticação; usar apenas na máquina de desenvolvimento.

O banco padrão é H2 em memória: os dados desaparecem ao encerrar o processo. O console local fica em `/h2-console`, JDBC `jdbc:h2:mem:challenge-amazul`, usuário `sfc`, senha vazia.

## Docker / VM Azure

```bash
cp .env.exemplo .env
# Preencher APP_ADMIN_PASSWORD com pelo menos 16 caracteres.
docker build -t amazul-api:s1 .
docker run -d --name amazul-api --env-file .env \
  --read-only --tmpfs /tmp:rw,noexec,nosuid,size=128m \
  --cap-drop ALL --security-opt no-new-privileges \
  --memory 640m --cpus 1 --pids-limit 256 \
  -p 127.0.0.1:8080:8080 amazul-api:s1
```

A imagem ativa o perfil `azure`: consultas e Swagger públicos, alterações via HTTP Basic com `APP_ADMIN_USERNAME` e `APP_ADMIN_PASSWORD`. Console H2 desativado. A imagem usa usuário 10001 e heap máximo de 256 MiB; o limite total inclui memória nativa da JVM. Para publicar consultas na VM, usar `-p 8080:8080`.

Para cadastrar pelo Swagger sem transportar credenciais em HTTP público, abrir um túnel SSH:

```bash
ssh -N -L 18080:127.0.0.1:8080 azureuser@IP_DA_VM
```

Abrir http://localhost:18080/swagger-ui/index.html e usar **Authorize**. Fora do túnel, usar HTTPS para operações autenticadas. Não versionar `.env`, senhas ou chaves.

## Rotas

| Recurso | Operações |
|---|---|
| `/api/artigos` | GET paginado, POST |
| `/api/artigos/{id}` | GET, PUT, DELETE |
| `/api/artigos/buscaPorTitulo?titulo=...` | GET por título |
| `/api/artigos/buscaPorPais?pais=Brazil` | GET por país do local |
| `/api/artigos/alterarStatus/{id}?status=PUBLICADO` | PUT |
| `/api/artigos/{id}/fontes` | POST com `fonteId` e `observacao` |
| `/api/artigos/{id}/fontes/{fonteId}` | DELETE do vínculo |
| `/api/artigos/{id}/locais/{localId}` | POST do vínculo |
| `/api/fontes` e `/api/locais` | GET paginado, POST; GET/PUT/DELETE por `/{id}` |
| `/api/paises` e `/api/paises/{codigoPais}` | GET na integração externa |

Paginação: `pagina=0&tamanho=10`; tamanho entre 1 e 100. Áreas: `SAUDE`, `ENERGIA`, `AGRICULTURA`, `INDUSTRIA`, `PESQUISA`.

```json
{
  "titulo": "Radiofármacos",
  "resumo": "Aplicações da tecnologia nuclear na saúde.",
  "aplicacaoReal": "Uso de radioisótopos em diagnóstico.",
  "impactoSociedade": "Apoio ao diagnóstico médico.",
  "area": "SAUDE"
}
```

Artigos começam em `RASCUNHO`; o estado pode avançar para `PUBLICADO` ou `ARQUIVADO`, ambos terminais nesta versão. Publicação não equivale a revisão científica automática. Respostas: 400 para entrada inválida, 404 inexistente, 409 conflito e 422 transição inválida.

## Países e Oracle

`API_COUNTRY_KEY` é opcional para iniciar a API; sem chave, os recursos que consultam países retornam 503. Configurar a chave real da Country State City para cadastrar local com país. `API_COUNTRY_BASE_URL` padrão: `https://api.countrystatecity.in/v1`. Coordenadas representam o país e não a posição exata de uma instalação.

Oracle: ativar `SPRING_PROFILES_ACTIVE=azure,oracle` no container e preencher `ORACLE_URL`, `ORACLE_USUARIO`, `ORACLE_SENHA`. O perfil valida um schema existente compatível com as entidades; não cria nem provisiona Oracle. H2 não comprova execução em Oracle.

## Verificar e encerrar

```bash
./mvnw verify
docker logs amazul-api
docker stop amazul-api
docker rm amazul-api
```

Collections em `collections/postman` e `collections/bruno`. Ajustar `baseUrl` ao endereço usado; no Azure, fornecer Basic Auth para alterações.
