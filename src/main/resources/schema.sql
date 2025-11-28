DROP TABLE IF EXISTS USUARIO;

CREATE TABLE USUARIO (
	usuario_id UUID PRIMARY KEY,
    nombre VARCHAR(9) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(256) NOT NULL,
    creado TIMESTAMP NOT NULL,
    modificado TIMESTAMP,
    ultimo_login TIMESTAMP NOT NULL,
    token VARCHAR(2000) NOT NULL,
    activo BOOLEAN DEFAULT TRUE
);

DROP TABLE IF EXISTS TELEFONO;

CREATE TABLE TELEFONO (
    telefono_id UUID PRIMARY KEY,
    usuario_id UUID,
    codigo_pais VARCHAR(2),
    codigo_ciudad VARCHAR(2),
    numero VARCHAR(8),
    FOREIGN KEY (usuario_id) REFERENCES USUARIO(usuario_id)
);