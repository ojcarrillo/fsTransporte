package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FsTransporteApplication {

	public static void main(String[] args) {
		SpringApplication.run(FsTransporteApplication.class, args);
//		String opc = "2202201805032018cucuta               bucaramanga          ";
//		String fechaInicial = opc.substring(0, 8);
//		String fechaFinal = opc.substring(8, 16);
//		String ciudadOrigen = opc.substring(16, 37);
//		String ciudadDestino = opc.substring(37, 58);
//		System.out.println(fechaInicial+"-"+fechaFinal+"-"+ciudadOrigen+"-"+ciudadDestino);
	}
}
