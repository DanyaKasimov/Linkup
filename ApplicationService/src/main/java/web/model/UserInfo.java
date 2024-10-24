package web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.constants.GenderType;


import java.util.UUID;

@Data
@Builder
@Entity
@Table(name = "users_info")
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    @Id
    private UUID id;

    private String city;

    private String birthday;

    private String status;

    private String about;

    private String education;

    @Enumerated(value = EnumType.STRING)
    private GenderType gender;

    private String work;

    private boolean isClose;
}