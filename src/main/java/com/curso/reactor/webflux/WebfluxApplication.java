package com.curso.reactor.webflux;

import com.curso.reactor.webflux.persistencia.entidad.Producto;
import com.curso.reactor.webflux.persistencia.repositorio.ProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactoryFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
public class WebfluxApplication implements CommandLineRunner {
	@Autowired
	private ProductoRepository productoRepository;

	@Autowired
	private ReactiveMongoTemplate reactiveMongoTemplate;

	private static final Logger logger = LoggerFactory.getLogger(WebfluxApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(WebfluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		reactiveMongoTemplate.dropCollection("producto")
							 .subscribe();

		Flux.just(
				new Producto("Teléfono", new BigDecimal("399.99")),
				new Producto("Laptop", new BigDecimal("899.99")),
				new Producto("Tablet", new BigDecimal("299.50")),
				new Producto("Cámara", new BigDecimal("199.99")),
				new Producto("Altavoces", new BigDecimal("79.99"))
				)
				.doOnNext(p -> p.setCreated_at(LocalDate.now()))
				.flatMap(productoRepository::save)
		 		.subscribe(p -> logger.info("Insert: " + p.getId() + " " + p.getNombre()));
	}
}
