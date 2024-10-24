package web.dto.request.account;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import web.constants.GenderType;

@Data
public class UserUpdateDto {

    @Length(min = 1, max = 50, message = "Длина имени должна быть от 1 до 50.")
    private String name;

    @Length(min = 1, max = 50, message = "Длина фамилии должна быть от 1 до 50.")
    private String secondName;

    @Length(max = 250, message = "Длина названия места проживания должна быть не более 250 символов.")
    private String city;

    @Length(max = 250, message = "Длина дня рождения должна быть не более 250 символов.")
    private String birthday;

    @Length(max = 250, message = "Длина сообщения в статусе должна быть не более 250 символов.")
    private String status;

    @Length(max = 500, message = "Длина описания должна быть не более 500 символов.")
    private String about;

    @Length(max = 250, message = "Длина сообщения о месте учебы должна быть не более 250 символов.")
    private String education;

    private GenderType gender;

    @Length(max = 250, message = "Длина сообщения о месте работы не должна быть более 250 символов.")
    private String work;
}
