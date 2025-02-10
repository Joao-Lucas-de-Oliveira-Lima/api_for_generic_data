# API for Generic Data

## Table of Contents

- [About](#about)
- [Getting Started](#getting-started)
- [How It Works](#how-it-works)
- [Testing](#testing)

## About <a name="about"></a>

A Java API that utilizes a generic repository interface.


## Getting Started <a name="getting-started"></a>

### Prerequisites

- Java JDK 21

### Usage

To start the application, use:

```sh
java -jar app.jar
```


### How It Works

First, a generic CRUD interface is created:

```java
public interface CrudRepository<T> {
    T findById(Long id);
    T save(T object);
    void delete(Long id);
    T updateById(T update);
    List<T> findAll();
}
```

Then, this interface is implemented by two concrete classes: `UserRepository` and `ProductRepository`. Each concrete class maintains an in-memory database using a `HashMap`:

```java
public class ProductRepository implements CrudRepository<ProductModel> {
    Map<Long, ProductModel> repository = new HashMap<>();
    
    @Override
    public ProductModel save(ProductModel object) throws ResourceAlreadyExistsException, IllegalArgumentException {
        if (object == null) {
            throw new IllegalArgumentException("Product data cannot be null");
        }
        if (findById(object.getId()) != null) {
            throw new ResourceAlreadyExistsException(String.format("Product with id %s already exists", object.getId()));
        }
        if (repository.containsValue(object)) {
            throw new ResourceAlreadyExistsException("The provided product data is a duplicate of an existing record");
        }
        repository.put(object.getId(), object);
        return object;
    }
    // Other methods
}
```

Finally, in the `Main` class, the Open-Closed and Liskov Substitution Principles are tested:

```java
// Test 01
CrudRepository<UserModel> userRepository = new UserRepository();
userRepository.save(new UserModel(1L, "User01", "user01@gmail.com"));
userRepository.save(new UserModel(2L, "User01", "user01@hotmail.com"));
System.out.println(userRepository.findAll()
        .stream()
        .map(UserModel::toString)
        .collect(Collectors.joining("\n", "Users:\n", "")));

// Test 02
CrudRepository<ProductModel> productRepository = new ProductRepository();
productRepository.save(new ProductModel(1L, "Product01"));
productRepository.save(new ProductModel(2L, "Product02"));
productRepository.save(new ProductModel(3L, "Product03"));
productRepository.updateById(new ProductModel(1L, "Product01Updated"));
productRepository.delete(3L);
System.out.println(productRepository.findAll()
        .stream()
        .map(ProductModel::toString)
        .collect(Collectors.joining("\n", "Products:\n", "")));
```

Execution output:

```plaintext
Users:
UserModel [id=1, username=User01, email=user01@gmail.com]
UserModel [id=2, username=User01, email=user01@hotmail.com]
Products:
ProductModel [id=1, name=Product01Updated]
ProductModel [id=2, name=Product02]
```

### Testing

To test the implementation, create a model entity with an `id` field of type `Long`, as shown below:

```java
package dev.jl.model;

public class ExampleModel {
    private Long id;
    private String name;
    // Boilerplate code for a default POJO
}
```

Next, create a repository that implements `CrudRepository` for the above entity. Use a `Map` or another data structure to simulate an in-memory database. Implement the `CrudRepository` methods to interact with this database:

```java
public class ExampleRepository implements CrudRepository<ExampleModel> {
    Map<Long, ExampleModel> repository = new HashMap<>();
    
    @Override
    public ExampleModel findById(Long id) {
        return repository.get(id);
    }
    
    @Override
    public ExampleModel save(ExampleModel object) {
        repository.put(object.getId(), object);
        return object;
    }
    
    @Override
    public void delete(Long id) {
        repository.remove(id);
    }
    
    @Override
    public ExampleModel updateById(ExampleModel update) {
        repository.put(update.getId(), update);
        return update;
    }
    
    @Override
    public List<ExampleModel> findAll() {
        return new ArrayList<>(repository.values());
    }
}
```

Finally, create an instance of the repository in the `Main` class and invoke the CRUD methods using the `CrudRepository` interface:

```java
public class Main {
    public static void main(String[] args) {
        // Testing CRUD operations
        CrudRepository<ExampleModel> exampleRepository = new ExampleRepository();
        exampleRepository.save(new ExampleModel(1L, "Example1"));
        exampleRepository.save(new ExampleModel(2L, "Example2"));
        System.out.println(exampleRepository.findAll());
    }
}
```
