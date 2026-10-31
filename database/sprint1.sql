--Drop table inicial
DROP TABLE tb_artigo_fonte CASCADE CONSTRAINTS;
DROP TABLE tb_artigo CASCADE CONSTRAINTS;
DROP TABLE tb_fonte CASCADE CONSTRAINTS;
DROP TABLE tb_local CASCADE CONSTRAINTS;
DROP TABLE tb_pais CASCADE CONSTRAINTS;

DROP SEQUENCE seq_pais;
DROP SEQUENCE seq_local;
DROP SEQUENCE seq_fonte;
DROP SEQUENCE seq_artigo;
DROP SEQUENCE seq_artigo_fonte;

CREATE SEQUENCE seq_pais START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_local START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_fonte START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_artigo START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_artigo_fonte START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

--Criação das tabelas
CREATE TABLE tb_pais (
                         id          NUMBER DEFAULT seq_pais.NEXTVAL NOT NULL,
                         nome        VARCHAR2(100) NOT NULL,
                         codigo_pais CHAR(2) NOT NULL UNIQUE,
                         latitude    VARCHAR2(30),
                         longitude   VARCHAR2(30),
                         CONSTRAINT tb_pais_pk PRIMARY KEY (id)
);

CREATE TABLE tb_local (
                          id      NUMBER DEFAULT seq_local.NEXTVAL NOT NULL,
                          nome    VARCHAR2(150) NOT NULL,
                          id_pais NUMBER,
                          CONSTRAINT tb_local_pk PRIMARY KEY (id),
                          CONSTRAINT fk_local_pais FOREIGN KEY ( id_pais )REFERENCES tb_pais ( id )
);

CREATE TABLE tb_fonte (
                          id            NUMBER DEFAULT seq_fonte.NEXTVAL NOT NULL,
                          titulo        VARCHAR2(150) NOT NULL,
                          url           VARCHAR2(500) NOT NULL UNIQUE,
                          data_consulta DATE DEFAULT sysdate,
                          atualizado_em TIMESTAMP DEFAULT current_timestamp,
                          CONSTRAINT tb_fonte_pk PRIMARY KEY (id)
);

CREATE TABLE tb_artigo (
                           id                NUMBER DEFAULT seq_artigo.NEXTVAL NOT NULL,
                           area_aplicacao    VARCHAR2(30) NOT NULL CHECK ( area_aplicacao IN ('ENERGIA', 'SAUDE', 'AGRICULTURA', 'INDUSTRIA', 'PESQUISA')),
                           titulo            VARCHAR2(150) NOT NULL,
                           resumo            VARCHAR2(500) NOT NULL,
                           aplicacao_real    VARCHAR2(1500) NOT NULL,
                           impacto_sociedade VARCHAR2(1500) NOT NULL,
                           status            VARCHAR2(20) DEFAULT 'RASCUNHO' NOT NULL CHECK ( status IN ( 'RASCUNHO', 'PUBLICADO', 'ARQUIVADO' ) ),
                           atualizado_em     TIMESTAMP DEFAULT current_timestamp,
                           id_local          NUMBER,
                           CONSTRAINT tb_artigo_pk PRIMARY KEY (id),
                           CONSTRAINT fk_artigo_local FOREIGN KEY ( id_local ) REFERENCES tb_local ( id )
);

CREATE TABLE tb_artigo_fonte (
                                 id         NUMBER DEFAULT seq_artigo_fonte.NEXTVAL NOT NULL,
                                 artigo_id  NUMBER NOT NULL,
                                 fonte_id   NUMBER NOT NULL,
                                 observacao VARCHAR2(255),
                                 CONSTRAINT tb_artigo_fonte_pk PRIMARY KEY (id),
                                 CONSTRAINT fk_af_artigo FOREIGN KEY ( artigo_id ) REFERENCES tb_artigo ( id ) ON DELETE CASCADE,
                                 CONSTRAINT fk_af_fonte FOREIGN KEY ( fonte_id ) REFERENCES tb_fonte ( id ) ON DELETE CASCADE,
                                 CONSTRAINT uk_artigo_fonte UNIQUE ( artigo_id, fonte_id )
);

-- Inserts (Carga de dados)                                                                                                                                       

--Paises
INSERT INTO tb_pais (nome, codigo_pais, latitude, longitude)
VALUES ( 'Brazil', 'BR', '-10', '-55' );

INSERT INTO tb_pais (nome, codigo_pais, latitude, longitude)
VALUES ( 'Ukraine', 'UA', '49', '32' );

INSERT INTO tb_pais (nome, codigo_pais, latitude, longitude)
VALUES ( 'United States', 'US', '38', '-97' );

INSERT INTO tb_pais (nome, codigo_pais, latitude, longitude)
VALUES ( 'Germany', 'DE', '51', '9' );

INSERT INTO tb_pais (nome, codigo_pais, latitude, longitude)
VALUES ( 'China', 'CN', '35', '105' );

--Locais    
INSERT INTO tb_local (nome, id_pais) VALUES ('Mina de Caetité/Lagoa Real', 1);
INSERT INTO tb_local (nome, id_pais) VALUES ('Usina Nuclear de Angra dos Reis', 1);
INSERT INTO tb_local (nome, id_pais) VALUES ('Centro Experimental Aramar', 1);
INSERT INTO tb_local (nome, id_pais) VALUES ('Reator Multipropósito Brasileiro (RMB)', 1);
INSERT INTO tb_local (nome, id_pais) VALUES ('Indústrias Nucleares do Brasil (INB) - Resende', 1);

INSERT INTO tb_local (nome, id_pais) VALUES ('Usina Nuclear de Chernobyl', 2);
INSERT INTO tb_local (nome, id_pais) VALUES ('Usina Nuclear de Zaporizhzhia', 2);
INSERT INTO tb_local (nome, id_pais) VALUES ('Usina Nuclear de Rivne', 2);
INSERT INTO tb_local (nome, id_pais) VALUES ('Usina Nuclear de Khmelnytskyi', 2);
INSERT INTO tb_local (nome, id_pais) VALUES ('Usina Nuclear de South Ukraine', 2);

INSERT INTO tb_local (nome, id_pais) VALUES ('Usina Nuclear de Palo Verde', 3);
INSERT INTO tb_local (nome, id_pais) VALUES ('Usina Nuclear de Three Mile Island', 3);
INSERT INTO tb_local (nome, id_pais) VALUES ('Laboratório Nacional de Oak Ridge', 3);
INSERT INTO tb_local (nome, id_pais) VALUES ('Usina Nuclear de Diablo Canyon', 3);
INSERT INTO tb_local (nome, id_pais) VALUES ('Idaho National Laboratory', 3);

INSERT INTO tb_local (nome, id_pais) VALUES ('Usina Nuclear de Isar', 4);
INSERT INTO tb_local (nome, id_pais) VALUES ('Usina Nuclear de Brokdorf', 4);
INSERT INTO tb_local (nome, id_pais) VALUES ('Usina Nuclear de Grohnde', 4);
INSERT INTO tb_local (nome, id_pais) VALUES ('Centro de Pesquisa de Jülich', 4);
INSERT INTO tb_local (nome, id_pais) VALUES ('Reator de Pesquisa FRM II (Garching)', 4);

INSERT INTO tb_local (nome, id_pais) VALUES ('Usina Nuclear de Daya Bay', 5);
INSERT INTO tb_local (nome, id_pais) VALUES ('Usina Nuclear de Qinshan', 5);
INSERT INTO tb_local (nome, id_pais) VALUES ('Usina Nuclear de Fuqing', 5);
INSERT INTO tb_local (nome, id_pais) VALUES ('Reator Experimental de Fusão EAST', 5);
INSERT INTO tb_local (nome, id_pais) VALUES ('Instituto de Energia Nuclear da China (CIAE)', 5);

--Fontes    
INSERT INTO tb_fonte (titulo, url, data_consulta) VALUES
    ('IAEA: Grossi pede progresso concreto na implantação da energia nuclear', 'https://www.iaea.org/newscenter/news/iaeas-grossi-calls-for-progress-in-deploying-nuclear-power-as-ministerial-conference-opens', sysdate);

INSERT INTO tb_fonte (titulo, url, data_consulta) VALUES
    ('Brasil cria grupo de trabalho focado em urânio', 'https://world-nuclear-news.org/articles/brazil-establishes-uranium-focused-working-group', sysdate);

INSERT INTO tb_fonte (titulo, url, data_consulta) VALUES
    ('ANS: Novas construções nucleares e desenvolvimentos de SMR no Canadá, Turquia, Filipinas e Tailândia', 'https://www.ans.org/news/2026-10-29/article-8529/new-nuclear-builds-smr-developments-in-canada-turkey-philippines-thailand/', sysdate);

INSERT INTO tb_fonte (titulo, url, data_consulta) VALUES
    ('Governo do Brasil institui grupo técnico para estudar reatores nucleares de potência', 'https://www.gov.br/mme/pt-br/assuntos/noticias/governo-do-brasil-institui-grupo-tecnico-para-estudar-reatores-nucleares-de-potencia-no-pais', sysdate);

INSERT INTO tb_fonte (titulo, url, data_consulta) VALUES
    ('IAEA INPRO: Aplicações não elétricas da energia nuclear', 'https://www.iaea.org/newscenter/news/26th-inpro-dialogue-forum-explores-non-electric-applications-of-nuclear-energy', sysdate);

INSERT INTO tb_fonte (titulo, url, data_consulta) VALUES
    ('China e Brasil entre os novos signatários da meta de triplicar a energia nuclear', 'https://world-nuclear-news.org/articles/china-and-brazil-sign-up-to-tripling-nuclear-goal', sysdate);

INSERT INTO tb_fonte (titulo, url, data_consulta) VALUES
    ('ARPA-E do DOE seleciona oito projetos de desenvolvimento de combustível transurânico', 'https://www.ans.org/news/article-8532/does-arpae-selects-eight-transuranic-fuel-development-projects/', sysdate);

INSERT INTO tb_fonte (titulo, url, data_consulta) VALUES
    ('Ministra Luciana Santos participa de reunião na CNEN sobre o Reator Multipropósito Brasileiro (RMB)', 'https://www.gov.br/ipen/pt-br/assuntos/clipping-de-noticias/ministra-luciana-santos-participa-de-reuniao-na-cnen-sobre-o-empreendimento-rmb', sysdate);

INSERT INTO tb_fonte (titulo, url, data_consulta) VALUES
    ('CNEN participa de audiência pública sobre pequenos reatores modulares (SMRs)', 'https://www.gov.br/cnen/pt-br/assunto/ultimas-noticias/cnen-participa-de-audiencia-publica-sobre-pequenos-reatores-modulares', sysdate);

INSERT INTO tb_fonte (titulo, url, data_consulta) VALUES
    ('IAEA projeta que capacidade nuclear global pode triplicar até 2060', 'https://www.iaea.org/newscenter/pressreleases/iaea-projects-global-nuclear-power-capacity-could-triple-by-2060-continuing-rising-trend', sysdate);

--Artigos
INSERT INTO tb_artigo (area_aplicacao, titulo, resumo, aplicacao_real, impacto_sociedade, status, id_local) VALUES
    ('SAUDE', 'Reator Multipropósito Brasileiro e Autossuficiência em Radiofármacos',
     'O RMB produzirá radioisótopos como Tc-99m para diagnóstico e tratamento oncológico, reduzindo a dependência de importações.',
     'Após conclusão prevista para 2030, o RMB fornecerá radiofármacos ao SUS para exames de imagem e terapias.',
     'Reduz custos e tempo de espera para pacientes oncológicos e cardíacos, fortalecendo a soberania em saúde nuclear.',
     'RASCUNHO', 2);

INSERT INTO tb_artigo (area_aplicacao, titulo, resumo, aplicacao_real, impacto_sociedade, status, id_local) VALUES
    ('ENERGIA', 'Pequenos Reatores Modulares (SMRs) na Europa: Estratégia e Desafios',
     'A Comissão Europeia lançou estratégia para colocar primeiros SMRs em operação no início da década de 2030.',
     'Projetos-piloto em países da UE buscam viabilizar SMRs para geração elétrica e calor industrial.',
     'Pode acelerar a descarbonização e reduzir dependência energética, mas exige investimento público e cooperação regulatória.',
     'RASCUNHO', 14);

INSERT INTO tb_artigo (area_aplicacao, titulo, resumo, aplicacao_real, impacto_sociedade, status, id_local) VALUES
    ('ENERGIA', 'Projeções da IAEA 2026: Capacidade Nuclear Global até 2060',
     'A IAEA revisou para cima as projeções: cenário alto prevê 1.284 GWe em 2060, com SMRs representando até 28% das novas adições.',
     'Países usam as projeções para planejamento energético de longo prazo e definição de metas de descarbonização.',
     'Indica papel crescente da nuclear na segurança energética global, mas requer investimentos massivos e gestão de resíduos.',
     'RASCUNHO', 16);

INSERT INTO tb_artigo (area_aplicacao, titulo, resumo, aplicacao_real, impacto_sociedade, status, id_local) VALUES
    ('ENERGIA', 'Expansão da Produção de Urânio no Brasil: Estratégias da INB',
     'A INB busca parcerias com BNDES para modelagem de negócios e ampliação da mineração e conversão de urânio.',
     'Com meta de 14 GW nucleares até 2050, o Brasil precisaria de cerca de 3 mil toneladas de urânio por ano.',
     'Reduz dependência externa e agrega valor à cadeia do combustível nuclear, com impactos econômicos e geopolíticos.',
     'RASCUNHO', 5);

INSERT INTO tb_artigo (area_aplicacao, titulo, resumo, aplicacao_real, impacto_sociedade, status, id_local) VALUES
    ('PESQUISA', 'Desenvolvimento de Novas Ligas para Reatores Nucleares Avançados',
     'Pesquisadores do PNNL usam design computacional (RAD) para criar ligas como V555, resistentes a radiação e altas temperaturas.',
     'A liga V555 será testada em condições de reator para aplicação em revestimentos de combustível e componentes estruturais.',
     'Materiais mais duráveis aumentam segurança e vida útil de reatores, viabilizando tecnologias de Geração IV.',
     'RASCUNHO', 11);

INSERT INTO tb_artigo (area_aplicacao, titulo, resumo, aplicacao_real, impacto_sociedade, status, id_local) VALUES
    ('ENERGIA', 'Reestruturação Financeira de Angra 3 e o Futuro da Energia Nuclear no Brasil',
     'O CNPE reconheceu interesse público na suspensão temporária de pagamentos de dívidas de Angra 3 para viabilizar a retomada.',
     'A medida permite que BNDES e Caixa avaliem reestruturação, mas não garante conclusão da obra.',
     'Decisão afeta preços de energia e investimentos no setor, com debate sobre custo-benefício da usina.',
     'RASCUNHO', 2);

INSERT INTO tb_artigo (area_aplicacao, titulo, resumo, aplicacao_real, impacto_sociedade, status, id_local) VALUES
    ('PESQUISA', 'Desafios de Governança e Financiamento do Reator Multipropósito Brasileiro',
     'TCU identificou riscos de atraso por falta de recursos previsíveis e falhas contratuais nas obras do RMB em Iperó (SP).',
     'Recomendações ao FNDCT e MCTI buscam dar previsibilidade ao cronograma e evitar paralisações.',
     'Atrasos elevam custos e adiam benefícios à saúde e à pesquisa, exigindo maior transparência na gestão pública.',
     'RASCUNHO', 4);

INSERT INTO tb_artigo (area_aplicacao, titulo, resumo, aplicacao_real, impacto_sociedade, status, id_local) VALUES
    ('ENERGIA', 'Aliança Trilateral EUA-Coreia do Sul-Japão para SMRs',
     'Memorando de cooperação busca combinar capacidades em implantação, cadeia de suprimentos e treinamento para SMRs.',
     'Empresas coreanas já investem em desenvolvedores americanos de SMR e fornecem componentes.',
     'Pode acelerar a implantação global de SMRs e redefinir competitividade no mercado nuclear.',
     'RASCUNHO', 11);

INSERT INTO tb_artigo (area_aplicacao, titulo, resumo, aplicacao_real, impacto_sociedade, status, id_local) VALUES
    ('PESQUISA', 'Ferramentas Analíticas para Combustíveis de Reatores de Geração IV',
     'Sandia desenvolve plataforma TRU-PATH para avaliar segurança, salvaguardas e tecnoconomia de combustíveis avançados.',
     'Ferramenta apoiará desenvolvedores na transição de tecnologias de combustível do laboratório para produção comercial.',
     'Combustíveis mais seguros e eficientes são essenciais para viabilizar reatores avançados e reduzir resíduos.',
     'RASCUNHO', 13);

INSERT INTO tb_artigo (area_aplicacao, titulo, resumo, aplicacao_real, impacto_sociedade, status, id_local) VALUES
    ('INDUSTRIA', 'SMRs e Aplicações Não Elétricas: Calor Industrial e Hidrogênio',
     'Fórum INPRO da IAEA discute uso de SMRs para descarbonizar setores como química, siderurgia e produção de hidrogênio.',
     'Projetos-piloto na Europa e Ásia avaliam viabilidade técnica e econômica de SMRs para calor de processo.',
     'Pode descarbonizar indústrias difíceis de eletrificar, mas exige adaptação regulatória e cadeias de suprimento.',
     'RASCUNHO', 21);

--Relacionamento - Artigo-Fonte
INSERT INTO tb_artigo_fonte (artigo_id, fonte_id, observacao) VALUES
    (1, 8, 'Reunião da ministra Luciana Santos na CNEN sobre o empreendimento RMB e produção de radioisótopos');
INSERT INTO tb_artigo_fonte (artigo_id, fonte_id, observacao) VALUES
    (1, 10, 'Contexto de expansão da capacidade nuclear e demanda por radioisótopos no planejamento até 2060');

INSERT INTO tb_artigo_fonte (artigo_id, fonte_id, observacao) VALUES
    (2, 3, 'Detalhes da estratégia da Comissão Europeia para os primeiros SMRs na década de 2030');
INSERT INTO tb_artigo_fonte (artigo_id, fonte_id, observacao) VALUES
    (2, 10, 'Projeções da IAEA sobre participação de SMRs nas novas adições de capacidade até 2060');

-- Artigo 3: Projeções da IAEA (ENERGIA)
INSERT INTO tb_artigo_fonte (artigo_id, fonte_id, observacao) VALUES
    (3, 10, 'Dados do relatório de projeções: 1.284 GWe no cenário alto e 28% de SMRs nas novas adições');
INSERT INTO tb_artigo_fonte (artigo_id, fonte_id, observacao) VALUES
    (3, 1, 'Declarações de Grossi sobre progresso concreto na implantação da energia nuclear');

INSERT INTO tb_artigo_fonte (artigo_id, fonte_id, observacao) VALUES
    (4, 4, 'Participação da INB no Nuclear Summit e estratégias para ampliar produção de urânio');
INSERT INTO tb_artigo_fonte (artigo_id, fonte_id, observacao) VALUES
    (4, 2, 'Criação de grupo de trabalho focado em urânio no Brasil');
INSERT INTO tb_artigo_fonte (artigo_id, fonte_id, observacao) VALUES
    (4, 6, 'Metas de expansão nuclear brasileira e demanda estimada de 3 mil t/ano de urânio até 2050');

INSERT INTO tb_artigo_fonte (artigo_id, fonte_id, observacao) VALUES
    (5, 5, 'Metodologia RAD e desenvolvimento da liga V555 no PNNL');

INSERT INTO tb_artigo_fonte (artigo_id, fonte_id, observacao) VALUES
    (6, 7, 'Decisão do CNPE sobre interesse público na suspensão do pagamento das dívidas de Angra 3');
INSERT INTO tb_artigo_fonte (artigo_id, fonte_id, observacao) VALUES
    (6, 6, 'Contexto de metas nucleares brasileiras e papel de Angra 3 na matriz');

INSERT INTO tb_artigo_fonte (artigo_id, fonte_id, observacao) VALUES
    (7, 9, 'Achados do TCU sobre riscos de atraso, falta de recursos previsíveis e falhas contratuais no RMB');
INSERT INTO tb_artigo_fonte (artigo_id, fonte_id, observacao) VALUES
    (7, 8, 'Posicionamento da CNEN/MCTI sobre o andamento do empreendimento RMB');

INSERT INTO tb_artigo_fonte (artigo_id, fonte_id, observacao) VALUES
    (8, 3, 'Memorando trilateral e investimentos de empresas coreanas em desenvolvedores americanos de SMR');
INSERT INTO tb_artigo_fonte (artigo_id, fonte_id, observacao) VALUES
    (8, 10, 'Perspectiva de mercado global de SMRs e competitividade internacional');

INSERT INTO tb_artigo_fonte (artigo_id, fonte_id, observacao) VALUES
    (9, 5, 'Financiamento de US$ 3,38 milhões à Sandia e plataforma TRU-PATH');

INSERT INTO tb_artigo_fonte (artigo_id, fonte_id, observacao) VALUES
    (10, 1, 'Fórum INPRO da IAEA sobre aplicações não elétricas da energia nuclear');
INSERT INTO tb_artigo_fonte (artigo_id, fonte_id, observacao) VALUES
    (10, 10, 'Projeções de demanda por calor industrial descarbonizado no horizonte 2060');

COMMIT;

--JOINS

--Join para ver a quantidade de artigos registrados para cada país da nossa base
SELECT p.nome,
       COUNT(*) as ArtigosPais
FROM tb_artigo a
         JOIN tb_local l
              ON a.id_local = l.id
         JOIN tb_pais p
              ON l.id_pais = p.id
GROUP BY p.nome;

--Join - Locais citados nos artigos registrados
SELECT a.titulo,
       l.nome
FROM tb_artigo a
         JOIN tb_local l
              ON a.id_local = l.id;

--Join - Fontes mais utilizadas - Retorna a quantidade de artigos em que estão sendo usadas as fontes de forma ordenada
SELECT f.titulo,
       COUNT(*) QtdArtigos
FROM tb_artigo_fonte af
         JOIN tb_artigo a
              ON af.artigo_id = a.id
         JOIN tb_fonte f
              ON af.fonte_id = f.id
GROUP BY f.id, f.titulo
ORDER BY qtdartigos DESC; 