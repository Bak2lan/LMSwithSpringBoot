package peakSoft.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Table(name = "tasks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_gen")
    @SequenceGenerator(name = "task_gen",
            sequenceName = "task_seq",
            allocationSize = 1)
    private Long id;
    @NotNull
    @Column(name = "task_name")
    private String taskName;
    @Column(name = "task_text")
    private String taskText;
    @Column(name = "dead_line")
    private LocalDate deadLine;
    @ManyToOne(fetch = FetchType.EAGER,cascade = {
            CascadeType.REFRESH,
            CascadeType.DETACH
    })
    private Lesson lesson;

}
