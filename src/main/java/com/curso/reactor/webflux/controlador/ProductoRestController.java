package com.curso.reactor.webflux.controlador;

import com.curso.reactor.webflux.persistencia.entidad.Producto;
import com.curso.reactor.webflux.persistencia.repositorio.ProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/productos")
public class ProductoRestController {
    Logger logger = LoggerFactory.getLogger(ProductoControlador.class);

    @Autowired
    private ProductoRepository productoRepository;


    @GetMapping
    public Flux<Producto> index() {
        Flux<Producto> productos = productoRepository.findAll()
                .doOnNext(p -> p.setNombre(p.getNombre().toUpperCase()))
                .doOnNext(p -> logger.info(p.getNombre()));

        return productos;
    }

    @GetMapping("/{id}")
    public Mono<Producto> getById(@PathVariable String id) {
        return productoRepository.findById(id);
    }
}
