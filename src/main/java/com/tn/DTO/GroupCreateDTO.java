package com.tn.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GroupCreateDTO {
    private Integer id;
    private String groupName;
    private Integer member;
    private String creator;
    private LocalDate createdDate;
}
