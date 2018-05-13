package com.example.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class GenerarDatosUtils {

	private final static List<String> ciudades = Arrays.asList(
			"Puerto Inirida,San Jose del Guaviare,Neiva,Riohacha,Santa Marta,Villavicencio,Pasto,Cucuta,Mocoa,Armenia,Pereira,San Andres,Bucaramanga,Sincelejo,Ibague,Cali,Mitu,Puerto Carreño,Leticia,Medellin,Arauca,Barranquilla,Cartagena,Tunja,Manizales,Florencia,Yopal,Popayan,Valledupar,Quibdo,Monteria,Bogota"
					.split(","));
	private final static String FORMATO_FECHA_HORA = "ddMMyyyyHH00";
	private final static String FORMATO_FECHA_ENTRADA = "ddMMyyyy";
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat(FORMATO_FECHA_HORA);
	private final static String LETRAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	private final static Integer TOTAL_PUESTOS = 26;
	private final static Double IVA = 0.71D; // 29%
	private final static Integer FACTOR_PRECIO = 30;
	private final static Double FACTOR_PRECIO_DINERO = 5000D;

	public String getFechaSalida(Date beginTime, Date endTime) {
		return dateFormat.format(new Date(getRandomTimeBetweenTwoDates(beginTime, endTime)));
	}

	public String getCiudad() {
		return StringUtils.rightPad(ciudades.get(new Random().nextInt(ciudades.size()) + 1), 21, " ");
	}

	public String getPuestosDisponibles() {
		return String.format("%02d", new Random().nextInt(TOTAL_PUESTOS) + 1);
	}

	public Integer getPuestosComprados() {
		return new Random().nextInt(TOTAL_PUESTOS) + 1;
	}

	public Date getFechaEntrada(String fecha) {
		try {
			return new SimpleDateFormat(FORMATO_FECHA_ENTRADA).parse(fecha);
		} catch (ParseException e) {
			return null;
		}
	}

	public String getIdViaje() {
		return generateRandomStr(LETRAS, 3) + String.format("%06d", new Random().nextInt(100000) + 1);
	}
	
	public String getIdReserva() {
		return generateRandomStr(LETRAS, 1) + String.format("%08d", new Random().nextInt(100000) + 1);
	}
	
	public String getIdCancelacion() {
		return String.format("%07d", new Random().nextInt(1000000) + 1);
	}

	public String getFiltros(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String datos = br.readLine();
		br.close();
		return datos;
	}
	
	public List<String> getFiltrosVarios(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String opc = null;
		List<String> datos = new ArrayList<String>();
		while ((opc = br.readLine()) != null) {
			datos.add(opc);
		}
		br.close();
		return datos;
	}

	private String generateRandomStr(String aToZ, Integer size) {
		Random rand = new Random();
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < size; i++) {
			int randIndex = rand.nextInt(aToZ.length());
			res.append(aToZ.charAt(randIndex));
		}
		return res.toString();
	}

	private Long getRandomTimeBetweenTwoDates(Date beginTime, Date endTime) {
		long diff = endTime.getTime() - beginTime.getTime() + 1;
		return beginTime.getTime() + (long) (Math.random() * diff);
	}

	public Double getValorPto(Integer cantPtos) {
		Double valorPto = (new Random().nextInt(FACTOR_PRECIO) + 1D) * FACTOR_PRECIO_DINERO;
		return Math.floor(cantPtos * valorPto);
	}

	public Double getValorIVA(Double valorTotal) {
		return valorTotal - (valorTotal * IVA);
	}

	public String getValoresMonetarios() {
		Integer cantPuestos = getPuestosComprados();
		Double valorPuestos = getValorPto(cantPuestos);
		Double valorIVA = getValorIVA(valorPuestos);
		return StringUtils.leftPad(String.valueOf(cantPuestos), 2, "0")
				+ StringUtils.leftPad(String.valueOf(valorPuestos), 17, "0")
				+ StringUtils.leftPad(String.valueOf(valorIVA), 17, "0");
	}
	
	public String getValoresMonetarios(Integer cantPuestos) {
		Double valorPuestos = getValorPto(cantPuestos);
		Double valorIVA = getValorIVA(valorPuestos);
		return StringUtils.leftPad(String.valueOf(cantPuestos), 2, "0")
				+ StringUtils.leftPad(String.valueOf(valorPuestos), 17, "0")
				+ StringUtils.leftPad(String.valueOf(valorIVA), 17, "0");
	}
	
	public Date getFutureDay(Date date, Integer days){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, days);  // number of days to add
		return c.getTime();
	}
	
	public Date parseDate(String fecha,String formato) throws ParseException {
		SimpleDateFormat formatoDate = new SimpleDateFormat(formato);
		return formatoDate.parse(fecha);
	}
}
