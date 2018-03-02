package com.example.demo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CronBolivariano {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GenerarDatosUtils gendata;
	
	/* ruta del directorio compartido */
	private static final String PATH = File.separator + "ftp" + File.separator + "touresbalon" + File.separator;
	private static final String PREFIJO = "rta_";
	private static final String END_LINE = "\r\n";

	/* formato para la fecha hora de ejecucion */
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

	@Scheduled(fixedDelay = 5000, initialDelay = 5000)
	public void comprobarDirectorio() {
		log.info("aca esta! " + dateTimeFormatter.format(LocalDateTime.now()));
		log.info(PATH);
		leerDirectorio();
	}

	public void leerDirectorio() {
		File folder = new File(PATH);
		if (folder.exists()) {
			File[] files = folder.listFiles();
			for (File file : files) {
				if (file.isFile()) {
					System.out.println(file.getName());
					routingAccion(file.getName());
				}
			}
		} else {			
			log.error("no existe >>> " + PATH);
		}
	}

	public Boolean validateRoutingAccion(String fileName) {
		if (fileName.startsWith("query_reservas")) {
			crearArchivoRta(fileName);
		} else if (fileName.startsWith("query_servicios")) {

		} else if (fileName.startsWith("query_compras")) {

		} else if (fileName.startsWith("reservar")) {

		} else if (fileName.startsWith("comprar")) {

		} else if (fileName.startsWith("cancelar")) {

		} else if (fileName.startsWith("cambiar")) {

		}
		return false;
	}
	
	public void routingAccion(String fileName) {
		if (fileName.startsWith("query_reservas")) {
			crearArchivoRta(fileName);
		} else if (fileName.startsWith("query_servicios")) {

		} else if (fileName.startsWith("query_compras")) {

		} else if (fileName.startsWith("reservar")) {

		} else if (fileName.startsWith("comprar")) {

		} else if (fileName.startsWith("cancelar")) {

		} else if (fileName.startsWith("cambiar")) {

		}
	}

	public void crearArchivoRta(String fileNameInput) {
		File fileInput = new File(PATH + fileNameInput);
		String fileNameOutput = PATH + PREFIJO + fileNameInput;
		File fileOutput = new File(fileNameOutput);
		if (!fileOutput.exists()) {
			try {
				fileOutput.createNewFile();
			} catch (IOException e) {
				log.error("IOException: %s%n", e);
			}
		}		
		/* agrega el nuevo mensaje al archivo */
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileNameOutput), StandardOpenOption.APPEND)) {
			for(String x : getDatosRtaReservas(fileInput)) {
				writer.write(x);
				writer.newLine();
			}
			writer.close();
		} catch (IOException ioe) {
			log.error("IOException: %s%n", ioe);
		} 
		/* elimina archivo de entrada */
		try {
			System.out.println(Files.deleteIfExists(fileInput.toPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<String> getDatosRtaReservas(File file) throws IOException {
		List<String> rtas = new ArrayList<String>();
		String opc = gendata.getFiltros(file);
		if(opc!=null) {
			String fechaInicial = opc.substring(0, 8);
			String fechaFinal = opc.substring(8, 16).trim();
			String ciudadOrigen = opc.substring(16, 37).trim();
			String ciudadDestino = opc.substring(37, 58).trim();
			log.info(fechaInicial+"-"+fechaFinal+"-"+ciudadOrigen+"-"+ciudadDestino);			
			for(int i=1;i<new Random().nextInt(10)+1;i++) {
				StringBuilder salida = new StringBuilder();
				salida.append(gendata.getIDReserva());
				salida.append(gendata.getFechaSalida(gendata.getFechaEntrada(fechaInicial), gendata.getFechaEntrada(fechaFinal)));
				salida.append(ciudadOrigen.length()>0 ? ciudadOrigen : gendata.getCiudad());
				salida.append(ciudadDestino.length()>0 ? ciudadDestino : gendata.getCiudad());
				salida.append(gendata.getPuestosDisponibles());
				rtas.add(salida.toString());
			}
		}
		return rtas;
	}
}
