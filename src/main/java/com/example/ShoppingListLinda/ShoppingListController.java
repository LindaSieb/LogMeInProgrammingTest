package com.example.ShoppingListLinda;

//code taken from https://spring.io/guides/tutorials/rest/
//some code removed and some code changed for naming
//debugging a missing import using https://github.com/spring-guides/gs-rest-hateoas/issues/8

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
class ShoppingListController {

  private final ShoppingList repository;

  ShoppingListController(ShoppingList repository) {
    this.repository = repository;
  }


  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/items")
  CollectionModel<EntityModel<ShoppingItem>> all() {

    List<EntityModel<ShoppingItem>> items = repository.findAll().stream()
        .map(item -> EntityModel.of(item,
            linkTo(methodOn(ShoppingListController.class).one(item.getId())).withSelfRel(),
            linkTo(methodOn(ShoppingListController.class).all()).withRel("items")))
        .collect(Collectors.toList());

    return CollectionModel.of(items, linkTo(methodOn(ShoppingListController.class).all()).withSelfRel());
  }
  
  // end::get-aggregate-root[]

  @PostMapping("/items")
  ShoppingItem newItem(@RequestBody ShoppingItem newItem) {
    return repository.save(newItem);
  }

  // Single item

//  @GetMapping("/items/{id}")
//  ShoppingItem one(@PathVariable Long id) {
//
//    return repository.findById(id)
//      .orElseThrow(() -> new ItemNotFoundException(id));
//  }
  
  @GetMapping("/items/{id}")
  EntityModel<ShoppingItem> one(@PathVariable Long id) {

    ShoppingItem item = repository.findById(id) //
        .orElseThrow(() -> new ItemNotFoundException(id));

    return EntityModel.of(item, //
        linkTo(methodOn(ShoppingListController.class).one(id)).withSelfRel(),
        linkTo(methodOn(ShoppingListController.class).all()).withRel("items"));
  }

  @PutMapping("/items/{id}")
  ShoppingItem replaceItem(@RequestBody ShoppingItem newItem, @PathVariable Long id) {

    return repository.findById(id)
      .map(item -> {
        item.setName(newItem.getName());
        item.setQuantity(newItem.getQuantity());
        return repository.save(item);
      })
      .orElseGet(() -> {
        newItem.setId(id);
        return repository.save(newItem);
      });
  }

  @DeleteMapping("/items/{id}")
  void deleteItem(@PathVariable Long id) {
    repository.deleteById(id);
  }
}
