package com.example.ShoppingListLinda;

//code taken from https://spring.io/guides/tutorials/rest/
//some code removed and some code changed for naming

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity //Makes it possible to store in a JPA repository
class ShoppingItem {

  private @Id @GeneratedValue Long id; //@Id makes sure it's the primary key, and @GeneratedValue creates a unique id automatically
  private String name;
  private Integer quantity; //set this so that a user can add an item without a quantity (it will be null because we have no data for it)
  //private Boolean quantityWasGiven; //this was a failed experiment to set a flag on ones where the quantity was specified by the user
  //TODO: question for programming team on why this didn't work
  
  ShoppingItem() {} //blank constructor does nothing.

  ShoppingItem(String name) { //can create an item with just a name
    this.name = name;
  }
  
  ShoppingItem(String name, int quantity) { //can also create an item with a name and a quantity
	    this.name = name;
	    this.quantity = quantity;
  }
  
  //getters and setters for the shopping item variables (id, name, and quantity)
  public Long getId() {
	  	return this.id;
  }

  public String getName() {
	  	return this.name;
  }
  
  public Integer getQuantity() {
	    return this.quantity;
  }

  public void setId(Long id) {
	  	this.id = id;
  }

  public void setName(String name) {
	  	this.name = name;
  }
  
  public void setQuantity(Integer quantity) {
	    this.quantity = quantity;
  }
  
  
}
