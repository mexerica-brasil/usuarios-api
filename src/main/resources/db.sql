-- Tabela Usuario
CREATE TABLE Usuario (
    id                  BIGINT          NOT NULL AUTO_INCREMENT,
    cpf                 VARCHAR(11)     NOT NULL,
    nome                VARCHAR(100)    NOT NULL,
    dataNascimento      DATE            NOT NULL,
    dataCriacao         DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    dataUltimaAlteracao DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    PRIMARY KEY (id),
    UNIQUE KEY uk_usuario_cpf (cpf)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- Tabela Endereco
CREATE TABLE Endereco (
    id                  BIGINT          NOT NULL AUTO_INCREMENT,
    logradouro          VARCHAR(200)    NOT NULL,
    numero              BIGINT          NOT NULL,
    cidade              VARCHAR(100)    NOT NULL,
    uf                  CHAR(2)         NOT NULL,
    cep                 VARCHAR(9)      NOT NULL,
    
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

ALTER TABLE Usuario
    ADD COLUMN endereco_id BIGINT NULL,
    ADD CONSTRAINT fk_usuario_endereco
    FOREIGN KEY (endereco_id) REFERENCES Endereco(id);

ALTER TABLE Endereco
    ADD CONSTRAINT uk_endereco
    UNIQUE (logradouro, numero, cidade, cep);

CREATE INDEX idx_usuario_nome ON Usuario(nome);  -- desnecessário