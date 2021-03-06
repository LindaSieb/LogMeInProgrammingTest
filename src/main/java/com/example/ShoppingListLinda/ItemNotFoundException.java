package com.example.ShoppingListLinda;

//code taken from https://spring.io/guides/tutorials/rest/
//some code changed for naming

class ItemNotFoundException extends RuntimeException {
	
	  ItemNotFoundException(Long id) {
	    super("Could not find item " + id); //super is used to set the new detail message for this exception
	  }
	}