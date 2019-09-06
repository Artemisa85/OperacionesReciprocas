/*****************************************************************************/
/*  Archivo: 06_insert_plantilla.sql                                         */
/*  Base de datos: oreciprocas                                               */
/*  Producto: Operaciones Reciprocas                                         */
/*  Aplicaciones Impactadas: oreciprocas                                     */
/*  Diseño: Zamir García                                                     */
/*                                                                           */
/*                              PREREQUISITOS                                */
/*  1. 01_oreciprocas_db.sql                                                 */
/*                                                                           */
/*                              PROPOSITO                                    */
/*  Cargar los datos de la tabla ADM_PLANTILLA                               */
/*                                                                           */
/*                              MODIFICACIONES                               */
/*  FECHA                   AUTOR                           RAZON            */
/* 2019-03-08           Zamir García            Construcción de WI_46008     */
/*****************************************************************************/

USE oreciprocas
GO

INSERT INTO ADM_PLANTILLA (NOMBRE, MODO_PLANTILLA, CHARSET, FECHA_CREACION, FECHA_MODIFICACION, USUARIO_CREADOR, USUARIO_EDITOR, ACTIVO, CONTENIDO) 
     VALUES ('registro', 'HTML', 'UTF-8', GETDATE(), NULL, 'INSTALACION', NULL, 1, 
     '
<html xmlns:th="http://www.thymeleaf.org">
<head>
<!--[if gte mso 9]><xml><o:OfficeDocumentSettings><o:AllowPNG/><o:PixelsPerInch>96</o:PixelsPerInch></o:OfficeDocumentSettings></xml><![endif]-->
<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
<meta content="width=device-width" name="viewport">
<!--[if !mso]><!-->
<meta content="IE=edge" http-equiv="X-UA-Compatible">
<!--<![endif]-->
<title>Registro</title>
<style type="text/css">
body {
margin: 0;
padding: 0;
}

table,
td,
tr {
vertical-align: top;
border-collapse: collapse;
}

* {
line-height: inherit;
}

a[x-apple-data-detectors=true] {
color: inherit !important;
text-decoration: none !important;
}

.ie-browser table {
table-layout: fixed;
}

[owa] .img-container div,
[owa] .img-container button {
display: block !important;
}

[owa] .fullwidth button {
width: 100% !important;
}

[owa] .block-grid .col {
display: table-cell;
float: none !important;
vertical-align: top;
}

.ie-browser .block-grid,
.ie-browser .num12,
[owa] .num12,
[owa] .block-grid {
width: 620px !important;
}

.ie-browser .mixed-two-up .num4,
[owa] .mixed-two-up .num4 {
width: 204px !important;
}

.ie-browser .mixed-two-up .num8,
[owa] .mixed-two-up .num8 {
width: 408px !important;
}

.ie-browser .block-grid.two-up .col,
[owa] .block-grid.two-up .col {
width: 306px !important;
}

.ie-browser .block-grid.three-up .col,
[owa] .block-grid.three-up .col {
width: 306px !important;
}

.ie-browser .block-grid.four-up .col [owa] .block-grid.four-up .col {
width: 153px !important;
}

.ie-browser .block-grid.five-up .col [owa] .block-grid.five-up .col {
width: 124px !important;
}

.ie-browser .block-grid.six-up .col,
[owa] .block-grid.six-up .col {
width: 103px !important;
}

.ie-browser .block-grid.seven-up .col,
[owa] .block-grid.seven-up .col {
width: 88px !important;
}

.ie-browser .block-grid.eight-up .col,
[owa] .block-grid.eight-up .col {
width: 77px !important;
}

.ie-browser .block-grid.nine-up .col,
[owa] .block-grid.nine-up .col {
width: 68px !important;
}

.ie-browser .block-grid.ten-up .col,
[owa] .block-grid.ten-up .col {
width: 60px !important;
}

.ie-browser .block-grid.eleven-up .col,
[owa] .block-grid.eleven-up .col {
width: 54px !important;
}

.ie-browser .block-grid.twelve-up .col,
[owa] .block-grid.twelve-up .col {
width: 50px !important;
}
</style>
<style id="media-query" type="text/css">
@media only screen and (min-width: 640px) {
.block-grid {
width: 620px !important;
}

.block-grid .col {
vertical-align: top;
}

.block-grid .col.num12 {
width: 620px !important;
}

.block-grid.mixed-two-up .col.num3 {
width: 153px !important;
}

.block-grid.mixed-two-up .col.num4 {
width: 204px !important;
}

.block-grid.mixed-two-up .col.num8 {
width: 408px !important;
}

.block-grid.mixed-two-up .col.num9 {
width: 459px !important;
}

.block-grid.two-up .col {
width: 310px !important;
}

.block-grid.three-up .col {
width: 206px !important;
}

.block-grid.four-up .col {
width: 155px !important;
}

.block-grid.five-up .col {
width: 124px !important;
}

.block-grid.six-up .col {
width: 103px !important;
}

.block-grid.seven-up .col {
width: 88px !important;
}

.block-grid.eight-up .col {
width: 77px !important;
}

.block-grid.nine-up .col {
width: 68px !important;
}

.block-grid.ten-up .col {
width: 62px !important;
}

.block-grid.eleven-up .col {
width: 56px !important;
}

.block-grid.twelve-up .col {
width: 51px !important;
}
}

@media (max-width: 640px) {

.block-grid,
.col {
min-width: 320px !important;
max-width: 100% !important;
display: block !important;
}

.block-grid {
width: 100% !important;
}

.col {
width: 100% !important;
}

.col>div {
margin: 0 auto;
}

img.fullwidth,
img.fullwidthOnMobile {
max-width: 100% !important;
}

.no-stack .col {
min-width: 0 !important;
display: table-cell !important;
}

.no-stack.two-up .col {
width: 50% !important;
}

.no-stack .col.num4 {
width: 33% !important;
}

.no-stack .col.num8 {
width: 66% !important;
}

.no-stack .col.num4 {
width: 33% !important;
}

.no-stack .col.num3 {
width: 25% !important;
}

.no-stack .col.num6 {
width: 50% !important;
}

.no-stack .col.num9 {
width: 75% !important;
}

.mobile_hide {
min-height: 0px;
max-height: 0px;
max-width: 0px;
display: none;
overflow: hidden;
font-size: 0px;
}
}
</style>
</head>
<body class="clean-body"
style="margin: 0pt; padding: 0pt; background-color: rgb(255, 255, 255); width: 677px;">
<style id="media-query-bodytag" type="text/css">
@media (max-width: 640px) {
.block-grid {
min-width: 320px!important;
max-width: 100%!important;
width: 100%!important;
display: block!important;
}
.col {
min-width: 320px!important;
max-width: 100%!important;
width: 100%!important;
display: block!important;
}
.col > div {
margin: 0 auto;
}
img.fullwidth {
max-width: 100%!important;
height: auto!important;
}
img.fullwidthOnMobile {
max-width: 100%!important;
height: auto!important;
}
.no-stack .col {
min-width: 0!important;
display: table-cell!important;
}
.no-stack.two-up .col {
width: 50%!important;
}
.no-stack.mixed-two-up .col.num4 {
width: 33%!important;
}
.no-stack.mixed-two-up .col.num8 {
width: 66%!important;
}
.no-stack.three-up .col.num4 {
width: 33%!important
}
.no-stack.four-up .col.num3 {
width: 25%!important
}
}
</style>
<!--[if IE]><div class="ie-browser"><![endif]-->
<table class="nl-container"
style="margin: 1pt auto; table-layout: fixed; vertical-align: top; min-width: 320px; border-spacing: 0pt; border-collapse: collapse; background-color: rgb(255, 255, 255); width: 70%;"
valign="top" bgcolor="#ffffff" cellpadding="0" cellspacing="0"
width="100%">
<tbody>
<tr style="vertical-align: top;" valign="top">
<td style="vertical-align: top; border-collapse: collapse;"
valign="top">
<!--[if (mso)|(IE)]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td align="center" style="background-color:#FFFFFF"><![endif]--><img
alt="Fondo nacional del ahorrro" th:src="|cid:${imgEncabezado}|">
<div style="background-color: transparent;">
<div class="block-grid"
style="margin: 0pt auto; min-width: 320px; max-width: 620px; background-color: transparent;">
<div
style="border-collapse: collapse; display: table; width: 100%; background-color: transparent;"><!--[if (mso)|(IE)]><table width="100%" cellpadding="0" cellspacing="0" border="0" style="background-color:transparent;"><tr><td align="center"><table cellpadding="0" cellspacing="0" border="0" style="width:620px"><tr class="layout-full-width" style="background-color:transparent"><![endif]--><!--[if (mso)|(IE)]><td align="center" width="620" style="background-color:transparent;width:620px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;" valign="top"><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:10px;"><![endif]-->
<div class="col num12"
style="min-width: 320px; max-width: 620px; display: table-cell; vertical-align: top;">
<div style="width: 100% ! important;"><!--[if (!mso)&(!IE)]><!-->
<div style="border: 0px solid transparent; padding: 5px 0px 10px;"><!--<![endif]--><!--[if mso]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 5px; font-family: Tahoma, Verdana, sans-serif"><![endif]-->
<div
style="padding: 10px 10px 5px; color: rgb(0, 0, 0); font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; line-height: 120%;">
<div
style="font-size: 12px; line-height: 14px; font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; color: rgb(0, 0, 0);">
<p
style="margin: 0pt; font-size: 14px; line-height: 16px; text-align: left;"><span
style="font-size: 14px; line-height: 16px;"><span
style="line-height: 16px; font-size: 14px;">Señores:</span></span></p>
</div>
</div>
<!--[if mso]></td></tr></table><![endif]-->
<!--[if mso]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 5px; font-family: Tahoma, Verdana, sans-serif"><![endif]-->
<div
style="padding: 10px 10px 5px; color: rgb(0, 0, 0); font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; line-height: 120%;">
<div
style="font-size: 12px; line-height: 14px; font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; color: rgb(0, 0, 0);">
<p
style="margin: 0pt; font-size: 14px; line-height: 19px; text-align: left;"><span
style="font-size: 16px;"><strong><span
style="line-height: 19px; font-size: 16px;"
th:text="${entidad.razonSocial}">[NOMBRE_ENTIDAD]</span></strong></span></p>
</div>
</div>
<!--[if mso]></td></tr></table><![endif]-->
<!--[if mso]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 25px; font-family: Tahoma, Verdana, sans-serif"><![endif]-->
<div
style="padding: 10px 10px 25px; color: rgb(0, 0, 0); font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; line-height: 120%;">
<div
style="font-size: 12px; line-height: 14px; font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; color: rgb(0, 0, 0);">
<p
style="margin: 0pt; font-size: 14px; line-height: 16px; text-align: left;">Cordial
saludo,</p>
<p
style="margin: 0pt; font-size: 14px; line-height: 16px; text-align: left;">
</p>
<p
style="margin: 0pt; font-size: 14px; line-height: 16px; text-align: left;">El
Fondo Nacional del Ahorro les agradece por haberse registrado en el
sistema Operaciones Recíprocas. Le informamos que su cuenta de ingreso
a la aplicación ha sido creada.</p>
</div>
</div>
<!--[if mso]></td></tr></table><![endif]-->
<!--[if mso]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 5px; font-family: Tahoma, Verdana, sans-serif"><![endif]-->
<div
style="padding: 10px 10px 5px; color: rgb(0, 0, 0); font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; line-height: 120%;">
<div
style="font-size: 12px; line-height: 14px; font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; color: rgb(0, 0, 0);">
<p
style="margin: 0pt; font-size: 14px; line-height: 16px; text-align: left;"><span
style="font-size: 14px; line-height: 16px;"><span
style="line-height: 16px; font-size: 14px;">Para acceder a nuestra
aplicación, por favor ingresar con los siguientes datos:</span></span></p>
</div>
</div>
<!--[if mso]></td></tr></table><![endif]-->
<!--[if (!mso)&(!IE)]><!--></div>
<!--<![endif]-->
</div>
</div>
<!--[if (mso)|(IE)]></td></tr></table><![endif]-->
<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]--></div>
</div>
</div>
<div style="background-color: transparent;">
<div class="block-grid mixed-two-up"
style="margin: 0pt auto; min-width: 320px; max-width: 620px; background-color: rgb(255, 255, 255);">
<div
style="border-collapse: collapse; display: table; width: 100%; background-color: rgb(255, 255, 255);"><!--[if (mso)|(IE)]><table width="100%" cellpadding="0" cellspacing="0" border="0" style="background-color:transparent;"><tr><td align="center"><table cellpadding="0" cellspacing="0" border="0" style="width:620px"><tr class="layout-full-width" style="background-color:#FFFFFF"><![endif]--><!--[if (mso)|(IE)]><td align="center" width="413" style="background-color:#FFFFFF;width:413px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;" valign="top"><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 0px; padding-left: 0px; padding-top:15px; padding-bottom:0px;"><![endif]-->
<div class="col num8"
style="display: table-cell; vertical-align: top; min-width: 320px; max-width: 408px;">
<div style="width: 100% ! important;"><!--[if (!mso)&(!IE)]><!-->
<div style="border: 0px solid transparent; padding: 15px 0px 0px;"><!--<![endif]--><!--[if mso]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 20px; padding-left: 20px; padding-top: 10px; padding-bottom: 10px; font-family: Tahoma, Verdana, sans-serif"><![endif]-->
<div
style="padding: 10px 20px; color: rgb(0, 0, 0); font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; line-height: 120%;">
<div
style="font-size: 12px; line-height: 14px; font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; color: rgb(0, 0, 0);">
<p style="margin: 0pt; font-size: 14px; line-height: 16px;"><span
style="color: rgb(0, 0, 0); font-size: 14px; line-height: 16px;"><a
href="https://beefree.io" rel="noopener"
style="text-decoration: none; color: rgb(0, 0, 0);" target="_blank">NIT</a></span></p>
</div>
</div>
<!--[if mso]></td></tr></table><![endif]-->
<!--[if (!mso)&(!IE)]><!--></div>
<!--<![endif]-->
</div>
</div>
<!--[if (mso)|(IE)]></td></tr></table><![endif]-->
<!--[if (mso)|(IE)]></td><td align="center" width="206" style="background-color:#FFFFFF;width:206px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;" valign="top"><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 0px; padding-left: 0px; padding-top:15px; padding-bottom:0px;"><![endif]-->
<div class="col num4"
style="display: table-cell; vertical-align: top; max-width: 320px; min-width: 204px;">
<div style="width: 100% ! important;"><!--[if (!mso)&(!IE)]><!-->
<div style="border: 0px solid transparent; padding: 15px 0px 0px;"><!--<![endif]--><!--[if mso]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 20px; padding-left: 20px; padding-top: 10px; padding-bottom: 10px; font-family: Tahoma, Verdana, sans-serif"><![endif]-->
<div
style="padding: 10px 20px; color: rgb(0, 0, 0); font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; line-height: 120%;">
<div
style="font-size: 12px; line-height: 14px; font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; color: rgb(0, 0, 0);">
<p style="margin: 0pt; font-size: 14px; line-height: 16px;"
th:text="${entidad.nit}">[NIT]</p>
</div>
</div>
<!--[if mso]></td></tr></table><![endif]-->
<!--[if (!mso)&(!IE)]><!--></div>
<!--<![endif]-->
</div>
</div>
<!--[if (mso)|(IE)]></td></tr></table><![endif]-->
<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]--></div>
</div>
</div>
<div style="background-color: transparent;">
<div class="block-grid mixed-two-up"
style="margin: 0pt auto; min-width: 320px; max-width: 620px; background-color: rgb(255, 255, 255);">
<div
style="border-collapse: collapse; display: table; width: 100%; background-color: rgb(255, 255, 255);"><!--[if (mso)|(IE)]><table width="100%" cellpadding="0" cellspacing="0" border="0" style="background-color:transparent;"><tr><td align="center"><table cellpadding="0" cellspacing="0" border="0" style="width:620px"><tr class="layout-full-width" style="background-color:#FFFFFF"><![endif]--><!--[if (mso)|(IE)]><td align="center" width="413" style="background-color:#FFFFFF;width:413px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;" valign="top"><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:0px;"><![endif]-->
<div class="col num8"
style="display: table-cell; vertical-align: top; min-width: 320px; max-width: 408px;">
<div style="width: 100% ! important;"><!--[if (!mso)&(!IE)]><!-->
<div style="border: 0px solid transparent; padding: 5px 0px 0px;"><!--<![endif]--><!--[if mso]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 20px; padding-left: 20px; padding-top: 10px; padding-bottom: 10px; font-family: Tahoma, Verdana, sans-serif"><![endif]-->
<div
style="padding: 10px 20px; color: rgb(0, 0, 0); font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; line-height: 120%;">
<div
style="font-size: 12px; line-height: 14px; font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; color: rgb(0, 0, 0);">
<p style="margin: 0pt; font-size: 14px; line-height: 16px;"><span
style="color: rgb(0, 0, 0); font-size: 14px; line-height: 16px;"><a
href="https://beefree.io" rel="noopener"
style="text-decoration: none; color: rgb(0, 0, 0);" target="_blank">Identificador
asignado por la CGN</a></span></p>
</div>
</div>
<!--[if mso]></td></tr></table><![endif]-->
<!--[if mso]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 20px; padding-left: 20px; padding-top: 10px; padding-bottom: 10px; font-family: Tahoma, Verdana, sans-serif"><![endif]-->
<div
style="padding: 10px 20px; color: rgb(0, 0, 0); font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; line-height: 120%;">
<div
style="font-size: 12px; line-height: 14px; font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; color: rgb(0, 0, 0);">
<p style="margin: 0pt; font-size: 14px; line-height: 16px;"><span
style="color: rgb(0, 0, 0); font-size: 14px; line-height: 16px;"><a
href="https://beefree.io" rel="noopener"
style="text-decoration: none; color: rgb(0, 0, 0);" target="_blank">Clave
temporal</a></span></p>
</div>
</div>
<!--[if mso]></td></tr></table><![endif]-->
<!--[if (!mso)&(!IE)]><!--></div>
<!--<![endif]-->
</div>
</div>
<!--[if (mso)|(IE)]></td></tr></table><![endif]-->
<!--[if (mso)|(IE)]></td><td align="center" width="206" style="background-color:#FFFFFF;width:206px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;" valign="top"><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:0px;"><![endif]-->
<div class="col num4"
style="display: table-cell; vertical-align: top; max-width: 320px; min-width: 204px;">
<div style="width: 100% ! important;"><!--[if (!mso)&(!IE)]><!-->
<div style="border: 0px solid transparent; padding: 5px 0px 0px;"><!--<![endif]--><!--[if mso]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 20px; padding-left: 20px; padding-top: 10px; padding-bottom: 10px; font-family: Tahoma, Verdana, sans-serif"><![endif]-->
<div
style="padding: 10px 20px; color: rgb(0, 0, 0); font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; line-height: 120%;">
<div
style="font-size: 12px; line-height: 14px; font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; color: rgb(0, 0, 0);">
<p style="margin: 0pt; font-size: 14px; line-height: 16px;"
th:text="${entidad.idCGN}">[ID_CGN]</p>
</div>
</div>
<!--[if mso]></td></tr></table><![endif]-->
<!--[if mso]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 20px; padding-left: 20px; padding-top: 10px; padding-bottom: 10px; font-family: Tahoma, Verdana, sans-serif"><![endif]-->
<div
style="padding: 10px 20px; color: rgb(0, 0, 0); font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; line-height: 120%;">
<div
style="font-size: 12px; line-height: 14px; font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; color: rgb(0, 0, 0);">
<p style="margin: 0pt; font-size: 14px; line-height: 16px;"
th:text="${clave}">[CLAVE]</p>
</div>
</div>
<!--[if mso]></td></tr></table><![endif]-->
<!--[if (!mso)&(!IE)]><!--></div>
<!--<![endif]-->
</div>
</div>
<!--[if (mso)|(IE)]></td></tr></table><![endif]-->
<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]--></div>
</div>
</div>
<img alt="FNA - Todos los derechos reservados" th:src="|cid:${imgPie}|"><!--[if (mso)|(IE)]></td></tr></table><![endif]--></td>
</tr>
</tbody>
</table>
<!--[if (IE)]></div><![endif]-->
</body>
</html>
     ')
GO


INSERT INTO ADM_PLANTILLA (NOMBRE, MODO_PLANTILLA, CHARSET, FECHA_CREACION, FECHA_MODIFICACION, USUARIO_CREADOR, USUARIO_EDITOR, ACTIVO, CONTENIDO) 
     VALUES ('recuperar-clave', 'HTML', 'UTF-8', GETDATE(), NULL, 'INSTALACION', NULL, 1, 
     '
<html xmlns:th="http://www.thymeleaf.org">
<head>
<!--[if gte mso 9]><xml><o:OfficeDocumentSettings><o:AllowPNG/><o:PixelsPerInch>96</o:PixelsPerInch></o:OfficeDocumentSettings></xml><![endif]-->
<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
<meta content="width=device-width" name="viewport">
<!--[if !mso]><!-->
<meta content="IE=edge" http-equiv="X-UA-Compatible">
<!--<![endif]-->
<title>Registro</title>
<style type="text/css">
body {
margin: 0;
padding: 0;
}

table,
td,
tr {
vertical-align: top;
border-collapse: collapse;
}

* {
line-height: inherit;
}

a[x-apple-data-detectors=true] {
color: inherit !important;
text-decoration: none !important;
}

.ie-browser table {
table-layout: fixed;
}

[owa] .img-container div,
[owa] .img-container button {
display: block !important;
}

[owa] .fullwidth button {
width: 100% !important;
}

[owa] .block-grid .col {
display: table-cell;
float: none !important;
vertical-align: top;
}

.ie-browser .block-grid,
.ie-browser .num12,
[owa] .num12,
[owa] .block-grid {
width: 620px !important;
}

.ie-browser .mixed-two-up .num4,
[owa] .mixed-two-up .num4 {
width: 204px !important;
}

.ie-browser .mixed-two-up .num8,
[owa] .mixed-two-up .num8 {
width: 408px !important;
}

.ie-browser .block-grid.two-up .col,
[owa] .block-grid.two-up .col {
width: 306px !important;
}

.ie-browser .block-grid.three-up .col,
[owa] .block-grid.three-up .col {
width: 306px !important;
}

.ie-browser .block-grid.four-up .col [owa] .block-grid.four-up .col {
width: 153px !important;
}

.ie-browser .block-grid.five-up .col [owa] .block-grid.five-up .col {
width: 124px !important;
}

.ie-browser .block-grid.six-up .col,
[owa] .block-grid.six-up .col {
width: 103px !important;
}

.ie-browser .block-grid.seven-up .col,
[owa] .block-grid.seven-up .col {
width: 88px !important;
}

.ie-browser .block-grid.eight-up .col,
[owa] .block-grid.eight-up .col {
width: 77px !important;
}

.ie-browser .block-grid.nine-up .col,
[owa] .block-grid.nine-up .col {
width: 68px !important;
}

.ie-browser .block-grid.ten-up .col,
[owa] .block-grid.ten-up .col {
width: 60px !important;
}

.ie-browser .block-grid.eleven-up .col,
[owa] .block-grid.eleven-up .col {
width: 54px !important;
}

.ie-browser .block-grid.twelve-up .col,
[owa] .block-grid.twelve-up .col {
width: 50px !important;
}
</style>
<style id="media-query" type="text/css">
@media only screen and (min-width: 640px) {
.block-grid {
width: 620px !important;
}

.block-grid .col {
vertical-align: top;
}

.block-grid .col.num12 {
width: 620px !important;
}

.block-grid.mixed-two-up .col.num3 {
width: 153px !important;
}

.block-grid.mixed-two-up .col.num4 {
width: 204px !important;
}

.block-grid.mixed-two-up .col.num8 {
width: 408px !important;
}

.block-grid.mixed-two-up .col.num9 {
width: 459px !important;
}

.block-grid.two-up .col {
width: 310px !important;
}

.block-grid.three-up .col {
width: 206px !important;
}

.block-grid.four-up .col {
width: 155px !important;
}

.block-grid.five-up .col {
width: 124px !important;
}

.block-grid.six-up .col {
width: 103px !important;
}

.block-grid.seven-up .col {
width: 88px !important;
}

.block-grid.eight-up .col {
width: 77px !important;
}

.block-grid.nine-up .col {
width: 68px !important;
}

.block-grid.ten-up .col {
width: 62px !important;
}

.block-grid.eleven-up .col {
width: 56px !important;
}

.block-grid.twelve-up .col {
width: 51px !important;
}
}

@media (max-width: 640px) {

.block-grid,
.col {
min-width: 320px !important;
max-width: 100% !important;
display: block !important;
}

.block-grid {
width: 100% !important;
}

.col {
width: 100% !important;
}

.col>div {
margin: 0 auto;
}

img.fullwidth,
img.fullwidthOnMobile {
max-width: 100% !important;
}

.no-stack .col {
min-width: 0 !important;
display: table-cell !important;
}

.no-stack.two-up .col {
width: 50% !important;
}

.no-stack .col.num4 {
width: 33% !important;
}

.no-stack .col.num8 {
width: 66% !important;
}

.no-stack .col.num4 {
width: 33% !important;
}

.no-stack .col.num3 {
width: 25% !important;
}

.no-stack .col.num6 {
width: 50% !important;
}

.no-stack .col.num9 {
width: 75% !important;
}

.mobile_hide {
min-height: 0px;
max-height: 0px;
max-width: 0px;
display: none;
overflow: hidden;
font-size: 0px;
}
}
</style>
</head>
<body class="clean-body"
style="margin: 0pt; padding: 0pt; background-color: rgb(255, 255, 255); width: 677px;">
<style id="media-query-bodytag" type="text/css">
@media (max-width: 640px) {
.block-grid {
min-width: 320px!important;
max-width: 100%!important;
width: 100%!important;
display: block!important;
}
.col {
min-width: 320px!important;
max-width: 100%!important;
width: 100%!important;
display: block!important;
}
.col > div {
margin: 0 auto;
}
img.fullwidth {
max-width: 100%!important;
height: auto!important;
}
img.fullwidthOnMobile {
max-width: 100%!important;
height: auto!important;
}
.no-stack .col {
min-width: 0!important;
display: table-cell!important;
}
.no-stack.two-up .col {
width: 50%!important;
}
.no-stack.mixed-two-up .col.num4 {
width: 33%!important;
}
.no-stack.mixed-two-up .col.num8 {
width: 66%!important;
}
.no-stack.three-up .col.num4 {
width: 33%!important
}
.no-stack.four-up .col.num3 {
width: 25%!important
}
}
</style>
<!--[if IE]><div class="ie-browser"><![endif]-->
<table class="nl-container"
style="margin: 1pt auto; table-layout: fixed; vertical-align: top; min-width: 320px; border-spacing: 0pt; border-collapse: collapse; background-color: rgb(255, 255, 255); width: 70%;"
valign="top" bgcolor="#ffffff" cellpadding="0" cellspacing="0"
width="100%">
<tbody>
<tr style="vertical-align: top;" valign="top">
<td style="vertical-align: top; border-collapse: collapse;"
valign="top">
<!--[if (mso)|(IE)]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td align="center" style="background-color:#FFFFFF"><![endif]--><img
alt="Fondo nacional del ahorrro" th:src="|cid:${imgEncabezado}|">
<div style="background-color: transparent;">
<div class="block-grid"
style="margin: 0pt auto; min-width: 320px; max-width: 620px; background-color: transparent;">
<div
style="border-collapse: collapse; display: table; width: 100%; background-color: transparent;"><!--[if (mso)|(IE)]><table width="100%" cellpadding="0" cellspacing="0" border="0" style="background-color:transparent;"><tr><td align="center"><table cellpadding="0" cellspacing="0" border="0" style="width:620px"><tr class="layout-full-width" style="background-color:transparent"><![endif]--><!--[if (mso)|(IE)]><td align="center" width="620" style="background-color:transparent;width:620px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;" valign="top"><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:10px;"><![endif]-->
<div class="col num12"
style="min-width: 320px; max-width: 620px; display: table-cell; vertical-align: top;">
<div style="width: 100% ! important;"><!--[if (!mso)&(!IE)]><!-->
<div style="border: 0px solid transparent; padding: 5px 0px 10px;"><!--<![endif]--><!--[if mso]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 5px; font-family: Tahoma, Verdana, sans-serif"><![endif]-->
<div
style="padding: 10px 10px 5px; color: rgb(0, 0, 0); font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; line-height: 120%;">
<div
style="font-size: 12px; line-height: 14px; font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; color: rgb(0, 0, 0);">
<p
style="margin: 0pt; font-size: 14px; line-height: 16px; text-align: left;"><span
style="font-size: 14px; line-height: 16px;"><span
style="line-height: 16px; font-size: 14px;">Señores:</span></span></p>
</div>
</div>
<!--[if mso]></td></tr></table><![endif]-->
<!--[if mso]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 5px; font-family: Tahoma, Verdana, sans-serif"><![endif]-->
<div
style="padding: 10px 10px 5px; color: rgb(0, 0, 0); font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; line-height: 120%;">
<div
style="font-size: 12px; line-height: 14px; font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; color: rgb(0, 0, 0);">
<p
style="margin: 0pt; font-size: 14px; line-height: 19px; text-align: left;"><span
style="font-size: 16px;"><strong><span
style="line-height: 19px; font-size: 16px;"
th:text="${entidad.razonSocial}">[NOMBRE_ENTIDAD]</span></strong></span></p>
</div>
</div>
<!--[if mso]></td></tr></table><![endif]-->
<!--[if mso]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 25px; font-family: Tahoma, Verdana, sans-serif"><![endif]-->
<div
style="padding: 10px 10px 25px; color: rgb(0, 0, 0); font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; line-height: 120%;">
<div
style="font-size: 12px; line-height: 14px; font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; color: rgb(0, 0, 0);">
<p
style="margin: 0pt; font-size: 14px; line-height: 16px; text-align: left;">Cordial
saludo,</p>
<p
style="margin: 0pt; font-size: 14px; line-height: 16px; text-align: left;">
</p>
<p
style="margin: 0pt; font-size: 14px; line-height: 16px; text-align: left;">El
Fondo Nacional del Ahorro les informa que se ha generado una nueva clave para acceder 
a la aplicación Operaciones Recíprocas.</p>
</div>
</div>
<!--[if mso]></td></tr></table><![endif]-->
<!--[if mso]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 5px; font-family: Tahoma, Verdana, sans-serif"><![endif]-->
<div
style="padding: 10px 10px 5px; color: rgb(0, 0, 0); font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; line-height: 120%;">
<div
style="font-size: 12px; line-height: 14px; font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; color: rgb(0, 0, 0);">
<p
style="margin: 0pt; font-size: 14px; line-height: 16px; text-align: left;"><span
style="font-size: 14px; line-height: 16px;"><span
style="line-height: 16px; font-size: 14px;">Para acceder a nuestra
aplicación, por favor ingresar con los siguientes datos:</span></span></p>
</div>
</div>
<!--[if mso]></td></tr></table><![endif]-->
<!--[if (!mso)&(!IE)]><!--></div>
<!--<![endif]-->
</div>
</div>
<!--[if (mso)|(IE)]></td></tr></table><![endif]-->
<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]--></div>
</div>
</div>
<div style="background-color: transparent;">
<div class="block-grid mixed-two-up"
style="margin: 0pt auto; min-width: 320px; max-width: 620px; background-color: rgb(255, 255, 255);">
<div
style="border-collapse: collapse; display: table; width: 100%; background-color: rgb(255, 255, 255);"><!--[if (mso)|(IE)]><table width="100%" cellpadding="0" cellspacing="0" border="0" style="background-color:transparent;"><tr><td align="center"><table cellpadding="0" cellspacing="0" border="0" style="width:620px"><tr class="layout-full-width" style="background-color:#FFFFFF"><![endif]--><!--[if (mso)|(IE)]><td align="center" width="413" style="background-color:#FFFFFF;width:413px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;" valign="top"><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 0px; padding-left: 0px; padding-top:15px; padding-bottom:0px;"><![endif]-->
<div class="col num8"
style="display: table-cell; vertical-align: top; min-width: 320px; max-width: 408px;">
<div style="width: 100% ! important;"><!--[if (!mso)&(!IE)]><!-->
<div style="border: 0px solid transparent; padding: 15px 0px 0px;"><!--<![endif]--><!--[if mso]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 20px; padding-left: 20px; padding-top: 10px; padding-bottom: 10px; font-family: Tahoma, Verdana, sans-serif"><![endif]-->
<div
style="padding: 10px 20px; color: rgb(0, 0, 0); font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; line-height: 120%;">
<div
style="font-size: 12px; line-height: 14px; font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; color: rgb(0, 0, 0);">
<p style="margin: 0pt; font-size: 14px; line-height: 16px;"><span
style="color: rgb(0, 0, 0); font-size: 14px; line-height: 16px;"><a
href="https://beefree.io" rel="noopener"
style="text-decoration: none; color: rgb(0, 0, 0);" target="_blank">NIT</a></span></p>
</div>
</div>
<!--[if mso]></td></tr></table><![endif]-->
<!--[if (!mso)&(!IE)]><!--></div>
<!--<![endif]-->
</div>
</div>
<!--[if (mso)|(IE)]></td></tr></table><![endif]-->
<!--[if (mso)|(IE)]></td><td align="center" width="206" style="background-color:#FFFFFF;width:206px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;" valign="top"><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 0px; padding-left: 0px; padding-top:15px; padding-bottom:0px;"><![endif]-->
<div class="col num4"
style="display: table-cell; vertical-align: top; max-width: 320px; min-width: 204px;">
<div style="width: 100% ! important;"><!--[if (!mso)&(!IE)]><!-->
<div style="border: 0px solid transparent; padding: 15px 0px 0px;"><!--<![endif]--><!--[if mso]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 20px; padding-left: 20px; padding-top: 10px; padding-bottom: 10px; font-family: Tahoma, Verdana, sans-serif"><![endif]-->
<div
style="padding: 10px 20px; color: rgb(0, 0, 0); font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; line-height: 120%;">
<div
style="font-size: 12px; line-height: 14px; font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; color: rgb(0, 0, 0);">
<p style="margin: 0pt; font-size: 14px; line-height: 16px;"
th:text="${entidad.nit}">[NIT]</p>
</div>
</div>
<!--[if mso]></td></tr></table><![endif]-->
<!--[if (!mso)&(!IE)]><!--></div>
<!--<![endif]-->
</div>
</div>
<!--[if (mso)|(IE)]></td></tr></table><![endif]-->
<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]--></div>
</div>
</div>
<div style="background-color: transparent;">
<div class="block-grid mixed-two-up"
style="margin: 0pt auto; min-width: 320px; max-width: 620px; background-color: rgb(255, 255, 255);">
<div
style="border-collapse: collapse; display: table; width: 100%; background-color: rgb(255, 255, 255);"><!--[if (mso)|(IE)]><table width="100%" cellpadding="0" cellspacing="0" border="0" style="background-color:transparent;"><tr><td align="center"><table cellpadding="0" cellspacing="0" border="0" style="width:620px"><tr class="layout-full-width" style="background-color:#FFFFFF"><![endif]--><!--[if (mso)|(IE)]><td align="center" width="413" style="background-color:#FFFFFF;width:413px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;" valign="top"><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:0px;"><![endif]-->
<div class="col num8"
style="display: table-cell; vertical-align: top; min-width: 320px; max-width: 408px;">
<div style="width: 100% ! important;"><!--[if (!mso)&(!IE)]><!-->
<div style="border: 0px solid transparent; padding: 5px 0px 0px;"><!--<![endif]--><!--[if mso]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 20px; padding-left: 20px; padding-top: 10px; padding-bottom: 10px; font-family: Tahoma, Verdana, sans-serif"><![endif]-->
<div
style="padding: 10px 20px; color: rgb(0, 0, 0); font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; line-height: 120%;">
<div
style="font-size: 12px; line-height: 14px; font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; color: rgb(0, 0, 0);">
<p style="margin: 0pt; font-size: 14px; line-height: 16px;"><span
style="color: rgb(0, 0, 0); font-size: 14px; line-height: 16px;"><a
href="https://beefree.io" rel="noopener"
style="text-decoration: none; color: rgb(0, 0, 0);" target="_blank">Identificador
asignado por la CGN</a></span></p>
</div>
</div>
<!--[if mso]></td></tr></table><![endif]-->
<!--[if mso]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 20px; padding-left: 20px; padding-top: 10px; padding-bottom: 10px; font-family: Tahoma, Verdana, sans-serif"><![endif]-->
<div
style="padding: 10px 20px; color: rgb(0, 0, 0); font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; line-height: 120%;">
<div
style="font-size: 12px; line-height: 14px; font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; color: rgb(0, 0, 0);">
<p style="margin: 0pt; font-size: 14px; line-height: 16px;"><span
style="color: rgb(0, 0, 0); font-size: 14px; line-height: 16px;"><a
href="https://beefree.io" rel="noopener"
style="text-decoration: none; color: rgb(0, 0, 0);" target="_blank">Clave
temporal</a></span></p>
</div>
</div>
<!--[if mso]></td></tr></table><![endif]-->
<!--[if (!mso)&(!IE)]><!--></div>
<!--<![endif]-->
</div>
</div>
<!--[if (mso)|(IE)]></td></tr></table><![endif]-->
<!--[if (mso)|(IE)]></td><td align="center" width="206" style="background-color:#FFFFFF;width:206px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;" valign="top"><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:0px;"><![endif]-->
<div class="col num4"
style="display: table-cell; vertical-align: top; max-width: 320px; min-width: 204px;">
<div style="width: 100% ! important;"><!--[if (!mso)&(!IE)]><!-->
<div style="border: 0px solid transparent; padding: 5px 0px 0px;"><!--<![endif]--><!--[if mso]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 20px; padding-left: 20px; padding-top: 10px; padding-bottom: 10px; font-family: Tahoma, Verdana, sans-serif"><![endif]-->
<div
style="padding: 10px 20px; color: rgb(0, 0, 0); font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; line-height: 120%;">
<div
style="font-size: 12px; line-height: 14px; font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; color: rgb(0, 0, 0);">
<p style="margin: 0pt; font-size: 14px; line-height: 16px;"
th:text="${entidad.idCGN}">[ID_CGN]</p>
</div>
</div>
<!--[if mso]></td></tr></table><![endif]-->
<!--[if mso]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 20px; padding-left: 20px; padding-top: 10px; padding-bottom: 10px; font-family: Tahoma, Verdana, sans-serif"><![endif]-->
<div
style="padding: 10px 20px; color: rgb(0, 0, 0); font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; line-height: 120%;">
<div
style="font-size: 12px; line-height: 14px; font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; color: rgb(0, 0, 0);">
<p style="margin: 0pt; font-size: 14px; line-height: 16px;"
th:text="${clave}">[CLAVE]</p>
</div>
</div>
<!--[if mso]></td></tr></table><![endif]-->
<!--[if (!mso)&(!IE)]><!--></div>
<!--<![endif]-->
</div>
</div>
<!--[if (mso)|(IE)]></td></tr></table><![endif]-->
<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]--></div>
</div>
</div>
<img alt="FNA - Todos los derechos reservados" th:src="|cid:${imgPie}|"><!--[if (mso)|(IE)]></td></tr></table><![endif]--></td>
</tr>
</tbody>
</table>
<!--[if (IE)]></div><![endif]-->
</body>
</html>
	')
GO

INSERT INTO ADM_PLANTILLA (NOMBRE, MODO_PLANTILLA, CHARSET, FECHA_CREACION, FECHA_MODIFICACION, USUARIO_CREADOR, USUARIO_EDITOR, ACTIVO, CONTENIDO) 
     VALUES ('comentario', 'HTML', 'UTF-8', GETDATE(), NULL, 'INSTALACION', NULL, 1, 
     '
<html>
<head>
<!--[if gte mso 9]><xml><o:OfficeDocumentSettings><o:AllowPNG/><o:PixelsPerInch>96</o:PixelsPerInch></o:OfficeDocumentSettings></xml><![endif]-->
<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
<meta content="width=device-width" name="viewport">
<!--[if !mso]><!-->
<meta content="IE=edge" http-equiv="X-UA-Compatible">
<!--<![endif]-->
<title>Registro</title>
<style type="text/css">
body {
margin: 0;
padding: 0;
}

table,
td,
tr {
vertical-align: top;
border-collapse: collapse;
}

* {
line-height: inherit;
}

a[x-apple-data-detectors=true] {
color: inherit !important;
text-decoration: none !important;
}

.ie-browser table {
table-layout: fixed;
}

[owa] .img-container div,
[owa] .img-container button {
display: block !important;
}

[owa] .fullwidth button {
width: 100% !important;
}

[owa] .block-grid .col {
display: table-cell;
float: none !important;
vertical-align: top;
}

.ie-browser .block-grid,
.ie-browser .num12,
[owa] .num12,
[owa] .block-grid {
width: 620px !important;
}

.ie-browser .mixed-two-up .num4,
[owa] .mixed-two-up .num4 {
width: 204px !important;
}

.ie-browser .mixed-two-up .num8,
[owa] .mixed-two-up .num8 {
width: 408px !important;
}

.ie-browser .block-grid.two-up .col,
[owa] .block-grid.two-up .col {
width: 306px !important;
}

.ie-browser .block-grid.three-up .col,
[owa] .block-grid.three-up .col {
width: 306px !important;
}

.ie-browser .block-grid.four-up .col [owa] .block-grid.four-up .col {
width: 153px !important;
}

.ie-browser .block-grid.five-up .col [owa] .block-grid.five-up .col {
width: 124px !important;
}

.ie-browser .block-grid.six-up .col,
[owa] .block-grid.six-up .col {
width: 103px !important;
}

.ie-browser .block-grid.seven-up .col,
[owa] .block-grid.seven-up .col {
width: 88px !important;
}

.ie-browser .block-grid.eight-up .col,
[owa] .block-grid.eight-up .col {
width: 77px !important;
}

.ie-browser .block-grid.nine-up .col,
[owa] .block-grid.nine-up .col {
width: 68px !important;
}

.ie-browser .block-grid.ten-up .col,
[owa] .block-grid.ten-up .col {
width: 60px !important;
}

.ie-browser .block-grid.eleven-up .col,
[owa] .block-grid.eleven-up .col {
width: 54px !important;
}

.ie-browser .block-grid.twelve-up .col,
[owa] .block-grid.twelve-up .col {
width: 50px !important;
}
</style>
<style id="media-query" type="text/css">
@media only screen and (min-width: 640px) {
.block-grid {
width: 620px !important;
}

.block-grid .col {
vertical-align: top;
}

.block-grid .col.num12 {
width: 620px !important;
}

.block-grid.mixed-two-up .col.num3 {
width: 153px !important;
}

.block-grid.mixed-two-up .col.num4 {
width: 204px !important;
}

.block-grid.mixed-two-up .col.num8 {
width: 408px !important;
}

.block-grid.mixed-two-up .col.num9 {
width: 459px !important;
}

.block-grid.two-up .col {
width: 310px !important;
}

.block-grid.three-up .col {
width: 206px !important;
}

.block-grid.four-up .col {
width: 155px !important;
}

.block-grid.five-up .col {
width: 124px !important;
}

.block-grid.six-up .col {
width: 103px !important;
}

.block-grid.seven-up .col {
width: 88px !important;
}

.block-grid.eight-up .col {
width: 77px !important;
}

.block-grid.nine-up .col {
width: 68px !important;
}

.block-grid.ten-up .col {
width: 62px !important;
}

.block-grid.eleven-up .col {
width: 56px !important;
}

.block-grid.twelve-up .col {
width: 51px !important;
}
}

@media (max-width: 640px) {

.block-grid,
.col {
min-width: 320px !important;
max-width: 100% !important;
display: block !important;
}

.block-grid {
width: 100% !important;
}

.col {
width: 100% !important;
}

.col>div {
margin: 0 auto;
}

img.fullwidth,
img.fullwidthOnMobile {
max-width: 100% !important;
}

.no-stack .col {
min-width: 0 !important;
display: table-cell !important;
}

.no-stack.two-up .col {
width: 50% !important;
}

.no-stack .col.num4 {
width: 33% !important;
}

.no-stack .col.num8 {
width: 66% !important;
}

.no-stack .col.num4 {
width: 33% !important;
}

.no-stack .col.num3 {
width: 25% !important;
}

.no-stack .col.num6 {
width: 50% !important;
}

.no-stack .col.num9 {
width: 75% !important;
}

.mobile_hide {
min-height: 0px;
max-height: 0px;
max-width: 0px;
display: none;
overflow: hidden;
font-size: 0px;
}
}
</style>
</head>
<body class="clean-body"
style="margin: 0pt; padding: 0pt; background-color: rgb(255, 255, 255); width: 677px;">
<style id="media-query-bodytag" type="text/css">
@media (max-width: 640px) {
.block-grid {
min-width: 320px!important;
max-width: 100%!important;
width: 100%!important;
display: block!important;
}
.col {
min-width: 320px!important;
max-width: 100%!important;
width: 100%!important;
display: block!important;
}
.col > div {
margin: 0 auto;
}
img.fullwidth {
max-width: 100%!important;
height: auto!important;
}
img.fullwidthOnMobile {
max-width: 100%!important;
height: auto!important;
}
.no-stack .col {
min-width: 0!important;
display: table-cell!important;
}
.no-stack.two-up .col {
width: 50%!important;
}
.no-stack.mixed-two-up .col.num4 {
width: 33%!important;
}
.no-stack.mixed-two-up .col.num8 {
width: 66%!important;
}
.no-stack.three-up .col.num4 {
width: 33%!important
}
.no-stack.four-up .col.num3 {
width: 25%!important
}
}
</style>
<!--[if IE]><div class="ie-browser"><![endif]-->
<table class="nl-container"
style="margin: 1pt auto; table-layout: fixed; vertical-align: top; min-width: 320px; border-spacing: 0pt; border-collapse: collapse; background-color: rgb(255, 255, 255); width: 70%;"
valign="top" bgcolor="#ffffff" cellpadding="0" cellspacing="0"
width="100%">
<tbody>
<tr style="vertical-align: top;" valign="top">
<td style="vertical-align: top; border-collapse: collapse;"
valign="top"><!--[if (mso)|(IE)]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td align="center" style="background-color:#FFFFFF"><![endif]--><img
alt="Fondo nacional del ahorrro" th:src="|cid:${imgEncabezado}|">
<div style="background-color: transparent;">
<div class="block-grid"
style="margin: 0pt auto; min-width: 320px; max-width: 620px; background-color: transparent;">
<div
style="border-collapse: collapse; display: table; width: 100%; background-color: transparent;"><!--[if (mso)|(IE)]><table width="100%" cellpadding="0" cellspacing="0" border="0" style="background-color:transparent;"><tr><td align="center"><table cellpadding="0" cellspacing="0" border="0" style="width:620px"><tr class="layout-full-width" style="background-color:transparent"><![endif]--><!--[if (mso)|(IE)]><td align="center" width="620" style="background-color:transparent;width:620px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;" valign="top"><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:10px;"><![endif]-->
<div class="col num12"
style="min-width: 320px; max-width: 620px; display: table-cell; vertical-align: top;">
<div style="width: 100% ! important;"><!--[if (!mso)&(!IE)]><!-->
<div style="border: 0px solid transparent; padding: 5px 0px 10px;"><!--<![endif]--><!--[if mso]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 5px; font-family: Tahoma, Verdana, sans-serif"><![endif]--><!--[if mso]></td></tr></table><![endif]-->
<!--[if mso]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 25px; font-family: Tahoma, Verdana, sans-serif"><![endif]-->
<div
style="padding: 10px 10px 25px; color: rgb(0, 0, 0); font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; line-height: 120%;">
<div
style="font-size: 12px; line-height: 14px; font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; color: rgb(0, 0, 0);">
<p
style="margin: 0pt; font-size: 12px; line-height: 16px; text-align: left;">Señores:</p>
<p><b><span th:text="${razonSocial}">[NOMBRE_ENTIDAD]</span></b></p>
<p
style="margin: 0pt; font-size: 12px; line-height: 16px; text-align: left;">Se
ha realizado una gestión sobre la partida conciliatoria mencionada en
el asunto. El usuario <b><span th:text="${usuario}">[USUARIO]</span></b>
ha escrito lo siguiente:</p>
<p
style="margin: 20px; font-size: 12px; text-align: left; color: rgb(78, 78, 80);">"<span
th:text="${comentario}">[COMENTARIO]</span>"</p>
<p style="margin: 0px; font-size: 12px; text-align: left;">El
estado actual de la partida conciliatoria es: <span th:text="${estado}">[ESTADO_PARTIDA]</span>
</p>
</div>
</div>
<!--[if mso]></td></tr></table><![endif]-->
<!--[if mso]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 5px; font-family: Tahoma, Verdana, sans-serif"><![endif]--><br>
<div
style="padding: 10px 10px 5px; color: rgb(0, 0, 0); font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; line-height: 120%;">
<div
style="font-size: 12px; line-height: 14px; font-family: ''Lato'',Tahoma,Verdana,Segoe,sans-serif; color: rgb(0, 0, 0);">
<p style="margin: 0pt; font-size: 12px; text-align: left;">Este
mensaje se ha generado automáticamente, por favor no responda. Si desea
gestionar esta partida conciliatoria haga clic <a th:href="${urlAplicacion}">aquí</a></p>
</div>
</div>
<!--[if mso]></td></tr></table><![endif]-->
<!--[if (!mso)&(!IE)]><!--></div>
<!--<![endif]--> </div>
</div>
<!--[if (mso)|(IE)]></td></tr></table><![endif]--><!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]--> </div>
</div>
</div>
<br>
<img alt="FNA - Todos los derechos reservados"
th:src="|cid:${imgPie}|"><!--[if (mso)|(IE)]></td></tr></table><![endif]--></td>
</tr>
</tbody>
</table>
<!--[if (IE)]></div><![endif]-->
</body>
</html>
	')
GO