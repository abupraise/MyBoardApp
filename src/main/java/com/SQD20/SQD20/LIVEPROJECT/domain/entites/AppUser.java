package com.SQD20.SQD20.LIVEPROJECT.domain.entites;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_tbl")
public class AppUser extends BaseClass{

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;

    @OneToMany(mappedBy = "user_tbl", cascade = CascadeType.ALL)
    private List<TaskList> taskList;
}
