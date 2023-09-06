package com.tn.controller;

import com.tn.DTO.UserCreateDTO;
import com.tn.DTO.UserDTO;
import com.tn.entity.Group_M;
import com.tn.entity.User;
import com.tn.repository.UserRepository;
import com.tn.repository.GroupRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(value = "*")
@Slf4j
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private GroupRepository groupRepo;


    // GET ALL USER
    @GetMapping("user")
    public ResponseEntity<?> getAll(Pageable pageable){
        log.info("Get all list user");
        System.out.println(pageable);

        Page<User> page_users=userRepo.findAll(pageable);
        System.out.println(page_users.getSize());
        List<User> users = page_users.stream().collect(Collectors.toList());

        List<UserDTO> userDTOS = new ArrayList<>();

        for (int i = 0; i < users.size(); i++) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(users.get(i).getId());
            userDTO.setFullName(users.get(i).getFullName());
            userDTO.setRole(users.get(i).getRole());
            userDTO.setUserName(users.get(i).getUserName());

            Group_M groups= users.get(i).getGroupM();

            if (groups != null){
                Integer a = users.get(i).getGroupM().getId();
                userDTO.setGroupId(a);
            }else {
                userDTO.setGroupId(null);
            }
            userDTOS.add(userDTO);
        }
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    // GET USER BY ID
    @GetMapping("user/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id){
        log.info("Get user by id: " + id);
        Optional<User> user =userRepo.findById(id);
        if (user.isEmpty()){
            return new ResponseEntity<>("Id not found"+id, HttpStatus.BAD_REQUEST);
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.get().getId());
        userDTO.setUserName(user.get().getUserName());
        userDTO.setFullName(user.get().getFullName());
        userDTO.setRole(user.get().getRole());
        Group_M groups= user.get().getGroupM();
        if (groups != null){
            Integer a = user.get().getGroupM().getId();
            userDTO.setGroupId(a);
        }else {
            userDTO.setGroupId(null);
        }
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }


    // CREATE NEW USER
    @PostMapping("user")
    public ResponseEntity<?> create(@RequestBody UserCreateDTO userCreateDTO) {
        User user = new User();

        user.setFullName(userCreateDTO.getFullName());
        user.setUserName(userCreateDTO.getUserName());
        user.setRole(userCreateDTO.getRole());

        String passwordEncoder = new BCryptPasswordEncoder().encode(userCreateDTO.getPassword());
        user.setPassword(passwordEncoder);

        Group_M groupM = groupRepo.findById(userCreateDTO.getGroupId()).get();
        user.setGroupM(groupM);

        userRepo.save(user);
        return new ResponseEntity<>("Created user Successfully",HttpStatus.CREATED);
    }


    @PutMapping ("user")
    public ResponseEntity<?> update(@RequestBody UserDTO userDTO) {
        log.info("Update user successfully: " + userDTO);

        User user = userRepo.getReferenceById(userDTO.getId());
        user.setId(userDTO.getId());
        user.setUserName(userDTO.getUserName());
        user.setFullName(userDTO.getFullName());
        user.setRole(userDTO.getRole());

        String passwordEncoder = new BCryptPasswordEncoder().encode(userDTO.getPassword());
        user.setPassword(passwordEncoder);

        Group_M groupM = groupRepo.findById(userDTO.getGroupId()).get();
        if (groupM != null) {
            user.setGroupM(groupM);
        } else {
            user.setGroupM(null);
        }
        userRepo.save(user);
        return new ResponseEntity<>("user Update Successfully!", HttpStatus.OK);
    }

    @DeleteMapping ("user/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id  ){
        log.info("Delete user successfully: " + id);
        Optional<User> user = userRepo.findById(id);
        if (user.isEmpty()){
            return new ResponseEntity<>("Id not found :"+id, HttpStatus.BAD_REQUEST);
        }
        userRepo.deleteById(id);
        return new ResponseEntity<>("Delete Successfully", HttpStatus.OK);
    }


}
