package com.hjnu.service.impl;

import com.hjnu.model.po.PassengerInfo;
import com.hjnu.dao.PassengerDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * 对应PassengerDao层 对应乘客信息
 */
@Service
public class PassengerService {

    @Resource
    private PassengerDao passengerDao;

    /**
     *
     * 查询对应用户下的所有乘客信息
     */
    public List<PassengerInfo>  selectPassenger(String user_phone_number)
    {
        return passengerDao.findPassenger(user_phone_number);
    }

    /**
     *
     *
     * 添加乘客
     */
    public void insertPassenger(String user_phone_number, String passenger_phone_number, String passenger_real_name, String passenger_id_number,int passenger_type, String passenger_address)
    {
        passengerDao.insertPassenger(user_phone_number,passenger_phone_number,passenger_real_name,passenger_id_number,passenger_type,passenger_address);
    }

    /**
     *
     * 删除乘客
     */
    public void deletePassenger(String user_phone_number, String passenger_phone_number)
    {
        passengerDao.deletePassenger(user_phone_number,passenger_phone_number);
    }

    public List<PassengerInfo> selectPassengerBynumber(String passenger_phone_number)
    {
        return passengerDao.searchPassenger(passenger_phone_number);
    }


    public List<PassengerInfo> searchAllPassenger()
    {
        return passengerDao.searchAllPassenger();
    }
}
