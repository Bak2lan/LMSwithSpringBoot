package peakSoft.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "lessons")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "lesson_gen")
    @SequenceGenerator(name = "lesson_gen",
            sequenceName = "lesson_seq",
            allocationSize = 1)
    private Long id;
    @NotNull
    @Column(name = "lesson_name")
    private String lessonName;
    @ManyToOne
    private Course course;
    @OneToMany(mappedBy = "lesson",cascade = CascadeType.REMOVE,fetch = FetchType.EAGER)
    private Set<Task> tasks;

}
