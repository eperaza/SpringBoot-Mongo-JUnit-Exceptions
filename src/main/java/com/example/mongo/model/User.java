package com.example.mongo.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;



@Component
@Document(collection="user")

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Transient
    @Id
    public static final String SEQUENCE_NAME = "user_sequence";
    private String id;
    private String name;
    private String email;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    public User() {
        super();
        
    }


    
    
    
}
