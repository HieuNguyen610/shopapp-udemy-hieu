package hieu.shopappudemyhoang.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoleCreateRequest {
    @NotBlank(message = "name cannot be empty")
    private String name;
}
