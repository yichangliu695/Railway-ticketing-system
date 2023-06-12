package com.hjnu.service.impl;

import com.hjnu.model.vo.TrainScheduleInfo;
import com.hjnu.model.vo.TrainTransferSchedule;
import com.hjnu.dao.TrainScheduleDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * 对应TrainScheduleDao
 * 进行列车时刻查询
 */
@Service
public class TrainScheduleService {

    @Resource
    private TrainScheduleDao trainScheduleDao;

    /**
     *
     * 查询列车时刻表
     * @param start_station
     * @param end_station
     * @return
     */
    public List<TrainScheduleInfo> searchTrainScheduleInfo(String start_station,String end_station)
    {
            return trainScheduleDao.searchTrainSchedule(start_station,end_station);
    }


    /**
     * 查询列车的详细经停信息
     * @param train_no
     * @param start_no
     * @param end_no
     * @return
     */
    public List<TrainScheduleInfo> searchTrainScheduleInfoList(String train_no, String start_no,String end_no)
    {
        return trainScheduleDao.searchTrainScheduleList(train_no,start_no,end_no);
    }

    public List<TrainTransferSchedule> searchTransferSchedule(String start_station, String end_station)
    {
            return trainScheduleDao.searchTransferSchedule(start_station,end_station);
    }
}
