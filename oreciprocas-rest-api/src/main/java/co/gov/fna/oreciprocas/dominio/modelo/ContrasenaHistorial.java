/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.modelo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa el historial de contrase&ntilde;as de un {@link UsuarioExterno}
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "ADM_CONTRASENA_HISTORIAL")
public class ContrasenaHistorial {
	
	/**
	 * Identificador de la entidad en el sistema Operaciones Rec&iacute;procas.
	 */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	/**
	 * Identificador del usuario al que pertenece el historial.
	 */
	@Column(name = "ADM_USUARIO_EXTERNO_ID")
	private Integer idUsuario;
	
	/**
	 * Contrase&ntilde;a historial
	 */
	@Column(name = "CONTRASENA")
	private String contrasena;

	/**
	 * Fecha en que la contrase&ntilde; empez&oacute; a ser parte del historial.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_CAMBIO_CONTRASENA")
	private Date fechaCambioContrasena;

}
