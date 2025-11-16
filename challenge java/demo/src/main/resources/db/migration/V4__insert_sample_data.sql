
-- Estados
INSERT INTO TB_MTT_ESTADO (sigla_estado, nome_estado) VALUES ('SP', 'São Paulo');
INSERT INTO TB_MTT_ESTADO (sigla_estado, nome_estado) VALUES ('RJ', 'Rio de Janeiro');
INSERT INTO TB_MTT_ESTADO (sigla_estado, nome_estado) VALUES ('MG', 'Minas Gerais');
INSERT INTO TB_MTT_ESTADO (sigla_estado, nome_estado) VALUES ('BA', 'Bahia');
INSERT INTO TB_MTT_ESTADO (sigla_estado, nome_estado) VALUES ('PR', 'Paraná');

-- Cidades
INSERT INTO TB_MTT_CIDADE (tb_mtt_estado_id, nome_cidade, numero_ddd) VALUES (1, 'São Paulo', 11);
INSERT INTO TB_MTT_CIDADE (tb_mtt_estado_id, nome_cidade, numero_ddd) VALUES (2, 'Rio de Janeiro', 21);
INSERT INTO TB_MTT_CIDADE (tb_mtt_estado_id, nome_cidade, numero_ddd) VALUES (3, 'Belo Horizonte', 31);
INSERT INTO TB_MTT_CIDADE (tb_mtt_estado_id, nome_cidade, numero_ddd) VALUES (4, 'Salvador', 71);
INSERT INTO TB_MTT_CIDADE (tb_mtt_estado_id, nome_cidade, numero_ddd) VALUES (1, 'Campinas', 19);

-- Filiais
INSERT INTO TB_MTT_FILIAL DEFAULT VALUES;
INSERT INTO TB_MTT_FILIAL DEFAULT VALUES;
INSERT INTO TB_MTT_FILIAL DEFAULT VALUES;

-- Usuários (com senhas criptografadas e papéis)
INSERT INTO TB_MTT_USUARIO (username, email, senha, role) VALUES ('admin', 'admin@mottu.com', '$2a$10$QcNYXeLct7FZKvPxSd/dwe2lSl5gH6Gz.EVJ2m4bdFIByfwSw8bFS', 'ADMIN');
INSERT INTO TB_MTT_USUARIO (username, email, senha, role) VALUES ('user', 'user@mottu.com', '$2a$10$debnHh68p8wTdJq6Lohqlej5jehfin0SB45LqQLqZlzL9T6SYwUBG', 'USER');
INSERT INTO TB_MTT_USUARIO (username, email, senha, role) VALUES ('flima', 'fernanda.lima@example.com', '$2a$10$E2upv52Y6s.33e4L6tVv3.42c23U0.Ie9.n23uA0A6z5A2g1g5G3e', 'USER');

-- Veículos (associados aos usuários criados)
INSERT INTO TB_MTT_VEICULO (tb_mtt_usuario_id, placa_antiga, placa_nova, tipo_veiculo) VALUES ((SELECT id FROM TB_MTT_USUARIO WHERE username = 'user'), 'ABC1234', 'BRA1A23', 'Motocicleta');
INSERT INTO TB_MTT_VEICULO (tb_mtt_usuario_id, placa_antiga, placa_nova, tipo_veiculo) VALUES ((SELECT id FROM TB_MTT_USUARIO WHERE username = 'flima'), NULL, 'MER2B34', 'Automóvel Hatch');
INSERT INTO TB_MTT_VEICULO (tb_mtt_usuario_id, placa_antiga, placa_nova, tipo_veiculo) VALUES ((SELECT id FROM TB_MTT_USUARIO WHERE username = 'user'), 'XYZ7890', 'COS3C45', 'Automóvel Sedan');

-- Câmeras
INSERT INTO TB_MTT_CAMERA (tb_mtt_filial_id, modelo) VALUES (1, 'Intelbras VIP 3230 B');
INSERT INTO TB_MTT_CAMERA (tb_mtt_filial_id, modelo) VALUES (2, 'Hikvision DS-2CD2143G0-IS');

-- IoT
INSERT INTO TB_MTT_IOT (tb_mtt_veiculo_id, status, tipo) VALUES (1, 1, 'GPS Tracker');
INSERT INTO TB_MTT_IOT (tb_mtt_veiculo_id, status, tipo) VALUES (2, 0, 'Acelerômetro');

-- Logradouros
INSERT INTO TB_MTT_LOGRADOURO (tb_mtt_usuario_id, tb_mtt_filial_id, tb_mtt_cidade_id, nome_logradouro, numero_logradouro, cep, complemento) 
VALUES ((SELECT id FROM TB_MTT_USUARIO WHERE username = 'user'), NULL, 1, 'Rua Augusta', '1250', '01304001', 'Apto 101');
INSERT INTO TB_MTT_LOGRADOURO (tb_mtt_usuario_id, tb_mtt_filial_id, tb_mtt_cidade_id, nome_logradouro, numero_logradouro, cep, complemento) 
VALUES (NULL, 1, 3, 'Avenida Afonso Pena', '4000', '30130009', 'Loja 05');
