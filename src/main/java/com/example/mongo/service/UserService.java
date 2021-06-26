package com.example.mongo.service;

import java.util.List;
import java.util.Optional;

import com.example.mongo.apierror.CustomException;
import com.example.mongo.model.User;
import com.example.mongo.model.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private User n;
    @Autowired
    private UserRepo repo;

    @Override
    public String addUser(String name, String email){
        n.setName(name);
        n.setEmail(email);
        repo.save(n);
        return "Saved";  
    }
    @Override
    public List<User> findAll(){
        return repo.findAll();  
    }
    @Override
    public String update(String id, String name, String email){ 
        Optional<User> user = repo.findById(id);
        if (user.isPresent()) {
            n=user.get();
            n.setName(name);
            n.setEmail(email);
            repo.save(n);
            return "Updated";
        }
        else return "Update Failed";
        
    }
    @Override
    public User findById(String id) throws CustomException{ 
        
        Optional<User> user = repo.findById(id);
        // isEmpty() only for Java 11
        if (!user.isPresent()) {
			throw new CustomException("User not found for given id: " + id + "", HttpStatus.NOT_FOUND);
		}
        n=user.get();
        return n;

    }

    
}

