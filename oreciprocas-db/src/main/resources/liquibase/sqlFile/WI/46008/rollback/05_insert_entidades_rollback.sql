/*****************************************************************************/
/*	Archivo: 05_insert_entidades_rollback.sql                        		 */
/*	Base de datos: oreciprocas										 	     */
/*	Producto: Operaciones Reciprocas										 */
/*	Aplicaciones Impactadas: oreciprocas				    	             */
/*	Diseño: Guillermo García         									     */
/*						                                                     */
/*								PREREQUISITOS		                         */
/*	No existe prerequisito.                            						 */
/*                                                                           */
/*								PROPOSITO									 */
/*	Borrar los registros de la tabla ADM_ENTIDAD  							 */
/*                                                                           */
/*								MODIFICACIONES								 */
/*	FECHA					AUTOR							RAZON			 */
/* 2019-02-08           Guillermo García            Construcción de WI_46008 */
/*****************************************************************************/

USE oreciprocas
GO

IF EXISTS (SELECT 1 FROM sysobjects INNER JOIN sysusers ON sysobjects.uid=sysusers.uid 
            WHERE sysobjects.name = 'ADM_ENTIDAD_USUARIO' AND sysusers.name = 'dbo') 
TRUNCATE TABLE dbo.ADM_ENTIDAD_USUARIO 
GO

IF EXISTS (SELECT 1 FROM sysobjects INNER JOIN sysusers ON sysobjects.uid=sysusers.uid 
            WHERE sysobjects.name = 'ADM_ENTIDAD' AND sysusers.name = 'dbo') 
TRUNCATE TABLE dbo.ADM_ENTIDAD 
GO
