/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.adaptador;

import java.util.Collection;

/**
 * Define la manera en que se deben convertir objetos entre modelo y DTO (Y viceversa)
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public interface ModeloDtoAdaptador <M, D> {
	
	/**
	 * Adapta un objeto del modelo a su respectivo DTO
	 * @param modelo Objeto del modelo
	 * @return DTO correspondiente al modelo
	 */
	D modeloAdto(M modelo);
	
	/**
	 * Adapta una colección de objetos del modelo a una colección de DTO
	 * @param coleccionModelo Colección de objetos del modelo.
	 * @return Colección de objetos DTO correspondientes
	 */
	Collection<D> modeloAdto(Collection<M> coleccionModelo); 
	
	/**
	 * Adapta un objeto DTO a su respectivo modelo.
	 * @param dto Objeto DTO
	 * @return Objeto del modelo correspondiente.
	 */
	M dtoAmodelo(D dto);
	
	/**
	 * Adapta una colección de objetos DTO a una colección de objetos del modelo.
	 * @param coleccionDTO Colección de objetos DTO
	 * @return Colección de objetos del modelo correspondiente.
	 */
	Collection<M> dtoAmodelo (Collection<D> coleccionDTO);

}
