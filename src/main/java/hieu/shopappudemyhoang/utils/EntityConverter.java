package hieu.shopappudemyhoang.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import hieu.shopappudemyhoang.entity.Role;
import hieu.shopappudemyhoang.entity.User;
import hieu.shopappudemyhoang.response.RoleResponse;
import hieu.shopappudemyhoang.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EntityConverter {

    private final ObjectMapper objectMapper;

    public RoleResponse convertRoleEntityToResponse(Role entity) {
        return objectMapper.convertValue(entity, RoleResponse.class);
    }

    public List<RoleResponse> convertRolesEntityToResponses(List<Role> entities) {
        return entities.stream().map(this::convertRoleEntityToResponse).collect(Collectors.toList());
    }

    public UserResponse convertUserEntityToResponse(User entity) {
        return objectMapper.convertValue(entity, UserResponse.class);
    }
}
