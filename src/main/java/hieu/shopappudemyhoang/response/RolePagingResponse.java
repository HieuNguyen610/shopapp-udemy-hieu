package hieu.shopappudemyhoang.response;

import hieu.shopappudemyhoang.entity.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Getter
@Builder
public class RolePagingResponse {
    private int count;
    private List<Role> roles;
}
