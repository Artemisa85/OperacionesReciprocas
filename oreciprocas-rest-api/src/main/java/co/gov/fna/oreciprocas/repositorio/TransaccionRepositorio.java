/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.repositorio;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import co.gov.fna.oreciprocas.dominio.dto.TransaccionRowDTO;
import co.gov.fna.oreciprocas.dominio.enumeracion.EstadoTransaccion;
import co.gov.fna.oreciprocas.dominio.enumeracion.Periodo;
import co.gov.fna.oreciprocas.dominio.modelo.Transaccion;

/**
 * Repositorio de entidades tipo {@link Transaccion}
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public interface TransaccionRepositorio extends PagingAndSortingRepository<Transaccion, BigInteger> {
	
	/**
	 * Recupera las transacciones asociadas con determinada entidad externa
	 * @param idCgnEntidadReciproca
	 * @return
	 */
	Collection<Transaccion> findByIdCgnEntidadReciproca(String idCgnEntidadReciproca);
	
	/**
	 * @param idCgnEntidad
	 * @return
	 */
	@Query("select new co.gov.fna.oreciprocas.dominio.dto.TransaccionRowDTO" + 
			" (t.id, t.entidadesInvolucradas, t.idCgnEntidadReciproca, t.nombreEntidadReciproca," +
			" o.liquidez, o.codigoCuenta, o.descripcionCuenta, o.valorReportado," +
			" o.partidaConciliatoria, t.estado, t.periodo)" +
			" from Transaccion t" +
			" inner join t.operaciones o on t.id = o.idTransaccion.id " + 
			" where o.idCgn = '920300000'" + 
			" and ( :idCgn is null or t.idCgnEntidadReciproca = :idCgn)" +
			" and ( :annio is null or year(t.fechaConsolidado) = :annio)" +
			" and ( :periodo is null or t.periodo = :periodo)" +
			" and ( :estado is null or t.estado = :estado)" +
			" and ( :fechaConsolidado is null or t.fechaConsolidado = :fechaConsolidado)" +
			" and ( :idTransaccion is null or t.id = :idTransaccion)" +
			" and ( :entidadesInv is null or t.entidadesInvolucradas like %:entidadesInv%)"
			)
	Page<TransaccionRowDTO> findTransactionsBy(
			@Param("idCgn") String idCgnEntidad,
			@Param("annio") Integer annio,
			@Param("periodo") Periodo periodo,
			@Param("estado") EstadoTransaccion estado,
			@Param("fechaConsolidado") Date fechaConsolidado,
			@Param("idTransaccion") BigInteger idTransaccion,
			@Param("entidadesInv") String entidadesInv,
			Pageable pageable);
	
	
	/**
	 * Obtiene las fechas de consolidaci&oacute;n de los archivos.
	 * @return
	 */
	@Query(
		"select distinct(t.fechaConsolidado) from Transaccion t" +
		" order by t.fechaConsolidado"
			)
	Collection<Date> transactionsConsolidateDates();
        
        /**
         * Revisa si existe una fecha de consolidado especifica en la tabla de transacciones
         * @param fechaConsolidado la fecha a buscar
         * @return tru, si existe de lo contrario false
         */
        boolean existsByFechaConsolidado(Date fechaConsolidado);
}
