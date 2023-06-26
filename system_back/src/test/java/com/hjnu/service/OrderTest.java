package com.hjnu.service;

import com.hjnu.model.vo.GetAllOrderList;
import com.hjnu.service.impl.OrderListService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


/**
 * @author LiuYiChang
 * @date 2023/6/12 18:32
 */
@SpringBootTest
public class OrderTest {

    @Autowired
    private OrderListService orderListService;

    @Test
    public void getAllOrders(){
        List<GetAllOrderList> allOrderLists = orderListService.getAllOrderLists("131");
        System.out.println(allOrderLists.toString());
    }

}
