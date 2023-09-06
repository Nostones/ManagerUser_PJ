package com.tn.controller;

import com.tn.DTO.GroupDTO;
import com.tn.entity.Group_M;
import com.tn.entity.User;
import com.tn.repository.GroupRepository;
import com.tn.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(value = "*")
@Slf4j
public class GroupController {

    @Autowired
    private GroupRepository groupRepo;
    @Autowired
    private UserRepository userRepo;

    // GET ALL GROUP
    @GetMapping("group")
    public ResponseEntity<?> getAll() {
        List<Group_M> groupMS = groupRepo.findAll();
        List<GroupDTO> groupDTOS = new ArrayList<>();
        for (int i = 0; i < groupMS.size(); i++) {
            GroupDTO groupDTO = new GroupDTO();
            groupDTO.setId(groupMS.get(i).getId());
            groupDTO.setGroupName(groupMS.get(i).getGroupName());
            groupDTO.setCreator(groupMS.get(i).getCreator());
            groupDTO.setCreatedDate(groupMS.get(i).getCreatedDate());

            // AUTO UPDATE MEMBER SIZE
            List<User> user = userRepo.findByGroupM_Id(groupMS.get(i).getId());
            groupDTO.setMember(user.size());
            groupDTOS.add(groupDTO);
        }
        return new ResponseEntity<>(groupDTOS, HttpStatus.OK);
    }

    // GET GROUP BY ID
    @GetMapping("group/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Optional<Group_M> group = groupRepo.findById(id);
        if (group.isEmpty()) {
            return new ResponseEntity<>("Id not found" + id, HttpStatus.BAD_REQUEST);
        }
            GroupDTO groupDTO = new GroupDTO();
            groupDTO.setId(group.get().getId());
            groupDTO.setGroupName(group.get().getGroupName());
            groupDTO.setCreator(group.get().getCreator());
            groupDTO.setCreatedDate(group.get().getCreatedDate());

            List<User> user = userRepo.findByGroupM_Id(groupDTO.getId());
            groupDTO.setMember(user.size());

        return new ResponseEntity<>(groupDTO, HttpStatus.OK);
    }

    // CREATE NEW GROUP
    @PostMapping("groupAdd/{groupName}")
    public ResponseEntity<?> create(@PathVariable String groupName) {
        System.out.println(groupName);
        Group_M group = new Group_M();
        group.setGroupName(groupName);
        group.setCreator("ADMIN");
        group.setCreatedDate(LocalDate.now());
        groupRepo.save(group);
        return new ResponseEntity<>("Create Successfully", HttpStatus.OK);
    }

    // UPDATE GROUP BY ID
    @PutMapping("group")
    public ResponseEntity<?> updateGroup(@RequestBody GroupDTO groupDTO) {
        Optional<Group_M> group = groupRepo.findById(groupDTO.getId());

        if (group.isEmpty()) {
            return new ResponseEntity<>("Group is empty", HttpStatus.BAD_REQUEST);
        }else {
            Group_M groupM = groupRepo.findById(groupDTO.getId()).get();
            groupM.setGroupName(groupDTO.getGroupName());
            groupM.setCreator(groupDTO.getCreator());
            groupM.setCreatedDate(LocalDate.now());
            groupRepo.save(groupM);
        }
        return new ResponseEntity<>("Update Successfully", HttpStatus.OK);
    }


    // DELETE GROUP BY ID
    @DeleteMapping("group/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        Optional<Group_M> group = groupRepo.findById(id);
        System.out.println("Run in delete group");
        if(group.isEmpty()){
            return new ResponseEntity<>("id is null" + id, HttpStatus.BAD_REQUEST);
        }
        List<User> users = group.get().getUsers();
        if (users.size() > 0) {
            return new ResponseEntity<>("group is user by user with group id" + id, HttpStatus.OK);
        }
        groupRepo.deleteById(id);
        return new ResponseEntity<>("Delete Successfully" + id, HttpStatus.OK);
    }
}






