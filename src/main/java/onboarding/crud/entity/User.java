package onboarding.crud.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok Data;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name="email", unique = true, nullable = false)
    private String email;

}
