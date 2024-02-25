package com.curso.reactor.webflux.controlador;

import com.curso.reactor.webflux.persistencia.entidad.Producto;
import com.curso.reactor.webflux.persistencia.repositorio.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;

@Controller
public class ProductoControlador {

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping({"/listar", "/"})
    public String listar(Model model) {
        Flux<Producto> productos = productoRepository.findAll();
        model.addAttribute("productos", productos);
        model.addAttribute("titulo", "Listado de productos");
        return "listar";
    }
}
