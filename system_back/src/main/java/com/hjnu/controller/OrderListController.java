package com.hjnu.controller;

import com.hjnu.model.vo.*;
import com.hjnu.service.impl.OrderListService;
import com.hjnu.utils.RedisUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author LiuYiChang
 * @date 2023/6/12 19:20
 */
@RestController
@RequestMapping("/order")
public class OrderListController {
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private OrderListService orderListService;
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    /**
     * 所有订单
     */
    @RequestMapping(value ="/getAllOrderList",method = RequestMethod.GET)
    public GetAllOrderListReturnData GetAllOrderList(@RequestParam String token) {
        String user = redisUtils.get(token);
        String[] data = user.split(",");
        String user_phone_number = data[1];
        //String user_phone_number="131";
        List<GetAllOrderList> getAllOrderList = orderListService.getAllOrderLists(user_phone_number);
        return new GetAllOrderListReturnData(1,getAllOrderList);
    }


    /**
     * 未支付订单
     */
    @RequestMapping(value ="/getNoPayOrderList",method = RequestMethod.GET)
    public GetAllOrderListReturnData GetNoPauOrderList(@RequestParam String token) {

        String user = redisUtils.get(token);
        String[] data = user.split(",");
        String user_phone_number = data[1];
       // String user_phone_number="131";
        List<GetAllOrderList> getNoPayOrderList = orderListService.getNopayOrderLists(user_phone_number);
        return new GetAllOrderListReturnData(1,getNoPayOrderList);

    }

    /**
     * 已支付订单
     */
    @RequestMapping(value ="/getAlreadyPayOrderList",method = RequestMethod.GET)
    public GetAllOrderListReturnData GetAlreadyPayOrderList(@RequestParam String token) {

        String user = redisUtils.get(token);
        String[] data = user.split(",");
        String user_phone_number = data[1];
        List<GetAllOrderList> getAlreadyPayOrderList = orderListService.getAlreadyPayOrderLists(user_phone_number);

        return new GetAllOrderListReturnData(1,getAlreadyPayOrderList);

    }

    @RequestMapping(value ="/refundTicket",method = RequestMethod.GET)
    public RespBean RefundTicket(@RequestParam String token, String order_id) {
        String user = redisUtils.get(token);
        String[] data = user.split(",");
        String user_phone_number = data[1];
        orderListService.RefundTicket(user_phone_number,order_id);
        return new RespBean(1,"退票成功，购票金额自动退回账户");
    }
    @RequestMapping(value ="/getNotripOrderList",method = RequestMethod.GET)
    public GetAllOrderListReturnData GetNoTripOrderList(@RequestParam String token) {

        String user = redisUtils.get(token);
        String[] data = user.split(",");
        String user_phone_number = data[1];
        List<GetAllOrderList> getNotripOrderListLists = orderListService.getNotripOrderLists(user_phone_number);
        logger.info(String.valueOf(getNotripOrderListLists.size()));
        for(GetAllOrderList getAllOrderList :getNotripOrderListLists) {
            getAllOrderList.setSeat_no(GetResult_Seat_no(getAllOrderList.getSeat_type(), Integer.parseInt(getAllOrderList.getSeat_no())));
        }
        return new GetAllOrderListReturnData(1,getNotripOrderListLists);

    }

    @RequestMapping(value ="/ticketChange",method = RequestMethod.POST)
    public RespBean TicketChange(@Valid @RequestBody Map<String,Object> request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError().getDefaultMessage());
        }
        String order_id = (String)request.get("order_id");
        String passenger_phone_number  =  (String)request.get("passenger_phone_number");

        orderListService.ChangeTicket(passenger_phone_number,order_id);
        return new RespBean(1,"改签成功");


    }

    @RequestMapping(value ="/getOrder",method = RequestMethod.POST)
    public GetOrderListReturnData getOrderInfo(@Valid @RequestBody Map<String,Object> request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        String order_id = (String)request.get("order_id");

        List<GetOrderList> getOrderLists = orderListService.getOrderInof(order_id);
        for(GetOrderList getOrderList:getOrderLists) {
            getOrderList.setSeat_no(GetResult_Seat_no(getOrderList.getSeat_type(), Integer.parseInt(getOrderList.getSeat_no())));
        }

        return new GetOrderListReturnData(1,getOrderLists);
    }


    @RequestMapping(value ="/getOrderChangeResult",method = RequestMethod.GET)
    public GetOrderListReturnData getOrderChangeResult(@RequestParam String token,String datetime,String train_no,String start_no,String end_no ,String passenger_phone_number)  {

        String user = redisUtils.get(token);

        String[] data = user.split(",");

        datetime = datetime.substring(0,10);
        String user_phone_number = data[1];
        logger.info(datetime);
        List<GetOrderList> getOrderLists =   orderListService.GetOrderChagngeList(user_phone_number,datetime,train_no,start_no,end_no,passenger_phone_number);
        logger.info(String.valueOf(getOrderLists.size()));
        for(GetOrderList getOrderList:getOrderLists) {
            getOrderList.setSeat_no(GetResult_Seat_no(getOrderList.getSeat_type(), Integer.parseInt(getOrderList.getSeat_no())));
        }

        return new GetOrderListReturnData(1,getOrderLists);

    }

    /**
     * 该订单要多少
     */
    @RequestMapping(value ="/getOrderMoney",method = RequestMethod.GET)
    public RespBean getOrderChangeResult(@RequestParam String order_id)  {

        String order_money = orderListService.getOrderMoney(order_id);

        return new RespBean(1,order_money);
    }

    /**
     * 获取所有订单
     */
    @RequestMapping(value ="/getAllOrder",method = RequestMethod.GET)
    public GetAllOrderListReturnData GetOrderList(){

        List<GetAllOrderList> getAllOrderListLists = orderListService.GetAllOrder();
        for(GetAllOrderList getAllOrderList :getAllOrderListLists) {
            getAllOrderList.setSeat_no(GetResult_Seat_no(getAllOrderList.getSeat_type(), Integer.parseInt(getAllOrderList.getSeat_no())));
        }

        return new GetAllOrderListReturnData(1,getAllOrderListLists);
    }

    /**
     * 通过手机号码查询订单
     */
    @RequestMapping(value ="/getOrderByPhoneNumber",method = RequestMethod.GET)
    public GetAllOrderListReturnData getOrderByPhoneNumber(@RequestParam String user_phone_number){

        List<GetAllOrderList> getAllOrderListLists = orderListService.getAllOrderLists(user_phone_number);
        for(GetAllOrderList getAllOrderList :getAllOrderListLists) {
            getAllOrderList.setSeat_no(GetResult_Seat_no(getAllOrderList.getSeat_type(), Integer.parseInt(getAllOrderList.getSeat_no())));
        }

        return new GetAllOrderListReturnData(1,getAllOrderListLists);
    }


    private String GetResult_Seat_no(String Seat_type,int seat_no) {
        String result_seat = null;
        if(Seat_type.equals("特等座") ) {
            result_seat = seat_no / 3 + 1 +"排";
            if(seat_no%3 == 0) {
                result_seat +="A座";
            }
            if(seat_no%3 == 1) {
                result_seat +="B座";
            }
            if(seat_no%3 == 2) {
                result_seat +="C座";
            }
        }
        if(Seat_type.equals("一等座") ) {
            result_seat = seat_no / 4 + 1 +"排";
            if(seat_no%4 == 0) {
                result_seat +="A座";
            }
            if(seat_no%4 == 1) {
                result_seat +="B座";
            }
            if(seat_no%4 == 2) {
                result_seat +="C座";
            }
            if(seat_no%4 == 3) {
                result_seat +="D座";
            }
        }
        if(Seat_type.equals("二等座") ) {
            result_seat = seat_no / 5 + 1 +"排";
            if(seat_no%5 == 0) {
                result_seat +="A座";
            }
            if(seat_no%5 == 1) {
                result_seat +="B座";
            }
            if(seat_no%5 == 2) {
                result_seat +="C座";
            }
            if(seat_no%5 == 3) {
                result_seat +="D座";
            }
            if(seat_no%5 == 4) {
                result_seat +="E座";
            }
        }
        if(Seat_type.equals("软卧") ) {

            result_seat = seat_no / 2 + 1 +"排" ;
            if(seat_no%2 == 0) {
                result_seat +="上铺";
            }
            if(seat_no%2 == 1) {
                result_seat +="下铺";
            }

        }

        if(Seat_type.equals("硬卧") ) {
            result_seat = seat_no / 3 + 1 +"排";
            if(seat_no%3 == 0) {
                result_seat +="上铺";
            }
            if(seat_no%3 == 1) {
                result_seat +="中铺";
            }
            if(seat_no%3 == 2) {
                result_seat +="下铺";
            }

        }
        if(Seat_type.equals("硬坐") ) {
            result_seat = seat_no / 6 + 1 +"排";
            if(seat_no%6 == 0) {
                result_seat +="A座";
            }
            if(seat_no%6 == 1) {
                result_seat +="B座";
            }
            if(seat_no%6 == 2) {
                result_seat +="C座";
            }
            if(seat_no%6 == 3) {
                result_seat +="D座";
            }
            if(seat_no%6 == 4) {
                result_seat +="E座";
            }
            if(seat_no%6 == 5) {
                result_seat +=" F座";
            }
        }
        return result_seat;
    }

}
