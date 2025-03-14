package hieu.shopappudemyhoang.response;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderPagingResponse {
    private int count;
    private List<OrderResponse> orders;
}
