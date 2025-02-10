package dev.jl.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.jl.exception.ResourceAlreadyExistsException;
import dev.jl.model.UserModel;
import dev.jl.repository.CrudRepository;

public class UserRepository implements CrudRepository<UserModel> {
    Map<Long, UserModel> repository = new HashMap<>();

    @Override
    public UserModel findById(Long id) {
        if(repository.containsKey(id)){
            return repository.get(id);
        }
        return null;
    }

    @Override
    public UserModel save(UserModel object) throws ResourceAlreadyExistsException, IllegalArgumentException{
        if(object == null){
            throw new IllegalArgumentException("User data cannot be null");
        }
        if(findById(object.getId()) != null){
            throw new ResourceAlreadyExistsException(String.format("User with id %s already exists", object.getId()));
        }
        if(repository.containsValue(object)){
            throw new ResourceAlreadyExistsException("The provided user data is a duplicate of an existing record");
        }
        repository.put(object.getId(), object);
        return object;
    }

    @Override
    public void delete(Long id) {
        repository.remove(id);
    }

    @Override
    public UserModel updateById(UserModel update) {
        if(repository.containsKey(update.getId())){
            repository.put(update.getId(), update);
            return update;
        }
        return null;
    }

    @Override
    public List<UserModel> findAll() {
        return repository.values().stream().toList();
    }
    
}
