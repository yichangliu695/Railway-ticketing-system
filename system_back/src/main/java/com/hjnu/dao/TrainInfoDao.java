package com.hjnu.dao;

import com.hjnu.model.po.SeatInfo;
import com.hjnu.model.po.TrainInfo;
import com.hjnu.model.po.TrainParkingInfo;
import com.hjnu.model.vo.AddTrainInfoVo;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 *
 * trainInfo 持久层
 */
@Mapper
public interface TrainInfoDao {

    @Select("SELECT * FROM train_info LIMIT 100")
    List<TrainInfo> findAllTrainInfo();

    @Select("SELECT * FROM train_info LIMIT #{limit} OFFSET  #{offset}")
    List<TrainInfo> findTrainInfoByLimit(@Param("offset") int offset,@Param("limit") int limit);


    @Select("SELECT * FROM train_info where train_number = #{train_number}")
    TrainInfo findTrainInfo(@Param("train_number") String train_number);


    @Update("update train_info set train_running_type = '已停运' where train_no = #{train_no}")
    void updateTrainTypeStop(@Param("train_no") String train_no);

    @Update("update train_info set train_running_type = '正在运行' where train_no = #{train_no}")
    void updateTrainTypeStart(@Param("train_no") String train_no);

    @Select("select train_info.train_no as train_no ,train_number ,carriage_no ,seat_type,seat_count from train_info ,seat where train_info.train_no = seat.train_no and train_info.train_number=#{train_number}")
    List<SeatInfo> SelectSeatInfoByTrainNumber(@Param("train_number") String train_number);

    @Delete("delete from seat where train_no = #{train_no} and carriage_no = #{carriage_no}")
    void deleteTrainSeat(@Param("train_no") String train_no,@Param("carriage_no") String carriage_no);


    @Insert("insert into  seat (train_no,carriage_no,seat_type,seat_count) VALUES ( #{seatInfo.train_no}, #{seatInfo.carriage_no}, #{seatInfo.seat_type},#{seatInfo.seat_count})")
    void addTrainSeat(@Param("seatInfo") SeatInfo seatInfo);

    @Select("select train_number from train_info")
    List<String> selectAllTrainNumber();


    @Insert("insert into train_info (train_no,train_number,train_type,train_carriages,train_start_station,train_end_station," +
            "train_start_time,train_end_time,train_arrive_day,train_running_time,train_running_type) values (#{train_no}," +
            "#{trainInfo.train_number},#{trainInfo.train_type},#{trainInfo.train_carriages},#{trainInfo.train_start_station}," +
            "#{trainInfo.train_end_station},#{trainInfo.train_start_time},#{trainInfo.train_end_time},#{trainInfo.train_arrive_day}," +
            "#{trainInfo.train_running_time},#{trainInfo.train_running_type})")
    void AddTrainInfo(@Param("trainInfo") AddTrainInfoVo trainInfo,@Param("train_no") String train_no);


    @Insert("insert into train_parking_station (train_no,train_number,arrive_day_str,arrive_time,start_time,running_time," +
            "station_no,station_name) values (#{train_no},#{trainInfo.train_number}," +
            "#{trainInfo.arrive_day_str},#{trainInfo.arrive_time}," +
            "#{trainInfo.start_time},#{trainInfo.running_time}," +
            "#{trainInfo.station_no},#{trainInfo.station_name})")
    void AddTrainStation(@Param("trainInfo") TrainParkingInfo trainParkingInfo, @Param("train_no") String train_no);

    @Update("update train_info set train_start_station=#{train_start_station},train_end_station=#{train_end_station} where train_number=#{train_number}")
    void updateStation(@Param("train_number") String train_number, @Param("train_start_station") String train_start_station,@Param("train_end_station") String train_end_station);

    @Delete("delete from train_info where train_number=#{train_number}")
    void deleteTrainByNumber(@Param("train_number") String train_number);


    @Select("select train_number from train_info where train_start_station like #{start_station_name} and train_end_station like #{train_end_station}")
    String getTrainNumber(@Param("start_station_name") String start_station_name, @Param("end_station_name") String end_station_name);
}
