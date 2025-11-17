CREATE OR REPLACE TRIGGER TRG_AUDIT_USUARIO
AFTER INSERT OR UPDATE OR DELETE ON T_USUARIO
FOR EACH ROW
DECLARE
    v_operacao      VARCHAR2(10);
    v_dados_antigos CLOB;
    v_dados_novos   CLOB;
BEGIN
    IF INSERTING THEN
        v_operacao := 'INSERT';
        v_dados_novos := 'ID: ' || :NEW.ID_USUARIO || ', NOME: ' || :NEW.NOME_USUARIO || ', EMAIL: ' || :NEW.EMAIL_USUARIO;
    ELSIF UPDATING THEN
        v_operacao := 'UPDATE';
        v_dados_antigos := 'ID: ' || :OLD.ID_USUARIO || ', NOME: ' || :OLD.NOME_USUARIO || ', EMAIL: ' || :OLD.EMAIL_USUARIO;
        v_dados_novos := 'ID: ' || :NEW.ID_USUARIO || ', NOME: ' || :NEW.NOME_USUARIO || ', EMAIL: ' || :NEW.EMAIL_USUARIO;
    ELSIF DELETING THEN
        v_operacao := 'DELETE';
        v_dados_antigos := 'ID: ' || :OLD.ID_USUARIO || ', NOME: ' || :OLD.NOME_USUARIO || ', EMAIL: ' || :OLD.EMAIL_USUARIO;
    END IF;
    INSERT INTO T_AUDITORIA_TRANSACOES (NOME_TABELA, OPERACAO, USUARIO_DB, DT_OPERACAO, DADOS_ANTIGOS, DADOS_NOVOS)
    VALUES ('T_USUARIO', v_operacao, USER, SYSDATE, v_dados_antigos, v_dados_novos);
EXCEPTION
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20001, 'Erro no trigger TRG_AUDIT_USUARIO: ' || SQLERRM);
END;
/

CREATE OR REPLACE TRIGGER TRG_AUDIT_VAGA
AFTER INSERT OR UPDATE OR DELETE ON T_VAGA
FOR EACH ROW
DECLARE
    v_operacao      VARCHAR2(10);
    v_dados_antigos CLOB;
    v_dados_novos   CLOB;
BEGIN
    IF INSERTING THEN
        v_operacao := 'INSERT';
        v_dados_novos := 'ID_VAGA: ' || :NEW.ID_VAGA || ', TITULO: ' || :NEW.TITULO_VAGA || ', SALARIO: ' || :NEW.SALARIO_MEDIO;
    ELSIF UPDATING THEN
        v_operacao := 'UPDATE';
        v_dados_antigos := 'ID_VAGA: ' || :OLD.ID_VAGA || ', TITULO: ' || :OLD.TITULO_VAGA || ', SALARIO: ' || :OLD.SALARIO_MEDIO;
        v_dados_novos := 'ID_VAGA: ' || :NEW.ID_VAGA || ', TITULO: ' || :NEW.TITULO_VAGA || ', SALARIO: ' || :NEW.SALARIO_MEDIO;
    ELSIF DELETING THEN
        v_operacao := 'DELETE';
        v_dados_antigos := 'ID_VAGA: ' || :OLD.ID_VAGA || ', TITULO: ' || :OLD.TITULO_VAGA || ', SALARIO: ' || :OLD.SALARIO_MEDIO;
    END IF;
    INSERT INTO T_AUDITORIA_TRANSACOES (NOME_TABELA, OPERACAO, USUARIO_DB, DT_OPERACAO, DADOS_ANTIGOS, DADOS_NOVOS)
    VALUES ('T_VAGA', v_operacao, USER, SYSDATE, v_dados_antigos, v_dados_novos);
EXCEPTION
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20002, 'Erro no trigger TRG_AUDIT_VAGA: ' || SQLERRM);
END;
/

CREATE OR REPLACE TRIGGER TRG_AUDIT_COMPETENCIA
AFTER INSERT OR UPDATE OR DELETE ON T_COMPETENCIA
FOR EACH ROW
DECLARE
    v_operacao      VARCHAR2(10);
    v_dados_antigos CLOB;
    v_dados_novos   CLOB;
BEGIN
    IF INSERTING THEN
        v_operacao := 'INSERT';
        v_dados_novos := 'ID: ' || :NEW.ID_COMPETENCIA || ', NOME: ' || :NEW.NOME_COMPETENCIA;
    ELSIF UPDATING THEN
        v_operacao := 'UPDATE';
        v_dados_antigos := 'ID: ' || :OLD.ID_COMPETENCIA || ', NOME: ' || :OLD.NOME_COMPETENCIA;
        v_dados_novos := 'ID: ' || :NEW.ID_COMPETENCIA || ', NOME: ' || :NEW.NOME_COMPETENCIA;
    ELSIF DELETING THEN
        v_operacao := 'DELETE';
        v_dados_antigos := 'ID: ' || :OLD.ID_COMPETENCIA || ', NOME: ' || :OLD.NOME_COMPETENCIA;
    END IF;
    INSERT INTO T_AUDITORIA_TRANSACOES (NOME_TABELA, OPERACAO, USUARIO_DB, DT_OPERACAO, DADOS_ANTIGOS, DADOS_NOVOS)
    VALUES ('T_COMPETENCIA', v_operacao, USER, SYSDATE, v_dados_antigos, v_dados_novos);
EXCEPTION
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20003, 'Erro no trigger TRG_AUDIT_COMPETENCIA: ' || SQLERRM);
END;
/

CREATE OR REPLACE TRIGGER TRG_AUDIT_CURSO
AFTER INSERT OR UPDATE OR DELETE ON T_CURSO_REQUALIFICACAO
FOR EACH ROW
DECLARE
    v_operacao      VARCHAR2(10);
    v_dados_antigos CLOB;
    v_dados_novos   CLOB;
BEGIN
    IF INSERTING THEN
        v_operacao := 'INSERT';
        v_dados_novos := 'ID: ' || :NEW.ID_CURSO || ', NOME: ' || :NEW.NOME_CURSO;
    ELSIF UPDATING THEN
        v_operacao := 'UPDATE';
        v_dados_antigos := 'ID: ' || :OLD.ID_CURSO || ', NOME: ' || :OLD.NOME_CURSO;
        v_dados_novos := 'ID: ' || :NEW.ID_CURSO || ', NOME: ' || :NEW.NOME_CURSO;
    ELSIF DELETING THEN
        v_operacao := 'DELETE';
        v_dados_antigos := 'ID: ' || :OLD.ID_CURSO || ', NOME: ' || :OLD.NOME_CURSO;
    END IF;
    INSERT INTO T_AUDITORIA_TRANSACOES (NOME_TABELA, OPERACAO, USUARIO_DB, DT_OPERACAO, DADOS_ANTIGOS, DADOS_NOVOS)
    VALUES ('T_CURSO_REQUALIFICACAO', v_operacao, USER, SYSDATE, v_dados_antigos, v_dados_novos);
EXCEPTION
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20004, 'Erro no trigger TRG_AUDIT_CURSO: ' || SQLERRM);
END;
/

CREATE OR REPLACE TRIGGER TRG_AUDIT_USUARIO_COMP
AFTER INSERT OR UPDATE OR DELETE ON T_USUARIO_COMPETENCIA
FOR EACH ROW
DECLARE
    v_operacao      VARCHAR2(10);
    v_dados_antigos CLOB;
    v_dados_novos   CLOB;
BEGIN
    IF INSERTING THEN
        v_operacao := 'INSERT';
        v_dados_novos := 'USUARIO_ID: ' || :NEW.ID_USUARIO || ', COMPETENCIA_ID: ' || :NEW.ID_COMPETENCIA;
    ELSIF UPDATING THEN
        v_operacao := 'UPDATE';
        v_dados_antigos := 'USUARIO_ID: ' || :OLD.ID_USUARIO || ', COMPETENCIA_ID: ' || :OLD.ID_COMPETENCIA || ', NIVEL: ' || :OLD.NIVEL_COMPETENCIA;
        v_dados_novos := 'USUARIO_ID: ' || :NEW.ID_USUARIO || ', COMPETENCIA_ID: ' || :NEW.ID_COMPETENCIA || ', NIVEL: ' || :NEW.NIVEL_COMPETENCIA;
    ELSIF DELETING THEN
        v_operacao := 'DELETE';
        v_dados_antigos := 'USUARIO_ID: ' || :OLD.ID_USUARIO || ', COMPETENCIA_ID: ' || :OLD.ID_COMPETENCIA;
    END IF;
    INSERT INTO T_AUDITORIA_TRANSACOES (NOME_TABELA, OPERACAO, USUARIO_DB, DT_OPERACAO, DADOS_ANTIGOS, DADOS_NOVOS)
    VALUES ('T_USUARIO_COMPETENCIA', v_operacao, USER, SYSDATE, v_dados_antigos, v_dados_novos);
EXCEPTION
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20005, 'Erro no trigger TRG_AUDIT_USUARIO_COMP: ' || SQLERRM);
END;
/

CREATE OR REPLACE TRIGGER TRG_AUDIT_VAGA_COMP
AFTER INSERT OR UPDATE OR DELETE ON T_VAGA_COMPETENCIA
FOR EACH ROW
DECLARE
    v_operacao      VARCHAR2(10);
    v_dados_antigos CLOB;
    v_dados_novos   CLOB;
BEGIN
    IF INSERTING THEN
        v_operacao := 'INSERT';
        v_dados_novos := 'VAGA_ID: ' || :NEW.ID_VAGA || ', COMPETENCIA_ID: ' || :NEW.ID_COMPETENCIA;
    ELSIF UPDATING THEN
        v_operacao := 'UPDATE';
        v_dados_antigos := 'VAGA_ID: ' || :OLD.ID_VAGA || ', COMPETENCIA_ID: ' || :OLD.ID_COMPETENCIA;
        v_dados_novos := 'VAGA_ID: ' || :NEW.ID_VAGA || ', COMPETENCIA_ID: ' || :NEW.ID_COMPETENCIA;
    ELSIF DELETING THEN
        v_operacao := 'DELETE';
        v_dados_antigos := 'VAGA_ID: ' || :OLD.ID_VAGA || ', COMPETENCIA_ID: ' || :OLD.ID_COMPETENCIA;
    END IF;
    INSERT INTO T_AUDITORIA_TRANSACOES (NOME_TABELA, OPERACAO, USUARIO_DB, DT_OPERACAO, DADOS_ANTIGOS, DADOS_NOVOS)
    VALUES ('T_VAGA_COMPETENCIA', v_operacao, USER, SYSDATE, v_dados_antigos, v_dados_novos);
EXCEPTION
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20006, 'Erro no trigger TRG_AUDIT_VAGA_COMP: ' || SQLERRM);
END;
/

CREATE OR REPLACE TRIGGER TRG_AUDIT_CURSO_COMP
AFTER INSERT OR UPDATE OR DELETE ON T_CURSO_COMPETENCIA
FOR EACH ROW
DECLARE
    v_operacao      VARCHAR2(10);
    v_dados_antigos CLOB;
    v_dados_novos   CLOB;
BEGIN
    IF INSERTING THEN
        v_operacao := 'INSERT';
        v_dados_novos := 'CURSO_ID: ' || :NEW.ID_CURSO || ', COMPETENCIA_ID: ' || :NEW.ID_COMPETENCIA;
    ELSIF UPDATING THEN
        v_operacao := 'UPDATE';
        v_dados_antigos := 'CURSO_ID: ' || :OLD.ID_CURSO || ', COMPETENCIA_ID: ' || :OLD.ID_COMPETENCIA;
        v_dados_novos := 'CURSO_ID: ' || :NEW.ID_CURSO || ', COMPETENCIA_ID: ' || :NEW.ID_COMPETENCIA;
    ELSIF DELETING THEN
        v_operacao := 'DELETE';
        v_dados_antigos := 'CURSO_ID: ' || :OLD.ID_CURSO || ', COMPETENCIA_ID: ' || :OLD.ID_COMPETENCIA;
    END IF;
    INSERT INTO T_AUDITORIA_TRANSACOES (NOME_TABELA, OPERACAO, USUARIO_DB, DT_OPERACAO, DADOS_ANTIGOS, DADOS_NOVOS)
    VALUES ('T_CURSO_COMPETENCIA', v_operacao, USER, SYSDATE, v_dados_antigos, v_dados_novos);
EXCEPTION
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20007, 'Erro no trigger TRG_AUDIT_CURSO_COMP: ' || SQLERRM);
END;
/