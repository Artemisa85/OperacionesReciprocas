/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.enumeracion;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Estado de la conciliaci&oacute;n de las Operaciones reportadas por la CGN
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public enum EstadoTransaccion {
	
	VACIO					(null, ""),
	ABIERTO					("ABIERTO", "Abierto"),
	CERRADO					("CERRADO", "Cerrado"),
	TRAMITE_FNA				("TRA_FNA", "En trámite FNA"),
	TRAMITE_ENTIDAD			("TRA_ENT", "En trámite entidad"),
    OBSOLETO				("OBSOLETO", "Obsoleto")
	;
	
	/**
	 * C&oacute;digo del estado
	 */
	private String codigo;
	
	/**
	 * Descripci&oacute;n del estado.
	 */
	private String descripcion;
	
	
	private EstadoTransaccion(String codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}
	
	/**
	 * Retorna codigo
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Retorna descripcion
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Retorna un {@link EstadoTransaccion} a partir de su c&oacute;digo.
	 * @param codigo C&oacute;digo buscado
	 * @return Estado coincidiente o null
	 */
	public static EstadoTransaccion get(String codigo) {
		if (codigo == null) {
			return null;
		}
		List<EstadoTransaccion> estados = Arrays.asList(EstadoTransaccion.values());
		return estados
			.stream()
			.filter(estado -> ! Objects.isNull(estado.codigo) && estado.codigo.equalsIgnoreCase(codigo))
			.findFirst().orElse(VACIO);
	}

}
