package com.hjnu.controller;

import com.hjnu.utils.RedisUtils;
import com.hjnu.model.vo.RespBean;
import com.hjnu.service.impl.PassengerService;
import com.hjnu.model.po.PassengerInfo;
import com.hjnu.model.vo.PassengerInfoReturnData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;


/**
 *
 * 乘客管理的业务处理层
 */
@RestController
@RequestMapping("/passenger")
public class PassengerController {

    @Resource
    private PassengerService passengerService;
    @Resource
    private RedisUtils redisUtils;


    /**
     *
     * 根据用户  获取此用户下的乘客信息
     */
    @RequestMapping(value ="/getPassengerInfo",method = RequestMethod.GET)
    public PassengerInfoReturnData getPassengerInfo(@RequestParam String token) {
        String user = redisUtils.get(token);
        String[] data = user.split(",");
        String user_phone_number = data[1];
        List<PassengerInfo> passengerInfoList = passengerService.selectPassenger(user_phone_number);
        return new PassengerInfoReturnData(1,passengerInfoList);


    }

    /**
     *
     * 用户添加乘客
     * 对应前端的addPassengerInfo请求

     */
    @RequestMapping(value ="/addPassengerInfo",method = RequestMethod.POST)
    public RespBean UserRegister(@Valid @RequestBody Map<String,Object> request, BindingResult bindingResult) {

        String token = (String) request.get("token");
        String passenger_phone_number = (String) request.get("passenger_phone_number");
        String passenger_real_name = (String) request.get("passenger_real_name");
        String  passenger_id_number = (String)request.get("passenger_id_number");
        String  passenger_type = (String)request.get("passenger_type");
        String passenger_address = (String) request.get("passenger_address");
        String user = redisUtils.get(token);
        String data [] = user.split(",");
        String user_phone_number = data[1];

        int type = 0 ;
        if(passenger_type.equals("成人")) {
            type = 1;
        }

        try {
            passengerService.insertPassenger(user_phone_number,passenger_phone_number,passenger_real_name,passenger_id_number,type,passenger_address);
            return new RespBean(1,"添加成功");
        } catch (Exception e) {
            return new RespBean(405,"添加失败");
        }

    }


    /**
     *
     * 删除乘客信息
     * 对应前端的deletePassengerInfo请求
     */
    @RequestMapping(value ="/deletePassengerInfo",method = RequestMethod.GET)
    public RespBean DeletePassengerInfo(@RequestParam String token,String passenger_phone_number) {

        String user = redisUtils.get(token);
        String data [] = user.split(",");
        String user_phone_number = data[1];
        try {
            passengerService.deletePassenger(user_phone_number,passenger_phone_number);
            return new RespBean(1,"删除成功");
        }
        catch (Exception e)
        {
            return new RespBean(405,"删除失败");
        }

    }

    @RequestMapping(value ="/getPassengerInfoByNumber",method = RequestMethod.GET)
    public PassengerInfoReturnData searchPassengerByNumber(@RequestParam String passenger_phone_number) {

            List<PassengerInfo> passengerInfoList = passengerService.selectPassengerBynumber(passenger_phone_number);
            return new PassengerInfoReturnData(1,passengerInfoList);
    }

    @RequestMapping(value ="/deletePassenger",method = RequestMethod.GET)
    public RespBean deletePassenger(@RequestParam String user_phone_number,String passenger_phone_number) {

        try {
            passengerService.deletePassenger(user_phone_number,passenger_phone_number);
            return new RespBean(1,"删除成功");
        }
        catch (Exception e)
        {
            return new RespBean(405,"删除失败");
        }
    }

}
