/*****************************************************************************/
/*  Archivo: 07_insert_configuracion_rollback.sql                            */
/*  Base de datos: oreciprocas                                               */
/*  Producto: Operaciones Reciprocas                                         */
/*  Aplicaciones Impactadas: oreciprocas                                     */
/*  Diseño: Zamir García                                                     */
/*                                                                           */
/*                              PREREQUISITOS                                */
/*  No tiene prerequisitos para su ejecución.                                */
/*                                                                           */
/*                              PROPOSITO                                    */
/*  Rollback Carga inicial de datos en la tabla CONFIGURACION                */
/*                                                                           */
/*                              MODIFICACIONES                               */
/*  FECHA                   AUTOR                           RAZON            */
/* 2019-02-05           Zamir García            Construcción de WI_46008     */
/*****************************************************************************/

USE oreciprocas
GO

IF EXISTS (SELECT 1 FROM sysobjects INNER JOIN sysusers ON sysobjects.uid=sysusers.uid 
            WHERE sysobjects.name = 'CONFIGURACION' AND sysusers.name = 'dbo') 
DELETE FROM CONFIGURACION

GO
