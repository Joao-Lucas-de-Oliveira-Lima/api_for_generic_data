package dev.jl;

import java.util.stream.Collectors;

import dev.jl.model.ProductModel;
import dev.jl.model.UserModel;
import dev.jl.repository.CrudRepository;
import dev.jl.repository.impl.ProductRepository;
import dev.jl.repository.impl.UserRepository;

public class Main {
    public static void main(String[] args) {
        // Test 01
        CrudRepository<UserModel> userRepository = new UserRepository();
        userRepository.save(new UserModel(1L, "User01", "user01@gmail.com"));

        userRepository.save(new UserModel(2L, "User01", "user01@hotmail.com"));

        System.out.println(userRepository.findAll()
                .stream()
                .map(user -> user.toString())
                .collect(Collectors.joining("\n", "Users:\n", "")));

        // Test 02
        CrudRepository<ProductModel> productRepository = new ProductRepository();
        productRepository.save(new ProductModel(1L, "Product01"));
        productRepository.save(new ProductModel(2L, "Product02"));
        productRepository.save(new ProductModel(3L, "Product03"));
        productRepository.updateById(new ProductModel(1L, "Procuct01Updated"));
        productRepository.delete(3L);

        System.out.println(productRepository.findAll()
                .stream()
                .map(product -> product.toString())
                .collect(Collectors.joining("\n", "Products:\n", "")));

    }
}