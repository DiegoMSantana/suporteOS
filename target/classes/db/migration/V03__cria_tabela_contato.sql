CREATE TABLE tbl_contato(
	codigo_contato BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	email VARCHAR(20),
	telefone VARCHAR(15),
	cargo VARCHAR(40)
)ENGINE=InnoDB DEFAULT CHARSET=utf8