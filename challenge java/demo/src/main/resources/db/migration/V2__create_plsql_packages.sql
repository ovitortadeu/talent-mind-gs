-- Pacote de Inserção (SPEC)
CREATE OR REPLACE PACKAGE PKG_DASHBOARD_INSERTS AS
    PROCEDURE PRC_INS_USUARIO (
        p_nome_usuario   IN T_USUARIO.NOME_USUARIO%TYPE,
        p_email_usuario  IN T_USUARIO.EMAIL_USUARIO%TYPE,
        p_senha_usuario  IN T_USUARIO.SENHA_USUARIO%TYPE,
        p_role_usuario   IN T_USUARIO.ROLE%TYPE DEFAULT 'USER',
        p_id_out         OUT T_USUARIO.ID_USUARIO%TYPE
    );
    PROCEDURE PRC_INS_COMPETENCIA (
        p_nome_comp      IN T_COMPETENCIA.NOME_COMPETENCIA%TYPE,
        p_descricao_comp IN T_COMPETENCIA.DESCRICAO_COMP%TYPE,
        p_id_out         OUT T_COMPETENCIA.ID_COMPETENCIA%TYPE
    );
    PROCEDURE PRC_INS_VAGA (
        p_titulo_vaga    IN T_VAGA.TITULO_VAGA%TYPE,
        p_descricao_vaga IN T_VAGA.DESCRICAO_VAGA%TYPE,
        p_empresa_vaga   IN T_VAGA.EMPRESA_VAGA%TYPE,
        p_salario_medio  IN T_VAGA.SALARIO_MEDIO%TYPE,
        p_id_out         OUT T_VAGA.ID_VAGA%TYPE
    );
    PROCEDURE PRC_INS_CURSO (
        p_nome_curso        IN T_CURSO_REQUALIFICACAO.NOME_CURSO%TYPE,
        p_instituicao_curso IN T_CURSO_REQUALIFICACAO.INSTITUICAO_CURSO%TYPE,
        p_link_curso        IN T_CURSO_REQUALIFICACAO.LINK_CURSO%TYPE,
        p_id_out            OUT T_CURSO_REQUALIFICACAO.ID_CURSO%TYPE
    );
    PROCEDURE PRC_INS_USUARIO_COMP (
        p_id_usuario        IN T_USUARIO_COMPETENCIA.ID_USUARIO%TYPE,
        p_id_competencia    IN T_USUARIO_COMPETENCIA.ID_COMPETENCIA%TYPE,
        p_nivel_competencia IN T_USUARIO_COMPETENCIA.NIVEL_COMPETENCIA%TYPE
    );
    PROCEDURE PRC_INS_VAGA_COMP (
        p_id_vaga           IN T_VAGA_COMPETENCIA.ID_VAGA%TYPE,
        p_id_competencia    IN T_VAGA_COMPETENCIA.ID_COMPETENCIA%TYPE
    );
    PROCEDURE PRC_INS_CURSO_COMP (
        p_id_curso          IN T_CURSO_COMPETENCIA.ID_CURSO%TYPE,
        p_id_competencia    IN T_CURSO_COMPETENCIA.ID_COMPETENCIA%TYPE
    );
END PKG_DASHBOARD_INSERTS;
/

-- Pacote de Inserção (BODY)
CREATE OR REPLACE PACKAGE BODY PKG_DASHBOARD_INSERTS AS
    PROCEDURE PRC_INS_USUARIO (
        p_nome_usuario   IN T_USUARIO.NOME_USUARIO%TYPE,
        p_email_usuario  IN T_USUARIO.EMAIL_USUARIO%TYPE,
        p_senha_usuario  IN T_USUARIO.SENHA_USUARIO%TYPE,
        p_role_usuario   IN T_USUARIO.ROLE%TYPE DEFAULT 'USER',
        p_id_out         OUT T_USUARIO.ID_USUARIO%TYPE
    ) AS
    BEGIN
        INSERT INTO T_USUARIO (NOME_USUARIO, EMAIL_USUARIO, SENHA_USUARIO, ROLE)
        VALUES (p_nome_usuario, p_email_usuario, p_senha_usuario, p_role_usuario)
        RETURNING ID_USUARIO INTO p_id_out;
    EXCEPTION
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20010, 'Erro ao inserir usuário: ' || SQLERRM);
    END PRC_INS_USUARIO;

    PROCEDURE PRC_INS_COMPETENCIA (
        p_nome_comp      IN T_COMPETENCIA.NOME_COMPETENCIA%TYPE,
        p_descricao_comp IN T_COMPETENCIA.DESCRICAO_COMP%TYPE,
        p_id_out         OUT T_COMPETENCIA.ID_COMPETENCIA%TYPE
    ) AS
    BEGIN
        INSERT INTO T_COMPETENCIA (NOME_COMPETENCIA, DESCRICAO_COMP)
        VALUES (p_nome_comp, p_descricao_comp)
        RETURNING ID_COMPETENCIA INTO p_id_out;
    EXCEPTION
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20011, 'Erro ao inserir competência: ' || SQLERRM);
    END PRC_INS_COMPETENCIA;

    PROCEDURE PRC_INS_VAGA (
        p_titulo_vaga    IN T_VAGA.TITULO_VAGA%TYPE,
        p_descricao_vaga IN T_VAGA.DESCRICAO_VAGA%TYPE,
        p_empresa_vaga   IN T_VAGA.EMPRESA_VAGA%TYPE,
        p_salario_medio  IN T_VAGA.SALARIO_MEDIO%TYPE,
        p_id_out         OUT T_VAGA.ID_VAGA%TYPE
    ) AS
    BEGIN
        INSERT INTO T_VAGA (TITULO_VAGA, DESCRICAO_VAGA, EMPRESA_VAGA, SALARIO_MEDIO)
        VALUES (p_titulo_vaga, p_descricao_vaga, p_empresa_vaga, p_salario_medio)
        RETURNING ID_VAGA INTO p_id_out;
    EXCEPTION
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20012, 'Erro ao inserir vaga: ' || SQLERRM);
    END PRC_INS_VAGA;

    PROCEDURE PRC_INS_CURSO (
        p_nome_curso        IN T_CURSO_REQUALIFICACAO.NOME_CURSO%TYPE,
        p_instituicao_curso IN T_CURSO_REQUALIFICACAO.INSTITUICAO_CURSO%TYPE,
        p_link_curso        IN T_CURSO_REQUALIFICACAO.LINK_CURSO%TYPE,
        p_id_out            OUT T_CURSO_REQUALIFICACAO.ID_CURSO%TYPE
    ) AS
    BEGIN
        INSERT INTO T_CURSO_REQUALIFICACAO (NOME_CURSO, INSTITUICAO_CURSO, LINK_CURSO)
        VALUES (p_nome_curso, p_instituicao_curso, p_link_curso)
        RETURNING ID_CURSO INTO p_id_out;
    EXCEPTION
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20013, 'Erro ao inserir curso: ' || SQLERRM);
    END PRC_INS_CURSO;

    PROCEDURE PRC_INS_USUARIO_COMP (
        p_id_usuario        IN T_USUARIO_COMPETENCIA.ID_USUARIO%TYPE,
        p_id_competencia    IN T_USUARIO_COMPETENCIA.ID_COMPETENCIA%TYPE,
        p_nivel_competencia IN T_USUARIO_COMPETENCIA.NIVEL_COMPETENCIA%TYPE
    ) AS
    BEGIN
        INSERT INTO T_USUARIO_COMPETENCIA (ID_USUARIO, ID_COMPETENCIA, NIVEL_COMPETENCIA)
        VALUES (p_id_usuario, p_id_competencia, p_nivel_competencia);
    EXCEPTION
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20014, 'Erro ao associar usuário e competência: ' || SQLERRM);
    END PRC_INS_USUARIO_COMP;

    PROCEDURE PRC_INS_VAGA_COMP (
        p_id_vaga           IN T_VAGA_COMPETENCIA.ID_VAGA%TYPE,
        p_id_competencia    IN T_VAGA_COMPETENCIA.ID_COMPETENCIA%TYPE
    ) AS
    BEGIN
        INSERT INTO T_VAGA_COMPETENCIA (ID_VAGA, ID_COMPETENCIA)
        VALUES (p_id_vaga, p_id_competencia);
    EXCEPTION
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20015, 'Erro ao associar vaga e competência: ' || SQLERRM);
    END PRC_INS_VAGA_COMP;

    PROCEDURE PRC_INS_CURSO_COMP (
        p_id_curso          IN T_CURSO_COMPETENCIA.ID_CURSO%TYPE,
        p_id_competencia    IN T_CURSO_COMPETENCIA.ID_COMPETENCIA%TYPE
    ) AS
    BEGIN
        INSERT INTO T_CURSO_COMPETENCIA (ID_CURSO, ID_COMPETENCIA)
        VALUES (p_id_curso, p_id_competencia);
    EXCEPTION
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20016, 'Erro ao associar curso e competência: ' || SQLERRM);
    END PRC_INS_CURSO_COMP;
END PKG_DASHBOARD_INSERTS;
/

-- Pacote de Funções (SPEC)
CREATE OR REPLACE PACKAGE PKG_DASHBOARD_FUNCTIONS AS
    FUNCTION FN_GET_USUARIO_JSON (
        p_id_usuario IN T_USUARIO.ID_USUARIO%TYPE
    ) RETURN CLOB;

    FUNCTION FN_VALIDA_EMAIL (
        p_email IN VARCHAR2
    ) RETURN BOOLEAN;

    FUNCTION FN_CALC_COMPATIBILIDADE (
        p_id_usuario IN T_USUARIO.ID_USUARIO%TYPE,
        p_id_vaga    IN T_VAGA.ID_VAGA%TYPE
    ) RETURN NUMBER;

    PROCEDURE PRC_EXPORTA_DATASET_JSON (
        p_dataset_json OUT CLOB
    );
END PKG_DASHBOARD_FUNCTIONS;
/

-- Pacote de Funções (BODY)
CREATE OR REPLACE PACKAGE BODY PKG_DASHBOARD_FUNCTIONS AS
    FUNCTION FN_GET_USUARIO_JSON (
        p_id_usuario IN T_USUARIO.ID_USUARIO%TYPE
    ) RETURN CLOB AS
        v_json_clob     CLOB;
        v_usuario       T_USUARIO%ROWTYPE;
        v_primeiro_item BOOLEAN := TRUE;
    BEGIN
        SELECT * INTO v_usuario 
        FROM T_USUARIO 
        WHERE ID_USUARIO = p_id_usuario;

        v_json_clob := '{' ||
            '"idUsuario": '      || v_usuario.ID_USUARIO || ',' ||
            '"nome": "'          || v_usuario.NOME_USUARIO || '",' ||
            '"email": "'         || v_usuario.EMAIL_USUARIO || '",' ||
            '"dataCadastro": "'  || TO_CHAR(v_usuario.DT_CADASTRO, 'YYYY-MM-DD') || '",';

        v_json_clob := v_json_clob || '"competencias": [';

        FOR c IN (
            SELECT 
                comp.NOME_COMPETENCIA,
                comp.DESCRICAO_COMP,
                uc.NIVEL_COMPETENCIA
            FROM T_USUARIO_COMPETENCIA uc
            JOIN T_COMPETENCIA comp ON uc.ID_COMPETENCIA = comp.ID_COMPETENCIA
            WHERE uc.ID_USUARIO = p_id_usuario
        ) LOOP
            IF NOT v_primeiro_item THEN
                v_json_clob := v_json_clob || ',';
            END IF;
            v_json_clob := v_json_clob || 
                '{' ||
                    '"nome": "'     || c.NOME_COMPETENCIA || '",' ||
                    '"nivel": "'    || c.NIVEL_COMPETENCIA || '",' ||
                    '"descricao": "'|| c.DESCRICAO_COMP || '"' ||
                '}';
            v_primeiro_item := FALSE;
        END LOOP;
        
        v_json_clob := v_json_clob || ']}';
        RETURN v_json_clob;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN '{"erro": "Usuário não encontrado.", "codigo": 404}';
        WHEN OTHERS THEN
            RETURN '{"erro": "Erro inesperado ao gerar JSON: ' || SQLERRM || '", "codigo": 500}';
    END FN_GET_USUARIO_JSON;

    FUNCTION FN_VALIDA_EMAIL (
        p_email IN VARCHAR2
    ) RETURN BOOLEAN AS
        v_regex_email VARCHAR2(255) := '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$';
    BEGIN
        RETURN REGEXP_LIKE(p_email, v_regex_email);
    EXCEPTION
        WHEN OTHERS THEN
            RETURN FALSE;
    END FN_VALIDA_EMAIL;

    FUNCTION FN_CALC_COMPATIBILIDADE (
        p_id_usuario IN T_USUARIO.ID_USUARIO%TYPE,
        p_id_vaga    IN T_VAGA.ID_VAGA%TYPE
    ) RETURN NUMBER AS
        v_total_requisitos      NUMBER := 0;
        v_total_compativel      NUMBER := 0;
        v_count                 NUMBER;
        ex_dados_invalidos      EXCEPTION;
    BEGIN
        SELECT COUNT(*) INTO v_count FROM T_USUARIO WHERE ID_USUARIO = p_id_usuario;
        IF v_count = 0 THEN RAISE ex_dados_invalidos; END IF;

        SELECT COUNT(*) INTO v_count FROM T_VAGA WHERE ID_VAGA = p_id_vaga;
        IF v_count = 0 THEN RAISE ex_dados_invalidos; END IF;

        SELECT COUNT(ID_COMPETENCIA) 
        INTO v_total_requisitos 
        FROM T_VAGA_COMPETENCIA 
        WHERE ID_VAGA = p_id_vaga;

        IF v_total_requisitos = 0 THEN
            RETURN 100;
        END IF;

        SELECT COUNT(uc.ID_COMPETENCIA)
        INTO v_total_compativel
        FROM T_USUARIO_COMPETENCIA uc
        WHERE uc.ID_USUARIO = p_id_usuario
          AND uc.ID_COMPETENCIA IN (SELECT vc.ID_COMPETENCIA 
                                    FROM T_VAGA_COMPETENCIA vc 
                                    WHERE vc.ID_VAGA = p_id_vaga);

        RETURN ROUND((v_total_compativel / v_total_requisitos) * 100, 2);
    EXCEPTION
        WHEN ex_dados_invalidos THEN
            RETURN 0;
        WHEN OTHERS THEN
            RETURN 0;
    END FN_CALC_COMPATIBILIDADE;

    PROCEDURE PRC_EXPORTA_DATASET_JSON (
        p_dataset_json OUT CLOB
    ) AS
    BEGIN
        SELECT JSON_OBJECT(
                'nome_dataset'    VALUE 'Dataset Vagas Futuro do Trabalho',
                'data_exportacao' VALUE SYSDATE,
                'vagas'           VALUE (
                    SELECT JSON_ARRAYAGG(
                        JSON_OBJECT(
                            'id_vaga'        VALUE v.ID_VAGA,
                            'titulo'         VALUE v.TITULO_VAGA,
                            'empresa'        VALUE v.EMPRESA_VAGA,
                            'salario_medio'  VALUE v.SALARIO_MEDIO,
                            'competencias_exigidas' VALUE (
                                SELECT JSON_ARRAYAGG(
                                    JSON_OBJECT(
                                        'nome_competencia' VALUE c.NOME_COMPETENCIA,
                                        'descricao'        VALUE c.DESCRICAO_COMP
                                    )
                                )
                                FROM T_VAGA_COMPETENCIA vc
                                JOIN T_COMPETENCIA c ON vc.ID_COMPETENCIA = c.ID_COMPETENCIA
                                WHERE vc.ID_VAGA = v.ID_VAGA
                            ),
                            'cursos_sugeridos' VALUE (
                                SELECT JSON_ARRAYAGG(
                                    JSON_OBJECT(
                                         'nome_curso'   VALUE cr.NOME_CURSO,
                                         'instituicao'  VALUE cr.INSTITUICAO_CURSO,
                                         'link'         VALUE cr.LINK_CURSO
                                    )
                                )
                                FROM T_CURSO_REQUALIFICACAO cr
                                WHERE cr.ID_CURSO IN (
                                    SELECT DISTINCT cc.ID_CURSO
                                    FROM T_CURSO_COMPETENCIA cc
                                    WHERE cc.ID_COMPETENCIA IN (
                                        SELECT vc.ID_COMPETencia
                                        FROM T_VAGA_COMPETENCIA vc
                                        WHERE vc.ID_VAGA = v.ID_VAGA
                                    )
                                )
                            )
                        )
                    )
                    FROM T_VAGA v
                )
            )
        INTO p_dataset_json
        FROM DUAL;
    EXCEPTION
        WHEN OTHERS THEN
            p_dataset_json := '{"erro": "Falha ao exportar dataset JSON: ' || SQLERRM || '"}';
    END PRC_EXPORTA_DATASET_JSON;
END PKG_DASHBOARD_FUNCTIONS;
/