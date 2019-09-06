/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.servicio.impl;

import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import co.gov.fna.core.common.exception.ErrorDetail;
import co.gov.fna.core.common.exception.FnaException;
import co.gov.fna.oreciprocas.adaptador.impl.AdjuntoAdaptator;
import co.gov.fna.oreciprocas.dominio.dto.AdjuntoDTO;
import co.gov.fna.oreciprocas.dominio.dto.AdjuntoDescargaDTO;
import co.gov.fna.oreciprocas.dominio.dto.CargaAdjuntoRespuestaDTO;
import co.gov.fna.oreciprocas.dominio.dto.CargaAdjuntoSolicitudDTO;
import co.gov.fna.oreciprocas.dominio.modelo.ArchivoAdjunto;
import co.gov.fna.oreciprocas.dominio.modelo.Comentario;
import co.gov.fna.oreciprocas.dominio.modelo.ConfiguracionRegistro;
import co.gov.fna.oreciprocas.repositorio.AdjuntoRepositorio;
import co.gov.fna.oreciprocas.repositorio.ComentarioRepositorio;
import co.gov.fna.oreciprocas.servicio.AdjuntoServicio;
import co.gov.fna.oreciprocas.servicio.ConfiguracionServicio;

import static co.gov.fna.oreciprocas.constantes.OReciprocasConstantes.DB_DOMINIO_ARCHIVOS_PERMITIDOS;

/**
 * Implementaci&oacute;n por defecto de {@link AdjuntoServicio}
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Service
public class AdjuntoServicioImpl implements AdjuntoServicio {
	
	/**
	 * Prefijo de nombre de directorio para transacciones
	 */
	private static String PREFIJO_DIR_TRANSACCION = "trx";
	
	@Value("${oreciprocas.upload.base-dir}")
	private String directorioBaseArchivos;
	
	@Autowired
	private ConfiguracionServicio configuracionServicio;
	
	@Autowired
	private AdjuntoRepositorio adjuntoRepositorio;
	
	@Autowired
	private ComentarioRepositorio comentarioRepositorio;
	
	@Autowired
	private AdjuntoAdaptator adjuntoAdaptador;
	
	private static Logger logger = LoggerFactory.getLogger(AdjuntoServicioImpl.class);
	
	/**
	  * {@inheritDoc}
	  */
	public CargaAdjuntoRespuestaDTO guardarAdjuntos(CargaAdjuntoSolicitudDTO solicitud) {
		
		MultipartFile[] archivos = solicitud.getArchivos();
		if (Objects.isNull(archivos) || archivos.length == 0) {
			lanzarExcepcionArchivosNoSuministrados();
		}
		
		Optional<Comentario> comentario = 
				comentarioRepositorio.findById(solicitud.getIdComentario());
		
		if ( !comentario.isPresent() ) {
			lanzarExcepcionComentarioNoEncontrado();
		}
		
		List<AdjuntoDTO> archivosGuardados = new ArrayList<>();
		
		for (MultipartFile archivo: archivos) {
			ArchivoAdjunto registro = guardarArchivo(archivo, comentario.get());
			AdjuntoDTO salida = registrarAdjunto(registro, solicitud.getContextoSolicitado());
			archivosGuardados.add(salida);
			logger.info("Archivo: {}, extension: {}, tipo mime: {}, ubicacion: {}", 
					registro.getNombre(), registro.getExtension(), 
					registro.getTipoMime(), registro.getUbicacion());
		}
		
		return CargaAdjuntoRespuestaDTO.builder()
				.mensaje( String.format("Se han cargado %d archivo(s)", archivosGuardados.size()) )
				.archivos(archivosGuardados)
				.build();
	}
	
	/**
	  * {@inheritDoc}
	  */
	@Override
	public AdjuntoDescargaDTO obtenerAdjunto(BigInteger idAdjunto) {
		Optional<ArchivoAdjunto> adjunto = adjuntoRepositorio.findById(idAdjunto);
		if (!adjunto.isPresent()) {
			lanzarExcepcionAdjuntoNoEncontrado();
		}
		
		String ubicacion = adjunto.get().getUbicacion();
		Path ruta = Paths.get(ubicacion);
		
		if (!Files.exists(ruta)) {
			lanzarExcepcionAdjuntoNoEncontrado();
		}
		try {
			Resource archivo = new UrlResource(ruta.toUri());
			HttpHeaders encabezadosHttp = new HttpHeaders();
			encabezadosHttp.add(HttpHeaders.CONTENT_DISPOSITION, 
					"attachment; filename=\"" + adjunto.get().getNombre() + "\"");
			
			return AdjuntoDescargaDTO.builder()
					.archivo(archivo)
					.encabezadosHttp(encabezadosHttp)
					.tipoMime(adjunto.get().getTipoMime())
					.build();
		} catch (MalformedURLException e) {
			logger.error("Imposible transformar ruta a recurso {}", ruta.toString());
			lanzarExcepcionAdjuntoNoEncontrado();
		}
			
		
		return null;
	}

	/**
	 * Guarda un archivo en el sistema de archivos.
	 * 
	 * @param archivo Archivo a ser guardado
	 * @param idComentario Identificador del comentario al que se asociar&aacute; el archivo.
	 * @param comentario Comentario relacionado con el archivo.
	 * @return Modelo persistente de un archivo adjunto
	 */
	private ArchivoAdjunto guardarArchivo(MultipartFile archivo, Comentario comentario) {
		
		Path ruta  = obtenerRutaGuardado(comentario, true);
		validar(archivo);
		
		String nombre = StringUtils.stripAccents(archivo.getOriginalFilename()).replaceAll(" ", "");
		String extension = nombre.substring(nombre.lastIndexOf(".") + 1);
		
		try {
			InputStream inputStream = archivo.getInputStream();
			Files.copy(inputStream, ruta.resolve(nombre),
                    StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			lanzarExcepcionImposibleGuardarArchivo(nombre);
		}
		
		return ArchivoAdjunto.builder()
				.comentario(comentario)
				.nombre(nombre)
				.extension(extension)
				.tipoMime(archivo.getContentType())
				.ubicacion(ruta.toString() + File.separator + nombre)
				.build();
	}
	
	/**
	 * Registra en base de datos el adjunto guardado.
	 * 
	 * @param registro Entidad a guardar.
	 * @param contextoSolicitado Contexto del servidor en donde fue llamado el servicio.
	 * @return DTO con la informaci&oacute;n de respuesta.
	 */
	private AdjuntoDTO registrarAdjunto(ArchivoAdjunto registro, String contextoSolicitado) {
		ArchivoAdjunto adjunto = adjuntoRepositorio.save(registro);
		adjunto.setUrl(contextoSolicitado + "/api/adjuntos/" + adjunto.getId());
		return adjuntoAdaptador.modeloAdto(adjunto);
	}

	/**
	 * Eval&uacute;a si un archivo puede ser guardado en el sistema.
	 * 
	 * @param archivo Archivo a ser evaluado
	 */
	private void validar(MultipartFile archivo) {
		String nombreArchivo = archivo.getOriginalFilename();
		if (archivo.isEmpty()) { 
			//Archivo no puede ser vacío
			lanzarExcepcionArchivoVacio(nombreArchivo);
		}
		if (archivo.getOriginalFilename().contains("..")) {
             // Esto es una verificación de seguridad
			 lanzarExcepcionIntentoEscrituraDirectorioInvalido(nombreArchivo);
        }
		boolean archivoPermitido = false;
		
		Collection<ConfiguracionRegistro> tiposMimePermitidos = 
				configuracionServicio.obtenerConfiguracionDominio(DB_DOMINIO_ARCHIVOS_PERMITIDOS);
		
		for (ConfiguracionRegistro registro: tiposMimePermitidos) {
			String tipo = registro.getValor();
			if (tipo.equalsIgnoreCase(archivo.getContentType())) {
				archivoPermitido = true;
				break;
			}
		}
		if ( !archivoPermitido ) {
			// Archivo debe estar permitido para cargar
			lanzarExcepcionTipoArchivoNoPermitido(nombreArchivo);
		}
		
	}

	/**
	 * Obtiene la ruta base para guardar archivos en el sistema.
	 * @param comentario Comentario de donde se sacar&aacute; informaci&oacute;n para 
	 * 		creaci&oacute;n del directorio.
	 * @return Ruta base de guardado.
	 */
	private Path obtenerRutaGuardado(Comentario comentario, boolean crearCuandoNoExiste) {
		StringBuilder sb = new StringBuilder();
		sb.append(directorioBaseArchivos);
		sb.append(File.separator);
		sb.append(PREFIJO_DIR_TRANSACCION);
		sb.append(comentario.getIdTransaccion());

		Path ruta = Paths.get(sb.toString());
		
		if (Files.notExists(ruta) && crearCuandoNoExiste) {
			try {
				logger.info("Creando directorio no existente {}", sb.toString());
				Files.createDirectories(ruta);
			} catch (IOException e) {
				logger.error("Ha ocurrido un error creando el directorio {}", sb.toString());
				logger.error(e.getLocalizedMessage(),e);
				lanzarExcepcionImposibleCrearDirectorio(sb.toString());
			}
		}
		
		return ruta;
	}
	

	/**
	 * Lanza una excepci&oacute;n cuando no es posible crear el directorio para guardar los
	 * adjuntos
	 * @param directorio Nombre del directorio que se iba a crear.
	 */
	private void lanzarExcepcionImposibleCrearDirectorio(String directorio) {
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setCode("DIRECTORIO_NO_CREADO");
		errorDetail.setMessage("Imposible crear el directorio " + directorio); 
		errorDetail.setTimestamp(new Date());
		throw new FnaException(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Lanza una excepci&oacute;n cuando hay problemas t&eacute;cnicos para guardar archivos
	 * en el sistema de archivos
	 * @param nombre Nombre del archivo que present&oacute; problemas.
	 */
	private void lanzarExcepcionImposibleGuardarArchivo(String nombre) {
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setCode("DIRECTORIO_NO_CREADO");
		errorDetail.setMessage(String.format("Imposible guardar el archivo %s", nombre)); 
		errorDetail.setTimestamp(new Date());
		throw new FnaException(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

	/**
	 * Lanza una excepci&oacute;n para cuando no se ha encontrado un comentario.
	 */
	private void lanzarExcepcionComentarioNoEncontrado() {
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setCode("COMENTARIO_NO_ENCONTRADO");
		errorDetail.setMessage("No existe el comentario para agregar adjuntos"); 
		errorDetail.setTimestamp(new Date());
		throw new FnaException(errorDetail, HttpStatus.NOT_FOUND);
	}

	/**
	 * Lanza excepci&oacute;n para cuando no se han suministrado archivos. 
	 */
	private void lanzarExcepcionArchivosNoSuministrados() {
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setCode("ARCHIVOS_NO_SUMINISTRADOS");
		errorDetail.setMessage("Se debe cargar al menos un archivo"); 
		errorDetail.setTimestamp(new Date());
		throw new FnaException(errorDetail, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Lanza excepci&oacute;n para cuando no se ha encontrado el adjunto.
	 */
	private void lanzarExcepcionAdjuntoNoEncontrado() {
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setCode("ARCHIVO_NO_ENCONTRADO");
		errorDetail.setMessage("No se ha encontrado el archivo solicitado"); 
		errorDetail.setTimestamp(new Date());
		throw new FnaException(errorDetail, HttpStatus.NOT_FOUND);
		
	}

	/**
	 * Lanza excepci&oacute;n para cuando se intenta guardar un archivo vac&iacute;o
	 * @param nombreArchivo Nombre del archivo que viene vac&iacute;o
	 */
	private void lanzarExcepcionArchivoVacio(String nombreArchivo) {
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setCode("ARCHIVO_VACIO");
		errorDetail.setMessage(String.format("El archivo %s está vacío", nombreArchivo)); 
		errorDetail.setTimestamp(new Date());
		throw new FnaException(errorDetail, HttpStatus.BAD_REQUEST);
	}

	/**
	 * 
	 */
	private void lanzarExcepcionTipoArchivoNoPermitido(String nombreArchivo) {
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setCode("TIPO_ARCHIVO_INVALIDO");
		errorDetail.setMessage(String.format("El tipo de archivo es inválido: %s", nombreArchivo)); 
		errorDetail.setTimestamp(new Date());
		throw new FnaException(errorDetail, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Lanza una excepci&oacute;n cuando mediante el nombre del archivo se intenta escribir en
	 * una ubicaci&oacute;n no válida
	 */
	private void lanzarExcepcionIntentoEscrituraDirectorioInvalido(String nombreArchivo) {
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setCode("NOMBRE_ILEGAL_ARCHIVO");
		errorDetail.setMessage(String.format("El nombre del archivo es ilegal: %s", nombreArchivo)); 
		errorDetail.setTimestamp(new Date());
		throw new FnaException(errorDetail, HttpStatus.BAD_REQUEST);
		
	}

}
