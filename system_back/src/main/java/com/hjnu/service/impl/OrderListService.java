package com.hjnu.service.impl;

import com.hjnu.model.po.SeatInfo;
import com.hjnu.model.vo.AllOrder;
import com.hjnu.model.vo.GetAllNoTripData;
import com.hjnu.model.vo.GetAllOrderList;
import com.hjnu.model.vo.GetOrderList;
import com.hjnu.dao.OrderListDao;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderListService {

    @Resource
    private OrderListDao orderListDao;

    /**
     * 所有订单
     * @param user_phone_number
     * @return
     */
    public List<GetAllOrderList> getAllOrderLists (String user_phone_number) {
        //查询订单表
        List<GetAllOrderList> allOrderLists = orderListDao.GetAllOrderList(user_phone_number);
        //补全座位号和座位类型
        allOrderLists.forEach(item ->{
            String seat_type=orderListDao.getSeatTypeByNo(item.getSeat_no());
            if(seat_type!=null){
                item.setSeat_type(seat_type);
            }
        });

        return allOrderLists;
    }



    public List<GetAllOrderList> getNotripOrderLists (String user_phone_number)
    {
        return orderListDao.GetNotripOrderList(user_phone_number);
    }

    /**
     * 未支付订单
     * @param user_phone_number
     * @return
     */
    public List<GetAllOrderList> getNopayOrderLists (String user_phone_number) {
        List<GetAllOrderList> allOrderLists = getAllOrderLists(user_phone_number);
        List<GetAllOrderList> noPayOrderList = new ArrayList<>();
        allOrderLists.forEach(item->{
            if(item.getOrder_status().equals("未支付")){
                noPayOrderList.add(item);
            }
        });
        return noPayOrderList;
    }

    public void RefundTicket(String user_phone_number ,String order_id)
    {
        orderListDao.RefundTicket(user_phone_number,order_id);
    }

    public  void ChangeTicket(String passenger_phone_number,String order_id)
    {
        orderListDao.ChangeTicket(passenger_phone_number,order_id);
    }


    public List<GetOrderList> getOrderInof(String order_id)
    {
        return orderListDao.GetOrderInfo(order_id);
    }

    public List<GetOrderList> GetOrderChagngeList(String user_phone_number, String datetime, String train_no,  String start_no,  String end_no,String passenger_phone_number)
    {
        return orderListDao.GetOrderChagngeList(user_phone_number,datetime,train_no,start_no,end_no,passenger_phone_number);
    }
    public String getOrderMoney(String order_id)
    {
        return orderListDao.GetOrderMoney(order_id);
    }


    public List<GetAllOrderList> GetAllOrder()
    {
        return orderListDao.GetAllOrder();
    }

    public   void UpdateNoPayOrderStatus( int order_id)
    {
        orderListDao.UpdateNoPayOrderStatus(order_id);
    }
    public   void UpdatePayOrderStatus( String order_id)
    {
        orderListDao.UpdatePayOrderStatus(order_id);
    }

    public   List<GetAllNoTripData>  GetAllNoTripOrder()
    {
            return orderListDao.GetAllNoTripOrder();
    }
    public  List<AllOrder>  GetAllNoPayOrder()
    {
        return orderListDao.GetAllNoPayOrder();
    }

    public List<AllOrder> GetAllNoTripOrderByPassenger(String passenger_phone_number)
    {
        return orderListDao.GetAllNoTripOrderByPassenger(passenger_phone_number);
    }

}
