package com.hjnu.controller;

import com.hjnu.model.po.TrainInfo;
import com.hjnu.model.vo.*;
import com.hjnu.service.impl.TrainInfoService;
import com.hjnu.utils.RedisUtils;
import com.hjnu.service.impl.TrainScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * 列车时刻表查询的业务处理逻辑
 *
 *
 */
@RestController
@RequestMapping("/trainSchedule")
public class TrainScheduleController {

    @Resource
    private TrainScheduleService trainScheduleService;

    @Autowired
    private TrainInfoService trainInfoService;

    private static final Logger logger = LoggerFactory.getLogger(TrainInfoController.class);

    /**
     *
     * 根据起始站 目的站  查询符合条件的列车信息
     *
     * 对应前端的searchTrainSchedule请求
     */
    @RequestMapping(value ="/searchTrainSchedule",method = RequestMethod.GET)
    public TrainInfoReturnData GetTrainScheduleInfo(@RequestParam String train_start_station, String train_end_station) {


        List<TrainInfo>  trainInfos = trainInfoService.selectAllTrainInfo(0,2141);
        ArrayList<TrainInfo> result = new ArrayList<>();
        trainInfos.forEach(item->{
            if(item.getTrain_start_station().equals(train_start_station)&&item.getTrain_end_station().equals(train_end_station)){
                result.add(item);
            }
        });

        return new TrainInfoReturnData(1,result);
    }


    /**
     * 返回列车具体的经停信息
     * 对应前端的getTrainScheduleList请求
     */
    @RequestMapping(value ="/getTrainScheduleList",method = RequestMethod.GET)
    public TrainScheduleReturnData GetTrainScheduleInfoList(@RequestParam String train_start_station_no, String  train_end_station_no, String train_no) {

        List<TrainScheduleInfo> trainScheduleInfos = trainScheduleService.searchTrainScheduleInfoList(train_no,train_start_station_no,train_end_station_no);
        return new TrainScheduleReturnData(1,trainScheduleInfos);
    }


    /**
     *
     * 查询接续换乘路线
     */

    @RequestMapping(value ="/searchTransferSchedule",method = RequestMethod.GET)
    public TrainTransferScheduleReturnData GetTrainScheduleInfoList(@RequestParam String train_start_station, String train_end_station) {

       logger.info(train_start_station);
       logger.info(train_end_station);
       List<TrainTransferSchedule> trainTransferScheduleList = trainScheduleService.searchTransferSchedule(train_start_station,train_end_station);

        Iterator<TrainTransferSchedule> iterator = trainTransferScheduleList.iterator();

        while (iterator.hasNext())
        {
            TrainTransferSchedule trainTransferSchedule = iterator.next();
            int j =  getMin(trainTransferSchedule.getStart_time_2()) -getMin(trainTransferSchedule.getArrive_time_1());
            if(j> 150 || j<30)
            {
                iterator.remove();
            }
        }

        for(int i = 0 ; i<trainTransferScheduleList.size() ; i ++)
        {
            for(int j = 0 ; j <trainTransferScheduleList.size() - 1 - i ; j++)
            {
                int m  = getMin(trainTransferScheduleList.get(j).getStart_time_2()) -getMin(trainTransferScheduleList.get(j).getArrive_time_1());
                int n = getMin(trainTransferScheduleList.get(j+1).getStart_time_2()) -getMin(trainTransferScheduleList.get(j+1).getArrive_time_1());
                if(m>n)
                {
                    TrainTransferSchedule trainTransferSchedule = trainTransferScheduleList.get(j);
                    trainTransferScheduleList.set(j,trainTransferScheduleList.get(j+1));
                    trainTransferScheduleList.set(j+1,trainTransferSchedule);
                }
            }
        }
        for(TrainTransferSchedule trainTransferSchedule :trainTransferScheduleList)
        {
            logger.info(String.valueOf(getMin(trainTransferSchedule.getStart_time_2()) -getMin(trainTransferSchedule.getArrive_time_1())));
        }
            return new TrainTransferScheduleReturnData(1,trainTransferScheduleList);
    }

    public int getMin(String time) {
        String [] time2 = time.split(":");
        int Hour = Integer.parseInt(time2[0]);
        int Min = Integer.parseInt(time2[1]);

        return Hour *60 +Min;
    }

    /**
     * 添加车次信息
     */
    @RequestMapping(value ="/AddTrainInformation",method = RequestMethod.POST)
    public RespBean addTrainInformation(@RequestBody AddTrainInfoVo trainInfoVo){

        trainInfoService.AddTrainInfo(trainInfoVo);
        return new RespBean(1,"添加成功");

    }

}
