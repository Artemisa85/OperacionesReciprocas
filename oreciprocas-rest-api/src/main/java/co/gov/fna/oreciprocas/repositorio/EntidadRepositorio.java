/*
 * Copyright 2018. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.repositorio;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.gov.fna.oreciprocas.dominio.dto.EntidadDTO;
import co.gov.fna.oreciprocas.dominio.modelo.Entidad;

/**
 * Repositorio de entidates tipo {@link Entidad}
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public interface EntidadRepositorio extends JpaRepository<Entidad, Integer> {
	
	/**
	 * Busca una entidad por el Id asignado por la Contadur&iacute;a
	 * General de la Naci&oacute;n.
	 * @param idCGN Id asignado por la Contadur&iacute;a General de la Naci&oacute;n.
	 * @return Entidad encontrada.
	 */
	Optional<Entidad> findByIdCGN(String idCGN);

	/**
	 * @param idCGN Id asignado por la Contadur&iacute;a General de la Naci&oacute;n.
	 * @param nit N&uacute;mero de identificaci&oacute;n tributaria
	 * @return Entidad encontrada.
	 */
	Optional<Entidad> findByIdCGNAndNit(String idCGN, String nit);
	
	/**
	 * Recupera y pagina todas las entidades almacenadas.
	 * @param pageable
	 * @return
	 */
	@Query("select new co.gov.fna.oreciprocas.dominio.dto.EntidadDTO("
			+ "e.nit, e.idCGN, e.razonSocial,"
			+ "e.sigla, e.direccion, e.telefono, "
			+ "e.nombreContacto, e.apellidoContacto, "
			+ "e.correo, e.sitioWEB, e.fechaCreacion, "
			+ "e.fechaModificacion, e.usuarioCreador, "
			+ "e.usuarioEditor, e.activo, m.codigoDANE, d.codigoDANE) "
			+ "from Entidad e join e.municipio m join m.departamento d")
	Page<EntidadDTO> findAndPageAll(Pageable pageable);

	
	/**
	 * @param nit NIT de la entidad
	 * @param idCgn Id CGN 
	 * @param razonSocial Raz&oacute;n social de la entidad.
	 * @param pageable Configuraci&oacute; de paginaci&oacute;n
	 * @return Entidades que correspondan a los criterios.
	 */
	@Query("select new co.gov.fna.oreciprocas.dominio.dto.EntidadDTO("
			+ "e.nit, e.idCGN, e.razonSocial,"
			+ "e.sigla, e.direccion, e.telefono, "
			+ "e.nombreContacto, e.apellidoContacto, "
			+ "e.correo, e.sitioWEB, e.fechaCreacion, "
			+ "e.fechaModificacion, e.usuarioCreador, "
			+ "e.usuarioEditor, e.activo, m.codigoDANE, d.codigoDANE) "
			+ "from Entidad e join e.municipio m join m.departamento d "
			+ "where 1=1 "
			+ "and ( :nit is null or e.nit like %:nit%) "
			+ "and ( :idCgn is null or e.idCGN like %:idCgn%) "
			+ "and ( :razonSocial is null or upper(e.razonSocial) like upper(concat('%',:razonSocial,'%')) )"
			)
	Page<EntidadDTO> buscar(@Param("nit") String nit, @Param("idCgn") String idCgn, 
			@Param("razonSocial") String razonSocial, Pageable pageable);

}
