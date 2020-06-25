CREATE TABLE tbl_usuario (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    senha VARCHAR(120) NOT NULL,
    ativo BOOLEAN DEFAULT true,
    data_nascimento DATE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE tbl_grupo (
    codigo BIGINT(20) PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE tbl_permissao (
    codigo BIGINT(20) PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE tbl_usuario_grupo (
    codigo_usuario BIGINT(20) NOT NULL,
    codigo_grupo BIGINT(20) NOT NULL,
    PRIMARY KEY (codigo_usuario, codigo_grupo),
    FOREIGN KEY (codigo_usuario) REFERENCES tbl_usuario(codigo),
    FOREIGN KEY (codigo_grupo) REFERENCES tbl_grupo(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE tbl_grupo_permissao (
    codigo_grupo BIGINT(20) NOT NULL,
    codigo_permissao BIGINT(20) NOT NULL,
    PRIMARY KEY (codigo_grupo, codigo_permissao),
    FOREIGN KEY (codigo_grupo) REFERENCES tbl_grupo(codigo),
    FOREIGN KEY (codigo_permissao) REFERENCES tbl_permissao(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO tbl_usuario (nome, email, senha, ativo) VALUES ('Admin', 'admin@diego.com', '$2a$10$BP3qGCpa1D4Zrc8eMV14XuCH6KqRYq3aOmQpkGH4wO8NxFGDR8BqS', 1);

INSERT INTO tbl_grupo (codigo, nome) VALUES (1, 'Administrador');
INSERT INTO tbl_grupo (codigo, nome) VALUES (2, 'Vendedor');

INSERT INTO tbl_permissao VALUES (1, 'ROLE_CADASTRAR_CIDADE');
INSERT INTO tbl_permissao VALUES (2, 'ROLE_CADASTRAR_USUARIO');
INSERT INTO tbl_permissao VALUES (3, 'ROLE_CADASTRAR_ORDEM_SERVICO');
INSERT INTO tbl_permissao VALUES (4, 'ROLE_CADASTRAR_PRODUTO');
INSERT INTO tbl_permissao VALUES (5, 'ROLE_CADASTRAR_FORNECEDOR');

INSERT INTO tbl_grupo_permissao (codigo_grupo, codigo_permissao) VALUES (1, 1);
INSERT INTO tbl_grupo_permissao (codigo_grupo, codigo_permissao) VALUES (1, 2);
INSERT INTO tbl_grupo_permissao (codigo_grupo, codigo_permissao) VALUES (1, 3);
INSERT INTO tbl_grupo_permissao (codigo_grupo, codigo_permissao) VALUES (1, 4);
INSERT INTO tbl_grupo_permissao (codigo_grupo, codigo_permissao) VALUES (1, 5);

INSERT INTO tbl_usuario_grupo (codigo_usuario, codigo_grupo) VALUES ((SELECT codigo FROM tbl_usuario WHERE email = 'admin@diego.com'), 1);