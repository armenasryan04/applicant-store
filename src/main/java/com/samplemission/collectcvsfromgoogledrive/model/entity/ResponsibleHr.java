package com.samplemission.collectcvsfromgoogledrive.model.entity;

import com.samplemission.collectcvsfromgoogledrive.model.enums.HrRole;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_responsible_hr")
@EqualsAndHashCode(callSuper = true ,onlyExplicitlyIncluded = true)
public class ResponsibleHr extends PersistableEntity {
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "surname", length = 50)
    private String surname;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 250)
    private String password;

    @Column(name = "role", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private HrRole role;

    @OneToMany(cascade = CascadeType.REMOVE,fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = Applicant_.RESPONSIBLE_HR_ID, updatable = false, insertable = false)
    private List<Applicant> applicants = new ArrayList<>();

    public String getLogin()  {
        return this.email + " " + this.username;
    }

}