/*****************************************************************************/
/*  Archivo: 07_insert_configuracion.sql                                     */
/*  Base de datos: oreciprocas                                               */
/*  Producto: Operaciones Reciprocas                                         */
/*  Aplicaciones Impactadas: oreciprocas                                     */
/*  Diseño: Zamir García                                                     */
/*                                                                           */
/*                              PREREQUISITOS                                */
/*  1. 01_oreciprocas_db.sql                                                 */
/*                                                                           */
/*                              PROPOSITO                                    */
/*  Carga inicial de la configuracion de la aplicacion                       */
/*                                                                           */
/*                              MODIFICACIONES                               */
/*  FECHA                   AUTOR                           RAZON            */
/* 2019-04-04           Zamir García            Construcción de WI_46008     */
/* 2019-05-31           Zamir García            Correccion defectos WI_46008 */
/*****************************************************************************/

USE oreciprocas
GO
	
DELETE FROM dbo.CONFIGURACION
GO

INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('oreciprocas', 'url-int', 'https://fnabogsoa.fna.gov.co:8099/oreciprocas-ui/#/InicioSesionFNA', 'URL externa para la aplicación')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('oreciprocas', 'url-ext', 'https://www.fna.gov.co:8099/oreciprocas-ui/#/InicioSesion', 'URL externa para la aplicación')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('ldap', 'url', 'ldap://fnabogpr1:389', 'URL del servidor LDAP')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('ldap', 'manager-dn', 'CN=procdesa,OU=Servicios,OU=Otros,DC=FNA,DC=COM,DC=CO', 'Nombre distinguido del usuario de consulta LDAP')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('ldap', 'manager-password', 'EF5C0328C129E77B61D0A8323E2EFEDE', 'Password cifrado del manager-dn')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('seguridad', 'password-expiration-days', '90', 'Número de días límite para cambiar contraseña')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('csrf', 'Access-Control-Allow-Origin', 'https://www.fna.gov.co:8099 https://fnabogsoa.fna.gov.co:8099', 'Orígenes HTTP permitidos')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('csrf', 'Access-Control-Allow-Methods', 'POST, GET, PUT, DELETE, OPTIONS', 'Métodos HTTP permitidos')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('csrf', 'Access-Control-Max-Age', '3600', 'Duración de acceso')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('csrf', 'Access-Control-Allow-Headers', 'Content-Type, x-requested-with, authorization, token, access-control-allow-headers', 'Encabezados HTTP permitidos')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'doc', 'application/msword', 'Microsoft Word')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'docm', 'application/vnd.ms-word.document.macroenabled.12', 'Microsoft Word - Macro-Enabled Document')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'docx', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 'Microsoft Office - OOXML - Word Document')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'dot', 'application/msword', 'Microsoft Word')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'dotm', 'application/vnd.ms-word.template.macroenabled.12', 'Microsoft Word - Macro-Enabled Template')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'dotx', 'application/vnd.openxmlformats-officedocument.wordprocessingml.template', 'Microsoft Office - OOXML - Word Document Template')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'htm', 'text/html', 'HyperText Markup Language (HTML)')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'html', 'text/html', 'HyperText Markup Language (HTML)')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'mht', 'message/rfc822', 'Web Archive file	')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'mhtml', 'message/rfc822', 'Web Archive file	')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'odt', 'application/vnd.oasis.opendocument.text', 'OpenDocument Text')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'pdf', 'application/pdf', 'Adobe Portable Document Format')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'rtf', 'application/rtf', 'Rich Text Format')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'txt', 'text/plain', 'Text File')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'wps', 'application/vnd.ms-works', 'Microsoft Works')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'xml', 'application/xml', 'XML - Extensible Markup Language')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'xps', 'application/vnd.ms-xpsdocument', 'Microsoft XML Paper Specification')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'csv', 'text/csv', 'Comma-Seperated Values')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'dbf', 'application/dbf', 'dBASE Table File Format (DBF)')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'ods', 'application/vnd.oasis.opendocument.spreadsheet', 'OpenDocument Spreadsheet')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'xlam', 'application/vnd.ms-excel.addin.macroenabled.12', 'Microsoft Excel - Add-In File')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'xls', 'application/vnd.ms-excel', 'Microsoft Excel')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'xlsb', 'application/vnd.ms-excel.sheet.binary.macroenabled.12', 'Microsoft Excel - Binary Workbook')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'xlsm', 'application/vnd.ms-excel.sheet.macroenabled.12', 'Microsoft Excel - Macro-Enabled Workbook')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'xlsx', 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet', 'Microsoft Office - OOXML - Spreadsheet')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'xltm', 'application/vnd.ms-excel.template.macroenabled.12', 'Microsoft Excel - Macro-Enabled Template File')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'xltx', 'application/vnd.openxmlformats-officedocument.spreadsheetml.template', 'Microsoft Office - OOXML - Spreadsheet Template')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'bmp', 'image/bmp', 'Bitmap Image File')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'gif', 'image/gif', 'Graphics Interchange Format')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'jpg', 'image/jpeg', 'JPEG Image')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'mp4', 'video/mp4', 'MPEG-4 Video')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'odp', 'application/vnd.oasis.opendocument.presentation', 'OpenDocument Presentation')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'png', 'image/png', 'Portable Network Graphics (PNG)')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'potm', 'application/vnd.ms-powerpoint.template.macroenabled.12', 'Microsoft PowerPoint - Macro-Enabled Template File')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'potx', 'application/vnd.openxmlformats-officedocument.presentationml.template', 'Microsoft Office - OOXML - Presentation Template')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'ppam', 'application/vnd.ms-powerpoint.addin.macroenabled.12', 'Microsoft PowerPoint - Add-in file')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'pps', 'application/vnd.ms-powerpoint', 'Microsoft PowerPoint file')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'ppsm', 'application/vnd.ms-powerpoint.slideshow.macroenabled.12', 'Microsoft PowerPoint - Macro-Enabled Slide Show File')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'ppsx', 'application/vnd.openxmlformats-officedocument.presentationml.slideshow', 'Microsoft Office - OOXML - Presentation (Slideshow)')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'ppt', 'application/vnd.ms-powerpoint', 'Microsoft PowerPoint')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'pptm', 'application/vnd.ms-powerpoint.presentation.macroenabled.12', 'Microsoft PowerPoint - Macro-Enabled Presentation File')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'pptx', 'application/vnd.openxmlformats-officedocument.presentationml.presentation', 'Microsoft Office - OOXML - Presentation')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'thmx', 'application/vnd.ms-officetheme', 'Microsoft Office System Release Theme')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'tiff', 'image/tiff', 'Tagged Image File Format')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'wmf', 'application/x-msmetafile', 'Microsoft Windows Metafile')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'wmv', 'video/x-ms-wmv', 'Microsoft Windows Media Video')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'msg', 'application/octet-stream', 'Microsoft Outlook message')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'eml', 'message/rfc822', 'Email message')
GO
INSERT INTO  dbo.CONFIGURACION (DOMINIO, CODIGO, VALOR, DESCRIPCION) VALUES ('archivos-permitidos', 'mbox', 'application/octet-stream', 'Mailbox files')
GO