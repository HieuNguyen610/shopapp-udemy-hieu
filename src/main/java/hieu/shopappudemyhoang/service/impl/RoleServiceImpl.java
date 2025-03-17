package hieu.shopappudemyhoang.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import hieu.shopappudemyhoang.entity.Role;
import hieu.shopappudemyhoang.repository.RoleRepository;
import hieu.shopappudemyhoang.request.RoleCreateRequest;
import hieu.shopappudemyhoang.response.RolePagingResponse;
import hieu.shopappudemyhoang.response.RoleResponse;
import hieu.shopappudemyhoang.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final ObjectMapper objectMapper;

    @Override
    public RolePagingResponse findAll() {
        Sort sort = Sort.by(Sort.Order.by("id"));
        List<Role> roles = roleRepository.findAll(sort);
        int count = (int) roleRepository.count();
        return RolePagingResponse.builder()
                .count(count)
                .roles(convertEntitiesToResponses(roles))
                .build();
    }

    @Override
    public RoleResponse addRole(RoleCreateRequest request) {
        String name;
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        } else {
            name = request.getName();
            if (roleRepository.findByName(name).isPresent()) {
                throw new IllegalArgumentException("Category name already exists");
            }
        }

        Role role = Role.builder()
                .name(name)
                .build();
        return convertEntityToResponse(roleRepository.save(role));
    }

    @Override
    public RoleResponse updateRole(Long id, RoleCreateRequest request) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isEmpty()) {
            throw new IllegalArgumentException("Role not found id = " + id);
        }

        Role updatingRole = role.get();
        String name;

        if (request.getName() == null || request.getName().isEmpty()) {
            throw new IllegalArgumentException("Request name is invalid");
        } else {
            name = request.getName();
            if (roleRepository.findByName(name).isPresent()) {
                throw new IllegalArgumentException("Role name already exists");
            }
        }
        updatingRole.setName(name);
        return convertEntityToResponse(roleRepository.save(updatingRole));
    }

    private RoleResponse convertEntityToResponse(Role entity) {
        return objectMapper.convertValue(entity, RoleResponse.class);
    }

    private List<RoleResponse> convertEntitiesToResponses(List<Role> entities) {
        return entities.stream()
               .map(this::convertEntityToResponse)
               .collect(Collectors.toList());
    }
}
