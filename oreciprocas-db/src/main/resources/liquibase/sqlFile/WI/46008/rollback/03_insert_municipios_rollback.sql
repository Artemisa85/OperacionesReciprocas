/*****************************************************************************/
/*	Archivo: 03_insert_municipios_rollback.sql                          	 */
/*	Base de datos: oreciprocas										 	     */
/*	Producto: Operaciones Reciprocas										 */
/*	Aplicaciones Impactadas: oreciprocas				    	             */
/*	Diseño: Zamir García         									         */
/*						                                                     */
/*								PREREQUISITOS		                         */
/*	No tiene prerequisitos para su ejecución.                                */
/*                                                                           */
/*								PROPOSITO									 */
/*	Rollback Carga inicial de datos en la tabla ADM_MUNICIPIO 		         */
/*  			                                                             */
/*								MODIFICACIONES								 */
/*	FECHA					AUTOR							RAZON			 */
/* 2019-02-05           Zamir García            Construcción de WI_46008     */
/*****************************************************************************/

Use oreciprocas
GO

if exists (select 1 from sysconstraints where status & 64 = 64 and object_name(tableid) = 'ADM_ENTIDAD' and object_name(constrid) = 'FK_ADM_ENTIDAD_ADM_MUNICIPIO')
ALTER TABLE ADM_ENTIDAD 
 DROP CONSTRAINT FK_ADM_ENTIDAD_ADM_MUNICIPIO
GO

IF EXISTS (SELECT 1 FROM sysobjects INNER JOIN sysusers ON sysobjects.uid=sysusers.uid 
			WHERE sysobjects.name = 'ADM_MUNICIPIO' AND sysusers.name = 'dbo')  
delete from dbo.ADM_MUNICIPIO 
GO
