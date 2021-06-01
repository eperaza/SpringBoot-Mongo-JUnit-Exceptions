package com.example.mongo.service;

import java.util.List;

import com.example.mongo.apierror.CustomException;
import com.example.mongo.model.User;

public interface IUserService {
    public String addUser(String name, String email);
    public List<User> findAll();
    public String update(String id, String name, String email);
    public User findById(String id) throws CustomException;



}
