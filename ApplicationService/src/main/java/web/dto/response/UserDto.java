package web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.constants.RoleType;
import web.model.Email;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private UUID id;

    private String username;

    private String name;

    private String secondName;

    private RoleType userRole;

    private Email email;

    private Boolean isDeleted;

}
