/*****************************************************************************/
/*	Archivo: 02_insert_departamentos_rollback.sql                          	 */
/*	Base de datos: oreciprocas										 	     */
/*	Producto: Operaciones Reciprocas										 */
/*	Aplicaciones Impactadas: oreciprocas				    	             */
/*	Diseño: Zamir García         									         */
/*						                                                     */
/*								PREREQUISITOS		                         */
/*	No tiene prerequisitos para su ejecución.                                */
/*                                                                           */
/*								PROPOSITO									 */
/*	Rollback Carga inicial de datos en la tabla ADM_DEPARTAMENTO 		     */
/*  			                                                             */
/*								MODIFICACIONES								 */
/*	FECHA					AUTOR							RAZON			 */
/* 2019-02-05           Zamir García            Construcción de WI_46008     */
/*****************************************************************************/

Use oreciprocas
GO

IF EXISTS (SELECT 1 FROM sysobjects INNER JOIN sysusers ON sysobjects.uid=sysusers.uid 
			WHERE sysobjects.name = 'ADM_DEPARTAMENTO' AND sysusers.name = 'dbo')  
delete from dbo.ADM_DEPARTAMENTO 
GO