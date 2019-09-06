/*****************************************************************************/
/*  Archivo: 08_update_configuracion_certificacion.sql                       */
/*  Base de datos: oreciprocas                                               */
/*  Producto: Operaciones Reciprocas                                         */
/*  Aplicaciones Impactadas: oreciprocas                                     */
/*  Diseño: Zamir García                                                     */
/*                                                                           */
/*                              PREREQUISITOS                                */
/*  1. 07_insert_configuracion.sql                                           */
/*                                                                           */
/*                              PROPOSITO                                    */
/*  Ajustar configuración para ambiente de certificacion                     */
/*                                                                           */
/*                              MODIFICACIONES                               */
/*  FECHA                   AUTOR                           RAZON            */
/* 2019-04-04           Zamir García            Construcción de WI_46008     */
/*****************************************************************************/

USE oreciprocas
GO

UPDATE dbo.CONFIGURACION SET VALOR = 'https://fnabogcsoa.fna.gov.co:8099/oreciprocas-ui/#/InicioSesionFNA' WHERE DOMINIO = 'oreciprocas' AND CODIGO = 'url-int'
GO
UPDATE dbo.CONFIGURACION SET VALOR = 'https://fnabogcsoa.fna.gov.co:8099/oreciprocas-ui/#/InicioSesion' WHERE DOMINIO = 'oreciprocas' AND CODIGO = 'url-ext'
GO
UPDATE dbo.CONFIGURACION SET VALOR = 'https://fnabogcsoa.fna.gov.co:8099' WHERE DOMINIO = 'csrf' AND CODIGO = 'Access-Control-Allow-Origin'
GO
