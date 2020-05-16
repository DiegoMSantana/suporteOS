CREATE TABLE cliente (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(80) NOT NULL,
    tipo_pessoa VARCHAR(15) NOT NULL,
    cpf_cnpj VARCHAR(30),
    email VARCHAR(50) NOT NULL,
	telefone VARCHAR(20),
	celular VARCHAR(20),
    logradouro VARCHAR(50),
    numero VARCHAR(15),
    complemento VARCHAR(20),
	cidade VARCHAR(30),
	estado VARCHAR(10),
    cep VARCHAR(15)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;