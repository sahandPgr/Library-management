package library_management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import library_management.entity.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private Long id;

    @NotBlank
    private String fullname;

    @Email
    @NotBlank
    private String email;

    private String password;

    private UserRole role;
}