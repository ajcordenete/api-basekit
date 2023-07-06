package com.ajcordenete.basekit.data;

import com.ajcordenete.basekit.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private LocalDate dateOfBirth;
    private String password;
    private Role role;
}
