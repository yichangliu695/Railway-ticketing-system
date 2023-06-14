package com.hjnu.dao;

import com.hjnu.model.po.PassengerInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 *
 * 在数据库的passenger上进行操作
 */
@Mapper
public interface PassengerDao {

    /**
     *
     * 查询某用户下的所有乘客
     */
    @Select("SELECT " +
            "user_phone_number," +
            "passenger_real_name," +
            "passenger_phone_number," +
            "passenger_id_number," +
            "passenger_type," +
            "passenger_address " +
            "FROM passenger where user_phone_number = #{user_phone_number}")
    List<PassengerInfo> findPassenger(@Param("user_phone_number") String user_phone_number);

    /**
     *
     * 添加乘客信息
     */
    @Insert("insert into  passenger (user_phone_number,passenger_phone_number,passenger_real_name,passenger_id_number,passenger_type,passenger_address) VALUES ( #{user_phone_number}, #{passenger_phone_number}, #{passenger_real_name},#{passenger_id_number},#{passenger_type},#{passenger_address})")
    void insertPassenger(@Param("user_phone_number") String user_phone_number,@Param("passenger_phone_number") String passenger_phone_number,@Param("passenger_real_name") String passenger_real_name,@Param("passenger_id_number") String passenger_id_number,@Param("passenger_type") int passenger_type,@Param("passenger_address") String passenger_address);

    /**
     * 删除乘客信息
     *
     */

    @Delete("delete from passenger where user_phone_number = #{ user_phone_number} and passenger_phone_number = #{passenger_phone_number} ")
    void deletePassenger(@Param("user_phone_number") String user_phone_number,@Param("passenger_phone_number") String passenger_phone_number);


    @Select("select * from passenger where passenger_phone_number = #{passenger_phone_number} ")
    List<PassengerInfo> searchPassenger(@Param("passenger_phone_number") String passenger_phone_number);


    @Select("select * from passenger")
    List<PassengerInfo> searchAllPassenger();


    @Select("select passenger_real_name from passenger where passenger_phone_number=#{passenger_phone_number}")
    String getRealName(@Param("passenger_phone_number") String passenger_phone_number);
}
