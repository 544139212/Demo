package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		/**
		 * 此行不加，则报异常：
		 * nested exception is java.lang.IllegalStateException: availableProcessors is already set to [8], rejecting [8]
		 */
		System.setProperty("es.set.netty.runtime.available.processors", "false");

		SpringApplication.run(DemoApplication.class, args);
	}
}
