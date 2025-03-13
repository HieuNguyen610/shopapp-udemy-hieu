package hieu.shopappudemyhoang.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class UserRegisterRequest {
    @JsonProperty("fullname")
    private String fullName;

    @NotBlank(message = "Phone number is required")
    @Size(max = 10, min = 10, message = "Phone must have 10 characters")
    private String phoneNumber;

    private String address;

    @NotBlank(message = "Password is required")
    private String password;

    @JsonProperty("retype_password")
    private String retypePassword;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private boolean isActive;

    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;

    @JsonProperty("facebook_account_id")
    private int facebookAccountId;

    @JsonProperty("google_account_id")
    private int googleAccountId;

    @NotNull(message = "Role Id is required")
    @JsonProperty("role_id")
    private Long roleId;
}
