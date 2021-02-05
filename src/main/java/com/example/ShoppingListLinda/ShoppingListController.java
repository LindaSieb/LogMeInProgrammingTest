package com.example.ShoppingListLinda;

//almost all code taken from https://spring.io/guides/tutorials/rest/
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


  @GetMapping("/items") //GET ALL ITEMS
  CollectionModel<EntityModel<ShoppingItem>> all() {
	  																//collect all items into a list, make sure they each have a link to themselves and a link to the main list
    List<EntityModel<ShoppingItem>> items = repository.findAll().stream()
        .map(item -> EntityModel.of(item,
            linkTo(methodOn(ShoppingListController.class).one(item.getId())).withSelfRel(),
            linkTo(methodOn(ShoppingListController.class).all()).withRel("items")))
        .collect(Collectors.toList());
    																//return the list, make sure it has a link to itself as well
    return CollectionModel.of(items, linkTo(methodOn(ShoppingListController.class).all()).withSelfRel());
    																//(if there's no items yet, this will return a blank list that still has a link to itself)
  }


  @PostMapping("/items") //POST (CREATE A NEW ITEM)
  ShoppingItem newItem(@RequestBody ShoppingItem newItem) { 		//From what I understand, @RequestBody is used because we have a JSON array of text being passed, not just a variable
    return repository.save(newItem);
  }

  
  @GetMapping("/items/{id}") //GET A SPECIFIC ITEM BASED ON ID
  EntityModel<ShoppingItem> one(@PathVariable Long id) {

    ShoppingItem item = repository.findById(id) 					//findById returns an optional, but the orElseThrow has it take the value of the Optional, and if it's null, it creates a new exception
        .orElseThrow(() -> new ItemNotFoundException(id)); 

    return EntityModel.of(item, 									//make sure the item has a link to itself and a link to the main list
        linkTo(methodOn(ShoppingListController.class).one(id)).withSelfRel(),
        linkTo(methodOn(ShoppingListController.class).all()).withRel("items"));
  }

  @PutMapping("/items/{id}") //PUT (REPLACE/UPDATE A CURRENT ITEM BASED ON ID). OR MAKES A NEW ITEM IF CURRENT ONE NOT FOUND?
  ShoppingItem replaceItem(@RequestBody ShoppingItem newItem, @PathVariable Long id) {

	  																//code added to this section to make it possible to only update a name or only update a quantity of an item
    return repository.findById(id)
      .map(item -> {
    	  if (newItem.getName() != null) { 							//only set the name if a new one has been specified
    		  item.setName(newItem.getName());
    	  }
    	  if ((newItem.getQuantity() != item.getQuantity()) && newItem.getQuantity()!= null) { 		//only set the quantity if a new one has been specified
        	 item.setQuantity(newItem.getQuantity());
    	  }
    	  return repository.save(item);
      }) 															//the map function performs an operation on the optional value, and if that doesn't work then it makes a new item.
      .orElseGet(() -> {
    	  newItem.setId(id); 										//doesn't create a new id, but uses the one given because this is PUT, not POST. https://tools.ietf.org/html/rfc7231#section-4.3.4
    	  return repository.save(newItem);
      });
  }

  @DeleteMapping("/items/{id}") //DELETE AN ITEM BASED ON ID
  void deleteItem(@PathVariable Long id) {
    repository.deleteById(id);
  }
  
  @DeleteMapping("/items") //DELETE ALL ITEMS //this mapping was not in tutorial
  void deleteAll() {
	  repository.deleteAll();
  }
}
