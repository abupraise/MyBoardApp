package com.SQD20.SQD20.LIVEPROJECT.domain.entites;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "taskList_tbl")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TaskList extends BaseClass{

    private String title;
    private String description;

    @ManyToOne
    private AppUser user;

    @OneToMany(mappedBy = "taskList", cascade = CascadeType.ALL)
    private List<Task> tasks;

}
