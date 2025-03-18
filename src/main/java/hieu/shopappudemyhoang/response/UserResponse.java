package hieu.shopappudemyhoang.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class UserResponse {

    private String fullName;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    private String address;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private boolean isActive;

    private LocalDate dateOfBirth;

    private int facebookAccountId;

    private int googleAccountId;

    private List<RoleResponse> roles;

}
