/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * Bean usado para obtener los mensajes principales utilizados en operaciones CRUD.
 *
 * @author <a href="tecnico_integracion5@utayecisa.com"> Guillermo Garc&iacute;a</a>
 * @version 1.0
 */
/**
 * Gets the find not found.
 *
 * @return the find not found
 */
@Getter

/**
 * Instantiates a new message bean.
 */
@NoArgsConstructor

/**
 * Instantiates a new message bean.
 *
 * @param crudCreated the crud created
 * @param crudUpdated the crud updated
 * @param crudDeleted the crud deleted
 * @param findFound the find found
 * @param findOneFound the find one found
 * @param findNotFound the find not found
 */
@AllArgsConstructor

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Builder()
@Component
public class MessageBean {
	
	/** The crud created. */
	@Value("${message.crud.created:false}")
	private String crudCreated;

	/** The crud updated. */
	@Value("${message.crud.updated:false}")
	private String crudUpdated;

	/** The crud deleted. */
	@Value("${message.crud.deleted:false}")
	private String crudDeleted;
	
	/** The find found. */
	@Value("${message.find.found:false}")
	private String findFound;
	
	/** The find one found. */
	@Value("${message.find.one_found:false}")
	private String findOneFound;
	
	/** The find not found. */
	@Value("${message.find.not_found:false}")	
	private String findNotFound;
	
}
