/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.servicio.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import co.gov.fna.core.common.exception.ErrorDetail;
import co.gov.fna.core.common.exception.FnaException;
import co.gov.fna.oreciprocas.adaptador.impl.OperacionAdaptador;
import co.gov.fna.oreciprocas.adaptador.impl.TransaccionAdaptador;
import co.gov.fna.oreciprocas.adaptador.impl.TransaccionDetalleAdaptador;
import co.gov.fna.oreciprocas.constantes.OReciprocasConstantes;
import co.gov.fna.oreciprocas.dominio.dto.CargarTransaccionesDTO;
import co.gov.fna.oreciprocas.dominio.dto.ConsultarOperacionesRespuestaDTO;
import co.gov.fna.oreciprocas.dominio.dto.ConsultarOperacionesSolicitudDTO;
import co.gov.fna.oreciprocas.dominio.dto.DescargarTransaccionesDTO;
import co.gov.fna.oreciprocas.dominio.dto.EntidadInfoBasicaDTO;
import co.gov.fna.oreciprocas.dominio.dto.EstadoDTO;
import co.gov.fna.oreciprocas.dominio.dto.FiltrosConsultaRespuestaDTO;
import co.gov.fna.oreciprocas.dominio.dto.OperacionDTO;
import co.gov.fna.oreciprocas.dominio.dto.PeriodoDTO;
import co.gov.fna.oreciprocas.dominio.dto.TransaccionDTO;
import co.gov.fna.oreciprocas.dominio.dto.TransaccionDetalleDTO;
import co.gov.fna.oreciprocas.dominio.dto.TransaccionRowDTO;
import co.gov.fna.oreciprocas.dominio.enumeracion.Campo;
import co.gov.fna.oreciprocas.dominio.enumeracion.EstadoTransaccion;
import co.gov.fna.oreciprocas.dominio.enumeracion.Periodo;
import co.gov.fna.oreciprocas.dominio.modelo.Comentario;
import co.gov.fna.oreciprocas.dominio.modelo.Comodin;
import co.gov.fna.oreciprocas.dominio.modelo.Operacion;
import co.gov.fna.oreciprocas.dominio.modelo.Transaccion;
import co.gov.fna.oreciprocas.exception.ErrorCargaLog;
import co.gov.fna.oreciprocas.repositorio.ComentarioRepositorio;
import co.gov.fna.oreciprocas.repositorio.TransaccionRepositorio;
import co.gov.fna.oreciprocas.servicio.TransaccionServicio;

/**
 * implementaci&oacute;n por defecto de {@link TransaccionServicio}
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Service
public class TransaccionServicioImpl implements TransaccionServicio {
	
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private ComentarioRepositorio comentarioRepositorio;

    @Autowired
    private TransaccionRepositorio transaccionRepositorio;

    @Autowired
    private TransaccionDetalleAdaptador transaccionDetalleAdaptador;

    @Autowired
    private TransaccionAdaptador transaccionAdaptador;

    @Autowired
    private OperacionAdaptador operacionAdaptador;

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(TransaccionServicioImpl.class);

    
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<TransaccionDTO> buscarTransaccionPorId(BigInteger id) {
        Optional<Transaccion> transaccion = transaccionRepositorio.findById(id);
        if (!transaccion.isPresent()) {
            Optional.empty();
        }
        return Optional.ofNullable(transaccionAdaptador.modeloAdto(transaccion.get()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConsultarOperacionesRespuestaDTO consultarTransacciones(
            ConsultarOperacionesSolicitudDTO solicitud, Authentication auth, Pageable pageable) {

        validarSolicitud(solicitud);

        if (StringUtils.EMPTY.equals(solicitud.getCodEstado())) {
            solicitud.setCodEstado(null);
        }
        if (StringUtils.EMPTY.equals(solicitud.getIdEntidad())) {
            solicitud.setIdEntidad(null);
        }
        if (StringUtils.EMPTY.equals(solicitud.getEntidadesInv())) {
            solicitud.setEntidadesInv(null);
        }

        String idCgnEntidad = auth != null ? extraerEmpresasAsociadas(auth) : null;

        if (idCgnEntidad == null) {
            idCgnEntidad = solicitud.getIdEntidad();
        }

        Page<TransaccionRowDTO> transacciones = Page.empty();

        transacciones = pageable != null ? transaccionRepositorio.findTransactionsBy(
                idCgnEntidad,
                solicitud.getAnnio(),
                Periodo.get(solicitud.getPeriodo()),
                EstadoTransaccion.get(solicitud.getCodEstado()),
                solicitud.getFechaConsolidacion(),
                solicitud.getIdTransaccion(),
                solicitud.getEntidadesInv(),
                pageable) : transaccionRepositorio.findTransactionsBy(
                        idCgnEntidad,
                        solicitud.getAnnio(),
                        Periodo.get(solicitud.getPeriodo()),
                        EstadoTransaccion.get(solicitud.getCodEstado()),
                        solicitud.getFechaConsolidacion(),
                        solicitud.getIdTransaccion(),
                        solicitud.getEntidadesInv(),
                        Pageable.unpaged());

        return ConsultarOperacionesRespuestaDTO.builder()
                .mensaje("Consulta transacciones OK").transacciones(transacciones).build();
    }

    /**
     * {@inheritDoc}
     */
    public Optional<TransaccionDTO> modificar(TransaccionDTO dto) {
        Optional<Transaccion> optModelo = transaccionRepositorio.findById(dto.getId());
        if (!optModelo.isPresent()) {
            return Optional.empty();
        }
        Transaccion modelo = optModelo.get();
        modelo.setPeriodo(dto.getPeriodo());
        modelo.setFechaConsolidado(dto.getFechaConsolidado());
        modelo.setIdCgnEntidadReciproca(dto.getIdCgnEntidadReciproca());
        modelo.setNombreEntidadReciproca(dto.getNombreEntidadReciproca());
        modelo.setEntidadesInvolucradas(dto.getEntidadesInvolucradas());
        modelo.setContactoContador(dto.getContactoContador());
        modelo.setEstado(dto.getEstado());
        modelo.setFechaCague(dto.getFechaCague());

        transaccionRepositorio.save(modelo);
        return Optional.of(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Date> obtenerFechasConsolidados() {
        return transaccionRepositorio.transactionsConsolidateDates();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TransaccionDetalleDTO getDetalleTransaccion(BigInteger idTransaccion) {

        Optional<Transaccion> transaccion = transaccionRepositorio.findById(idTransaccion);

        if (!transaccion.isPresent()) {
            lanzarExcepcionTransaccionNoEncontrada();
        }

        TransaccionDetalleDTO detalleTransaccion = transaccionDetalleAdaptador.modeloAdto(transaccion.get());

        Collection<OperacionDTO> operacionesCGN = new ArrayList<>();
        Collection<OperacionDTO> operacionesFNA = new ArrayList<>();
        Collection<OperacionDTO> operacionesOtros = new ArrayList<>();

        Collection<OperacionDTO> operaciones
                = operacionAdaptador.modeloAdto(transaccion.get().getOperaciones());

        Collection<EntidadInfoBasicaDTO> entidadesInvolucradas
                = extraerEntidadesInvolucradas(operaciones);
        detalleTransaccion.setEntidadesInvolucradas(entidadesInvolucradas);

        operaciones.forEach(op -> {
            switch (op.getIdEntidad()) {
                case "920300000":
                    operacionesCGN.add(op);
                    break;
                case "41300000":
                    operacionesFNA.add(op);
                    break;
                default:
                    operacionesOtros.add(op);
                    break;
            }
        });

        detalleTransaccion.setOperacionesCGN(operacionesCGN);
        detalleTransaccion.setOperacionesFNA(operacionesFNA);
        detalleTransaccion.setOperacionesOtros(operacionesOtros);

        return detalleTransaccion;
    }
    
    /**
	  * {@inheritDoc}
	  */
	@Override
	public FiltrosConsultaRespuestaDTO obtenerFiltrosConsulta() {
		
		Calendar calendar = Calendar.getInstance();
		Collection<String> setAnnios = new HashSet<>();
		List<String> annios = new ArrayList<>();
		Collection<String> strFechas = new ArrayList<>();
		
		strFechas.add("");
		Collection<Date> fechas = obtenerFechasConsolidados();
		
		
		fechas.forEach(fecha -> {
			strFechas.add(convertir(fecha));
			
			calendar.setTime(fecha);
			setAnnios.add(String.valueOf(calendar.get(Calendar.YEAR)));
		});
		
		
		annios.add("");
		annios.addAll(setAnnios);
		Collections.sort(annios);
		
		
		return FiltrosConsultaRespuestaDTO.builder()
				.periodos(obtenerPeridos())
				.annios(annios)
				.fechasConsolidacion(strFechas)
				.estados(obtenerEstados())
				.build();
	}

    /**
     * Extrae las entidades involucradas con la transacci&oacute;n
     *
     * @param operaciones
     * @return
     */
    private Collection<EntidadInfoBasicaDTO> extraerEntidadesInvolucradas(Collection<OperacionDTO> operaciones) {
        Set<EntidadInfoBasicaDTO> entidades = new HashSet<>();
        EntidadInfoBasicaDTO fna = EntidadInfoBasicaDTO.builder()
                .id(OReciprocasConstantes.ID_FNA)
                .nombre("Fondo Nacional del Ahorro")
                .build();

        entidades.add(fna);

        OperacionDTO operacion = operaciones.stream().filter(
                op
                -> !OReciprocasConstantes.ID_FNA.equals(op.getIdEntidad())
                && !OReciprocasConstantes.ID_CGN.equals(op.getIdEntidad())
        ).findFirst().orElse(null);

        EntidadInfoBasicaDTO entidadExt = EntidadInfoBasicaDTO.builder()
                .id(operacion.getIdEntidad())
                .nombre(operacion.getNombreEntidad())
                .build();
        entidades.add(entidadExt);

        return entidades;
    }

    /**
     * Retorna el id de la CGN asociado al usuario. Retorna null para los
     * usuarios de la CGN y FNA.
     *
     * @param auth
     * @return
     */
    private String extraerEmpresasAsociadas(Authentication auth) {

        Collection<String> rolesUsuario = new ArrayList<>();
        auth.getAuthorities().forEach(
                authority -> rolesUsuario.add(authority.getAuthority().replaceFirst("ROLE_", "")));

        //Si el usuario es de la CGN o FNA puede ver todas las transacciones.
        boolean noEntidad = rolesUsuario.contains(OReciprocasConstantes.ROL_CGN)
                || rolesUsuario.contains(OReciprocasConstantes.ROL_FNA);

        if (noEntidad) {
            return null;
        }
        return auth.getName();
    }

    /**
     * @param solicitud
     */
    private void validarSolicitud(ConsultarOperacionesSolicitudDTO solicitud) {
        boolean error = false;
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date());
        errorDetail.setCode("CAMPOS_REQUERIDOS");
        errorDetail.setMessage("Se requiere al menos uno de los campos (periodo, año, fecha consolidación, estado)"
                + " para realizar la búsqueda");

        error = solicitud == null || (solicitud.getAnnio() == null
                && StringUtils.isEmpty(solicitud.getCodEstado())
                && solicitud.getPeriodo() == null
                && solicitud.getFechaConsolidacion() == null);

        if (error) {
            throw new FnaException(errorDetail, HttpStatus.BAD_REQUEST);
        }
        errorDetail = null;
    }

    /**
     * Lanza excepci&oacute;n para transacciones no encontradas.
     */
    private void lanzarExcepcionTransaccionNoEncontrada() {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setCode("TRANSACCION_NO_ENCONTRADA");
        errorDetail.setMessage("Transacción no encontrada.");
        errorDetail.setTimestamp(new Date());
        throw new FnaException(errorDetail, HttpStatus.NOT_FOUND);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CargarTransaccionesDTO cargarTransacciones(CargarTransaccionesDTO cargarTransaccionesDTO) {
        try {
            List data = getDataFromExcel(cargarTransaccionesDTO);
            List<ErrorCargaLog> erroresEnCarga = new ArrayList<>();
            List<Transaccion> transacciones = mapDataToTransactions(data, erroresEnCarga);
            saveTransactions(transacciones, erroresEnCarga, cargarTransaccionesDTO);
            cargarTransaccionesDTO.setMensaje("Cargar transacciones OK");
        } catch (Exception e) {
            logger.error("Ocurrio un error con la estructura del archivo de cargue de transacciones");
        }
        return cargarTransaccionesDTO;
    }

    /**
     * Mapea la lista de datos que resultan de leer el excel, dentro de
     * transacciones con sus correpondientes operaciones
     *
     * @param data la lista de datos (diferentes tipos) para realizar mapeo
     * @return la lista de transacciones final
     */
    private List<Transaccion> mapDataToTransactions(List data, List<ErrorCargaLog> erroresEnCarga) {
        List<Transaccion> ans = new ArrayList<>();
        evaluarNombreEntidad(data.get(4), erroresEnCarga, 1);
        evaluarTituloArchivo(data.get(27), erroresEnCarga, 3);
        Periodo periodo = evaluarPeriodo(data.get(39), erroresEnCarga, 4);
        Date fechaConsolidado = evaluarFechaConsolidado(data.get(51), erroresEnCarga, 5);
        Date fechaCargue = new Date();
        int i = 154;
        int fila = 14;
        int registro = 0;
        while (i < data.size() && (!(data.get(i) instanceof String && data.get(i).equals("END_FILE")))) {
            Transaccion transaccion = new Transaccion();
            // Add transaction information
            transaccion.setPeriodo(periodo);
            transaccion.setFechaConsolidado(fechaConsolidado);
            transaccion.setId(evaluarIdTransaccion(data.get(i), erroresEnCarga, fila, registro));
            transaccion.setEntidadesInvolucradas(evaluarEntidadesInvolucradas(data.get(i + 1), erroresEnCarga, fila, registro));
            transaccion.setContactoContador(evaluarContactoContador(data.get(i + 11), erroresEnCarga, fila, registro));
            transaccion.setEstado(EstadoTransaccion.ABIERTO);
            transaccion.setFila(fila);
            transaccion.setFechaCague(fechaCargue);
            transaccion.setOperaciones(new ArrayList<>());

            //Add first operation corresponding to contaduria
            Operacion operacionContaduria = new Operacion();
            operacionContaduria.setIdCgn("920300000");
            operacionContaduria.setNombreEntidad("U.A.E. Contaduría General de la Nación");
            operacionContaduria.setLiquidez(evaluarLiquidez(data.get(i + 4), erroresEnCarga, fila, registro));
            operacionContaduria.setCodigoCuenta(evaluarCodigoCuenta(data.get(i + 5), erroresEnCarga, fila, registro));
            operacionContaduria.setDescripcionCuenta(evaluarDescripcionCuenta(data.get(i + 6), erroresEnCarga, fila, registro));
            operacionContaduria.setValorReportado(evaluarValorReportado(data.get(i + 7), erroresEnCarga, fila, registro));
            operacionContaduria.setPartidaConciliatoria(evaluarPartidaConsiliatoria(data.get(i + 8), erroresEnCarga, fila, registro));
            operacionContaduria.setOrigenDiferencia(evaluarOrigenDiferencia(data.get(i + 9), erroresEnCarga, fila, registro));
            operacionContaduria.setIdTransaccion(transaccion);
            transaccion.getOperaciones().add(operacionContaduria);

            i += 38;
            fila += 3;

            while (!(data.get(i) instanceof String && (data.get(i)).equals("END_TRANSACTION"))) /*|| (data.get(i) instanceof String) && (data.get(i).equals("NO_TIENE")))*/ {
                // Add the next operations
                Operacion operacion = new Operacion();
                operacion.setIdCgn(evaluarCodigoEntidad(data.get(i), erroresEnCarga, fila, registro));
                operacion.setNombreEntidad(evaluarEntidad(data.get(i + 1), erroresEnCarga, fila, registro));
                operacion.setLiquidez(evaluarLiquidez(data.get(i + 2), erroresEnCarga, fila, registro));
                operacion.setCodigoCuenta(evaluarCodigoCuenta(data.get(i + 3), erroresEnCarga, fila, registro));
                operacion.setDescripcionCuenta(evaluarDescripcionCuenta(data.get(i + 4), erroresEnCarga, fila, registro));
                operacion.setValorReportado(evaluarValorReportado(data.get(i + 5), erroresEnCarga, fila, registro));
                operacion.setPartidaConciliatoria(evaluarPartidaConsiliatoria(data.get(i + 6), erroresEnCarga, fila, registro));
                operacion.setOrigenDiferencia(evaluarOrigenDiferencia(data.get(i + 7), erroresEnCarga, fila, registro));
                operacion.setAnalistaCategoria(evaluaranalistaCategoria(data.get(i + 8), erroresEnCarga, fila, registro));
                operacion.setIdTransaccion(transaccion);
                // Add reciproca information
                if (transaccion.getIdCgnEntidadReciproca() == null && operacion.getIdCgn() != null && !(operacion.getIdCgn()).equals("41300000")) {
                    transaccion.setIdCgnEntidadReciproca(operacion.getIdCgn());
                    transaccion.setNombreEntidadReciproca(operacion.getNombreEntidad());
                }
                transaccion.getOperaciones().add(operacion);
                i += 12;
                fila++;
            }

            i++;
            fila++;
            ans.add(transaccion);
            registro++;
        }
        return ans;
    }

    /**
     * Obtiene la data relevante del documento excel que representa las
     * transacciones y la informacion asociada a esta
     *
     * @return una lista con todas las cadenas leidas del archivo excel,
     * ignorando casillas mezcladas, en blanco, con espacios vacios, etc.
     * @throws Exception
     */
    private List getDataFromExcel(CargarTransaccionesDTO cargarTransaccionesDTO) {
        List data = new ArrayList<>();
        try {
            InputStream is = new ByteArrayInputStream(cargarTransaccionesDTO.getExcelFile());
            XSSFWorkbook myWorkBook = new XSSFWorkbook(is);
            XSSFSheet mySheet = myWorkBook.getSheetAt(0);
            Iterator<Row> rowIterator = mySheet.iterator();
            List<CellRangeAddress> celdasMezcladas = mySheet.getMergedRegions();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (cell.getColumnIndex() < 12) {
                        switch (cell.getCellType()) {
                            case STRING:
                                if (!cell.getStringCellValue().trim().equals("")) {
                                    data.add(cell.getStringCellValue());
                                }
                                break;
                            case NUMERIC:
                                data.add(cell.getNumericCellValue());
                                break;
                            case BLANK:
                                data.add("NO_TIENE");
                                if (cell.getColumnIndex() == 11 && !celdasMezcladas.stream().anyMatch(a -> a.isInRange(cell))) {
                                    for (int i = 0; i < 10; i++) {
                                        data.remove(data.size() - 1);
                                    }
                                    data.add("END_TRANSACTION");
                                }
                                break;
                        }
                    }
                }
            }
            while (!data.get(data.size() - 1).equals("END_TRANSACTION")) {
                data.remove(data.size() - 1);
            }
            data.add("END_FILE");
        } catch (IOException ex) {
            ErrorDetail errorDetail = new ErrorDetail();
            errorDetail.setTimestamp(new Date());
            errorDetail.setCode("I/O_EXCEPTION");
            errorDetail.setMessage("Se presento un problema con la manipulacion del excel con las transacciones \n" + ex.getMessage());
            throw new FnaException(errorDetail, HttpStatus.BAD_REQUEST);
        }
        return data;
    }

    /**
     * Obtener el periodo segun la fecha que proviene del excel
     *
     * @param string la fecha que viene en una celda del excel
     * @return un periodo que corresponde al cuatrimestre en que se encuentre la
     * fecha
     */
    private Periodo getPeriodo(String periodo) throws Exception {
        return Periodo.getByMonth(Integer.parseInt(periodo.split("-")[1].trim()));
    }

    /**
     * Parsear la fecha que viene en la celda del excel a un formato fecha
     *
     * @param string la fecha de la celda (lenguaje natural)
     * @return una fecha que representa de manera tipada el valor de la celda
     */
    private Date getFechaConsolidado(String fechaString) throws Exception {
        Date date = null;
        try {
            String parsedFechaString = fechaString.replace("Fecha de Consolidación:", "").replace("PM", "").trim().replace("de", ",");
            String parsedFechaStringToFormat = parsedFechaString.substring(parsedFechaString.indexOf(" ") + 1);
            DateFormat format = new SimpleDateFormat("dd , MMMM , yyyy HH:mm:ss", new Locale("es", "ES"));
            date = format.parse(parsedFechaStringToFormat);
        } catch (ParseException ex) {
            Logger.getLogger(TransaccionServicioImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return date;
    }

    /**
     * Tranformar un Double a String compatible para crear un BigInteger
     *
     * @param numDouble, el objeto tipo Double que se transformara
     * @return la String lista con formato integer
     */
    private String convertDoubleToString(Object numDouble) {
        return new BigDecimal((double) numDouble).toPlainString();
    }

    /**
     * Transformar un Double a Integer
     *
     * @param numDouble, el objeto tipo Double que se transformara
     * @return el Integer que representa el Double ingresado
     */
    private Integer convertDoubleToInteger(Object numDouble) throws Exception {
        double numd = (double) numDouble;
        int numInt = (int) numd;
        return numInt;
    }

    /**
     * Evalua si existen transacciones repetidas y luego si no existe errores,
     * pasa a guardar, de lo contrario a generar el archivo de error
     *
     * @param transacciones las transaccione que seran evaluadas y
     * posteriormente guardadas o no
     */
    private void saveTransactions(List<Transaccion> transacciones, List<ErrorCargaLog> erroresEnCarga, CargarTransaccionesDTO cargarTransaccionesDTO) {
        for (int i = 0; i < transacciones.size(); i++) {
            Transaccion transaccion = transacciones.get(i);
            List<Transaccion> temp = new ArrayList<>(transacciones);
            List<BigInteger> idTransacciones = transacciones.stream().map(t -> t.getId()).collect(Collectors.toList());
            temp.remove(i);
            idTransacciones.remove(i);
            int indexDuplicate = idTransacciones.indexOf(transaccion.getId());
            while (indexDuplicate != -1) {
                erroresEnCarga.add(new ErrorCargaLog(transaccion.getFila(), i, Campo.TRANSACCION_ID.getNombre(), ErrorCargaLog.YA_EXISTE_ID_TRANSACCION_EXCEL + temp.get(indexDuplicate).getFila()));
                temp.remove(indexDuplicate);
                idTransacciones.remove(indexDuplicate);
                indexDuplicate = idTransacciones.indexOf(transaccion.getId());
            }
        }

        if (erroresEnCarga.isEmpty()) {
            saveAll(cargarTransaccionesDTO, transacciones);
        } else {
            byte[] errorFile = createErrorFile(transacciones, erroresEnCarga);
            cargarTransaccionesDTO.setErrorFile(errorFile);
        }
    }

    /**
     * Redondea un double a 2 decimales
     *
     * @param aDouble, el double que se desea aproximar
     * @return el double con aproximacion de 2 decimales
     */
    private String redondear(Double aDouble) {
        DecimalFormat df2 = new DecimalFormat(".##");
        return df2.format(aDouble);
    }

    private void evaluarNombreEntidad(Object nombreEntidad, List<ErrorCargaLog> erroresEnCarga, int fila) {
        if (nombreEntidad instanceof String) {
            String nombreEntidadString = (String) nombreEntidad;
            if (nombreEntidadString.equals("NO_TIENE")) {
                erroresEnCarga.add(new ErrorCargaLog(fila, null, Campo.NOMBRE_ENTIDAD.getNombre(), ErrorCargaLog.NO_CAMPO + Campo.NOMBRE_ENTIDAD.getTipo()));
            } else if (nombreEntidadString.length() > 250) {
                erroresEnCarga.add(new ErrorCargaLog(fila, null, Campo.NOMBRE_ENTIDAD.getNombre(), ErrorCargaLog.EXCEDE_CANTIDAD + Campo.NOMBRE_ENTIDAD.getTipo()));
            } else if (!nombreEntidadString.equals("CONTADURIA GENERAL DE LA NACIÓN")) {
                erroresEnCarga.add(new ErrorCargaLog(fila, null, Campo.NOMBRE_ENTIDAD.getNombre(), ErrorCargaLog.VALOR_DIF));
            }
        } else {
            erroresEnCarga.add(new ErrorCargaLog(fila, null, Campo.NOMBRE_ENTIDAD.getNombre(), ErrorCargaLog.DATO_ERRADO + Campo.NOMBRE_ENTIDAD.getTipo()));
        }
    }

    private void evaluarTituloArchivo(Object tituloArchivo, List<ErrorCargaLog> erroresEnCarga, int fila) {
        if (tituloArchivo instanceof String) {
            String tituloArchivoString = (String) tituloArchivo;
            if (tituloArchivoString.equals("NO_TIENE")) {
                erroresEnCarga.add(new ErrorCargaLog(fila, null, Campo.TITULO_ARCHIVO.getNombre(), ErrorCargaLog.NO_CAMPO + Campo.TITULO_ARCHIVO.getTipo()));
            } else if (tituloArchivoString.length() > 100) {
                erroresEnCarga.add(new ErrorCargaLog(fila, null, Campo.TITULO_ARCHIVO.getNombre(), ErrorCargaLog.EXCEDE_CANTIDAD + Campo.TITULO_ARCHIVO.getTipo()));
            } else if (!tituloArchivoString.equals("ENTIDADES QUE REGISTRAN PARTIDAS CONCILIATORIAS POR ENTIDAD POR VALOR")) {
                erroresEnCarga.add(new ErrorCargaLog(fila, null, Campo.TITULO_ARCHIVO.getNombre(), ErrorCargaLog.VALOR_DIF));
            }
        } else {
            erroresEnCarga.add(new ErrorCargaLog(fila, null, Campo.TITULO_ARCHIVO.getNombre(), ErrorCargaLog.DATO_ERRADO + Campo.TITULO_ARCHIVO.getTipo()));
        }
    }

    private Periodo evaluarPeriodo(Object periodo, List<ErrorCargaLog> erroresEnCarga, int fila) {
        Periodo ans = null;
        if (periodo instanceof String) {
            String periodoString = (String) periodo;
            if (periodoString.equals("NO_TIENE")) {
                erroresEnCarga.add(new ErrorCargaLog(fila, null, Campo.TRANSACCION_PERIODO.getNombre(), ErrorCargaLog.NO_CAMPO + Campo.TRANSACCION_PERIODO.getTipo()));
            } else {
                try {
                    ans = getPeriodo(periodoString);
                } catch (Exception ex) {
                    erroresEnCarga.add(new ErrorCargaLog(fila, null, Campo.TRANSACCION_PERIODO.getNombre(), ErrorCargaLog.DATO_ERRADO + Campo.TRANSACCION_PERIODO.getTipo()));
                }
            }
        } else {
            erroresEnCarga.add(new ErrorCargaLog(fila, null, Campo.TRANSACCION_PERIODO.getNombre(), ErrorCargaLog.DATO_ERRADO + Campo.TRANSACCION_PERIODO.getTipo()));
        }
        return ans;
    }

    private Date evaluarFechaConsolidado(Object fechaConsolidado, List<ErrorCargaLog> erroresEnCarga, int fila) {
        Date ans = null;
        if (fechaConsolidado instanceof String) {
            String fechaConsolidadoString = (String) fechaConsolidado;
            if (fechaConsolidadoString.equals("NO_TIENE")) {
                erroresEnCarga.add(new ErrorCargaLog(fila, null, Campo.TRANSACCION_FECHA_CONSOLIDACION.getNombre(), ErrorCargaLog.NO_CAMPO + Campo.TRANSACCION_FECHA_CONSOLIDACION.getTipo()));
            } else {
                try {
                    ans = getFechaConsolidado(fechaConsolidadoString);
                    if (ans == null) {
                        erroresEnCarga.add(new ErrorCargaLog(fila, null, Campo.TRANSACCION_FECHA_CONSOLIDACION.getNombre(), ErrorCargaLog.DATO_ERRADO + Campo.TRANSACCION_FECHA_CONSOLIDACION.getTipo()));

                    } else if (transaccionRepositorio.existsByFechaConsolidado(ans)) {
                        erroresEnCarga.add(new ErrorCargaLog(fila, null, Campo.TRANSACCION_FECHA_CONSOLIDACION.getNombre(), ErrorCargaLog.YA_EXISTE_FECHA_CONSOLIDADO));
                    }
                } catch (Exception ex) {
                    erroresEnCarga.add(new ErrorCargaLog(fila, null, Campo.TRANSACCION_FECHA_CONSOLIDACION.getNombre(), ErrorCargaLog.DATO_ERRADO + Campo.TRANSACCION_FECHA_CONSOLIDACION.getTipo()));
                }
            }

        } else {
            erroresEnCarga.add(new ErrorCargaLog(fila, null, Campo.TRANSACCION_FECHA_CONSOLIDACION.getNombre(), ErrorCargaLog.DATO_ERRADO + Campo.TRANSACCION_FECHA_CONSOLIDACION.getTipo()));
        }
        return ans;
    }

    private BigInteger evaluarIdTransaccion(Object idTransaccion, List<ErrorCargaLog> erroresEnCarga, int fila, int registro) {
        BigInteger ans = null;
        if (idTransaccion instanceof Double) {
            try {
                String len = new BigDecimal((double) idTransaccion).toPlainString();
                if (len.length() > 15) {
                    erroresEnCarga.add(new ErrorCargaLog(fila, registro, Campo.TRANSACCION_ID.getNombre(), ErrorCargaLog.EXCEDE_CANTIDAD + Campo.TRANSACCION_ID.getTipo()));
                } else {
                    String idTransaccionString = convertDoubleToString(idTransaccion);
                    ans = new BigInteger(idTransaccionString);
                    if (transaccionRepositorio.existsById(ans)) {
                        erroresEnCarga.add(new ErrorCargaLog(fila, registro, Campo.TRANSACCION_ID.getNombre(), ErrorCargaLog.YA_EXISTE_ID_TRANSACCION_DB));
                    }
                }
            } catch (Exception ex) {
                erroresEnCarga.add(new ErrorCargaLog(fila, registro, Campo.TRANSACCION_ID.getNombre(), ErrorCargaLog.DATO_ERRADO + Campo.TRANSACCION_ID.getTipo()));
            }

        } else if (idTransaccion instanceof String && ((String) idTransaccion).equals("NO_TIENE")) {
            erroresEnCarga.add(new ErrorCargaLog(fila, registro, Campo.TRANSACCION_ID.getNombre(), ErrorCargaLog.NO_CAMPO + Campo.TRANSACCION_ID.getTipo()));
        } else {
            erroresEnCarga.add(new ErrorCargaLog(fila, registro, Campo.TRANSACCION_ID.getNombre(), ErrorCargaLog.DATO_ERRADO + Campo.TRANSACCION_ID.getTipo()));
        }
        return ans;
    }

    private String evaluarEntidadesInvolucradas(Object entidadesInvolucradas, List<ErrorCargaLog> erroresEnCarga, int fila, int registro) {
        String ans = null;
        if (entidadesInvolucradas instanceof String) {
            String entidadesInvolucradasString = (String) entidadesInvolucradas;
            if (entidadesInvolucradasString.length() > 50) {
                erroresEnCarga.add(new ErrorCargaLog(fila, registro, Campo.TRANSACCION_ENTIDADES_INVOLUCRADAS.getNombre(), ErrorCargaLog.EXCEDE_CANTIDAD + Campo.TRANSACCION_ENTIDADES_INVOLUCRADAS.getTipo()));
            } else if (!entidadesInvolucradasString.equals("NO_TIENE")) {
                ans = entidadesInvolucradasString;
            }
        } else {
            erroresEnCarga.add(new ErrorCargaLog(fila, registro, Campo.TRANSACCION_ENTIDADES_INVOLUCRADAS.getNombre(), ErrorCargaLog.DATO_ERRADO + Campo.TRANSACCION_ENTIDADES_INVOLUCRADAS.getTipo()));
        }
        return ans;
    }

    private String evaluarContactoContador(Object contacto, List<ErrorCargaLog> erroresEnCarga, int fila, int registro) {
        String ans = null;
        if (contacto instanceof String) {
            String contactoString = (String) contacto;
            if (contactoString.length() > 500) {
                erroresEnCarga.add(new ErrorCargaLog(fila, registro, Campo.TRANSACCION_CONTACTO_CONTADOR.getNombre(), ErrorCargaLog.EXCEDE_CANTIDAD + Campo.TRANSACCION_CONTACTO_CONTADOR.getTipo()));
            } else if (!contactoString.equals("NO_TIENE")) {
                ans = contactoString;
            }
        } else {
            erroresEnCarga.add(new ErrorCargaLog(fila, registro, Campo.TRANSACCION_CONTACTO_CONTADOR.getNombre(), ErrorCargaLog.DATO_ERRADO + Campo.TRANSACCION_CONTACTO_CONTADOR.getTipo()));
        }
        return ans;
    }

    private Integer evaluarLiquidez(Object liquidez, List<ErrorCargaLog> erroresEnCarga, int fila, int registro) {
        Integer ans = null;
        if (liquidez instanceof Double) {
            try {
                ans = convertDoubleToInteger(liquidez);
                if (ans.toString().length() > 2) {
                    erroresEnCarga.add(new ErrorCargaLog(fila, registro, Campo.OPERACION_LIQUIDEZ.getNombre(), ErrorCargaLog.EXCEDE_CANTIDAD + Campo.OPERACION_LIQUIDEZ.getTipo()));
                }
            } catch (Exception ex) {
                erroresEnCarga.add(new ErrorCargaLog(fila, registro, Campo.OPERACION_LIQUIDEZ.getNombre(), ErrorCargaLog.DATO_ERRADO + Campo.OPERACION_LIQUIDEZ.getTipo()));
            }
        } else if (liquidez instanceof String && !((String) liquidez).equals("NO_TIENE")) {
            erroresEnCarga.add(new ErrorCargaLog(fila, registro, Campo.OPERACION_LIQUIDEZ.getNombre(), ErrorCargaLog.DATO_ERRADO + Campo.OPERACION_LIQUIDEZ.getTipo()));
        }
        return ans;
    }

    private String evaluarCodigoCuenta(Object codigoCuenta, List<ErrorCargaLog> erroresEnCarga, int fila, int registro) {
        String ans = null;
        if (codigoCuenta instanceof String) {
            String codigoCuentaString = (String) codigoCuenta;
            if (codigoCuentaString.length() > 30) {
                erroresEnCarga.add(new ErrorCargaLog(fila, registro, Campo.OPERACION_CODIGO_CUENTA.getNombre(), ErrorCargaLog.EXCEDE_CANTIDAD + Campo.OPERACION_CODIGO_CUENTA.getTipo()));
            } else if (!codigoCuentaString.equals("NO_TIENE")) {
                ans = codigoCuentaString;
            }
        } else {
            erroresEnCarga.add(new ErrorCargaLog(fila, registro, Campo.OPERACION_CODIGO_CUENTA.getNombre(), ErrorCargaLog.DATO_ERRADO + Campo.OPERACION_CODIGO_CUENTA.getTipo()));
        }
        return ans;

    }

    private String evaluarDescripcionCuenta(Object descripcionCuenta, List<ErrorCargaLog> erroresEnCarga, int fila, int registro) {
        String ans = null;
        if (descripcionCuenta instanceof String) {
            String descripcionCuentaString = (String) descripcionCuenta;
            if (descripcionCuentaString.length() > 250) {
                erroresEnCarga.add(new ErrorCargaLog(fila, registro, Campo.OPERACION_DESCRIPCION_CUENTA.getNombre(), ErrorCargaLog.EXCEDE_CANTIDAD + Campo.OPERACION_DESCRIPCION_CUENTA.getTipo()));
            } else if (!descripcionCuentaString.equals("NO_TIENE")) {
                ans = descripcionCuentaString;
            }
        } else {
            erroresEnCarga.add(new ErrorCargaLog(fila, registro, Campo.OPERACION_DESCRIPCION_CUENTA.getNombre(), ErrorCargaLog.DATO_ERRADO + Campo.OPERACION_DESCRIPCION_CUENTA.getTipo()));
        }
        return ans;
    }

    private BigDecimal evaluarValorReportado(Object valorReportado, List<ErrorCargaLog> erroresEnCarga, int fila, int registro) {
        BigDecimal ans = null;
        if (valorReportado instanceof Double) {
            Double valorReportadoDouble = (Double) valorReportado;
            try {
                ans = new BigDecimal(redondear(valorReportadoDouble));
                if (ans.toString().length() > 28) {
                    erroresEnCarga.add(new ErrorCargaLog(fila, registro, Campo.OPERACION_VALOR_REPORTADO.getNombre(), ErrorCargaLog.EXCEDE_CANTIDAD + Campo.OPERACION_VALOR_REPORTADO.getTipo()));
                }
            } catch (Exception ex) {
                erroresEnCarga.add(new ErrorCargaLog(fila, registro, Campo.OPERACION_VALOR_REPORTADO.getNombre(), ErrorCargaLog.DATO_ERRADO + Campo.OPERACION_VALOR_REPORTADO.getTipo()));
            }

        } else if (valorReportado instanceof String && !((String) valorReportado).equals("NO_TIENE")) {
            erroresEnCarga.add(new ErrorCargaLog(fila, registro, Campo.OPERACION_VALOR_REPORTADO.getNombre(), ErrorCargaLog.DATO_ERRADO + Campo.OPERACION_VALOR_REPORTADO.getTipo()));
        }
        return ans;
    }

    private BigDecimal evaluarPartidaConsiliatoria(Object partidaConsiliatoria, List<ErrorCargaLog> erroresEnCarga, int fila, int registro) {
        BigDecimal ans = null;
        if (partidaConsiliatoria instanceof Double) {
            Double partidaConsiliatoriaDouble = (Double) partidaConsiliatoria;
            try {
                ans = new BigDecimal(redondear(partidaConsiliatoriaDouble));
                if (ans.toString().length() > 28) {
                    erroresEnCarga.add(new ErrorCargaLog(fila, registro, Campo.OPERACION_PARTIDA_CONCILIATORIA.getNombre(), ErrorCargaLog.EXCEDE_CANTIDAD + Campo.OPERACION_PARTIDA_CONCILIATORIA.getTipo()));
                }
            } catch (Exception ex) {
                erroresEnCarga.add(new ErrorCargaLog(fila, registro, Campo.OPERACION_PARTIDA_CONCILIATORIA.getNombre(), ErrorCargaLog.DATO_ERRADO + Campo.OPERACION_PARTIDA_CONCILIATORIA.getTipo()));
            }

        } else if (partidaConsiliatoria instanceof String && !((String) partidaConsiliatoria).equals("NO_TIENE")) {
            erroresEnCarga.add(new ErrorCargaLog(fila, registro, Campo.OPERACION_PARTIDA_CONCILIATORIA.getNombre(), ErrorCargaLog.DATO_ERRADO + Campo.OPERACION_PARTIDA_CONCILIATORIA.getTipo()));
        }
        return ans;
    }

    private String evaluarOrigenDiferencia(Object origen, List<ErrorCargaLog> erroresEnCarga, int fila, int registro) {
        String ans = null;
        if (origen instanceof String) {
            String origenString = (String) origen;
            if (origenString.length() > 150) {
                erroresEnCarga.add(new ErrorCargaLog(fila, registro, Campo.OPERACION_ORIGEN_DIFERENCIA.getNombre(), ErrorCargaLog.EXCEDE_CANTIDAD + Campo.OPERACION_ORIGEN_DIFERENCIA.getTipo()));
            } else if (!origenString.equals("NO_TIENE")) {
                ans = origenString;
            }
        } else {
            erroresEnCarga.add(new ErrorCargaLog(fila, registro, Campo.OPERACION_ORIGEN_DIFERENCIA.getNombre(), ErrorCargaLog.DATO_ERRADO + Campo.OPERACION_ORIGEN_DIFERENCIA.getTipo()));
        }
        return ans;
    }

    private String evaluaranalistaCategoria(Object analista, List<ErrorCargaLog> erroresEnCarga, int fila, int registro) {
        String ans = null;
        if (analista instanceof String) {
            String analistaString = (String) analista;
            if (analistaString.length() > 20) {
                erroresEnCarga.add(new ErrorCargaLog(fila, registro, Campo.OPERACION_ANALISTA.getNombre(), ErrorCargaLog.EXCEDE_CANTIDAD + Campo.OPERACION_ANALISTA.getTipo()));
            } else if (!analistaString.equals("NO_TIENE")) {
                ans = analistaString;
            }
        } else {
            erroresEnCarga.add(new ErrorCargaLog(fila, registro, Campo.OPERACION_ANALISTA.getNombre(), ErrorCargaLog.DATO_ERRADO + Campo.OPERACION_ANALISTA.getTipo()));
        }
        return ans;
    }

    private String evaluarEntidad(Object entidad, List<ErrorCargaLog> erroresEnCarga, int fila, int registro) {
        String ans = null;
        if (entidad instanceof String) {
            String entidadString = (String) entidad;
            if (entidadString.length() > 250) {
                erroresEnCarga.add(new ErrorCargaLog(fila, registro, Campo.OPERACION_NOMBRE_ENTIDAD.getNombre(), ErrorCargaLog.EXCEDE_CANTIDAD + Campo.OPERACION_NOMBRE_ENTIDAD.getTipo()));
            } else if (!entidadString.equals("NO_TIENE")) {
                ans = entidadString;
            }
        } else {
            erroresEnCarga.add(new ErrorCargaLog(fila, registro, Campo.OPERACION_NOMBRE_ENTIDAD.getNombre(), ErrorCargaLog.DATO_ERRADO + Campo.OPERACION_NOMBRE_ENTIDAD.getTipo()));
        }
        return ans;
    }

    private String evaluarCodigoEntidad(Object codigoCuenta, List<ErrorCargaLog> erroresEnCarga, int fila, int registro) {
        String ans = null;
        if (codigoCuenta instanceof Double) {
            try {
                ans = convertDoubleToString(codigoCuenta);
                if (ans.length() > 50) {
                    erroresEnCarga.add(new ErrorCargaLog(fila, registro, Campo.OPERACION_CODIGO_ENTIDAD.getNombre(), ErrorCargaLog.EXCEDE_CANTIDAD + Campo.OPERACION_CODIGO_ENTIDAD.getTipo()));
                }
            } catch (Exception ex) {
                erroresEnCarga.add(new ErrorCargaLog(fila, registro, Campo.OPERACION_CODIGO_ENTIDAD.getNombre(), ErrorCargaLog.DATO_ERRADO + Campo.OPERACION_CODIGO_ENTIDAD.getTipo()));
            }
        } else if (codigoCuenta instanceof String && !((String) codigoCuenta).equals("NO_TIENE")) {
            if (((String) codigoCuenta).length() > 50) {
                erroresEnCarga.add(new ErrorCargaLog(fila, registro, Campo.OPERACION_CODIGO_ENTIDAD.getNombre(), ErrorCargaLog.EXCEDE_CANTIDAD + Campo.OPERACION_CODIGO_ENTIDAD.getTipo()));
            } else {
                ans = (String) codigoCuenta;
            }
        } else if (codigoCuenta instanceof String && ((String) codigoCuenta).equals("NO_TIENE")) {
            ans = null;
        } else {
            erroresEnCarga.add(new ErrorCargaLog(fila, registro, Campo.OPERACION_CODIGO_ENTIDAD.getNombre(), ErrorCargaLog.DATO_ERRADO + Campo.OPERACION_CODIGO_ENTIDAD.getTipo()));
        }

        return ans;
    }

    /**
     * Creacion del archivo de error apartir de la lista de errores recogida
     *
     * @param transacciones, la lista de transacciones para hacer calculos de
     * totales
     * @param erroresEnCarga, la lista de errores recogidos para luego mapear en
     * lineas del archivo de error
     * @return el arreglo de bytes que representa el archivo creado
     */
    private byte[] createErrorFile(List<Transaccion> transacciones, List<ErrorCargaLog> erroresEnCarga) {

        List<String> lines = erroresEnCarga.stream().sorted((a, b) -> Integer.compare(a.getFila(), b.getFila()))
                .map(e -> e.getFila() + ", " + e.getNombreCampo() + ", " + e.getDescripcion() + "\r\n")
                .collect(Collectors.toList());

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            os.write(("Transacciones Leídas " + transacciones.size() + "\r\n").getBytes(Charset.forName("UTF-8")));
            Set registrosError = erroresEnCarga.stream().filter(t -> t.getNumTransaccion() != null).map(a -> a.getNumTransaccion()).collect(Collectors.toSet());
            os.write(("Transacciones con Error " + registrosError.size() + "\r\n").getBytes(Charset.forName("UTF-8")));
            os.write(("Transacciones sin Error " + (transacciones.size() - registrosError.size()) + "\r\n" + "\r\n").getBytes(Charset.forName("UTF-8")));

            lines.forEach(e -> {
                try {
                    os.write(e.getBytes(Charset.forName("UTF-8")));
                } catch (IOException ex) {
                    Logger.getLogger(TransaccionServicioImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        } catch (IOException ex) {
            ErrorDetail errorDetail = new ErrorDetail();
            errorDetail.setTimestamp(new Date());
            errorDetail.setCode("I/O_EXCEPTION");
            errorDetail.setMessage("Se presento un problema con la manipulacion del txt con el detalle de los errores en cargue \n" + ex.getMessage());
            throw new FnaException(errorDetail, HttpStatus.BAD_REQUEST);
        }
        return os.toByteArray();
    }

    /**
     * Lee los comodines (si existen en el excel y los agrega o asocia a las
     * transacciones)
     *
     * @param cargarTransaccionesDTO, el DTO para hacer la consulta y
     * responderla
     * @param transacciones, las transacciones a guardar
     */
    private void saveAll(CargarTransaccionesDTO cargarTransaccionesDTO, List<Transaccion> transacciones) {
        try {
            // Input stream to read file
            InputStream is = new ByteArrayInputStream(cargarTransaccionesDTO.getExcelFile());
            // Finds the workbook instance for XLSX file
            XSSFWorkbook myWorkBook = new XSSFWorkbook(is);
            // Return first sheet from the XLSX workbook
            XSSFSheet mySheet = myWorkBook.getSheetAt(0);
            for (Transaccion transaccion : transacciones) {
                Comodin comodinModelo = new Comodin();
                comodinModelo.setIdTransaccion(transaccion);
                for (int i = 13; i < 18; i++) {
                    if (mySheet.getRow(transaccion.getFila() - 1).getCell(i) != null) {
                        mySheet.getRow(transaccion.getFila() - 1).getCell(i).setCellType(CellType.STRING);
                    }
                }
                comodinModelo.setComodin1(mySheet.getRow(transaccion.getFila() - 1).getCell(13) != null ? (mySheet.getRow(transaccion.getFila() - 1).getCell(13).getStringCellValue().length() > 500
                        ? mySheet.getRow(transaccion.getFila() - 1).getCell(13).getStringCellValue().substring(0, 500) : mySheet.getRow(transaccion.getFila() - 1).getCell(13).getStringCellValue()) : null);

                comodinModelo.setComodin2(mySheet.getRow(transaccion.getFila() - 1).getCell(14) != null ? (mySheet.getRow(transaccion.getFila() - 1).getCell(14).getStringCellValue().length() > 500
                        ? mySheet.getRow(transaccion.getFila() - 1).getCell(14).getStringCellValue().substring(0, 500) : mySheet.getRow(transaccion.getFila() - 1).getCell(14).getStringCellValue()) : null);

                comodinModelo.setComodin3(mySheet.getRow(transaccion.getFila() - 1).getCell(15) != null ? (mySheet.getRow(transaccion.getFila() - 1).getCell(15).getStringCellValue().length() > 500
                        ? mySheet.getRow(transaccion.getFila() - 1).getCell(15).getStringCellValue().substring(0, 500) : mySheet.getRow(transaccion.getFila() - 1).getCell(15).getStringCellValue()) : null);

                comodinModelo.setComodin4(mySheet.getRow(transaccion.getFila() - 1).getCell(16) != null ? (mySheet.getRow(transaccion.getFila() - 1).getCell(16).getStringCellValue().length() > 500
                        ? mySheet.getRow(transaccion.getFila() - 1).getCell(16).getStringCellValue().substring(0, 500) : mySheet.getRow(transaccion.getFila() - 1).getCell(16).getStringCellValue()) : null);

                comodinModelo.setComodin5(mySheet.getRow(transaccion.getFila() - 1).getCell(17) != null ? (mySheet.getRow(transaccion.getFila() - 1).getCell(17).getStringCellValue().length() > 500
                        ? mySheet.getRow(transaccion.getFila() - 1).getCell(17).getStringCellValue().substring(0, 500) : mySheet.getRow(transaccion.getFila() - 1).getCell(17).getStringCellValue()) : null);

                transaccion.setComodin(comodinModelo);
            }
            actualizarObsletasGuardarNuevas(transacciones);
            cargarTransaccionesDTO.setTransaccionesCargadas(transacciones.size());
        } catch (IOException ex) {
            ErrorDetail errorDetail = new ErrorDetail();
            errorDetail.setTimestamp(new Date());
            errorDetail.setCode("I/O_EXCEPTION");
            errorDetail.setMessage("Se presento un problema con la manipulacion del excel con respecto a los comodines \n" + ex.getMessage());
            throw new FnaException(errorDetail, HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Hace que las transaccione que habian en db cambien de estado a obsoleto
     */
    @Transactional
    private void actualizarObsletasGuardarNuevas(List<Transaccion> transacciones) {
        List<Transaccion> oldTransacciones = Lists.newArrayList(transaccionRepositorio.findAll());
        oldTransacciones.forEach(t -> t.setEstado(EstadoTransaccion.OBSOLETO));
        transaccionRepositorio.saveAll(oldTransacciones);
        transaccionRepositorio.saveAll(transacciones);
    }


    /*
     * Metodo para descargar transacciones segun lo solicitado
     * @param solicitud, representa los filtros para escoger las transacciones a poner en el archivo excel
     * @return, DTO para wrappear el excel de respuesta a descargar
     */
    @Override
    public DescargarTransaccionesDTO descargarTransacciones(ConsultarOperacionesSolicitudDTO solicitud) {
        DescargarTransaccionesDTO descargarTransaccionesDTO = new DescargarTransaccionesDTO();
        List<BigInteger> idTransacciones = consultarTransacciones(solicitud, null, null).getTransacciones().stream().map(t -> t.getIdTransaccion()).collect(Collectors.toList());
        List<Transaccion> transacciones = Lists.newArrayList(transaccionRepositorio.findAllById(idTransacciones));
        Workbook workbook = createContentFile(transacciones);
        saveWorkbook(workbook, descargarTransaccionesDTO);
        return descargarTransaccionesDTO;
    }

    /**
     * Crear el contenido del archivo excel
     *
     * @param transacciones, las transacciones a poner en la creacion del
     * archivo
     * @return un workbook que contiene el excel
     */
    private Workbook createContentFile(List<Transaccion> transacciones) {
        Workbook workbook = new XSSFWorkbook();
        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("Salida");
        setColumnSize(sheet);
        insertLogoImage(workbook, sheet, createHelper);
        insertHeaderFile(workbook, sheet, transacciones);
        insertHeaderTable(workbook, sheet);
        insertContentTable(workbook, sheet, transacciones);
        return workbook;
    }

    /**
     * Escribir el workbook en un archivo para devolver
     *
     * @param workbook, contenedor del archivo generado para escibir
     * @param descargarTransaccionesDTO, elobjeto que contiene el archivo a
     * devolver
     */
    private void saveWorkbook(Workbook workbook, DescargarTransaccionesDTO descargarTransaccionesDTO) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            workbook.write(byteArrayOutputStream);
            descargarTransaccionesDTO.setFileTransacciones(byteArrayOutputStream.toByteArray());
        } catch (IOException ex) {
            ErrorDetail errorDetail = new ErrorDetail();
            errorDetail.setTimestamp(new Date());
            errorDetail.setCode("I/O_EXCEPTION");
            errorDetail.setMessage("Se presento un problema con la escritura del workbook en arreglo de bytes \n" + ex.getMessage());
            throw new FnaException(errorDetail, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtener la gestion que se le hizo a cada transaccion
     *
     * @param id, el id de la transaccion a consultarle los comentarios
     * (gestion)
     * @return una cadena a poner en el campo de gestion del excel generado
     */
    private String getGestionByIdTransaccion(BigInteger id) {
        Collection<Comentario> comentarios = comentarioRepositorio.findByIdTransaccion(id);
        Optional<String> comentariosCombinados = comentarios.stream()
        		.sorted((d1, d2) -> d1.getFecha().compareTo(d2.getFecha()))
        		.map(c -> c.getAutor() + ": " + c.getTexto()).reduce((c1, c2) -> c1 + "\r\n" + c2);
        return comentariosCombinados.isPresent() ? comentariosCombinados.get() : null;
    }

    /**
     * Cambiar el tamanho de las celdas del excel a generar
     *
     * @param sheet aplicar el tamanho a las celdas de la estructura que se esta
     * generando
     */
    private void setColumnSize(Sheet sheet) {
        Integer[] widthSizes = new Integer[]{16, 14, 14, 17, 9, 11, 38, 38, 17, 48, 20, 47, 47, 9, 9, 9, 9, 9};
        IntStream.range(0, widthSizes.length).forEach(i -> sheet.setColumnWidth(i, 256 * widthSizes[i]));
        sheet.setDefaultRowHeight((short) 256);
    }

    /**
     * Insertar el logo del fna al excel generado
     *
     * @param workbook
     * @param sheet
     * @param createHelper
     */
    private void insertLogoImage(Workbook workbook, Sheet sheet, CreationHelper createHelper) {
        InputStream stream = null;
        try {
            stream = this.getClass().getClassLoader().getResourceAsStream("images/fna.png");
            Drawing drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = createHelper.createClientAnchor();
            anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
            int pictureIndex = workbook.addPicture(IOUtils.toByteArray(stream), Workbook.PICTURE_TYPE_PNG);
            anchor.setCol1(1);
            anchor.setRow1(1);
            Picture pict = drawing.createPicture(anchor, pictureIndex);
            pict.resize();
        } catch (IOException ex) {
            ErrorDetail errorDetail = new ErrorDetail();
            errorDetail.setTimestamp(new Date());
            errorDetail.setCode("I/O_EXCEPTION");
            errorDetail.setMessage("Se presento un problema con la manipulacion de la imagen a insertar en el excel \n" + ex.getMessage());
            throw new FnaException(errorDetail, HttpStatus.BAD_REQUEST);
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException ex) {
                ErrorDetail errorDetail = new ErrorDetail();
                errorDetail.setTimestamp(new Date());
                errorDetail.setCode("I/O_EXCEPTION");
                errorDetail.setMessage("Se presento un problema con el cierre del canal a la imagen a insertar en el excel \n" + ex.getMessage());
                throw new FnaException(errorDetail, HttpStatus.BAD_REQUEST);
            }
        }
    }

    /**
     * Insertar el encabezado del archivo
     *
     * @param workbook
     * @param sheet
     */
    private void insertHeaderFile(Workbook workbook, Sheet sheet, List<Transaccion> transacciones) {
        IntStream.range(0, 11).forEach(i -> sheet.addMergedRegion(new CellRangeAddress(i, i, 4, 7)));
        sheet.addMergedRegion(new CellRangeAddress(11, 11, 0, 11));

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setFontName("Verdana");
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        insertCell(sheet, 0, 4, headerCellStyle, "FONDO NACIONAL DEL AHORRO");
        insertCell(sheet, 1, 4, headerCellStyle, "CARLOS LLERAS RESTREPO");

        Font headerFont2 = workbook.createFont();
        headerFont2.setBold(true);
        headerFont2.setFontHeightInPoints((short) 9);
        headerFont2.setFontName("Verdana");
        CellStyle headerCellStyle2 = workbook.createCellStyle();
        headerCellStyle2.setFont(headerFont2);
        headerCellStyle2.setAlignment(HorizontalAlignment.CENTER);
        headerCellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
        insertCell(sheet, 2, 4, headerCellStyle2, "GESTION SOBRE PARTIDAS CONCILIATORIAS POR ENTIDAD POR VALOR");

        Font headerFont3 = workbook.createFont();
        headerFont3.setFontName("Andale WT");
        headerFont3.setFontHeightInPoints((short) 10);
        headerFont3.setBold(false);
        CellStyle headerCellStyle3 = workbook.createCellStyle();
        headerCellStyle3.setFont(headerFont3);
        headerCellStyle3.setAlignment(HorizontalAlignment.CENTER);
        headerCellStyle3.setVerticalAlignment(VerticalAlignment.CENTER);

        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");

        if (transacciones.stream().anyMatch(t -> t.getEstado().equals(EstadoTransaccion.OBSOLETO))) {
            insertCell(sheet, 3, 4, headerCellStyle3, "Periodo: ");
            insertCell(sheet, 4, 4, headerCellStyle3, "Fecha de Consolidación: ");
        } else {
            insertCell(sheet, 3, 4, headerCellStyle3, "Periodo: " + transacciones.get(0).getPeriodo().getDescripcion());
            insertCell(sheet, 4, 4, headerCellStyle3, "Fecha de Consolidación: " + sdf.format(transacciones.get(0).getFechaConsolidado()));
        }
        insertCell(sheet, 5, 4, headerCellStyle3, "Fecha de Descargue del Archivo: " + sdf.format(new Date()));

        Font headerFont4 = workbook.createFont();
        headerFont4.setFontName("Tahoma");
        headerFont4.setFontHeightInPoints((short) 7);
        headerFont4.setBold(false);
        CellStyle headerCellStyle4 = workbook.createCellStyle();
        headerCellStyle4.setFont(headerFont4);
        headerCellStyle4.setAlignment(HorizontalAlignment.RIGHT);
        headerCellStyle4.setVerticalAlignment(VerticalAlignment.CENTER);
        insertCell(sheet, 8, 4, headerCellStyle4, "Cifras en pesos");

    }

    /**
     * Insertar una celda especifica
     *
     * @param sheet
     * @param row
     * @param column
     * @param style
     * @param value
     */
    private void insertCell(Sheet sheet, int row, int column, CellStyle style, String value) {
        sheet.createRow(row).createCell(column);
        sheet.getRow(row).getCell(column).setCellStyle(style);
        sheet.getRow(row).getCell(column).setCellValue(value);
    }

    /**
     * Insertar una celda especifica
     *
     * @param sheet
     * @param row
     * @param column
     * @param style
     * @param value
     */
    private void insertCell(Sheet sheet, int row, int column, CellStyle style, double value) {
        sheet.createRow(row).createCell(column);
        sheet.getRow(row).getCell(column).setCellStyle(style);
        sheet.getRow(row).getCell(column).setCellValue(value);
    }

    /**
     * Inserta el encabezado de la tabla que contiene las transacciones
     *
     * @param workbook
     * @param sheet
     */
    private void insertHeaderTable(Workbook workbook, Sheet sheet) {
        Font headerTableFont = workbook.createFont();
        headerTableFont.setFontName("Andale WT");
        headerTableFont.setFontHeightInPoints((short) 10);
        headerTableFont.setBold(true);
        CellStyle headerTableStyle = workbook.createCellStyle();
        headerTableStyle.setFont(headerTableFont);
        headerTableStyle.setAlignment(HorizontalAlignment.CENTER);
        headerTableStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFWorkbook hwb = new HSSFWorkbook();
        HSSFPalette palette = hwb.getCustomPalette();
        HSSFColor myColor = palette.findSimilarColor(227, 227, 252);
        headerTableStyle.setFillForegroundColor(myColor.getIndex());
        headerTableStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        applyBorderStyle(headerTableStyle);

        String[] tableColumns = new String[]{"Id Transacción", "Entidades Involucradas", "Código Entidad", "Entidad", "Liquidez",
            "Código Cuenta", "Cuenta", "Valor Reportado", "Partida Conciliatoria", "Origen Diferencia", "Analista Categoría",
            "Contacto del Contador de la Entidad Reciproca", "GESTION", null, null, null, null, null};

        Row tableHeader = sheet.createRow(12);

        for (int i = 0; i < tableColumns.length; i++) {
            tableHeader.createCell(i);
            if (tableColumns[i] != null) {
                sheet.getRow(12).getCell(i).setCellStyle(headerTableStyle);
            }
            sheet.getRow(12).getCell(i).setCellValue(tableColumns[i]);
        }
    }

    /**
     * Inserta el contenido grueso de las transcciones al excel
     *
     * @param workbook
     * @param sheet
     * @param transacciones
     */
    private void insertContentTable(Workbook workbook, Sheet sheet, List<Transaccion> transacciones) {
        Font contentFont = workbook.createFont();
        contentFont.setFontHeightInPoints((short) 10);
        contentFont.setFontName("Andale WT");
        Font headerTableFont = workbook.createFont();
        headerTableFont.setFontName("Andale WT");
        headerTableFont.setFontHeightInPoints((short) 10);
        headerTableFont.setBold(true);
        CellStyle headerTableStyle = workbook.createCellStyle();
        headerTableStyle.setFont(headerTableFont);
        headerTableStyle.setAlignment(HorizontalAlignment.CENTER);
        headerTableStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFWorkbook hwb = new HSSFWorkbook();
        HSSFPalette palette = hwb.getCustomPalette();
        HSSFColor myColor = palette.findSimilarColor(227, 227, 252);
        headerTableStyle.setFillForegroundColor(myColor.getIndex());
        headerTableStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        applyBorderStyle(headerTableStyle);

        CellStyle decimalsStyle = workbook.createCellStyle();
        decimalsStyle.setDataFormat(workbook.createDataFormat().getFormat("#,##0.0"));
        decimalsStyle.setAlignment(HorizontalAlignment.RIGHT);
        decimalsStyle.setVerticalAlignment(VerticalAlignment.TOP);
        decimalsStyle.setFont(contentFont);
        applyBorderStyle(decimalsStyle);

        CellStyle topRigthStyle = workbook.createCellStyle();
        topRigthStyle.setAlignment(HorizontalAlignment.RIGHT);
        topRigthStyle.setVerticalAlignment(VerticalAlignment.TOP);
        topRigthStyle.setFont(contentFont);
        applyBorderStyle(topRigthStyle);

        CellStyle topLeftStyle = workbook.createCellStyle();
        topLeftStyle.setAlignment(HorizontalAlignment.LEFT);
        topLeftStyle.setVerticalAlignment(VerticalAlignment.TOP);
        topLeftStyle.setFont(contentFont);
        topLeftStyle.setWrapText(true);
        applyBorderStyle(topLeftStyle);

        int rowStart = 13;
        int rowEnd = rowStart + transacciones.get(0).getOperaciones().size() + 1;
        for (int i = 0; i < transacciones.size(); i++) {
            sheet.addMergedRegion(applyBorderStyleMerge(rowStart, rowEnd, 0, 0, workbook, sheet));
            sheet.addMergedRegion(applyBorderStyleMerge(rowStart, rowEnd, 1, 1, workbook, sheet));
            sheet.addMergedRegion(applyBorderStyleMerge(rowStart, rowStart + 2, 2, 2, workbook, sheet));
            sheet.addMergedRegion(applyBorderStyleMerge(rowStart, rowStart + 2, 4, 4, workbook, sheet));
            sheet.addMergedRegion(applyBorderStyleMerge(rowStart, rowStart + 2, 5, 5, workbook, sheet));
            sheet.addMergedRegion(applyBorderStyleMerge(rowStart, rowStart + 2, 6, 6, workbook, sheet));
            sheet.addMergedRegion(applyBorderStyleMerge(rowStart, rowStart + 2, 7, 7, workbook, sheet));
            sheet.addMergedRegion(applyBorderStyleMerge(rowStart, rowStart + 2, 8, 8, workbook, sheet));
            sheet.addMergedRegion(applyBorderStyleMerge(rowStart, rowStart + 2, 9, 9, workbook, sheet));
            sheet.addMergedRegion(applyBorderStyleMerge(rowStart, rowEnd, 11, 11, workbook, sheet));
            sheet.addMergedRegion(applyBorderStyleMerge(rowStart, rowEnd, 12, 12, workbook, sheet));

            Row rowCGN = sheet.getRow(rowStart) != null ? sheet.getRow(rowStart) : sheet.createRow(rowStart);
            Transaccion transaccion = transacciones.get(i);
            Operacion operacionCGN = ((List<Operacion>) transacciones.get(i).getOperaciones()).get(0);
            for (int j = 0; j < 18; j++) {
                rowCGN.createCell(j);
            }
            sheet.getRow(rowStart).getCell(0).setCellValue(transaccion.getId().intValue());
            sheet.getRow(rowStart).getCell(0).setCellStyle(topRigthStyle);
            sheet.getRow(rowStart).getCell(1).setCellValue(transaccion.getEntidadesInvolucradas());
            sheet.getRow(rowStart).getCell(1).setCellStyle(topLeftStyle);
            sheet.getRow(rowStart).getCell(2).setCellValue(0);
            sheet.getRow(rowStart).getCell(2).setCellStyle(topRigthStyle);
            if (operacionCGN.getLiquidez() != null) {
                sheet.getRow(rowStart).getCell(4).setCellValue(operacionCGN.getLiquidez());
            }
            sheet.getRow(rowStart).getCell(4).setCellStyle(topRigthStyle);
            if (operacionCGN.getCodigoCuenta() != null) {
                sheet.getRow(rowStart).getCell(5).setCellValue(operacionCGN.getCodigoCuenta());
            }
            sheet.getRow(rowStart).getCell(5).setCellStyle(topLeftStyle);
            if (operacionCGN.getDescripcionCuenta() != null) {
                sheet.getRow(rowStart).getCell(6).setCellValue(operacionCGN.getDescripcionCuenta());
            }
            sheet.getRow(rowStart).getCell(6).setCellStyle(topLeftStyle);
            if (operacionCGN.getValorReportado() != null) {
                sheet.getRow(rowStart).getCell(7).setCellValue(operacionCGN.getValorReportado().doubleValue());
            }
            sheet.getRow(rowStart).getCell(7).setCellStyle(decimalsStyle);
            if (operacionCGN.getPartidaConciliatoria() != null) {
                sheet.getRow(rowStart).getCell(8).setCellValue(operacionCGN.getPartidaConciliatoria().doubleValue());
            }
            sheet.getRow(rowStart).getCell(8).setCellStyle(decimalsStyle);
            if (operacionCGN.getOrigenDiferencia() != null) {
                sheet.getRow(rowStart).getCell(9).setCellValue(operacionCGN.getOrigenDiferencia());
            }
            sheet.getRow(rowStart).getCell(9).setCellStyle(topLeftStyle);
            if (transaccion.getContactoContador() != null) {
                sheet.getRow(rowStart).getCell(11).setCellValue(transaccion.getContactoContador().replace("\n", "\r\n"));
            }
            sheet.getRow(rowStart).getCell(11).setCellStyle(topLeftStyle);
            sheet.getRow(rowStart).getCell(12).setCellValue(getGestionByIdTransaccion(transaccion.getId()));
            sheet.getRow(rowStart).getCell(12).setCellStyle(topLeftStyle);

            if (transaccion.getComodin().getComodin1() != null) {
                sheet.getRow(rowStart).getCell(13).setCellStyle(topLeftStyle);
                sheet.getRow(rowStart).getCell(13).setCellValue(transaccion.getComodin().getComodin1());
                if (sheet.getRow(12).getCell(13).getStringCellValue().equals("")) {
                    sheet.getRow(12).getCell(13).setCellStyle(headerTableStyle);
                    sheet.getRow(12).getCell(13).setCellValue("Comodin1");
                }
            }

            if (transaccion.getComodin().getComodin2() != null) {
                sheet.getRow(rowStart).getCell(14).setCellStyle(topLeftStyle);
                sheet.getRow(rowStart).getCell(14).setCellValue(transaccion.getComodin().getComodin2());
                if (sheet.getRow(12).getCell(14).getStringCellValue().equals("")) {
                    sheet.getRow(12).getCell(14).setCellStyle(headerTableStyle);
                    sheet.getRow(12).getCell(14).setCellValue("Comodin2");
                }
            }

            if (transaccion.getComodin().getComodin3() != null) {
                sheet.getRow(rowStart).getCell(15).setCellStyle(topLeftStyle);
                sheet.getRow(rowStart).getCell(15).setCellValue(transaccion.getComodin().getComodin3());
                if (sheet.getRow(12).getCell(15).getStringCellValue().equals("")) {
                    sheet.getRow(12).getCell(15).setCellStyle(headerTableStyle);
                    sheet.getRow(12).getCell(15).setCellValue("Comodin3");
                }
            }

            if (transaccion.getComodin().getComodin4() != null) {
                sheet.getRow(rowStart).getCell(16).setCellStyle(topLeftStyle);
                sheet.getRow(rowStart).getCell(16).setCellValue(transaccion.getComodin().getComodin4());
                if (sheet.getRow(12).getCell(16).getStringCellValue().equals("")) {
                    sheet.getRow(12).getCell(16).setCellStyle(headerTableStyle);
                    sheet.getRow(12).getCell(16).setCellValue("Comodin4");
                }
            }

            if (transaccion.getComodin().getComodin5() != null) {
                sheet.getRow(rowStart).getCell(17).setCellStyle(topLeftStyle);
                sheet.getRow(rowStart).getCell(17).setCellValue(transaccion.getComodin().getComodin5());
                if (sheet.getRow(12).getCell(17).getStringCellValue().equals("")) {
                    sheet.getRow(12).getCell(17).setCellStyle(headerTableStyle);
                    sheet.getRow(12).getCell(17).setCellValue("Comodin5");
                }
            }

            for (int j = 1; j < transacciones.get(i).getOperaciones().size(); j++) {
                int currentRow = rowStart + 2 + j;
                Operacion currentOperacion = ((List<Operacion>) transacciones.get(i).getOperaciones()).get(j);
                Row insertionRow = sheet.getRow(currentRow) != null ? sheet.getRow(currentRow) : sheet.createRow(currentRow);
                for (int k = 2; k < 11; k++) {
                    insertionRow.createCell(k);
                }

                if (currentOperacion.getIdCgn() != null) {
                    sheet.getRow(currentRow).getCell(2).setCellValue(Integer.parseInt(currentOperacion.getIdCgn()));
                    sheet.getRow(currentRow).getCell(2).setCellStyle(topRigthStyle);
                }
                sheet.getRow(currentRow).getCell(3).setCellValue(currentOperacion.getNombreEntidad());
                sheet.getRow(currentRow).getCell(3).setCellStyle(topLeftStyle);
                if (currentOperacion.getLiquidez() != null) {
                    sheet.getRow(currentRow).getCell(4).setCellValue(currentOperacion.getLiquidez());
                }
                sheet.getRow(currentRow).getCell(4).setCellStyle(topRigthStyle);
                if (currentOperacion.getCodigoCuenta() != null) {
                    sheet.getRow(currentRow).getCell(5).setCellValue(currentOperacion.getCodigoCuenta());
                }
                sheet.getRow(currentRow).getCell(5).setCellStyle(topLeftStyle);
                if (currentOperacion.getDescripcionCuenta() != null) {
                    sheet.getRow(currentRow).getCell(6).setCellValue(currentOperacion.getDescripcionCuenta());
                }
                sheet.getRow(currentRow).getCell(6).setCellStyle(topLeftStyle);
                if (currentOperacion.getValorReportado() != null) {
                    sheet.getRow(currentRow).getCell(7).setCellValue(currentOperacion.getValorReportado().doubleValue());
                }
                sheet.getRow(currentRow).getCell(7).setCellStyle(decimalsStyle);
                if (currentOperacion.getPartidaConciliatoria() != null) {
                    sheet.getRow(currentRow).getCell(8).setCellValue(currentOperacion.getPartidaConciliatoria().doubleValue());
                }
                sheet.getRow(currentRow).getCell(8).setCellStyle(decimalsStyle);
                if (currentOperacion.getOrigenDiferencia() != null) {
                    sheet.getRow(currentRow).getCell(9).setCellValue(currentOperacion.getOrigenDiferencia());
                }
                sheet.getRow(currentRow).getCell(9).setCellStyle(topLeftStyle);
                if (currentOperacion.getAnalistaCategoria() != null) {
                    sheet.getRow(currentRow).getCell(10).setCellValue(currentOperacion.getAnalistaCategoria());
                }
                sheet.getRow(currentRow).getCell(10).setCellStyle(topLeftStyle);
            }
            rowStart = ++rowEnd;
            rowEnd = i < transacciones.size() - 1 ? rowStart + transacciones.get(i + 1).getOperaciones().size() + 1 : 0;
        }
    }

    /**
     * Aplicar el estilo de bordeado a un estilo de una celda pasado
     *
     * @param style
     */
    private void applyBorderStyle(CellStyle style) {
        HSSFWorkbook hwb = new HSSFWorkbook();
        HSSFPalette palette = hwb.getCustomPalette();
        HSSFColor myBorderColor = palette.findSimilarColor(204, 204, 204);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setTopBorderColor(myBorderColor.getIndex());
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBottomBorderColor(myBorderColor.getIndex());
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setLeftBorderColor(myBorderColor.getIndex());
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setRightBorderColor(myBorderColor.getIndex());
    }

    /**
     * Aplicar el estilo de bordeado a una celda de tipo mezclada
     *
     * @param row1
     * @param row2
     * @param col1
     * @param col2
     * @param workbook
     * @param sheet
     * @return
     */
    private CellRangeAddress applyBorderStyleMerge(int row1, int row2, int col1, int col2, Workbook workbook, Sheet sheet) {
        CellRangeAddress ans = new CellRangeAddress(row1, row2, col1, col2);
        HSSFWorkbook hwb = new HSSFWorkbook();
        HSSFPalette palette = hwb.getCustomPalette();
        HSSFColor myBorderColor = palette.findSimilarColor(204, 204, 204);
        CellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setTopBorderColor(myBorderColor.getIndex());
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBottomBorderColor(myBorderColor.getIndex());
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setLeftBorderColor(myBorderColor.getIndex());
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setRightBorderColor(myBorderColor.getIndex());
        for (int o = row1; o <= row2; o++) {
            Row row = sheet.getRow(o) != null ? sheet.getRow(o) : sheet.createRow(o);
            for (int j = col1; j <= col2; j++) {
                Cell cell = row.createCell(j);
                cell.setCellStyle(style);
            }
        }
        return ans;
    }
    
    /**
     * Obtiene los estados disponibles en el sistema.
	 * @return Estados disponibles para transacciones.
	 */
	private Collection<EstadoDTO> obtenerEstados() {
		EstadoTransaccion [] estados = EstadoTransaccion.values();
		Collection<EstadoDTO> outEstados = new ArrayList<>();
		for (EstadoTransaccion estado: estados) {
			EstadoDTO outEstado = EstadoDTO.builder()
					.codigo(estado.getCodigo())
					.descripcion(estado.getDescripcion()).build();
			outEstados.add(outEstado );
		}
		return outEstados;
	}

	/**
	 * Obtiene los periodos disponibles.
	 * @return Periodos de consolidaci&oacute;n disponibles
	 */
	private Collection<PeriodoDTO> obtenerPeridos() {
		
		Periodo [] periodos = Periodo.values();
		Collection<PeriodoDTO> outPeriodos 
			= new ArrayList<>();
		
		for (Periodo periodo: periodos) {
			PeriodoDTO outEstado = PeriodoDTO.builder()
					.codigo(periodo.getCodigo())
					.descripcion(periodo.getDescripcion()).build();
			outPeriodos.add(outEstado );
		}
		return outPeriodos;
	}
    
    /**
	 * Convierte fechas a String
	 * @param fecha Fecha origen
	 * @return Cadena que representa la fecha
	 */
	private String convertir(Date fecha) {
		return dateFormat.format(fecha);
	}

}
