package peakSoft.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "courses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "course_gen")
    @SequenceGenerator(name = "course_gen",
            sequenceName = "course_seq",
    allocationSize = 1)
    private Long id;
    @NotNull
    @Column(name = "course_name")
    private String courseName;
    @NotNull
    @Column(name = "date_of_start")
    private LocalDate dateOfStart;
    private String description;
    @ManyToOne(cascade = CascadeType.REMOVE)
    private Instructor instructor;
    @ManyToMany(mappedBy = "courses", cascade = CascadeType.REMOVE,fetch = FetchType.EAGER)
    private Set<Group> groups;
    @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE,fetch = FetchType.EAGER)
    private Set<Lesson>lessons;
    @ManyToOne(cascade = {
            CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE
    })
    @ToString.Exclude
    private Company company;


}
