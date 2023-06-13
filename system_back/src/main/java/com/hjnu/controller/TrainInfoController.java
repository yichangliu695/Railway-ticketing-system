package com.hjnu.controller;

import com.hjnu.model.po.SeatInfo;
import com.hjnu.model.po.TrainInfo;
import com.hjnu.model.po.TrainParkingInfo;
import com.hjnu.model.vo.*;
import com.hjnu.service.impl.TrainInfoService;
import com.hjnu.service.impl.StationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * 有关列车信息查询的业务逻辑层
 */
@RestController
@RequestMapping("/train")
public class TrainInfoController {
    private static final Logger logger = LoggerFactory.getLogger(TrainInfoController.class);
    @Resource
    private TrainInfoService  trainInfoService;

    @Resource
    private StationService stationService;

    /**
     * 查询所有列车信息
     * 对应前端的getTrainInfoData请求
     */
    @RequestMapping(value ="/traininfo",method = RequestMethod.GET)
    public TrainInfoReturnData TrainInfo(Integer offset, Integer limit) {
        List<TrainInfo>  trainInfos = trainInfoService.selectAllTrainInfo(offset,limit);
        if(!trainInfos.isEmpty()) {
            return new TrainInfoReturnData(1,trainInfos);
        }
        return new TrainInfoReturnData(404,trainInfos);
    }

    /**
     * 根据车次查询列车信息
     */
    @RequestMapping(value ="/searchtraininfo",method = RequestMethod.GET)
    public SearchTrainInfoReturnData SearchTrainInfo(String train_number) {

        TrainInfo trainInfo = trainInfoService.selectTrainInfo(train_number);
        if(!trainInfo.toString().equals("")) {
            return new SearchTrainInfoReturnData(1, trainInfo);
        }

            return new SearchTrainInfoReturnData(404,trainInfo);
        }

    /**
     *
     *根据车次查询列车的经停信息
     */
    @RequestMapping(value ="/searchtrainparkingInfo",method = RequestMethod.GET)
    public TrainParkingInfoReturnData SearchTrainInfoList(String train_number) {
        List<TrainParkingInfo> trainParkingInfos = stationService.selectTrainParkingInfo(train_number);
        return new TrainParkingInfoReturnData(1,trainParkingInfos);
    }

    @RequestMapping(value ="/updateTrainTypeStart",method = RequestMethod.GET)
    public RespBean updateTrainTypeStart(String train_no) {
        try {
            trainInfoService.updateTrainTypeStart(train_no);
            return new RespBean(1,"修改成功");
        } catch (Exception e) {
            return new RespBean(404,"修改失败");
        }
    }

    @RequestMapping(value ="/updateTrainTypeStop",method = RequestMethod.GET)
    public RespBean updateTrainTypeStop(String train_no) {
        try {
            trainInfoService.updateTrainTypeStop(train_no);
            return new RespBean(1,"修改成功");
        } catch (Exception e) {
            return new RespBean(404,"修改失败");
        }
    }

    @RequestMapping(value ="/selectSeatInfoByTrainNumber",method = RequestMethod.GET)
    public SeatInfoReturnData SelectSeatInfoByTrainNumber(String train_number) {
        try {
            return new SeatInfoReturnData(1, trainInfoService.SelectSeatInfoByTrainNumber(train_number));
        } catch (Exception e) {
            return new SeatInfoReturnData(404,null);
        }
    }

    @RequestMapping(value ="/deleteTrainSeat",method = RequestMethod.GET)
    public RespBean deleteTrainSeat(String train_no,String carriage_no) {
        try {
            trainInfoService.deleteTrainSeat(train_no,carriage_no);
            return new RespBean(1, "删除成功");
        } catch (Exception e) {
            return new RespBean(404,"删除失败");
        }
    }

    @RequestMapping(value ="/addTrainSeat",method = RequestMethod.GET)
    public RespBean addTrainSeat(String train_no,String carriage_no,String seat_type,String seat_count) {
        try {
           SeatInfo seatInfo = new SeatInfo(train_no,null,carriage_no,seat_type,Integer.parseInt(seat_count));
           trainInfoService.addTrainSeat(seatInfo);
           return new RespBean(1, "添加成功");
        } catch (Exception e) {
            return new RespBean(404,"添加失败");
        }
    }


    @RequestMapping(value ="/getAllTrainNumber",method = RequestMethod.GET)
    public GetAllTrainNumberListReturnData getAllTrainNumber() {
        try {
            List<String> trainNumberLists = trainInfoService.selectAllTrainNumber();
            List<TrainNumberData> trainNumberDatas = new ArrayList<>();
            for (String trainNumberList : trainNumberLists) {
                TrainNumberData trainNumberData = new TrainNumberData(trainNumberList, "111");
                trainNumberDatas.add(trainNumberData);
            }
            return new GetAllTrainNumberListReturnData(1,trainNumberDatas);
        } catch (Exception e) {
           return new GetAllTrainNumberListReturnData(404,null);

       }
    }

    @RequestMapping(value ="/getAllStationName",method = RequestMethod.GET)
    public GetAllTrainNumberListReturnData getAllStationName() {
        try {
            List<String> stationNameList = stationService.selectAllStationName();
            List<TrainNumberData> trainNumberDatas = new ArrayList<>();
            for (String s : stationNameList) {
                TrainNumberData trainNumberData = new TrainNumberData(s, "111");
                trainNumberDatas.add(trainNumberData);
            }
            return new GetAllTrainNumberListReturnData(1,trainNumberDatas);
        } catch (Exception e) {
            return new GetAllTrainNumberListReturnData(404,null);
        }
    }


    @RequestMapping(value ="/addTrainStation",method = RequestMethod.POST)
    public RespBean addTrainStation(@Valid @RequestBody Map<String,Object> request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError().getDefaultMessage());
        }
        String train_no = (String) request.get("train_no");
        String train_number = (String) request.get("train_number");
        String station_no = (String) request.get("station_no");
        String station_name = (String) request.get("station_name");
        String arrive_time = (String) request.get("arrive_time");
        String start_time = (String) request.get("start_time");
        String running_time = (String) request.get("running_time");
        String arrive_day_str = (String) request.get("arrive_day_str");
        try {
            TrainParkingInfo trainParkingInfo = new TrainParkingInfo(
                    station_no,station_name,train_number,start_time,arrive_time,running_time,arrive_day_str
            );
            trainInfoService.AddTrainStation(trainParkingInfo,train_no);
            return new RespBean(1,"插入成功");
        }catch (Exception e) {
            return new RespBean(403,"插入失败");
        }
    }

    /**
     * 修改站点信息
     */
    @RequestMapping(value ="/changeSiteInfo",method = RequestMethod.POST)
    public RespBean changeStationInfo(@RequestBody UpdateStation updateStation){
        trainInfoService.updateTrainStation(updateStation);
        return new RespBean(1,"修改成功");
    }

    /**
     * 删除车次信息
     */
    @RequestMapping(value = "/deleteTrainInfo",method = RequestMethod.POST)
    public RespBean deleteTrainInfo(@RequestBody UpdateStation updateStation){
        try {
            trainInfoService.deleteTrainInfo(updateStation.getTrain_number());
        } catch (Exception e) {
            return new RespBean(404,"删除失败");
        }
        return new RespBean(1,"删除成功");
    }

}
