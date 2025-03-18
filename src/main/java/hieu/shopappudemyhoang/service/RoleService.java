package hieu.shopappudemyhoang.service;

import hieu.shopappudemyhoang.request.RoleCreateRequest;
import hieu.shopappudemyhoang.response.RolePagingResponse;
import hieu.shopappudemyhoang.response.RoleResponse;

public interface RoleService {
    RolePagingResponse findAll();

    RoleResponse addRole(RoleCreateRequest request);

    RoleResponse updateRole(Long id, RoleCreateRequest request);
}
