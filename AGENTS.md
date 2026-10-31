# Convenções do projeto

API educativa AMAZUL, em Java 21 e Spring Boot. Manter a organização em controller, service, repository, DTO e mapper. Consultar o README antes de alterar a execução local ou Docker.

## Escrita e comentários

- Usar português simples e direto, seguindo o vocabulário e o estilo dos arquivos existentes.
- Comentar regras de negócio, escolhas e limitações que não estejam claras no código. Evitar comentários que apenas descrevem cada instrução ou contam como a alteração foi feita.
- Descrever nas PRs o problema, a alteração e as verificações realizadas. Registrar limitações concretas, sem relatos da conversa, autoavaliações ou assinaturas automáticas.
- Preservar os textos já revisados pelo grupo. Ler a descrição e os comentários atuais antes de atualizá-los.
- Não inventar autoria, resultados ou aprovações. Preservar licenças, créditos obrigatórios e histórico Git.

## Alterações

- Manter credenciais fora do Git e da imagem Docker.
- Validar mudanças funcionais com os cenários pertinentes; ajustes apenas de texto não exigem novo deploy.
- A branch `update-java` está em revisão. Não aprovar nem fazer merge sem pedido explícito.
