package com.hjnu.service.impl;

import com.hjnu.dao.PassengerDao;
import com.hjnu.dao.TrainInfoDao;
import com.hjnu.model.po.OrderList;
import com.hjnu.model.po.PassengerInfo;
import com.hjnu.model.po.SeatInfo;
import com.hjnu.model.po.TrainInfo;
import com.hjnu.model.vo.*;
import com.hjnu.dao.OrderListDao;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderListService {

    @Resource
    private OrderListDao orderListDao;

    @Resource
    private PassengerDao passengerDao;

    @Resource
    private TrainInfoDao trainInfoDao;


    /**
     * 所有订单
     */
    public List<GetAllOrderList> getAllOrderLists (String user_phone_number) {
        //查询订单表
        List<GetAllOrderList> allOrderLists = orderListDao.GetAllOrderList(user_phone_number);

        //补全座位号和座位类型
        allOrderLists.forEach(item ->{
            String seat_type=orderListDao.getSeatTypeByNo(item.getTrain_no(),item.getCarriage_no());
            item.setSeat_type(seat_type);

        });
        //补全车次信息
        allOrderLists.forEach(item ->{
            String trainNumber=trainInfoDao.getTrainNumber(item.getTrain_no());
            item.setTrain_number(trainNumber);
        });

        //补全真实姓名
        allOrderLists.forEach(item ->{
            String realName=passengerDao.getRealName(item.getPassenger_phone_number());
            item.setPassenger_real_name(realName);
        });

        return allOrderLists;
    }


    /**
     * 未支付订单
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

    /**
     * 已支付订单
     */
    public List<GetAllOrderList> getAlreadyPayOrderLists(String user_phone_number) {
        //获取所有订单
        List<GetAllOrderList> allOrderLists = getAllOrderLists(user_phone_number);

        List<GetAllOrderList> alreadyPayOrderList = new ArrayList<>();
        allOrderLists.forEach(item->{
            if(item.getOrder_status().equals("已支付")){
                alreadyPayOrderList.add(item);
            }
        });
        return alreadyPayOrderList;
    }

    public List<GetAllOrderList> getNotripOrderLists (String user_phone_number) {
        return orderListDao.GetNotripOrderList(user_phone_number);
    }

    public void RefundTicket(String user_phone_number ,String order_id) {
        orderListDao.RefundTicket(user_phone_number,order_id);
    }

    public  void ChangeTicket(String passenger_phone_number,String order_id) {
        orderListDao.ChangeTicket(passenger_phone_number,order_id);
    }


    public List<GetOrderList> getOrderInof(String order_id) {
        return orderListDao.GetOrderInfo(order_id);
    }

    public List<GetOrderList> GetOrderChagngeList(String user_phone_number, String datetime, String train_no,  String start_no,  String end_no,String passenger_phone_number) {
        return orderListDao.GetOrderChagngeList(user_phone_number,datetime,train_no,start_no,end_no,passenger_phone_number);
    }
    public String getOrderMoney(String order_id) {
        return orderListDao.GetOrderMoney(order_id);
    }


    public List<GetAllOrderList> GetAllOrder() {
        return orderListDao.GetAllOrder();
    }

    public void generateOrder(GenerateOrder generateOrder) {
        //获取列车信息
        String train_number = generateOrder.getTrain_number();
        TrainInfo trainInfo = trainInfoDao.getTrainInfoByNum(train_number);

        //获取乘客信息
        List<PassengerInfo> pas = generateOrder.getPassenger_data();
        //插入订单表
        for (PassengerInfo passenger : pas) {
            int rand1 = (int) (Math.random() * 9 + 1) * 1000;
            int rand2 = (int) (Math.random() * 9 + 1) * 100;
            String time = LocalDateTime.now()+"";
            //生成座位号
            String chars = "ABCDEFGHIZKLMNOPQRSTUVWXYZ"; //大写字母
            char seat_pre = chars.charAt((int)(Math.random() * 26)); //随机取一个字母
            OrderList orderList = new OrderList(passenger.getUser_phone_number(), passenger.getPassenger_phone_number(),
                    passenger.getPassenger_id_number(), trainInfo.getTrain_no(),
                    rand1 + "4", trainInfo.getTrain_start_station(),
                    rand1 + "5", trainInfo.getTrain_end_station(),
                    rand2 + "2", seat_pre+ "" + rand2, generateOrder.getOrder_money(),
                    time.substring(0,10), generateOrder.getOrder_status(), trainInfo.getTrain_start_time());

            orderListDao.insertOrder(rand1 + "9", orderList);

            SeatInfo seatInfo = new SeatInfo(trainInfo.getTrain_no(), train_number, orderList.getCarriage_no(), generateOrder.getSeat_type(), pas.size());
            passengerDao.insertData(seatInfo);

        }



    }

    /**
     * 支付订单
     */
    public void changeOrderStatus(String orderId) {
        //查找
//        OrderList orderList=orderListDao.getOrderById(orderId);
//        //删除
//        orderListDao.deleteById(orderId);
//        //插入
//        orderList.setOrder_status("已支付");
//        orderListDao.insertOrder(orderId,orderList);
        orderListDao.updateStatus("已支付",orderId);

    }
}
