package peakSoft.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import peakSoft.enums.Specialization;

import java.util.Set;

@Entity
@Table(name = "instructors")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "instructor_gen")
    @SequenceGenerator(name = "instructor_gen",
            sequenceName = "instructor_seq",
            allocationSize = 1)
    private Long id;
    @NotNull
    @Column(name = "first_name")
    private String firstName;
    @NotNull
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Enumerated(value = EnumType.STRING)
    private Specialization specialization;
    @OneToMany(mappedBy = "instructor",cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<Course>courses;
    @ManyToMany(mappedBy = "instructors",cascade ={CascadeType.DETACH, CascadeType.REFRESH,CascadeType.MERGE},fetch = FetchType.EAGER)
    private Set<Company>companies;

}
