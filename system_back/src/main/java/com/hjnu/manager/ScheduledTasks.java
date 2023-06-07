package com.hjnu.manager;


import com.hjnu.model.vo.AllOrder;
import com.hjnu.model.vo.GetAllNoTripData;
import com.hjnu.service.OrderListService;
import com.hjnu.service.TrainTicketOrderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ScheduledTasks {

    @Resource
    private OrderListService orderListService;

    @Resource
    private TrainTicketOrderService trainTicketOrderService;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    @Scheduled(fixedRate = 60000)
    public void updateOrderStatus() {
        List<GetAllNoTripData> getAllNoTripDataList = orderListService.GetAllNoTripOrder();

        for (GetAllNoTripData getAllNoTripData : getAllNoTripDataList) {
            String TrainStartTime = trainTicketOrderService.getTrainStartTime(getAllNoTripData.getTrain_no(), getAllNoTripData.getStart_station_no());

            TrainStartTime = TrainStartTime + ":00";
            if (TrainStartTime.compareTo(String.valueOf(dateFormat)) < 0) {
                orderListService.UpdatePayOrderStatus(getAllNoTripData.getOrder_id());
            }

        }

        List<AllOrder> allNopayList = orderListService.GetAllNoPayOrder();


        for (AllOrder allNppay : allNopayList) {
            String order_create_date = allNppay.getOrder_create_time();
            order_create_date = order_create_date.substring(11);
            int a = Integer.parseInt(order_create_date.substring(0, 2));
            a = a + 1;
            if (a < 10) {
                order_create_date = "0" + a + order_create_date.substring(2);
            } else {
                order_create_date = a + order_create_date.substring(2);
            }

            if (order_create_date.compareTo(String.valueOf(dateFormat)) > 0) {
                orderListService.UpdateNoPayOrderStatus(allNppay.getOrder_id());
            }

        }

        System.out.println("现在时间：" + dateFormat.format(new Date()));

    }
}
