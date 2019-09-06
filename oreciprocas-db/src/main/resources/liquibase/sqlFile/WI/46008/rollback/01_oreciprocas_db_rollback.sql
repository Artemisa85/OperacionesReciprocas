/*****************************************************************************/
/*	Archivo: 01_oreciprocas_db_rollback.sql                        			 */
/*	Base de datos: oreciprocas										 	     */
/*	Producto: Operaciones Reciprocas										 */
/*	Aplicaciones Impactadas: oreciprocas				    	             */
/*	Diseño: Guillermo García         									     */
/*						                                                     */
/*								PREREQUISITOS		                         */
/*	No tiene prerequisitos para su ejecución.                                */
/*                                                                           */
/*								PROPOSITO									 */
/*	Realiza el rollbac de las acciones ejecutadas en el archivo 			 */
/* 03_OreciprocasDB.sql.  													 */
/*  			                                                             */
/*								MODIFICACIONES								 */
/*	FECHA					AUTOR							RAZON			 */
/* 2019-01-24           Guillermo García            Construcción de WI_46008 */
/*****************************************************************************/

USE oreciprocas
GO



if exists (select 1 from sysconstraints where status & 64 = 64 and object_name(tableid) = 'ADM_ENTIDAD_USUARIO' and object_name(constrid) = 'FK_ADM_ENTIDAD_USUARIO_ADM_ENTIDAD')
ALTER TABLE ADM_ENTIDAD_USUARIO 
 DROP CONSTRAINT FK_ADM_ENTIDAD_USUARIO_ADM_ENTIDAD
GO

if exists (select 1 from sysconstraints where status & 64 = 64 and object_name(tableid) = 'ADM_CONTRASENA_HISTORIAL' and object_name(constrid) = 'FK_ADM_CONTRASENA_HISTORIAL_ADM_USUARIO_EXTERNO')
ALTER TABLE ADM_CONTRASENA_HISTORIAL 
 DROP CONSTRAINT FK_ADM_CONTRASENA_HISTORIAL_ADM_USUARIO_EXTERNO
GO

if exists (select 1 from sysconstraints where status & 64 = 64 and object_name(tableid) = 'ADM_ENTIDAD_USUARIO' and object_name(constrid) = 'FK_ADM_ENTIDAD_USUARIO_ADM_USUARIO_EXTERNO')
ALTER TABLE ADM_ENTIDAD_USUARIO 
 DROP CONSTRAINT FK_ADM_ENTIDAD_USUARIO_ADM_USUARIO_EXTERNO
GO

if exists (select 1 from sysconstraints where status & 64 = 64 and object_name(tableid) = 'ORE_COMODIN' and object_name(constrid) = 'FK_ORE_COMODIN_ORE_TRANSACCION')
ALTER TABLE ORE_COMODIN 
 DROP CONSTRAINT FK_ORE_COMODIN_ORE_TRANSACCION
GO

if exists (select 1 from sysconstraints where status & 64 = 64 and object_name(tableid) = 'ORE_OPERACION' and object_name(constrid) = 'FK_ORE_OPERACION_ORE_TRANSACCION')
ALTER TABLE ORE_OPERACION 
 DROP CONSTRAINT FK_ORE_OPERACION_ORE_TRANSACCION
GO

if exists (select 1 from sysconstraints where status & 64 = 64 and object_name(tableid) = 'ADM_ENTIDAD' and object_name(constrid) = 'FK_ADM_ENTIDAD_ADM_MUNICIPIO')
ALTER TABLE ADM_ENTIDAD 
 DROP CONSTRAINT FK_ADM_ENTIDAD_ADM_MUNICIPIO
GO

if exists (select 1 from sysconstraints where status & 64 = 64 and object_name(tableid) = 'ADM_MUNICIPIO' and object_name(constrid) = 'FK_ADM_MUNICIPIO_ADM_DEPARTAMENTO')
ALTER TABLE ADM_MUNICIPIO 
 DROP CONSTRAINT FK_ADM_MUNICIPIO_ADM_DEPARTAMENTO
GO

if exists (select 1 from sysconstraints where status & 64 = 64 and object_name(tableid) = 'ORE_ADJUNTO' and object_name(constrid) = 'FK_ORE_ADJUNTO_ORE_COMENTARIO')
ALTER TABLE ORE_ADJUNTO 
 DROP CONSTRAINT FK_ORE_ADJUNTO_ORE_COMENTARIO
GO

if exists (select 1 from sysconstraints where status & 64 = 64 and object_name(tableid) = 'ORE_COMENTARIO' and object_name(constrid) = 'FK_ORE_COMENTARIO_ORE_TRANSACCION')
ALTER TABLE ORE_COMENTARIO 
 DROP CONSTRAINT FK_ORE_COMENTARIO_ORE_TRANSACCION
GO

IF EXISTS (SELECT 1 FROM sysobjects INNER JOIN sysusers ON sysobjects.uid=sysusers.uid 
            WHERE sysobjects.name = 'OAUTH_CLIENT' AND sysusers.name = 'dbo')  
DROP TABLE OAUTH_CLIENT
GO

IF EXISTS (SELECT 1 FROM sysobjects INNER JOIN sysusers ON sysobjects.uid=sysusers.uid 
			WHERE sysobjects.name = 'CONFIGURACION' AND sysusers.name = 'dbo')  
DROP TABLE CONFIGURACION
GO

IF EXISTS (SELECT 1 FROM sysobjects INNER JOIN sysusers ON sysobjects.uid=sysusers.uid 
			WHERE sysobjects.name = 'ADM_PLANTILLA' AND sysusers.name = 'dbo')  
DROP TABLE ADM_PLANTILLA
GO

IF EXISTS (SELECT 1 FROM sysobjects INNER JOIN sysusers ON sysobjects.uid=sysusers.uid 
			WHERE sysobjects.name = 'ADM_ENTIDAD_USUARIO' AND sysusers.name = 'dbo')  
DROP TABLE ADM_ENTIDAD_USUARIO
GO

IF EXISTS (SELECT 1 FROM sysobjects INNER JOIN sysusers ON sysobjects.uid=sysusers.uid 
			WHERE sysobjects.name = 'ADM_CONTRASENA_HISTORIAL' AND sysusers.name = 'dbo')  
DROP TABLE ADM_CONTRASENA_HISTORIAL
GO

IF EXISTS (SELECT 1 FROM sysobjects INNER JOIN sysusers ON sysobjects.uid=sysusers.uid 
			WHERE sysobjects.name = 'ADM_USUARIO_EXTERNO' AND sysusers.name = 'dbo')  
DROP TABLE ADM_USUARIO_EXTERNO
GO

IF EXISTS (SELECT 1 FROM sysobjects INNER JOIN sysusers ON sysobjects.uid=sysusers.uid 
			WHERE sysobjects.name = 'ORE_OPERACION' AND sysusers.name = 'dbo')  
DROP TABLE ORE_OPERACION
GO

IF EXISTS (SELECT 1 FROM sysobjects INNER JOIN sysusers ON sysobjects.uid=sysusers.uid 
			WHERE sysobjects.name = 'ADM_ENTIDAD' AND sysusers.name = 'dbo')  
DROP TABLE ADM_ENTIDAD
GO

IF EXISTS (SELECT 1 FROM sysobjects INNER JOIN sysusers ON sysobjects.uid=sysusers.uid 
			WHERE sysobjects.name = 'ADM_MUNICIPIO' AND sysusers.name = 'dbo')  
DROP TABLE ADM_MUNICIPIO
GO

IF EXISTS (SELECT 1 FROM sysobjects INNER JOIN sysusers ON sysobjects.uid=sysusers.uid 
			WHERE sysobjects.name = 'ADM_DEPARTAMENTO' AND sysusers.name = 'dbo')  
DROP TABLE ADM_DEPARTAMENTO
GO

IF EXISTS (SELECT 1 FROM sysobjects INNER JOIN sysusers ON sysobjects.uid=sysusers.uid 
			WHERE sysobjects.name = 'ORE_ADJUNTO' AND sysusers.name = 'dbo')  
DROP TABLE ORE_ADJUNTO
GO

IF EXISTS (SELECT 1 FROM sysobjects INNER JOIN sysusers ON sysobjects.uid=sysusers.uid 
			WHERE sysobjects.name = 'ORE_COMENTARIO' AND sysusers.name = 'dbo')  
DROP TABLE ORE_COMENTARIO
GO

IF EXISTS (SELECT 1 FROM sysobjects INNER JOIN sysusers ON sysobjects.uid=sysusers.uid 
			WHERE sysobjects.name = 'ORE_COMODIN' AND sysusers.name = 'dbo')  
DROP TABLE dbo.ORE_COMODIN
GO

IF EXISTS (SELECT 1 FROM sysobjects INNER JOIN sysusers ON sysobjects.uid=sysusers.uid 
			WHERE sysobjects.name = 'ORE_TRANSACCION' AND sysusers.name = 'dbo')  
DROP TABLE ORE_TRANSACCION
GO
