package org.example.cloudmall.admin.service;

import org.example.cloudmall.model.OmsOrder;
import org.example.cloudmall.model.OmsOrderItem;

import java.util.List;

public interface OmsOrderService {

    List<OmsOrder> listOrder(Integer status, int pageNum, int pageSize);

    OmsOrder getOrder(Long id);

    List<OmsOrderItem> listOrderItem(Long orderId);

    int updateOrder(Long id, OmsOrder order);

    int deleteOrder(Long id);
}
