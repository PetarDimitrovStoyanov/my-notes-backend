package com.mynotesapp.backend.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity {

    @Column(columnDefinition = "VARCHAR(255)", nullable = false, unique = true)
    private String email;

    @Column(name = "full_name", columnDefinition = "VARCHAR(255)", nullable = false)
    private String fullName;

    @Column(columnDefinition = "VARCHAR(100)")
    private String password;

    @OneToMany(mappedBy="owner")
    private List<NoteEntity> notes;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private List<RoleEntity> roles;

}
