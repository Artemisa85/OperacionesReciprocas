/*
 * Copyright 2018. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.servicio.impl;

import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import co.gov.fna.core.common.exception.ErrorDetail;
import co.gov.fna.oreciprocas.adaptador.impl.EntidadAdaptador;
import co.gov.fna.oreciprocas.dominio.dto.BusquedaEntidadDTO;
import co.gov.fna.oreciprocas.dominio.dto.EntidadDTO;
import co.gov.fna.oreciprocas.dominio.dto.EntidadResultadoDTO;
import co.gov.fna.oreciprocas.dominio.dto.MunicipioDTO;
import co.gov.fna.oreciprocas.dominio.modelo.Departamento;
import co.gov.fna.oreciprocas.dominio.modelo.Entidad;
import co.gov.fna.oreciprocas.dominio.modelo.Municipio;
import co.gov.fna.oreciprocas.dominio.modelo.UsuarioExterno;
import co.gov.fna.oreciprocas.exception.EntidadNoEncontradaException;
import co.gov.fna.oreciprocas.repositorio.EntidadRepositorio;
import co.gov.fna.oreciprocas.repositorio.UsuarioExternoRepositorio;
import co.gov.fna.oreciprocas.servicio.EntidadServicio;
import co.gov.fna.oreciprocas.servicio.MunicipioServicio;

/**
 * Implementaci&oacute;n por defecto de {@link EntidadServicio}
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Service
public class EntidadServicioImpl implements EntidadServicio {
	
	
	private static Logger logger = LoggerFactory.getLogger(EntidadServicioImpl.class);
	
	@Autowired
	private EntidadRepositorio repoEntidad;
	
	@Autowired
	private UsuarioExternoRepositorio repoUsuario;
	
	@Autowired
	private EntidadAdaptador entidadAdaptador;
	
	@Autowired
	private MunicipioServicio municipioServicio;
		
	/**
	  * {@inheritDoc}
	  */
	@Override
	public EntidadResultadoDTO buscar(BusquedaEntidadDTO solicitud, Pageable pageable) {
		if (solicitud == null) {
			solicitud = new BusquedaEntidadDTO();
		}
		Page<EntidadDTO> entidades = repoEntidad.buscar(
				solicitud.getNit(),
				solicitud.getIdCgn(),
				solicitud.getRazonSocial(),
				pageable);
		
		return EntidadResultadoDTO.builder()
				.mensaje("")
				.entidades(entidades)
				.build();
	}

	/**
     * {@inheritDoc}
	 */
	public Optional<EntidadDTO> buscarEntidad(String nit, String idCGN) {
		Optional<Entidad> optEntidad = repoEntidad.findByIdCGNAndNit(idCGN, nit);
		if ( ! optEntidad.isPresent()) {
			lanzarExcepcionEntidadNoEncontrada();
		}
		
		Entidad entidad = optEntidad.get();
		EntidadDTO resultado = entidadAdaptador.modeloAdto(entidad);
		return Optional.of(resultado);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Optional<EntidadDTO> buscarEntidadPorIdCgn(String idCgn) {
		
		Optional<Entidad> optEntidad = repoEntidad.findByIdCGN(idCgn);
		
		if (optEntidad.isPresent()) {
			Entidad entidad = optEntidad.get();
			EntidadDTO resultado = entidadAdaptador.modeloAdto(entidad);
			Municipio municipio = entidad.getMunicipio();
			Departamento departamento = municipio.getDepartamento();
			resultado.setCodigoMunicipio(municipio.getCodigoDANE());
			resultado.setCodigoDepartamento(departamento.getCodigoDANE());
			return Optional.of(resultado);
		}
		return Optional.ofNullable(null);
	}
	
	
	/**
	  * {@inheritDoc}
	  */
	@Override
	public EntidadResultadoDTO recuperarEntidades(Pageable pageable) {
		Page<EntidadDTO> entidades = repoEntidad.findAndPageAll(pageable);
		
		return EntidadResultadoDTO.builder()
				.mensaje("")
				.entidades(entidades)
				.build();
	}

	
	/**
	  * {@inheritDoc}
	  */
	public EntidadDTO crear(EntidadDTO solicitud, Authentication auth) {
		Optional<Entidad> optEntidad = repoEntidad.findByIdCGN(solicitud.getIdCGN());
		
		if (optEntidad.isPresent()) {
			lanzarExcepcionEntidadYaExiste();
		}
		Entidad entidad = entidadAdaptador.dtoAmodelo(solicitud);
		MunicipioDTO municipio =
				municipioServicio.buscarPorCodigoDane(solicitud.getCodigoMunicipio());
		
		
		entidad.setMunicipio(new Municipio(municipio.getId()));
		entidad.setActivo(true);
		entidad.setFechaCreacion(new Date());
		entidad.setUsuarioCreador(auth.getName());
		
		
		entidad = repoEntidad.save(entidad);
		logger.info("Entidad creada: Usuario {}, Id {}, nit {}, razonSocial {}", auth.getName(), 
				entidad.getId(), entidad.getNit(), entidad.getRazonSocial());
		return solicitud;
	}

	/**
	  * {@inheritDoc}
	  */
	public EntidadDTO editar(EntidadDTO solicitud, Authentication auth) {
		Optional<Entidad> optEntidad = repoEntidad.findByIdCGN(solicitud.getIdCGN());
		if ( !optEntidad.isPresent() ) {
			lanzarExcepcionEntidadNoEncontrada();
		}
		
		MunicipioDTO municipio =
				municipioServicio.buscarPorCodigoDane(solicitud.getCodigoMunicipio());
		
		
		Entidad entidad = optEntidad.get();
		
		entidad.setNit(entidad.getNit());
		entidad.setRazonSocial(solicitud.getRazonSocial());
		entidad.setSigla(solicitud.getSigla());
		entidad.setDireccion(solicitud.getDireccion());
		entidad.setTelefono(solicitud.getTelefono());
		entidad.setNombreContacto(solicitud.getNombreContacto());
		entidad.setApellidoContacto(solicitud.getApellidoContacto());
		entidad.setCorreo(solicitud.getCorreo());
		entidad.setSitioWEB(solicitud.getSitioWEB());
		entidad.setActivo(solicitud.getActivo());
		entidad.setFechaModificacion(new Date());
		entidad.setUsuarioEditor(auth.getName());
		entidad.setMunicipio(new Municipio(municipio.getId()));
		
		Entidad otraEntidad  = repoEntidad.saveAndFlush(entidad);
		
		UsuarioExterno usuarioAsociado = otraEntidad.getUsuario();
		
		if (usuarioAsociado != null) {
			usuarioAsociado.setNombre(otraEntidad.getNombreContacto());
			usuarioAsociado.setApellido(otraEntidad.getApellidoContacto());
			repoUsuario.save(usuarioAsociado);
		}
		
		logger.info("Entidad modificada: Usuario {}, Id {}, nit {}, razonSocial {}", auth.getName(), 
				otraEntidad.getId(), otraEntidad.getNit(), otraEntidad.getRazonSocial());
				
		return entidadAdaptador.modeloAdto(otraEntidad);
	}

	/**
	 * Lanza una excepci&oacute;n indicando que la entidad no es v&aacute;lida para continuar 
	 * con el registro.
	 */
	private void lanzarExcepcionEntidadNoEncontrada() {
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setCode("ENTIDAD_NO_ENCONTRADA");
		errorDetail.setMessage("La entidad buscada no existe"); 
		errorDetail.setTimestamp(new Date());
		throw new EntidadNoEncontradaException(errorDetail, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Lanza una excepci&oacute;n indicando que la entidad ya existe en el sistema 
	 */
	private void lanzarExcepcionEntidadYaExiste() {
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setCode("ENTIDAD_DUPLICADA");
		errorDetail.setMessage("La entidad que intenta crear ya existe"); 
		errorDetail.setTimestamp(new Date());
		throw new EntidadNoEncontradaException(errorDetail, HttpStatus.BAD_REQUEST);
	}

	

}
