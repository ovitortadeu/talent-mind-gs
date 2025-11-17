DECLARE
    v_id_comp_ia_gen        NUMBER;
    v_id_comp_analise_dados NUMBER;
    v_id_comp_python        NUMBER;
    v_id_comp_azure         NUMBER;
    v_id_comp_aws           NUMBER;
    v_id_comp_devops        NUMBER;
    v_id_comp_cyber         NUMBER;
    v_id_comp_react         NUMBER;
    v_id_comp_spring        NUMBER;
    v_id_comp_lideranca     NUMBER;
    
    v_id_user_admin         NUMBER;
    v_id_user_1             NUMBER;
    v_id_user_2             NUMBER;
    v_id_user_3             NUMBER;
    v_id_user_4             NUMBER;
    v_id_user_5             NUMBER;
    v_id_user_6             NUMBER;
    v_id_user_7             NUMBER;
    v_id_user_8             NUMBER;
    v_id_user_9             NUMBER;
    v_id_user_10            NUMBER;
    
    v_id_vaga_1             NUMBER;
    v_id_vaga_2             NUMBER;
    v_id_vaga_3             NUMBER;
    v_id_vaga_4             NUMBER;
    v_id_vaga_5             NUMBER;
    v_id_vaga_6             NUMBER;
    v_id_vaga_7             NUMBER;
    v_id_vaga_8             NUMBER;
    v_id_vaga_9             NUMBER;
    v_id_vaga_10            NUMBER;
    
    v_id_curso_1            NUMBER;
    v_id_curso_2            NUMBER;
    v_id_curso_3            NUMBER;
    v_id_curso_4            NUMBER;
    v_id_curso_5            NUMBER;
    v_id_curso_6            NUMBER;
    v_id_curso_7            NUMBER;
    v_id_curso_8            NUMBER;
    v_id_curso_9            NUMBER;
    v_id_curso_10           NUMBER;
    
    -- Senha Criptografada Padrão: "password"
    -- (Gerada via BCrypt: $2a$10$f/TALbM3.Vq3.EHr4kvzO.3v/S3/oSUg1EN1j1.1thAOGnBv0B.qG)
    v_senha_padrao T_USUARIO.SENHA_USUARIO%TYPE := '$2a$10$f/TALbM3.Vq3.EHr4kvzO.3v/S3/oSUg1EN1j1.1thAOGnBv0B.qG';

BEGIN
    -- 1. Competências
    PKG_DASHBOARD_INSERTS.PRC_INS_COMPETENCIA('IA Generativa', 'Uso de LLMs e modelos de difusão', v_id_comp_ia_gen);
    PKG_DASHBOARD_INSERTS.PRC_INS_COMPETENCIA('Análise de Dados', 'ETL, BI e Data Visualization', v_id_comp_analise_dados);
    PKG_DASHBOARD_INSERTS.PRC_INS_COMPETENCIA('Python', 'Linguagem de programação para IA e backend', v_id_comp_python);
    PKG_DASHBOARD_INSERTS.PRC_INS_COMPETENCIA('Cloud - Azure', 'Computação em nuvem Microsoft', v_id_comp_azure);
    PKG_DASHBOARD_INSERTS.PRC_INS_COMPETENCIA('Cloud - AWS', 'Computação em nuvem Amazon', v_id_comp_aws);
    PKG_DASHBOARD_INSERTS.PRC_INS_COMPETENCIA('DevOps', 'CI/CD, Kubernetes e Docker', v_id_comp_devops);
    PKG_DASHBOARD_INSERTS.PRC_INS_COMPETENCIA('Cibersegurança', 'Segurança ofensiva e defensiva', v_id_comp_cyber);
    PKG_DASHBOARD_INSERTS.PRC_INS_COMPETENCIA('React Native', 'Desenvolvimento mobile híbrido', v_id_comp_react);
    PKG_DASHBOARD_INSERTS.PRC_INS_COMPETENCIA('Java (Spring)', 'Desenvolvimento backend robusto', v_id_comp_spring);
    PKG_DASHBOARD_INSERTS.PRC_INS_COMPETENCIA('Liderança e Comunicação', 'Soft skill para gestão de equipes', v_id_comp_lideranca);
    
    -- 2. Usuários
    -- Usuário ADMIN (senha: "password")
    PKG_DASHBOARD_INSERTS.PRC_INS_USUARIO('Administrador', 'admin@talentmind.com', v_senha_padrao, 'ADMIN', v_id_user_admin);
    
    -- Usuários Comuns (senha: "password")
    PKG_DASHBOARD_INSERTS.PRC_INS_USUARIO('Ana Silva', 'ana.silva@email.com', v_senha_padrao, 'USER', v_id_user_1);
    PKG_DASHBOARD_INSERTS.PRC_INS_USUARIO('Bruno Costa', 'bruno.costa@email.com', v_senha_padrao, 'USER', v_id_user_2);
    PKG_DASHBOARD_INSERTS.PRC_INS_USUARIO('Carla Dias', 'carla.dias@email.com', v_senha_padrao, 'USER', v_id_user_3);
    PKG_DASHBOARD_INSERTS.PRC_INS_USUARIO('Daniel Moreira', 'daniel.moreira@email.com', v_senha_padrao, 'USER', v_id_user_4);
    PKG_DASHBOARD_INSERTS.PRC_INS_USUARIO('Elisa Fernandes', 'elisa.fernandes@email.com', v_senha_padrao, 'USER', v_id_user_5);
    PKG_DASHBOARD_INSERTS.PRC_INS_USUARIO('Fábio Guedes', 'fabio.guedes@email.com', v_senha_padrao, 'USER', v_id_user_6);
    PKG_DASHBOARD_INSERTS.PRC_INS_USUARIO('Gabriela Lima', 'gabriela.lima@email.com', v_senha_padrao, 'USER', v_id_user_7);
    PKG_DASHBOARD_INSERTS.PRC_INS_USUARIO('Hugo Mendes', 'hugo.mendes@email.com', v_senha_padrao, 'USER', v_id_user_8);
    PKG_DASHBOARD_INSERTS.PRC_INS_USUARIO('Isabela Rocha', 'isabela.rocha@email.com', v_senha_padrao, 'USER', v_id_user_9);
    PKG_DASHBOARD_INSERTS.PRC_INS_USUARIO('João Pedro', 'joao.pedro@email.com', v_senha_padrao, 'USER', v_id_user_10);

    -- 3. Vagas
    PKG_DASHBOARD_INSERTS.PRC_INS_VAGA('Engenheiro de IA', 'Desenvolver modelos de IA Generativa', 'Tech Inovadora', 15000, v_id_vaga_1);
    PKG_DASHBOARD_INSERTS.PRC_INS_VAGA('Analista de Dados Pleno', 'Criar dashboards em Power BI', 'Varejo Digital', 8000, v_id_vaga_2);
    PKG_DASHBOARD_INSERTS.PRC_INS_VAGA('Desenvolvedor Python Sr', 'API REST com Django/FastAPI', 'Fintech Global', 12000, v_id_vaga_3);
    PKG_DASHBOARD_INSERTS.PRC_INS_VAGA('Arquiteto Cloud Azure', 'Migração de sistemas para nuvem', 'Consultoria X', 18000, v_id_vaga_4);
    PKG_DASHBOARD_INSERTS.PRC_INS_VAGA('Especialista DevOps', 'Manter pipelines CI/CD', 'Banco Y', 14000, v_id_vaga_5);
    PKG_DASHBOARD_INSERTS.PRC_INS_VAGA('Analista de Cibersegurança', 'Monitoramento SOC/SIEM', 'Indústria Z', 9000, v_id_vaga_6);
    PKG_DASHBOARD_INSERTS.PRC_INS_VAGA('Desenvolvedor React Native', 'App de e-commerce', 'Startup Log', 10000, v_id_vaga_7);
    PKG_DASHBOARD_INSERTS.PRC_INS_VAGA('Desenvolvedor Java Sênior', 'Microsserviços com Spring Boot', 'Seguradora W', 13000, v_id_vaga_8);
    PKG_DASHBOARD_INSERTS.PRC_INS_VAGA('Engenheiro de Dados AWS', 'Pipeline de dados com Glue e S3', 'Saúde Tech', 16000, v_id_vaga_9);
    PKG_DASHBOARD_INSERTS.PRC_INS_VAGA('Gerente de Projetos Tech', 'Liderar squads ágeis', 'E-commerce V', 17000, v_id_vaga_10);

    -- 4. Cursos
    PKG_DASHBOARD_INSERTS.PRC_INS_CURSO('Bootcamp IA Generativa', 'FIAP', 'link.fiap.com/ia', v_id_curso_1);
    PKG_DASHBOARD_INSERTS.PRC_INS_CURSO('Data Analytics Pro', 'Alura', 'link.alura.com/data', v_id_curso_2);
    PKG_DASHBOARD_INSERTS.PRC_INS_CURSO('Python para Back-end', 'Udemy', 'link.udemy.com/python', v_id_curso_3);
    PKG_DASHBOARD_INSERTS.PRC_INS_CURSO('Certificação AZ-900', 'Microsoft Learn', 'link.ms.com/az900', v_id_curso_4);
    PKG_DASHBOARD_INSERTS.PRC_INS_CURSO('AWS Cloud Practitioner', 'AWS Skill Builder', 'link.aws.com/cloud', v_id_curso_5);
    PKG_DASHBOARD_INSERTS.PRC_INS_CURSO('DevOps Completo', 'FIAP', 'link.fiap.com/devops', v_id_curso_6);
    PKG_DASHBOARD_INSERTS.PRC_INS_CURSO('Cybersecurity Essentials', 'Cisco', 'link.cisco.com/cyber', v_id_curso_7);
    PKG_DASHBOARD_INSERTS.PRC_INS_CURSO('React Native de A a Z', 'Udemy', 'link.udemy.com/react', v_id_curso_8);
    PKG_DASHBOARD_INSERTS.PRC_INS_CURSO('Microsserviços com Spring', 'Alura', 'link.alura.com/spring', v_id_curso_9);
    PKG_DASHBOARD_INSERTS.PRC_INS_CURSO('Liderança Ágil', 'Coursera', 'link.coursera.com/lider', v_id_curso_10);

    -- 5. Associações Usuário <-> Competência
    PKG_DASHBOARD_INSERTS.PRC_INS_USUARIO_COMP(v_id_user_1, v_id_comp_ia_gen, 'Avançado');
    PKG_DASHBOARD_INSERTS.PRC_INS_USUARIO_COMP(v_id_user_1, v_id_comp_python, 'Avançado');
    PKG_DASHBOARD_INSERTS.PRC_INS_USUARIO_COMP(v_id_user_2, v_id_comp_analise_dados, 'Pleno');
    PKG_DASHBOARD_INSERTS.PRC_INS_USUARIO_COMP(v_id_user_3, v_id_comp_spring, 'Sênior');
    PKG_DASHBOARD_INSERTS.PRC_INS_USUARIO_COMP(v_id_user_4, v_id_comp_azure, 'Júnior');
    PKG_DASHBOARD_INSERTS.PRC_INS_USUARIO_COMP(v_id_user_5, v_id_comp_devops, 'Pleno');
    PKG_DASHBOARD_INSERTS.PRC_INS_USUARIO_COMP(v_id_user_6, v_id_comp_cyber, 'Sênior');
    PKG_DASHBOARD_INSERTS.PRC_INS_USUARIO_COMP(v_id_user_7, v_id_comp_react, 'Pleno');
    PKG_DASHBOARD_INSERTS.PRC_INS_USUARIO_COMP(v_id_user_8, v_id_comp_aws, 'Avançado');
    PKG_DASHBOARD_INSERTS.PRC_INS_USUARIO_COMP(v_id_user_10, v_id_comp_lideranca, 'Sênior');

    -- 6. Associações Vaga <-> Competência
    PKG_DASHBOARD_INSERTS.PRC_INS_VAGA_COMP(v_id_vaga_1, v_id_comp_ia_gen);
    PKG_DASHBOARD_INSERTS.PRC_INS_VAGA_COMP(v_id_vaga_1, v_id_comp_python);
    PKG_DASHBOARD_INSERTS.PRC_INS_VAGA_COMP(v_id_vaga_2, v_id_comp_analise_dados);
    PKG_DASHBOARD_INSERTS.PRC_INS_VAGA_COMP(v_id_vaga_3, v_id_comp_python);
    PKG_DASHBOARD_INSERTS.PRC_INS_VAGA_COMP(v_id_vaga_4, v_id_comp_azure);
    PKG_DASHBOARD_INSERTS.PRC_INS_VAGA_COMP(v_id_vaga_5, v_id_comp_devops);
    PKG_DASHBOARD_INSERTS.PRC_INS_VAGA_COMP(v_id_vaga_6, v_id_comp_cyber);
    PKG_DASHBOARD_INSERTS.PRC_INS_VAGA_COMP(v_id_vaga_7, v_id_comp_react);
    PKG_DASHBOARD_INSERTS.PRC_INS_VAGA_COMP(v_id_vaga_8, v_id_comp_spring);
    PKG_DASHBOARD_INSERTS.PRC_INS_VAGA_COMP(v_id_vaga_9, v_id_comp_aws);
    PKG_DASHBOARD_INSERTS.PRC_INS_VAGA_COMP(v_id_vaga_10, v_id_comp_lideranca);


    -- 7. Associações Curso <-> Competência
    PKG_DASHBOARD_INSERTS.PRC_INS_CURSO_COMP(v_id_curso_1, v_id_comp_ia_gen);
    PKG_DASHBOARD_INSERTS.PRC_INS_CURSO_COMP(v_id_curso_2, v_id_comp_analise_dados);
    PKG_DASHBOARD_INSERTS.PRC_INS_CURSO_COMP(v_id_curso_3, v_id_comp_python);
    PKG_DASHBOARD_INSERTS.PRC_INS_CURSO_COMP(v_id_curso_4, v_id_comp_azure);
    PKG_DASHBOARD_INSERTS.PRC_INS_CURSO_COMP(v_id_curso_5, v_id_comp_aws);
    PKG_DASHBOARD_INSERTS.PRC_INS_CURSO_COMP(v_id_curso_6, v_id_comp_devops);
    PKG_DASHBOARD_INSERTS.PRC_INS_CURSO_COMP(v_id_curso_7, v_id_comp_cyber);
    PKG_DASHBOARD_INSERTS.PRC_INS_CURSO_COMP(v_id_curso_8, v_id_comp_react);
    PKG_DASHBOARD_INSERTS.PRC_INS_CURSO_COMP(v_id_curso_9, v_id_comp_spring);
    PKG_DASHBOARD_INSERTS.PRC_INS_CURSO_COMP(v_id_curso_10, v_id_comp_lideranca);
    
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END;
/