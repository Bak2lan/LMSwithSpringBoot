package peakSoft.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "groups")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "group_gen")
    @SequenceGenerator(name = "group_gen",
            sequenceName = "group_seq",
    allocationSize = 1)
    private Long id;
    @NotNull
    @Column(name = "group_name")
    private String groupName;
    @Column(name = "image_link")
    private String imageLink;
    private String description;
    @ToString.Exclude
    @ManyToMany(cascade = {
            CascadeType.REFRESH,
            CascadeType.DETACH
    })
    private List<Course>courses;
    @ToString.Exclude
    @OneToMany(mappedBy = "group",fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private List<Student>students;

}
