/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.exception;

/**
 *
 * @author Esteban Salinas
 */
public class ErrorCargaLog {

    // Generales
    public final static String EXCEDE_CANTIDAD = "Excede la cantidad de caracteres campo ";
    public final static String DATO_ERRADO = "Tipo de dato errado campo ";
    public final static String NO_CAMPO = "Dato es obligatorio campo ";
    public final static String VALOR_DIF = "Valor diferente al esperado";

    // Especificos
    public final static String YA_EXISTE_FECHA_CONSOLIDADO = "La fecha de consolidado ya existe en DB";
    public final static String YA_EXISTE_ID_TRANSACCION_EXCEL = "El Id Transacción esta repetido en el excel, en la fila ";
    public final static String YA_EXISTE_ID_TRANSACCION_DB = "El Id Transacción ya existe en DB";

    /**
     * la fila del excel en la cual esta el error 
     */
    private final int fila;
    
    /**
     * el nombre del campo que presenta el error
     */
    private final String nombreCampo;
    
    /**
     * la descripcion del error encontrado
     */
    private final String descripcion;
    
    /**
     * la transaccion a la que se asocia el error
     */
    private final Integer numTransaccion;

    public ErrorCargaLog(int fila, Integer numTransaccion, String nombreCampo, String descripcion) {
        this.fila = fila;
        this.nombreCampo = nombreCampo;
        this.descripcion = descripcion;
        this.numTransaccion = numTransaccion;
    }

    /**
     * @return the fila
     */
    public int getFila() {
        return fila;
    }

    /**
     * @return the nombreCampo
     */
    public String getNombreCampo() {
        return nombreCampo;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @return the numTransaccion
     */
    public Integer getNumTransaccion() {
        return numTransaccion;
    }

}
