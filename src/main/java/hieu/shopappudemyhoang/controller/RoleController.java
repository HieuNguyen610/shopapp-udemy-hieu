package hieu.shopappudemyhoang.controller;

import hieu.shopappudemyhoang.request.RoleCreateRequest;
import hieu.shopappudemyhoang.response.ApiResponse;
import hieu.shopappudemyhoang.response.RolePagingResponse;
import hieu.shopappudemyhoang.response.RoleResponse;
import hieu.shopappudemyhoang.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllCategories() {
        RolePagingResponse categories = roleService.findAll();
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Get all roles")
                .data(categories)
                .build());
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@Valid @RequestBody RoleCreateRequest request) {
        RoleResponse categoryResponse = roleService.addRole(request);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Add new role")
                .data(categoryResponse)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @Valid @RequestBody RoleCreateRequest request) {
        RoleResponse response = roleService.updateRole(id, request);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Update role")
                .data(response)
                .build());
    }
}

