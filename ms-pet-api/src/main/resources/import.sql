---- Inserir dados de usuários
INSERT INTO tb_user (id, cpf, user_status, user_type, created_at, updated_at)
VALUES 
  ('1', '12345678900', 'active', 'premium', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('2', '98765432100', 'active', 'basic', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('3', '55555555555', 'inactive', 'basic', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
