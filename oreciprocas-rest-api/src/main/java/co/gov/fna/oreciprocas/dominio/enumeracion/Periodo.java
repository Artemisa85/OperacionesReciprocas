/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.enumeracion;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Representa un periodo de tiempo.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public enum Periodo {

    ENERO_MARZO(1, "Enero - Marzo"),
    ABRIL_JUNIO(2, "Abril - Junio"),
    JULIO_SEPTIEMBRE(3, "Julio - Septiembre"),
    OCTUBRE_DICIEMBRE(4, "Octubre - Diciembre"),
    NO_DEFINIDO(-1, "No definido");

    /**
     * C&oacute;digo del periodo
     */
    private Integer codigo;

    /**
     * Descripci&oacute;n del estado.
     */
    private String descripcion;

    private Periodo(Integer codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    /**
     * Retorna codigo
     *
     * @return the codigo
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Retorna descripcion
     *
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Retorna un {@link EstadoTransaccion} a partir de su c&oacute;digo.
     *
     * @param codigo C&oacute;digo buscado
     * @return Estado coincidiente o null
     */
    public static Periodo get(Integer codigo) {
        List<Periodo> estados = Arrays.asList(Periodo.values());
        return estados
                .stream().filter(estado -> !Objects.isNull(estado.codigo) && estado.codigo.equals(codigo))
                .findFirst().orElse(null);
    }

    /**
     * consulta del periodo dependiendo el entero que representa el mes
     * @param mes el mes del cual se quiere consultar el periodo
     * @return el periodo correspondiente al mes
     */
    public static Periodo getByMonth(Integer mes) {
        Periodo ans = NO_DEFINIDO;
        if (mes >= 1 && mes <= 3) {
            ans = ENERO_MARZO;
        } else if (mes >= 4 && mes <= 6) {
            ans = ABRIL_JUNIO;
        } else if (mes >= 7 && mes <= 9) {
            ans = JULIO_SEPTIEMBRE;
        } else if (mes >= 10 && mes <= 12) {
            ans = OCTUBRE_DICIEMBRE;
        }
        return ans;
    }
}
