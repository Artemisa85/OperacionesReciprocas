/*****************************************************************************/
/*	Archivo: 01_oreciprocas_db.sql                          			     */
/*	Base de datos: oreciprocas										 	     */
/*	Producto: Operaciones Reciprocas										 */
/*	Aplicaciones Impactadas: oreciprocas				    	             */
/*	Diseño: Guillermo García         									     */
/*						                                                     */
/*								PREREQUISITOS		                         */
/*	No tiene prerequisitos para su ejecución.                                */
/*                                                                           */
/*								PROPOSITO									 */
/*	Creación de tablas inciales que usará la aplicación 					 */
/*  			                                                             */
/*								MODIFICACIONES								 */
/*	FECHA					AUTOR							RAZON			 */
/* 2019-01-24           Guillermo García            Construcción de WI_46008 */
/*****************************************************************************/

USE oreciprocas
GO

CREATE TABLE CONFIGURACION
(
	ID numeric(4) IDENTITY   ,
	DOMINIO varchar(25) NOT NULL   ,
	CODIGO varchar(50) NOT NULL   ,
	VALOR varchar(100) NOT NULL   ,
	DESCRIPCION varchar(200) NULL   
)
GO

CREATE TABLE OAUTH_CLIENT
(
    ID numeric(4) IDENTITY   ,
    CLIENT_ID varchar(50) NOT NULL   ,
    RESOURCE_IDS varchar(120) NOT NULL   ,
    CLIENT_SECRET varchar(100) NULL   ,
    SCOPE varchar(100) NULL   ,
    AUTHORIZED_GRANT_TYPES varchar(200) NOT NULL   ,
    AUTHORITIES varchar(250) NOT NULL   ,
    REDIRECT_URIS varchar(50) NULL   ,
    ACCESSTOKEN_VALIDITY_SECONDS numeric(6) NOT NULL   ,
    REFRESHTOKEN_VALIDITY_SECONDS numeric(6) NOT NULL   ,
    SCOPE_AUTO_APPROVE bit NOT NULL   
)
GO

CREATE TABLE ADM_PLANTILLA
(
	ID numeric(2) IDENTITY   ,
	NOMBRE varchar(20) NOT NULL   ,
	CONTENIDO text NOT NULL   ,
	MODO_PLANTILLA varchar(15) NOT NULL   ,
	CHARSET varchar(12) NOT NULL   ,
	FECHA_CREACION datetime NULL   ,
	FECHA_MODIFICACION datetime NULL   ,
	USUARIO_CREADOR varchar(50) NOT NULL   ,
	USUARIO_EDITOR varchar(50) NULL   ,
	ACTIVO bit DEFAULT 1 NOT NULL   
)
GO

CREATE TABLE ADM_ENTIDAD_USUARIO
(
	ADM_ENTIDAD_ID numeric(10) NOT NULL   ,
	ADM_USUARIO_EXTERNO_ID numeric(10) NOT NULL   
)
GO

CREATE TABLE ADM_USUARIO_EXTERNO
(
	ID numeric(10) IDENTITY   ,
	USUARIO varchar(15) NOT NULL   ,
	CORREO varchar(254) NOT NULL   ,
	NOMBRE varchar(100) NULL   ,
	APELLIDO varchar(50) NULL   ,
	CONTRASENA varchar(100) NULL   ,
	ROLES varchar(100) NULL   ,
	ULTIMO_INICIO_SESION datetime NULL   ,
	USUARIO_EXPIRADO bit NOT NULL   ,
	USUARIO_BLOQUEADO bit NOT NULL   ,
	CONTRASENA_VENCIDA bit NOT NULL   ,
	ACTIVO bit NOT NULL   ,
	FECHA_REGISTRO datetime NOT NULL   ,
	FECHA_CAMBIO_CONTRASENA datetime NULL   ,
	INTENTOS_FALLIDOS numeric(2) NULL   ,
	FECHA_ULTIMO_INTENTO datetime NULL   
)
GO

CREATE TABLE ADM_CONTRASENA_HISTORIAL
(
	ID numeric(12) IDENTITY   ,
	ADM_USUARIO_EXTERNO_ID numeric(10) NOT NULL   ,
	CONTRASENA varchar(100) NOT NULL   ,
	FECHA_CAMBIO_CONTRASENA datetime NOT NULL   
)
GO

CREATE TABLE ORE_OPERACION
(
	ID numeric(18) IDENTITY   ,
	ORE_TRANSACCION_ID numeric(15) NOT NULL   ,
	ID_CGN_ENTIDAD varchar(12) NULL   ,
	NOMBRE_ENTIDAD varchar(200) NULL   ,
	LIQUIDEZ numeric(3) NULL   ,
	COD_CUENTA varchar(20) NULL   ,
	DESC_CUENTA varchar(100) NULL   ,
	VALOR_REPORTADO numeric(18,2) NULL   ,
	PARTIDA_CONCILIATORIA numeric(18,2) NULL   ,
	ORIGEN_DIFERENCIA varchar(100) NULL   ,
	ANALISTA varchar(50) NULL   
)
GO

CREATE TABLE ADM_ENTIDAD
(
	ID numeric(10) IDENTITY   ,
    NIT varchar(12) NOT NULL   ,
    ID_CGN varchar(12) NOT NULL   ,
    RAZON_SOCIAL varchar(200) NOT NULL   ,
    SIGLA varchar(50) NULL   ,
    DIRECCION varchar(150) NOT NULL   ,
    TELEFONO varchar(50) NULL   ,
    NOMBRE_CONTACTO varchar(50) NULL   ,
    APELLIDO_CONTACTO varchar(50) NULL   ,
    CORREO varchar(100) NULL   ,
    SITIO_WEB varchar(100) NULL   ,
    FECHA_CREACION datetime NULL   ,
	FECHA_MODIFICACION datetime NULL   ,
	USUARIO_CREADOR varchar(50) NULL   ,
	USUARIO_EDITOR varchar(50) NULL   ,
    ACTIVO bit DEFAULT 1 NOT NULL,
    ADM_MUNICIPIO_ID numeric(4) NOT NULL   
)
GO

CREATE TABLE ADM_MUNICIPIO
(
	ID numeric(4) IDENTITY   ,
	CODIGO_DANE varchar(5) NOT NULL   ,
	NOMBRE varchar(50) NOT NULL   ,
	ADM_DEPARTAMENTO_ID numeric(3) NOT NULL   
)
GO

CREATE TABLE ADM_DEPARTAMENTO
(
	ID numeric(3) IDENTITY   ,
	CODIGO_DANE varchar(3) NOT NULL   ,
	NOMBRE varchar(60) NOT NULL   
)
GO

CREATE TABLE ORE_ADJUNTO
(
	ID numeric(18) IDENTITY   ,
	NOMBRE varchar(100) NOT NULL   ,
	EXTENSION varchar(10) NULL   ,
	TIPO_MIME varchar(100) NULL   ,
	UBICACION varchar(250) NOT NULL   ,
	ORE_COMENTARIO_ID numeric(18) NOT NULL   
)
GO

CREATE TABLE ORE_COMENTARIO
(
	ID numeric(18) IDENTITY   ,
	TEXTO varchar(500) NOT NULL   ,
	ID_AUTOR varchar(200) NOT NULL   ,
	AUTOR varchar(50) NOT NULL   ,
	AUTOR_ROL varchar(15) NOT NULL   ,
	FECHA datetime NOT NULL   ,
	ORE_TRANSACCION_ID numeric(15) NOT NULL   
)
GO

CREATE TABLE ORE_TRANSACCION
(
	ID numeric(15) NOT NULL   ,
	PERIODO numeric(2) NOT NULL   ,
	FECHA_CONSOLIDADO datetime NOT NULL   ,
	ID_ENTIDAD_RECIPROCA varchar(12) NULL   ,
	ENTIDAD_RECIPROCA varchar(200) NULL   ,
	ENT_INVOLUCRADAS varchar(50) NULL   ,
	CONTACTO_CONTADOR varchar(250) NULL   ,
	ESTADO varchar(12) NULL   ,
    FECHA_CARGUE datetime NULL
)
GO

CREATE TABLE ORE_COMODIN (
	ID NUMERIC(15,0) IDENTITY,
	ORE_TRANSACCION_ID NUMERIC(15,0) NOT NULL,
	COMODIN_1 VARCHAR(500) NULL,
	COMODIN_2 VARCHAR(500) NULL,
	COMODIN_3 VARCHAR(500) NULL,
	COMODIN_4 VARCHAR(500) NULL,
	COMODIN_5 VARCHAR(500) NULL,
)
GO

ALTER TABLE OAUTH_CLIENT 
 ADD CONSTRAINT PK_OAUTH_CLIENT
    PRIMARY KEY  (ID)   
GO

ALTER TABLE OAUTH_CLIENT 
 ADD CONSTRAINT UQ_OAUTH_CLIENT UNIQUE (CLIENT_ID)
GO

ALTER TABLE CONFIGURACION 
 ADD CONSTRAINT PK_CONFIGURACION
	PRIMARY KEY (ID)   
GO

ALTER TABLE CONFIGURACION 
 ADD CONSTRAINT UQ_CONFIGURACION_DOMINIO_CODIGO UNIQUE (DOMINIO,CODIGO)
GO

ALTER TABLE ADM_PLANTILLA 
 ADD CONSTRAINT PK_ADM_PLANTILLA
	PRIMARY KEY (ID)
GO

ALTER TABLE ADM_PLANTILLA 
 ADD CONSTRAINT UQ_ADM_PLANTILLA_NOMBRE UNIQUE (NOMBRE)
GO

CREATE INDEX IXFK_ADM_ENTIDAD_USUARIO_ADM_ENTIDAD ON ADM_ENTIDAD_USUARIO (ADM_ENTIDAD_ID ASC)
GO

CREATE INDEX IXFK_ADM_ENTIDAD_USUARIO_ADM_USUARIO_EXTERNO ON ADM_ENTIDAD_USUARIO (ADM_USUARIO_EXTERNO_ID ASC)
GO

ALTER TABLE ADM_ENTIDAD_USUARIO 
 ADD CONSTRAINT UQ_ADM_ENTIDAD_USUARIO UNIQUE (ADM_ENTIDAD_ID,ADM_USUARIO_EXTERNO_ID)
GO

ALTER TABLE ADM_USUARIO_EXTERNO
 ADD CONSTRAINT UQ_ADM_USUARIO_EXTERNO_USARIO UNIQUE (USUARIO)
GO

ALTER TABLE ADM_USUARIO_EXTERNO 
 ADD CONSTRAINT PK_USUARIO
	PRIMARY KEY  (ID)   
GO

CREATE INDEX IXFK_ADM_CONTRASENA_HISTORIAL_ADM_USUARIO_EXTERNO 
 ON ADM_CONTRASENA_HISTORIAL (ADM_USUARIO_EXTERNO_ID ASC)
GO

ALTER TABLE ADM_CONTRASENA_HISTORIAL 
 ADD CONSTRAINT PK_ADM_CLAVE_HISTORIAL
	PRIMARY KEY NONCLUSTERED (ID)   
GO

ALTER TABLE ADM_CONTRASENA_HISTORIAL 
 ADD CONSTRAINT FK_ADM_CONTRASENA_HISTORIAL_ADM_USUARIO_EXTERNO
	FOREIGN KEY (ADM_USUARIO_EXTERNO_ID) REFERENCES ADM_USUARIO_EXTERNO (ID)
GO

CREATE INDEX IXFK_ORE_OPERACION_ADM_ENTIDAD ON ORE_OPERACION (ID_CGN_ENTIDAD ASC)
GO

CREATE INDEX IXFK_ORE_OPERACION_ORE_TRANSACCION ON ORE_OPERACION (ORE_TRANSACCION_ID ASC)
GO

ALTER TABLE ORE_OPERACION 
 ADD CONSTRAINT PK_ORE_OPERACION
	PRIMARY KEY  (ID)   
GO

CREATE INDEX IXFK_ADM_ENTIDAD_ADM_MUNICIPIO ON ADM_ENTIDAD (ADM_MUNICIPIO_ID ASC)
GO

ALTER TABLE ADM_ENTIDAD 
 ADD CONSTRAINT PK_ADM_ENTIDAD
	PRIMARY KEY  (ID)   
GO

ALTER TABLE ADM_ENTIDAD 
 ADD CONSTRAINT UQ_ADM_ENTIDAD_IDGCG UNIQUE (ID_CGN)
GO

CREATE INDEX IXFK_ADM_MUNICIPIO_ADM_DEPARTAMENTO ON ADM_MUNICIPIO (ADM_DEPARTAMENTO_ID ASC)
GO

ALTER TABLE ADM_MUNICIPIO 
 ADD CONSTRAINT PK_ADM_MUNICIPIO
	PRIMARY KEY  (ID)   
GO

ALTER TABLE ADM_MUNICIPIO 
 ADD CONSTRAINT UQ_ADM_MUNICIPIO_CODDANE UNIQUE (CODIGO_DANE)
GO

ALTER TABLE ADM_DEPARTAMENTO 
 ADD CONSTRAINT PK_ADM_DEPARTAMENTO
	PRIMARY KEY  (ID)   
GO

ALTER TABLE ADM_DEPARTAMENTO 
 ADD CONSTRAINT UQ_ADM_DEPARTAMENTO_CODDANE UNIQUE (CODIGO_DANE)
GO

CREATE INDEX IXFK_ORE_ADJUNTO_ORE_COMENTARIO ON ORE_ADJUNTO (ORE_COMENTARIO_ID ASC)
GO

ALTER TABLE ORE_ADJUNTO 
 ADD CONSTRAINT PK_ORE_ADJUNTO
	PRIMARY KEY  (ID)   
GO

CREATE INDEX IXFK_ORE_COMENTARIO_ORE_TRANSACCION ON ORE_COMENTARIO (ORE_TRANSACCION_ID ASC)
GO

ALTER TABLE ORE_COMENTARIO 
 ADD CONSTRAINT PK_ORE_COMENTARIO
	PRIMARY KEY  (ID)   
GO

ALTER TABLE ORE_TRANSACCION 
 ADD CONSTRAINT PK_ORE_TRANSACCION
	PRIMARY KEY  (ID)   
GO

CREATE INDEX IX_ORE_TRANSACCION_CGN_EXTERNA ON ORE_TRANSACCION (ID_ENTIDAD_RECIPROCA ASC)
GO

ALTER TABLE dbo.ORE_COMODIN
 ADD CONSTRAINT PK_ORE_COMODIN
	PRIMARY KEY  (ID) 
GO

ALTER TABLE ADM_ENTIDAD_USUARIO 
 ADD CONSTRAINT FK_ADM_ENTIDAD_USUARIO_ADM_ENTIDAD
	FOREIGN KEY (ADM_ENTIDAD_ID) REFERENCES ADM_ENTIDAD (ID)
GO

ALTER TABLE ADM_ENTIDAD_USUARIO 
 ADD CONSTRAINT FK_ADM_ENTIDAD_USUARIO_ADM_USUARIO_EXTERNO
	FOREIGN KEY (ADM_USUARIO_EXTERNO_ID) REFERENCES ADM_USUARIO_EXTERNO (ID)
GO


ALTER TABLE ORE_OPERACION 
 ADD CONSTRAINT FK_ORE_OPERACION_ORE_TRANSACCION
	FOREIGN KEY (ORE_TRANSACCION_ID) REFERENCES ORE_TRANSACCION (ID)
GO

ALTER TABLE ADM_ENTIDAD 
 ADD CONSTRAINT FK_ADM_ENTIDAD_ADM_MUNICIPIO
	FOREIGN KEY (ADM_MUNICIPIO_ID) REFERENCES ADM_MUNICIPIO (ID)
GO

ALTER TABLE ADM_MUNICIPIO 
 ADD CONSTRAINT FK_ADM_MUNICIPIO_ADM_DEPARTAMENTO
	FOREIGN KEY (ADM_DEPARTAMENTO_ID) REFERENCES ADM_DEPARTAMENTO (ID)
GO

ALTER TABLE ORE_ADJUNTO 
 ADD CONSTRAINT FK_ORE_ADJUNTO_ORE_COMENTARIO
	FOREIGN KEY (ORE_COMENTARIO_ID) REFERENCES ORE_COMENTARIO (ID)
GO

ALTER TABLE ORE_COMENTARIO 
 ADD CONSTRAINT FK_ORE_COMENTARIO_ORE_TRANSACCION
	FOREIGN KEY (ORE_TRANSACCION_ID) REFERENCES ORE_TRANSACCION (ID)
GO

ALTER TABLE dbo.ORE_COMODIN
 ADD CONSTRAINT FK_ORE_COMODIN_ORE_TRANSACCION
	FOREIGN KEY (ORE_TRANSACCION_ID) REFERENCES dbo.ORE_TRANSACCION(ID)
GO
