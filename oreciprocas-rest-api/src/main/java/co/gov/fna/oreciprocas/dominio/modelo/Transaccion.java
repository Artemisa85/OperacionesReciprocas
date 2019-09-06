/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.modelo;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import co.gov.fna.oreciprocas.dominio.convertidor.EstadoTransaccionConvertidor;
import co.gov.fna.oreciprocas.dominio.convertidor.PeriodoConvertidor;
import co.gov.fna.oreciprocas.dominio.enumeracion.EstadoTransaccion;
import co.gov.fna.oreciprocas.dominio.enumeracion.Periodo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa una transacci&oacute; en el contexto de Operaciones
 * Rec&iacute;procas.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "ORE_TRANSACCION")
public class Transaccion implements Serializable {

    /**
	 * Serial autogenerado
	 */
	private static final long serialVersionUID = -2521768660577605395L;

	/**
     * Identificador de la transacci&oacute;n.
     */
    @Id
    @Column(name = "ID", unique = true)
    private BigInteger id;

    /**
     * Periodo de corte de informaci&oacute;n:
     * <br/>1. Enero - Marzo
     * <br/>2. Abril - Junio
     * <br/>3. Julio - Septiembre
     * <br/>4. Octubre - Diciembre
     */
    @Convert(converter = PeriodoConvertidor.class)
    @Column(name = "PERIODO")
    private Periodo periodo;

    /**
     * Fecha de consolidaci&oacute;n del archivo
     */
    @Column(name = "FECHA_CONSOLIDADO")
    private Date fechaConsolidado;

    /**
     * Identificador de la CGN asignado a la contraparte (Entidad
     * rec&iacute;proca).
     */
    @Column(name = "ID_ENTIDAD_RECIPROCA")
    private String idCgnEntidadReciproca;

    /**
     * Nombre de la entidad contraparte (Entidad rec&iacute;proca).
     */
    @Column(name = "ENTIDAD_RECIPROCA")
    private String nombreEntidadReciproca;

    /**
     * Entidades involucradas en la transacci&oacute;nl
     */
    @Column(name = "ENT_INVOLUCRADAS")
    private String entidadesInvolucradas;

    /**
     * Contacto contador en la transacci&oacute;nl
     */
    @Column(name = "CONTACTO_CONTADOR")
    private String contactoContador;

    /**
     * Estado de la conciliaci&oacute;n
     */
    @Convert(converter = EstadoTransaccionConvertidor.class)
    @Column(name = "ESTADO")
    private EstadoTransaccion estado;

    /**
     * Fila en que inicia la transaccion en el excel
     */
    @Transient
    private int fila;

    @Column(name = "FECHA_CARGUE")
    private Date fechaCague;

    @JsonManagedReference
    @OneToMany(mappedBy = "idTransaccion", targetEntity = Operacion.class, cascade = CascadeType.ALL)
    private Collection<Operacion> operaciones;

    @JsonManagedReference
    @OneToOne(mappedBy = "idTransaccion", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Comodin comodin;

}
