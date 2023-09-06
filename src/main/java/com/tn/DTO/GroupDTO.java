package com.tn.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GroupDTO {
    private Integer id;
    private String groupName;
    private String creator;
    private Integer member;
    private LocalDate createdDate;
}
