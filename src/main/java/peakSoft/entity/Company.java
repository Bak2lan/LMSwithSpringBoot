package peakSoft.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "companies")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "company_gen")
    @SequenceGenerator(name = "company_gen",
    sequenceName = "company_seq",
    allocationSize = 1)
    private Long id;
    @NotNull
    private String name;
    private String image;
    private String country;
    private String address;
    @Column(name = "phone_number")
    private String phoneNumber;
    @ManyToMany(cascade = CascadeType.REMOVE,fetch = FetchType.EAGER)
    private Set<Instructor> instructors;
    @OneToMany(mappedBy = "company",cascade = CascadeType.REMOVE,fetch = FetchType.EAGER)
    private Set<Course>courses;

}
