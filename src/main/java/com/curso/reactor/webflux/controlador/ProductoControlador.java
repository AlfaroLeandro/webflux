package com.curso.reactor.webflux.controlador;

import com.curso.reactor.webflux.persistencia.entidad.Producto;
import com.curso.reactor.webflux.persistencia.repositorio.ProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Controller
public class ProductoControlador {

    Logger logger = LoggerFactory.getLogger(ProductoControlador.class);

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("/listar-datadriver")
    public String listarDataDriver(Model model) {
        Flux<Producto> productos = productoRepository.findAll()
                                                     .doOnNext(p -> p.setNombre(p.getNombre().toUpperCase()))
                                                     .delayElements(Duration.ofSeconds(1));

        productos.subscribe(p -> logger.info(p.getNombre()));

        model.addAttribute("productos", new ReactiveDataDriverContextVariable(productos, 2));
        model.addAttribute("titulo", "Listado de productos");
        return "listar";
    }

    @GetMapping("/listar-full")
    public String listarFull(Model model) {
        Flux<Producto> productos = productoRepository.findAll()
                .doOnNext(p -> p.setNombre(p.getNombre().toUpperCase()))
                .repeat(5000);

        model.addAttribute("productos", productos);
        model.addAttribute("titulo", "Listado de productos");
        return "listar";
    }

    @GetMapping("/listar-chunked")
    public String listarChunked(Model model) {
        Flux<Producto> productos = productoRepository.findAll()
                .doOnNext(p -> p.setNombre(p.getNombre().toUpperCase()))
                .repeat(5000);

        model.addAttribute("productos", productos);
        model.addAttribute("titulo", "Listado de productos");
        return "listar-chunked";
    }

    @GetMapping({"/listar", "/"})
    public String listar(Model model) {
        Flux<Producto> productos = productoRepository.findAll()
                .doOnNext(p -> p.setNombre(p.getNombre().toUpperCase()));
        productos.subscribe(p -> logger.info(p.getNombre()));

        model.addAttribute("productos", productos);
        model.addAttribute("titulo", "Listado de productos");
        return "listar";
    }
}
