/*****************************************************************************/
/*  Archivo: 04_insert_oauth_client.sql                                      */
/*  Base de datos: oreciprocas                                               */
/*  Producto: Operaciones Reciprocas                                         */
/*  Aplicaciones Impactadas: oreciprocas                                     */
/*  Diseño: Zamir García                                                     */
/*                                                                           */
/*                              PREREQUISITOS                                */
/*  1. 01_oreciprocas_db.sql                                                 */
/*                                                                           */
/*                              PROPOSITO                                    */
/*  Carga inicial de datos en la tabla OAUTH_CLIENT                          */
/*                                                                           */
/*                              MODIFICACIONES                               */
/*  FECHA                   AUTOR                           RAZON            */
/* 2019-02-05           Zamir García            Construcción de WI_46008     */
/*****************************************************************************/

USE oreciprocas
GO

INSERT INTO OAUTH_CLIENT (CLIENT_ID, RESOURCE_IDS, 
            CLIENT_SECRET, 
            SCOPE, AUTHORIZED_GRANT_TYPES, AUTHORITIES,
            REDIRECT_URIS, ACCESSTOKEN_VALIDITY_SECONDS, REFRESHTOKEN_VALIDITY_SECONDS, SCOPE_AUTO_APPROVE) 
VALUES ('oreciprocas-ng', 'oreciprocas-rest',
        '$2a$10$Q.f.QxQuUrZeM3CAfmBLE.Ak7NxeAdYBJN7eYEFLPA.CDCY80iLyi',
        'read, write', 'client_credentials, password, refresh_token', 'ROLE_TRUSTED_CLIENT',
         null, 240, 600, 0)
GO