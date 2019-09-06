/*****************************************************************************/
/*  Archivo: 06_insert_plantilla_rollback.sql                                */
/*  Base de datos: oreciprocas                                               */
/*  Producto: Operaciones Reciprocas                                         */
/*  Aplicaciones Impactadas: oreciprocas                                     */
/*  Diseño: Zamir García                                                     */
/*                                                                           */
/*                              PREREQUISITOS                                */
/*  No tiene prerequisitos para su ejecución.                                */
/*                                                                           */
/*                              PROPOSITO                                    */
/*  Rollback para carga de datos de la tabla ADM_PLANTILLA                   */
/*                                                                           */
/*                              MODIFICACIONES                               */
/*  FECHA                   AUTOR                           RAZON            */
/* 2019-03-08           Zamir García            Construcción de WI_46008     */
/*****************************************************************************/

USE oreciprocas
GO

IF EXISTS (SELECT 1 FROM sysobjects INNER JOIN sysusers ON sysobjects.uid=sysusers.uid 
            WHERE sysobjects.name = 'ADM_PLANTILLA' AND sysusers.name = 'dbo') 
DELETE FROM ADM_PLANTILLA

GO