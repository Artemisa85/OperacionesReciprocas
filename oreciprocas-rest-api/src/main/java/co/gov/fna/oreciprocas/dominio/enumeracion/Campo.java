/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.enumeracion;

/**
 * Descripcion de campos de entrada para la carga de un excel con transacciones
 * 
 * @author Esteban Salinas
 * version 1.0
 */
public enum Campo {
    // Generales
    NOMBRE_ENTIDAD("Nombre de la Entidad", "Alfanumérico 250"),
    TITULO_ARCHIVO("Título del Archivo", "Alfanumérico  100"),
    // Asociados a Transaccion
    TRANSACCION_PERIODO("Periodo", "Tipo fecha"),
    TRANSACCION_FECHA_CONSOLIDACION("Fecha de Consolidación ", "Tipo fecha"),
    TRANSACCION_ID("Id Transacción ", "Numérico 15 "),
    TRANSACCION_ENTIDADES_INVOLUCRADAS("Entidades Involucradas ", "Alfanumérico 50"),
    TRANSACCION_CONTACTO_CONTADOR("Contacto del Contador de la Entidad Reciproca", "Alfanumérico 500"),
    // Asociados a operacion
    OPERACION_CODIGO_ENTIDAD("Código Entidad", "Alfanumérico 50"),
    OPERACION_NOMBRE_ENTIDAD("Entidad", "Alfanumérico 250"),
    OPERACION_LIQUIDEZ("Liquidez", "Numérico 2"),
    OPERACION_CODIGO_CUENTA("Código Cuenta", "Alfanumérico 30"),
    OPERACION_DESCRIPCION_CUENTA("Cuenta", "Alfanumérico 250"),
    OPERACION_VALOR_REPORTADO("Valor Reportado", "Numérico 25 - Decimal 1"),
    OPERACION_PARTIDA_CONCILIATORIA("Partida Conciliatoria", "Numérico 25 - Decimal 1"),
    OPERACION_ORIGEN_DIFERENCIA("Origen Diferencia", "Alfanumérico 150"),
    OPERACION_ANALISTA("Analista Categoría", "Alfanumérico 20");

    /**
     * nombre del campo
     */
    private String nombre;
    
    /**
     * tipo del campo
     */
    private String tipo;

    private Campo(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tripo to set
     */
    public void setTripo(String tipo) {
        this.tipo = tipo;
    }

}
