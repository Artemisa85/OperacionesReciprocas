/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.convertidor;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import co.gov.fna.oreciprocas.dominio.enumeracion.EstadoTransaccion;

/**
 * Convertidor JPA para enum {@link EstadoTransaccion}
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Converter(autoApply = true)
public class EstadoTransaccionConvertidor 
			implements AttributeConverter<EstadoTransaccion, String> {

	/**
	  * {@inheritDoc}
	  */
	@Override
	public String convertToDatabaseColumn(EstadoTransaccion attribute) {
		if (attribute == null) {
			return null;
		}
		return attribute.getCodigo();
	}

	/**
	  * {@inheritDoc}
	  */
	@Override
	public EstadoTransaccion convertToEntityAttribute(String dbData) {
		return EstadoTransaccion.get(dbData);
	}

}
