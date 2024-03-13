package models.dao;

import models.documents.Producto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductoDAO extends ReactiveMongoRepository<Producto, String> {
}
