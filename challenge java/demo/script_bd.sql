-- =================================================================================================
-- SCRIPT DE CRIAÇÃO DO BANCO DE DADOS PARA A SPRINT 3 - DEVOPS TOOLS & CLOUD COMPUTING
-- =================================================================================================
-- OBSERVAÇÃO:
-- Este script contém apenas o DDL (Data Definition Language) para as tabelas essenciais utilizadas
-- pela aplicação nesta entrega específica. O projeto completo em Java possui um número maior de
-- tabelas (migrations), mas, para manter o foco e a clareza exigidos nos critérios de
-- avaliação desta Sprint, incluímos somente as entidades que possuem um CRUD funcional
-- na aplicação que será demonstrada.
-- =================================================================================================


-- ====================================================================================
-- SEÇÃO 1: CRIAÇÃO DAS TABELAS
-- ====================================================================================

-- Tabela para armazenar os dados dos usuários do sistema.
-- Essencial para controle de acesso e para associar veículos a proprietários.
CREATE TABLE TB_MTT_USUARIO (
    id INT IDENTITY(1,1) PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL
);

-- Adicionando comentários para maior clareza, conforme solicitado nos critérios de avaliação.
COMMENT ON TABLE TB_MTT_USUARIO IS 'Armazena informações de login e perfil dos usuários do sistema.';
COMMENT ON COLUMN TB_MTT_USUARIO.id IS 'Identificador único e sequencial para cada usuário.';
COMMENT ON COLUMN TB_MTT_USUARIO.username IS 'Nome de usuário para login no sistema (único).';
COMMENT ON COLUMN TB_MTT_USUARIO.email IS 'Email do usuário, também utilizado para recuperação de senha (único).';
COMMENT ON COLUMN TB_MTT_USUARIO.senha IS 'Senha do usuário, armazenada de forma criptografada.';
COMMENT ON COLUMN TB_MTT_USUARIO.role IS 'Define o nível de permissão do usuário (ex: ADMIN, USER).';


-- Tabela para armazenar os veículos da frota.
-- Esta é uma das tabelas principais onde o CRUD completo será demonstrado.
CREATE TABLE TB_MTT_VEICULO (
    id INT IDENTITY(1,1) PRIMARY KEY,
    tb_mtt_usuario_id INT NOT NULL,
    placa_antiga VARCHAR(7),
    placa_nova VARCHAR(10) NOT NULL UNIQUE,
    tipo_veiculo VARCHAR(75) NOT NULL
);

-- Adicionando comentários para maior clareza.
COMMENT ON TABLE TB_MTT_VEICULO IS 'Armazena os detalhes dos veículos da frota, associados a um usuário.';
COMMENT ON COLUMN TB_MTT_VEICULO.id IS 'Identificador único e sequencial para cada veículo.';
COMMENT ON COLUMN TB_MTT_VEICULO.tb_mtt_usuario_id IS 'Chave estrangeira que referencia o ID do usuário proprietário do veículo na tabela TB_MTT_USUARIO.';
COMMENT ON COLUMN TB_MTT_VEICULO.placa_antiga IS 'Placa no formato antigo (opcional).';
COMMENT ON COLUMN TB_MTT_VEICULO.placa_nova IS 'Placa no formato Mercosul (obrigatória e única).';
COMMENT ON COLUMN TB_MTT_VEICULO.tipo_veiculo IS 'Descrição do tipo de veículo (ex: Motocicleta, Automóvel Sedan).';


-- ====================================================================================
-- SEÇÃO 2: ADIÇÃO DE CHAVES ESTRANGEIRAS (FOREIGN KEYS)
-- ====================================================================================

-- Garante a integridade referencial, assegurando que todo veículo pertença a um usuário existente.
ALTER TABLE TB_MTT_VEICULO ADD CONSTRAINT FK_VEICULO_USUARIO
FOREIGN KEY (tb_mtt_usuario_id) REFERENCES TB_MTT_USUARIO(id);


-- ====================================================================================
-- SEÇÃO 3: INSERÇÃO DE DADOS INICIAIS (AMOSTRA)
-- ====================================================================================
-- Inserção de pelo menos 2 registros para manipulação e demonstração do CRUD,
-- conforme requisito obrigatório da Sprint

INSERT INTO TB_MTT_USUARIO (username, email, senha, role) VALUES 
('admin', 'admin@mottu.com', '$2a$10$8.g9j.yL5Yk4P6c.B..ZFe3v2u6jB5/A1aM2w0i.w8c7.pS2R2o/q', 'ADMIN');

INSERT INTO TB_MTT_USUARIO (username, email, senha, role) VALUES 
('user', 'user@mottu.com', '$2a$10$Y1/yJ.Q/dD2A8.pOR8h3UuG8p2f/aI5uKb.bY/1j9/x8C8e3U0jS.', 'USER');

INSERT INTO TB_MTT_VEICULO (tb_mtt_usuario_id, placa_nova, tipo_veiculo) VALUES 
((SELECT id FROM TB_MTT_USUARIO WHERE username = 'user'), 'BRA2E19', 'Motocicleta');

INSERT INTO TB_MTT_VEICULO (tb_mtt_usuario_id, placa_nova, tipo_veiculo) VALUES
((SELECT id FROM TB_MTT_USUARIO WHERE username = 'user'), 'BRA2E18', 'Motocicleta');
