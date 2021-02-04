package com.example.ShoppingListLinda;

//general tutorial followed was https://spring.io/guides/tutorials/rest/
//Spring Boot Project set up with https://start.spring.io/ and imported into Eclipse with Maven
//Tested with Postman (for GET, POST, PUT, DELETE statements)

import org.springframework.data.jpa.repository.JpaRepository;

interface ShoppingList extends JpaRepository<ShoppingItem, Long> {

}