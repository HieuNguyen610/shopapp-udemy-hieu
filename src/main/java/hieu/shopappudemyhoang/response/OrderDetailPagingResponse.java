package hieu.shopappudemyhoang.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderDetailPagingResponse {

    private int count;
    private long orderId;
    private int page;
    private int size;
    private List<OrderDetailResponse> orderDetails;
}
