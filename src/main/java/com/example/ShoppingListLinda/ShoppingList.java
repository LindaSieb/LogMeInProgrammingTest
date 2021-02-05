package com.example.ShoppingListLinda;

//tutorial followed was https://spring.io/guides/tutorials/rest/
//this code was taken from that tutorial

//This is an existing feature in Spring Data JPA- here the JpaRepository is extended and contains the resource type "ShoppingItem", 
//which is created elsewhere in this project, and the id type Long.

//this automatically makes it possible to use POST, PUT, DELETE, and GET

//part of the tutorial includes preloading data into this repository, but I think that's not necessary or helpful for this shopping list.
import org.springframework.data.jpa.repository.JpaRepository;

interface ShoppingList extends JpaRepository<ShoppingItem, Long> {

}