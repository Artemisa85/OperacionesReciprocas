/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.convertidor;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import co.gov.fna.oreciprocas.dominio.enumeracion.Periodo;

/**
 * Convertidor JPA para enum {@link Periodo}
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Converter(autoApply = true)
public class PeriodoConvertidor 
			implements AttributeConverter<Periodo, Integer> {

	/**
	  * {@inheritDoc}
	  */
	@Override
	public Integer convertToDatabaseColumn(Periodo attribute) {
		if (attribute == null) {
			return null;
		}
		return attribute.getCodigo();
	}

	/**
	  * {@inheritDoc}
	  */
	@Override
	public Periodo convertToEntityAttribute(Integer dbData) {
		return Periodo.get(dbData);
	}

	

}
