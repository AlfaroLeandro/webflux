package com.curso.reactor.webflux.persistencia.repositorio;

import com.curso.reactor.webflux.persistencia.entidad.Producto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductoRepository extends ReactiveMongoRepository<Producto, String> {

}
