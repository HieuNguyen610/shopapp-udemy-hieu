package hieu.shopappudemyhoang.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse {
    private String message;
    private Object data;
}
