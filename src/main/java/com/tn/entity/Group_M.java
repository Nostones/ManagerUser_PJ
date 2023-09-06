package com.tn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@Data // getter, setter toString()
@NoArgsConstructor
@AllArgsConstructor
public class Group_M {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String groupName;
    private Integer member;
    private String creator;
    @CreatedDate
    private LocalDate createdDate;

    @OneToMany(mappedBy = "groupM")
    private List<User> users;

}
