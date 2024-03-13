package com.jumogira.APIRest;

import models.documents.Categoria;
import models.documents.Producto;
import models.service.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;

import java.util.Date;

@SpringBootApplication
public class ApiRestApplication implements CommandLineRunner {

	@Autowired
	private ProductoService productoService;

	@Autowired
	private ReactiveMongoTemplate mongoTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(ApiRestApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ApiRestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mongoTemplate.dropCollection("productos").subscribe();
		mongoTemplate.dropCollection("categorias").subscribe();

		Categoria electronica = new Categoria("Electrónica");
		Categoria deporte = new Categoria("Deporte");
		Categoria computacion = new Categoria("Computación");
		Categoria muebles = new Categoria("Muebles");

		Flux.just(electronica, deporte, computacion, muebles)
				.flatMap(productoService::saveCategoria)
				.doOnNext(c -> {
					LOGGER.info("Categoria creada: " + c.getId() + " Nombre: " + c.getNombre());
				}).thenMany(
						Flux.just(new Producto("TV Sonic Panasonic Pantalla LCD", 456.89, electronica),
								new Producto("Cámara Canon Digital", 356.89, electronica),
								new Producto("Notebook Sony Vaio", 856.89, computacion),
								new Producto("Bicicleta Bianchi", 456.89, deporte),
								new Producto("Mesa Oficina", 456.89, muebles)
						)
								.flatMap(producto -> {
									producto.setCreateAt(new Date());
									return productoService.save(producto);
								})
				).subscribe(producto -> LOGGER.info("Insert: " + producto.getId() + " " + producto.getNombre()));

	}
}
