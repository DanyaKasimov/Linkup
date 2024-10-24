package web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import web.constants.RoleType;

import java.util.UUID;

@Data
@Builder
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private UUID id;

    private String username;

    private String name;

    private String secondName;

    @JsonIgnore
    private String password;

    @Enumerated(value = EnumType.STRING)
    private RoleType userRole;

    @OneToOne
    @JoinColumn(name = "email_id")
    private Email email;

    private Boolean isDeleted;
}