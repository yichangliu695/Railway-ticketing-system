package com.hjnu.service.impl;

import com.hjnu.model.po.SeatInfo;
import com.hjnu.model.po.TrainInfo;
import com.hjnu.model.po.TrainParkingInfo;
import com.hjnu.dao.TrainInfoDao;
import com.hjnu.model.vo.AddTrainInfoVo;
import com.hjnu.model.vo.UpdateStation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.List;

/**
 *对应TrainInfoDao
 */
@Service
public class TrainInfoService {

    @Resource
    private TrainInfoDao trainInfoDao;

    /**
     *
     * 分页查询
     */
    public List<TrainInfo> selectAllTrainInfo(int offset,int limit)
    {
        return trainInfoDao.findTrainInfoByLimit(offset,limit);
    }

    /**
     *
     * 查询对应车次的列车信息
     */
    public TrainInfo selectTrainInfo(String train_number)
    {
        return trainInfoDao.findTrainInfo(train_number);
    }

    public void updateTrainTypeStop(String train_no)
    {
        trainInfoDao.updateTrainTypeStop(train_no);
    }
    public void updateTrainTypeStart(String train_no)
    {
        trainInfoDao.updateTrainTypeStart(train_no);
    }

    public  List<SeatInfo> SelectSeatInfoByTrainNumber(String train_number)
    {
        return trainInfoDao.SelectSeatInfoByTrainNumber(train_number);
    }
    public void deleteTrainSeat(String train_no,String carriage_no)
    {
        trainInfoDao.deleteTrainSeat(train_no,carriage_no);
    }
    public void addTrainSeat(SeatInfo seatInfo)
    {
        trainInfoDao.addTrainSeat(seatInfo);
    }

    public  List<String> selectAllTrainNumber()
    {
        return trainInfoDao.selectAllTrainNumber();
    }


    public void AddTrainStation(TrainParkingInfo trainParkingInfo, String train_no) {

        trainInfoDao.AddTrainStation(trainParkingInfo,train_no);
    }

    public void updateTrainStation(UpdateStation updateStation) {
        String train_number = updateStation.getTrain_number();
        String start_station_name = updateStation.getTrain_start_station();
        String end_station_name = updateStation.getTrain_end_station();
        trainInfoDao.updateStation(train_number,start_station_name,end_station_name);
    }

    public void deleteTrainInfo(String train_number) {
        trainInfoDao.deleteTrainByNumber(train_number);
    }

    /**
     * 添加车次信息
     */
    public void AddTrainInfo(AddTrainInfoVo trainInfo) {
        int rand=(int)(Math.random()*9+1)*10000;
        trainInfoDao.AddTrainInfo(trainInfo,rand+"");
    }
}
