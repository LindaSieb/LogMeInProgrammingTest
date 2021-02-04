package com.example.ShoppingListLinda;

//code taken from https://spring.io/guides/tutorials/rest/
//some code removed and some code changed for naming

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
class ShoppingItem {

  private @Id @GeneratedValue Long id;
  private String name;
  private int quantity = 1;

  ShoppingItem() {} //blank constructor with nothing in it

  ShoppingItem(String name) { //only need to construct with name of item
    this.name = name;
  }
  
  ShoppingItem(String name, int quantity) { //only need to construct with name of item
	    this.name = name;
	    this.quantity = quantity;
	  }

  //getters and setters for the shopping item variables (only id and name)
  public Long getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }
  
  public int getQuantity() {
	    return this.quantity;
	  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }
  
  public void setQuantity(int quantity) {
	    this.quantity = quantity;
  }
  
}
