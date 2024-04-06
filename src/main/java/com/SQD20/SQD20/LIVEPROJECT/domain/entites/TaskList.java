package com.SQD20.SQD20.LIVEPROJECT.domain.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "task_tbl")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TaskList extends BaseClass{
    private String title;
    private String description;
    @ManyToOne()
    AppUser user;
    @OneToMany()
    List<Task> tasks;

}
