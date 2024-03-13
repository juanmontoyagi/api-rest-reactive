package models.service;

import models.dao.CategoriaDAO;
import models.dao.ProductoDAO;
import models.documents.Categoria;
import models.documents.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoServiceImp {

    @Autowired
    private ProductoDAO productoDAO;

    @Autowired
    private CategoriaDAO categoriaDAO;

    public Flux<Producto> findAll() {
        return productoDAO.findAll();
    }

    public Mono<Producto> findById(String id) {
        return productoDAO.findById(id);
    }

    public Mono<Producto> save(Producto producto) {
        return productoDAO.save(producto);
    }

    public Mono<Void> delete(Producto producto) {
        return productoDAO.delete(producto);
    }

    public Flux<Producto> findAllConNombreUpperCase(){
        return productoDAO.findAll().map(producto -> {
            producto.setNombre(producto.getNombre().toUpperCase());
            return producto;
        });
    }

    public Flux<Producto> findAllConNombreUpperCaseRepeat(){
        return findAllConNombreUpperCase().repeat(5000);
    }

    public Flux<Categoria> findAllCategoria() {
        return categoriaDAO.findAll();
    }

    public Mono<Categoria> findCategoriaById(String id) {
        return categoriaDAO.findById(id);
    }

    public Mono<Categoria> saveCategoria(Categoria categoria) {
        return categoriaDAO.save(categoria);
    }
}
