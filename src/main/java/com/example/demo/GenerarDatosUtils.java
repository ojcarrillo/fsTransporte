package com.example.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;


@Component
public class GenerarDatosUtils {

	private final static List<String> ciudades = Arrays.asList("Puerto Inírida,San José del Guaviare,Neiva,Riohacha,Santa Marta,Villavicencio,Pasto,Cúcuta,Mocoa,Armenia,Pereira,San Andres,Bucaramanga,Sincelejo,Ibagué,Cali,Mitú,Puerto Carreño,Leticia,Medellín,Arauca,Barranquilla,Cartagena,Tunja,Manizales,Florencia,Yopal,Popayán,Valledupar,Quibdó,Montería,Bogotá".split(","));
	private final static String FORMATO_FECHA_HORA  = "ddMMyyyyHH00";
	private final static String FORMATO_FECHA_ENTRADA  = "ddMMyyyy";
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat(FORMATO_FECHA_HORA);
	private final static String LETRAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	
	public String getFechaSalida(Date beginTime, Date endTime) {
		return dateFormat.format(new Date(getRandomTimeBetweenTwoDates(beginTime, endTime)));
	}
	
	public String getCiudad() {
		return ciudades.get(new Random().nextInt(ciudades.size()) + 1) ;
	}
	
	public String getPuestosDisponibles() {
		return String.format("%02d", new Random().nextInt(26) + 1); 
	}
	
	public Date getFechaEntrada(String fecha) {
		try {
			return new SimpleDateFormat(FORMATO_FECHA_ENTRADA).parse(fecha);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public String getIDReserva() {
		return generateRandomStr(LETRAS,3)+String.format("%06d", new Random().nextInt(100000) + 1); 
	}
	
	public String getFiltros(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String datos = br.readLine();
		br.close();
		return datos;
	}
	
	private String generateRandomStr(String aToZ, Integer size) {
	    Random rand=new Random();
	    StringBuilder res=new StringBuilder();
	    for (int i = 0; i < size; i++) {
	       int randIndex=rand.nextInt(aToZ.length()); 
	       res.append(aToZ.charAt(randIndex));            
	    }
	    return res.toString();
	}
	
	private long getRandomTimeBetweenTwoDates (Date beginTime, Date endTime) {
	    long diff = endTime.getTime() - beginTime.getTime() + 1;
	    return beginTime.getTime() + (long) (Math.random() * diff);
	}
}
