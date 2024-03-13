package models.dao;

import models.documents.Categoria;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CategoriaDAO extends ReactiveMongoRepository<Categoria, String> {
}
