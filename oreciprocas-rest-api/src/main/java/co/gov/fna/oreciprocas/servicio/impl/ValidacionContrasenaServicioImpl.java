/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.servicio.impl;

import java.util.Collection;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.gov.fna.core.common.exception.ErrorDetail;
import co.gov.fna.core.common.exception.FnaException;
import co.gov.fna.oreciprocas.dominio.dto.ValidacionContrasenaDTO;
import co.gov.fna.oreciprocas.dominio.modelo.ContrasenaHistorial;
import co.gov.fna.oreciprocas.exception.CredencialesNoValidasException;
import co.gov.fna.oreciprocas.exception.EntidadNoEncontradaException;
import co.gov.fna.oreciprocas.repositorio.ContrasenaHistorialRepositorio;
import co.gov.fna.oreciprocas.servicio.ValidacionContrasenaServicio;

/**
 * Implementaci&oacute;n por defecto de {@link ValidacionClaveServicio}
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Service
public class ValidacionContrasenaServicioImpl implements ValidacionContrasenaServicio {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ContrasenaHistorialRepositorio historialRepositorio;

	/**
	  * {@inheritDoc}
	  */
	@Override
	public void validar(ValidacionContrasenaDTO contrasena) {
		validarConfirmacionContrasena(contrasena);
		validarAutenticacion(contrasena);
		validarFortaleza(contrasena);
		validarHistorial(contrasena);
	}
	
	/**
	 * Valida que la nueva contraseña y la confirmaci&oacute;n de la contrase&ntilde;
	 * coincidan
	 * 
	 * @param contrasena Datos de contrase&ntilde;a a validar.
	 */
	private void validarConfirmacionContrasena(ValidacionContrasenaDTO contrasena) {
		boolean noCoinciden = 
				!contrasena.getContrasenaNueva().equals(contrasena.getConfirmacionContrasena());
		
		if (noCoinciden) {
			lanzarExcepcionConfirmacionContrasena();
		}
	}

	/**
	 * Verifica que la contrase&ntilde;a suministrada en la solicitud coincide con la almacenada
	 * en el sistema.
	 * 
	 * @param contrasena Datos de contrase&ntilde;a a validar.
	 */
	private void validarAutenticacion(ValidacionContrasenaDTO contrasena) {
		boolean noCoincide = !passwordEncoder.matches(
				contrasena.getContrasenaActual(), 
				contrasena.getUsuario().getContrasena());
		
		if (noCoincide) {
			lanzarExcepcionClaveNoValida();
		}

	}

	/**
	 * Valida la fortaleza de una contraseña, en este caso valida que:<br/>
	 * 
	 * 1. Tenga longitud m&iacute;nima de 8 caracteres<br/>
	 * 2. Contenga al menos una may&uacte;scula<br/>
	 * 3. Contenga al menos una min&uacute;scula;
	 * 4. Contenga al menos uno de los siguientes caracteres especiales:
	 *      `~!@#$%^&()_-+={}[]\|:;"'<>,.?/
	 * 
	 * @param contrasena Datos de contrase&ntilde;a a validar.
	 */
	private void validarFortaleza(ValidacionContrasenaDTO contrasena) {
		
		String regex = "^.*(?=.{8,})((?=.*[~!@#$%^&()_-{}\\[\\]\\\\|:;\"'<>,.\\/\\?\\+\\=]){1})"
				+ "(?=.*\\d)((?=.*[a-z]){1})((?=.*[A-Z]){1}).*$";
		Pattern pattern = Pattern.compile(regex );
		Matcher matcher = pattern.matcher(contrasena.getContrasenaNueva());
		
		if (!matcher.matches()) {
			lanzarExcepcionClaveDebil();
		}
	}

	/**
	 * Valida que la contraseña nueva suministrada no haya sido usada anteriormente de acuerdo
	 * con la po&iacute;tica de seguridad.
	 * 
	 * @param contrasena Datos de contrase&ntilde;a a validar.
	 */
	private void validarHistorial(ValidacionContrasenaDTO contrasena) {
		// Validar que no sean iguales.
		boolean iguales = contrasena.getContrasenaActual().equals(contrasena.getContrasenaNueva());
		if (iguales) {
			lanzarExcepcionClaveAntigua();
		}
		// Validar que la contraseña no haya sido usada con anterioridad.
		Collection<ContrasenaHistorial> historial = 
				historialRepositorio.findFirst3ByIdUsuarioOrderByFechaCambioContrasenaDesc(
						contrasena.getUsuario().getId()
						);
		for (ContrasenaHistorial cHistorico: historial) {
			boolean coinciden = passwordEncoder.matches(
					contrasena.getContrasenaNueva(), cHistorico.getContrasena());
			
			if (coinciden) {
				lanzarExcepcionClaveAntigua();
			}
		}
	}

	/**
	 * Lanza una excepci&oacute;n indicando que la nueva contrase&ntilde;a y la confirmaci&oacute;n 
	 * de &eacute;sta no coinciden.
	 */
	private void lanzarExcepcionConfirmacionContrasena() {
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setCode("CREDENCIALES_NO_VALIDAS");
		errorDetail.setMessage("Confirmación de contraseña incorrecta, "
				+ "no coincide con tu nueva contraseña"); 
		errorDetail.setTimestamp(new Date());
		throw new EntidadNoEncontradaException(errorDetail, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Lanza una excepci&oacute;n indicando que la contrase&ntilde;a no cumple con los 
	 * requerimientos de fortaleza m&iacute;nima de la misma.
	 */
	private void lanzarExcepcionClaveDebil() {
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setCode("CREDENCIALES_NO_VALIDAS");
		errorDetail.setMessage("La contraseña no cumple con los requisitos mínimos"); 
		errorDetail.setTimestamp(new Date());
		throw new EntidadNoEncontradaException(errorDetail, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Lanza una excepci&oacute;n cuando la contrase&ntilde; no cumple con la pol&iacute;tica de 
	 * seguridad. 
	 */
	private void lanzarExcepcionClaveNoValida() {
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setCode("CREDENCIALES_NO_VALIDAS");
		errorDetail.setMessage("Error de autenticación: Credenciales no válidas"); 
		errorDetail.setTimestamp(new Date());
		throw new CredencialesNoValidasException(errorDetail, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Lanza una excepci&oacute;n indicando que la clave ya ha sido utilizada anteriormente.
	 */
	private void lanzarExcepcionClaveAntigua() {
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setCode("CREDENCIALES_NO_VALIDAS");
		errorDetail.setMessage("Esta clave ya ha sido utilizada anteriormente"); 
		errorDetail.setTimestamp(new Date());
		throw new FnaException(errorDetail, HttpStatus.BAD_REQUEST);
		
	}


	

}
