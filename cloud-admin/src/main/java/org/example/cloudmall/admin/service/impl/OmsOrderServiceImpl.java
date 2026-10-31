package org.example.cloudmall.admin.service.impl;

import com.github.pagehelper.PageHelper;
import org.example.cloudmall.admin.service.OmsOrderService;
import org.example.cloudmall.mapper.OmsOrderItemMapper;
import org.example.cloudmall.mapper.OmsOrderMapper;
import org.example.cloudmall.model.OmsOrder;
import org.example.cloudmall.model.OmsOrderExample;
import org.example.cloudmall.model.OmsOrderItem;
import org.example.cloudmall.model.OmsOrderItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OmsOrderServiceImpl implements OmsOrderService {

    @Autowired
    private OmsOrderMapper orderMapper;

    @Autowired
    private OmsOrderItemMapper orderItemMapper;

    @Override
    public List<OmsOrder> listOrder(Integer status, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        OmsOrderExample example = new OmsOrderExample();
        example.setOrderByClause("id desc");
        OmsOrderExample.Criteria criteria = example.createCriteria();
        if (status != null) {
            criteria.andStatusEqualTo(status);
        }
        return orderMapper.selectByExample(example);
    }

    @Override
    public OmsOrder getOrder(Long id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<OmsOrderItem> listOrderItem(Long orderId) {
        OmsOrderItemExample example = new OmsOrderItemExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        return orderItemMapper.selectByExample(example);
    }

    @Override
    public int updateOrder(Long id, OmsOrder order) {
        order.setId(id);
        return orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public int deleteOrder(Long id) {
        return orderMapper.deleteByPrimaryKey(id);
    }
}
