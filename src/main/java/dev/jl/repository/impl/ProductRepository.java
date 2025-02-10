package dev.jl.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.jl.exception.ResourceAlreadyExistsException;
import dev.jl.model.ProductModel;
import dev.jl.repository.CrudRepository;


public class ProductRepository implements CrudRepository<ProductModel> {
    Map<Long, ProductModel> repository = new HashMap<>();

    @Override
    public ProductModel findById(Long id) {
        if(repository.containsKey(id)){
            return repository.get(id);
        }
        return null;
    }

    @Override
    public ProductModel save(ProductModel object) throws ResourceAlreadyExistsException, IllegalArgumentException{
        if(object == null){
            throw new IllegalArgumentException("Product data cannot be null");
        }
        if(findById(object.getId()) != null){
            throw new ResourceAlreadyExistsException(String.format("Product with id %s already exists", object.getId()));
        }
        if(repository.containsValue(object)){
            throw new ResourceAlreadyExistsException("The provided product data is a duplicate of an existing record");
        }
        repository.put(object.getId(), object);
        return object;
    }

    @Override
    public void delete(Long id) {
        repository.remove(id);
    }

    @Override
    public ProductModel updateById(ProductModel update) {
        if(repository.containsKey(update.getId())){
            repository.put(update.getId(), update);
            return update;
        }
        return null;
    }

    @Override
    public List<ProductModel> findAll() {
        return repository.values().stream().toList();
    }
    
}
