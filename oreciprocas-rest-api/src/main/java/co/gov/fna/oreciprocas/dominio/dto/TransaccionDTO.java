/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto;

import java.math.BigInteger;
import java.util.Date;

import co.gov.fna.oreciprocas.dominio.enumeracion.EstadoTransaccion;
import co.gov.fna.oreciprocas.dominio.enumeracion.Periodo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa una transacción (DTO) en el contexto del sistema.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransaccionDTO {
	
	/**
     * Identificador de la transacci&oacute;n.
     */
    private BigInteger id;

    /**
     * Periodo de corte de informaci&oacute;n:
     * <br/>1. Enero - Marzo
     * <br/>2. Abril - Junio
     * <br/>3. Julio - Septiembre
     * <br/>4. Octubre - Diciembre
     */
    private Periodo periodo;

    /**
     * Fecha de consolidaci&oacute;n del archivo
     */
    private Date fechaConsolidado;

    /**
     * Identificador de la CGN asignado a la contraparte (Entidad
     * rec&iacute;proca).
     */
    private String idCgnEntidadReciproca;

    /**
     * Nombre de la entidad contraparte (Entidad rec&iacute;proca).
     */
    private String nombreEntidadReciproca;

    /**
     * Entidades involucradas en la transacci&oacute;nl
     */
    private String entidadesInvolucradas;

    /**
     * Contacto contador en la transacci&oacute;nl
     */
    private String contactoContador;

    /**
     * Estado de la conciliaci&oacute;n
     */
    private EstadoTransaccion estado;
	
	/**
     * Fecha en que fue cargada la transacci&oacute;n al sistema
     */
    private Date fechaCague;



}
