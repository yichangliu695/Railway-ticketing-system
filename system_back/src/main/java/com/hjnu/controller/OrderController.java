package com.hjnu.controller;

import com.hjnu.model.vo.*;
import com.hjnu.utils.RedisUtils;
import com.hjnu.service.impl.OrderListService;
import com.hjnu.service.impl.TrainTickerQueryService;
import com.hjnu.service.impl.TrainTicketOrderService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 订单控制器
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private TrainTickerQueryService trainTickerQueryService;

    @Resource
    private OrderListService orderListService;

    @Resource
    private TrainTicketOrderService trainTicketOrderService;

    @Resource
    private RedisUtils redisUtils;


    /**
     *
     * 具体的高铁动车选座算法 根据不同的 要求进行选座
     */
    public boolean equals(List<TrainSeatQuery> trainCarriageSeatCountList , int i) {
        for(TrainSeatQuery trainSeatQuery:trainCarriageSeatCountList) {
            if(Integer.parseInt(trainSeatQuery.getSeat_no()) == i) {
                return false;
            }
        }
        return true;
    }


    /**
     * 获得 本次订单 订单号
     */
    @RequestMapping(value ="/getOrderList",method = RequestMethod.GET)
    public GetOrderListReturnData GetOrderList(@RequestParam String token, String datetime, String train_no, String start_no, String end_no) {

        String user = redisUtils.get(token);
        String[] data = user.split(",");
        String user_phone_number = data[1];
        List<GetOrderList> orderLists = trainTicketOrderService.getOrderList(user_phone_number,train_no,start_no,end_no);
        return  new GetOrderListReturnData(1,orderLists);

    }

    /**
     * 支付成功  获取订单信息
     */
    @RequestMapping(value ="/paySuccess",method = RequestMethod.POST)
    public RespBean PaySuccess(@Valid @RequestBody Map<String,Object> request) {
        String order_id_list = (String)request.get("order_list");
        order_id_list = order_id_list.substring(0,order_id_list.length()-1);
        String [] order_list = order_id_list.split(",");

        for (String s : order_list) {
            trainTicketOrderService.UpdateOrderPaySuccess(s);
        }
        return new RespBean(1,"支付成功");
    }


    /**
     * 产生订单
     */
    @RequestMapping(value = "/generateOrderInformation",method = RequestMethod.POST)
    public RespBean generateOrderInformation(@RequestBody GenerateOrder generateOrder ){
        try {
            orderListService.generateOrder(generateOrder);
        }catch (Exception e){
            return new RespBean(500,"失败");
        }

        return new RespBean(1,"成功");
    }

    /**
     * 支付订单
     */
    @RequestMapping(value="/changeOrderStatus",method = RequestMethod.GET)
    public RespBean changeOrderStatus (@RequestParam("order_id") String orderId){

        try{
            orderListService.changeOrderStatus(orderId);
        }catch (Exception e){
            return new RespBean(400,"失败");
        }

        return new RespBean(1,"成功");
    }


}
