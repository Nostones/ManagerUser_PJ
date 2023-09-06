package com.tn.controller;

import com.tn.entity.User;
import com.tn.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(value = "*")
@Slf4j
public class Sigin_Controller {
    @Autowired
    private UserRepository userRepo;

    @GetMapping("login")
    public ResponseEntity<?> login() {

        List<User> userList = userRepo.findAll();
        if (userList.size() > 0) {
            return new ResponseEntity<>("Login Successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Login false, wrong username or password", HttpStatus.OK);
    }

}
