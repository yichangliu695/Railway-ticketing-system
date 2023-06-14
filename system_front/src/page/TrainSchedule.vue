<template>
    <div class="fillcontain">
        <meta  http-equiv="Cache-Control" content="no-cache,no-store,must-revlidate">
        <meta  http-equiv="Expires" content="O">
        <meta http-equiv="Pragma" content="no-cache">

        <head-top></head-top>
        <el-form :model="searchForm"  ref="searchForm">
        <el-row :gutter="20" style="margin-left: 300px;margin-top: 10px;width: 800px">
            <el-col :span="6"><div class="grid-content bg-purple">
                <el-autocomplete
                    class="inline-input"
                    v-model="searchForm.start_station"
                    :fetch-suggestions="querySearch"
                    placeholder="请输入始发站"
                    :trigger-on-focus="true"
                    @select="handleSelect"
                ></el-autocomplete>
            </div></el-col>
            <el-col :span="6"><div class="grid-content bg-purple" style="margin-left: 20px">

                <el-autocomplete
                    class="inline-input"
                    v-model="searchForm.end_station"
                    :fetch-suggestions="querySearch"
                    placeholder="请输入终点站"
                    :trigger-on-focus="true"
                    @select="handleSelect"
                ></el-autocomplete>
            </div></el-col>
            <el-col :span="6"><div class="grid-content bg-purple">
                <el-button type="primary" round  @click="submitForm('searchForm')">搜索</el-button>
            </div></el-col>
            <el-switch
                style="margin-top: 30px;margin-left: 20px"
                v-model="value1"
                @click.native="handelUpdate()"
                inactive-text="按开车时间排序"
                active-text="按运行时间排序">
            </el-switch>
        </el-row>
        </el-form>
        <div class="table_container">
            <el-table
                :data="tableData"
                style="width: 100%"
                row-key="train_number"
                :tree-props="{children: 'tableData_c', hasChildren: 'true'}">

                <el-table-column
                  label="车次"
                  prop="train_number">
                </el-table-column>
                <el-table-column
                  label="出发站"
                  prop="start_station">
                </el-table-column>
                <el-table-column
                  label="到达站"
                  prop="end_station">
                </el-table-column>
                <el-table-column
                    label="出发时间"
                    prop="start_time">
                </el-table-column>
                <el-table-column
                    label="到达时间"
                    prop="arrive_time">
                </el-table-column>
                <el-table-column
                    label="运行及停靠时间"
                    prop="running_time">
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button
                            size="mini"
                            @click="handleEdit(scope.$index, scope.row)">订购</el-button>
                    </template>
                </el-table-column>
            </el-table>
<!--            <el-row style="margin-top: 30px;margin-left: 150px">
                <el-col :span="24"><div class="grid-content bg-purple-dark" style="color: #1c8de0">如果查询结果中没有满足需求的车次，您还可以使用接续换乘 功能，查询途中换乘一次的部分列车余票情况</div></el-col>
            </el-row>
            <el-row style="margin-top: 30px;margin-left: 400px">
                <el-col :span="24"><div class="grid-content bg-purple">
                    <el-button type="primary"   @click="transfer()">接续换乘</el-button>
                </div></el-col>
            </el-row>-->
        </div>
            <el-dialog
                title="购票"
                :visible.sync="dialogVisible"
                width="80%"
                :before-close="handleClose"
                :close-on-click-modal="false"
            >
                <el-dialog
                    width="30%"
                    title="添加乘客"
                    :visible.sync="innerVisible"
                    append-to-body>
                    <el-form :rules="rules" class="form_contianer" :model="passenger" ref="passenger">
                        <el-form-item prop="passenger_phone_number">
                            <el-input v-model="passenger.passenger_phone_number" placeholder="电话号码"><span>dsfsf</span></el-input>
                        </el-form-item>
                        <el-form-item prop="passenger_real_name">
                            <el-input  placeholder="真实姓名" v-model="passenger.passenger_real_name"></el-input>
                        </el-form-item>
                        <el-form-item prop="passenger_id_number">
                            <el-input  placeholder="身份证号" v-model="passenger.passenger_id_number"></el-input>
                        </el-form-item>
                    </el-form>
                     <span slot="footer" class="dialog-footer">
                    <el-button   @click="innerVisible=false" >取消</el-button>
                <el-button type="primary" @click="addPass">确认</el-button>
                </span>
                </el-dialog>
                <el-scrollbar style="height:400px;">
                    <el-steps :active="active" align-center style="margin-top: 10px">
                        <el-step title="添加乘客" ></el-step>
                        <el-step title="选择座位等级" ></el-step>
                        <el-step title="付款" ></el-step>
                        <el-step title="订票成功" ></el-step>
                    </el-steps>
                    <el-table :data="passenger_data" v-show="active==0" style="width: 1000px;margin-left: 80px;margin-top: 20px">
                        <el-table-column property="passenger_real_name" label="乘客姓名" ></el-table-column>
                        <el-table-column property="passenger_phone_number" label="乘客电话号码" ></el-table-column>
                        <el-table-column property="passenger_id_number" label="身份证号"></el-table-column>
                    </el-table>
                    <el-row>
                        <el-button style="margin-top: 30px; margin-left: 80px;margin-right: 50px" @click="add" v-show="active==0"  type="primary" size="mini" >添加乘客</el-button>
                    </el-row>
                    <el-row>
                    <div style="margin-top: 30px; margin-left: 50px;margin-right: 50px"  v-show="this.active==3">
                        <el-alert
                            title="恭喜您购票成功,请在已支付订单中查看详细的信息"
                            type="success"
                            effect="dark">
                        </el-alert>
                    </div>
                    </el-row>
                    <el-row v-show="active==1">
                        <div  style="margin-top: 30px; margin-left: 100px;margin-right: 50px">
                        <el-radio v-model="seat_type" label="特等座">特等座</el-radio>
                        <el-radio v-model="seat_type" label="一等座">一等座</el-radio>
                        <el-radio v-model="seat_type" label="二等座">二等座</el-radio>
                        </div>
                    </el-row>
                    <el-row>
                        <div style="margin-top: 30px; margin-left: 50px;margin-right: 50px"  v-show="active==2">
                            <el-alert
                                :title="price_detail"
                                type="success"
                                effect="dark">
                            </el-alert>
                            <el-button style="margin-top: 30px;" @click="innerVisible1=true" type="primary">点击付款</el-button>
                        </div>
                    </el-row>
                </el-scrollbar>
                <span slot="footer" class="dialog-footer">
                    <el-button  v-show="active<2" type="primary" @click="next" >下一步</el-button>
                <el-button type="primary" v-show="active>=2" @click="close">{{this.operator}}</el-button>
                </span>
<!--                <el-row>
                    <el-col :span="12"><div class="grid-content bg-purple">
                        <div class="block">

                            <el-image
                                style="width: 300px; height: 500px"
                                :src="url2"
                                :fit="fit"></el-image>
                        </div>
                    </div></el-col>
                    <el-col :span="12"><div class="grid-content bg-purple-light"> <div class="block">
                        <el-image
                            style="width: 300px; height: 500px"
                            :src="url1"
                            :fit="fit"></el-image>
                    </div></div></el-col>
                </el-row>-->
                <el-dialog
                    width="60%"
                    title="支付成功"
                    :visible.sync="innerVisible1"
                    append-to-body>
                    <el-card class="box-card">
                        <div slot="header" class="clearfix">
                            <span>详细账单</span>
                        </div>
                        <div  class="text item">
                            {{'座位等级:'+this.seat_type}}
                        </div>
                        <div  class="text item">
                            {{'单价:'+this.ridao*100}}
                        </div>
                        <div  class="text item">
                            {{'票数:'+this.passenger_data.length}}
                        </div>
                        <div  class="text item">
                            {{'总金额:'+this.amout_money}}
                        </div>
                    </el-card>
                    <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="payMoney">确认</el-button>
                </span>
                </el-dialog>
            </el-dialog>
    </div>
</template>

<script>
    import headTop from '../components/headTop'
    import TicketOrder from "./TicketOrder";
    import {baseUrl, baseImgPath} from '@/config/env'
    import {getTrainScheduleList,searchTrainSchedule,getAllStationName,generateOrderInformation} from '@/api/getData'
    export default {
        data(){
            return {
                value1: false,
                city: {},
                offset: 0,
                limit: 20,
                count: 0,
                tableData: [

                ],
                currentPage: 1,
                selectTable: {},
                dialogFormVisible: false,
                categoryOptions: [],
                selectedCategory: [],
                address: {},
                searchForm:
                    {
                        start_station: '',
                        end_station:"",
                        date:""

                    },
                stationData:[],
                dialogVisible: false,
                active:0,
                operator: '',
                passenger_data:[],
                innerVisible:false,
                passenger:{
                    assenger_phone_number: '',
                    passenger_real_name:'',
                    passenger_id_number:''
                },
                rules: {
                    passenger_phone_number: [
                        { required: true, message: '请输入内容', trigger: 'blur' },
                        { pattern: /^1[3|5|7|8|9]\d{9}$/, message: '请输入正确的号码格式', trigger: 'blur' }
                    ],
                    passenger_real_name: [
                        { required: true, message: '请输入内容', trigger: 'blur' }
                    ],
                    passenger_id_number: [
                        { required: true, message: '请输入内容', trigger: 'blur' },
                        {
                            pattern: /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/,
                            message: '证件号码格式有误！',
                            trigger: 'blur'
                        }
                    ],
                    passenger_type: [
                        { required: true, message: '请输入内容', trigger: 'blur' }
                    ],
                    passenger_address: [
                        { required: true, message: '请输入内容', trigger: 'blur' }
                    ],
                },
                seat_type:'特等座',
                amout_money:0,
                price_detail:'',
                ridao: 1,
                innerVisible1: false,
                train_start_station:'',
                train_end_station:'',
                train_start_time:''
            }
        },
       async created(){
           const res = await getAllStationName()
           this.stationData = res.dataLists;
        },
    	components: {
    		headTop,
            TicketOrder
    	},
        watch:{
            active:{
                handler(a,b){
                     if(this.active==2){
                         this.operator='稍后支付'
                         if(this.seat_type === '特等座')
                             this.ridao = 1
                         else  if(this.seat_type ==='二等座')
                             this.ridao = 2
                         else
                             this.ridao = 3
                         this.amout_money = this.passenger_data.length * this.ridao * 100
                         this.price_detail = '您一共花费了'+this.amout_money+'元，请点击下方按钮完成付款,也可点击右下角按钮，稍后在未支付订单中进行付款'
                     }

                    if(this.active==3)
                        this.operator='完成'
                }
            }
        },
        methods: {

            async submitForm(formName) {
                this.$refs[formName].validate(async (valid) => {
                    if (valid) {
                        this.tableData = [];
                        const res = await searchTrainSchedule({train_start_station:this.searchForm.start_station , train_end_station:this.searchForm.end_station})
                         console.log('列车查询')
                        console.log(res)
                        if (res.status == 1) {
                            this.$message({
                                type: 'success',
                                message: '搜索成功'

                            });
                            // trainScheduleInfoList
                            this.tableData = [];
                            /*for(var i = 0 ; i < res.trainInfos.length ; i++ )
                            {
                                 console.log(res.trainInfos[i])
                                let   tableData = {
                                    train_number:"",
                                    start_station:"",
                                    end_station:"",
                                    start_time:"",
                                    arrive_time:"",
                                    running_time:"",
                                    tableData_c:[
                                        {
                                            train_number:"07",
                                            start_station:"088",
                                            end_station:"09",
                                            start_time:"049",
                                            arrive_time:"059",
                                            running_time:"069"
                                        }]};
                                tableData.train_number = res.trainInfos[i].train_number;
                                tableData.start_station =res.trainInfos[i].start_station;
                                tableData.end_station = res.trainInfos[i].end_station;
                                tableData.start_time = res.trainInfos[i].start_time;
                                tableData.arrive_time = res.trainInfos[i].arrive_time;
                                let start_running_time = res.trainInfos[i].start_running_time;
                                let end_running_time = res.trainInfos[i].end_running_time;
                                //console.log(start_running_time)
                                let start_running_time2 = start_running_time.split(":");
                                let end_running_time2 = end_running_time.split(":");
                                let start_second =  parseInt(start_running_time2[0]) *60   + parseInt(start_running_time2[1]);
                                let end_second =  parseInt(end_running_time2[0]) *60   + parseInt(end_running_time2[1]);
                                let sub  = end_second -start_second;
                                let h = Math.floor(sub/60);
                                let min = sub%60;
                                let h2   =   h.toString();
                                let min2   =   min.toString();
                                let min3 = "";
                                let h3 = "";
                                if(h2.length == 1)
                                {
                                     h3 = "0" +h2;
                                }
                                else
                                {
                                     h3 = h2;
                                }
                                if(min2.length == 1)
                                {
                                     min3 = "0" +min2;
                                }
                                else
                                {
                                     min3 = min2;
                                }

                                const result = h3+":"+min3;

                                tableData.running_time = result;

                                const TrainScheduleList  = await getTrainScheduleList({train_start_station_no:res.trainInfos[i].start_no , train_end_station_no:res.trainInfos[i].end_no ,train_no :res.trainInfos[i].train_no})
                                if(TrainScheduleList.status == 1)
                                {
                                    var tableData_c = [];
                                    for(var j = 0 ; j < TrainScheduleList.trainScheduleInfoList.length ; j++ )
                                    {
                                        var tableData_temp = {}
                                        tableData_temp.train_number = TrainScheduleList.trainScheduleInfoList[j].train_number+"--"+j;
                                        tableData_temp.start_station =TrainScheduleList.trainScheduleInfoList[j].start_station;
                                        tableData_temp.end_station = TrainScheduleList.trainScheduleInfoList[j].end_station;
                                        tableData_temp.start_time = TrainScheduleList.trainScheduleInfoList[j].start_time;
                                        tableData_temp.arrive_time = TrainScheduleList.trainScheduleInfoList[j].arrive_time;
                                        let start_running_time = TrainScheduleList.trainScheduleInfoList[j].start_running_time;
                                        let end_running_time = TrainScheduleList.trainScheduleInfoList[j].end_running_time;
                                        let start_running_time2 = start_running_time.split(":");
                                        let end_running_time2 = end_running_time.split(":");
                                        let start_second =  parseInt(start_running_time2[0]) *60   + parseInt(start_running_time2[1]);
                                        let end_second =  parseInt(end_running_time2[0]) *60   + parseInt(end_running_time2[1]);
                                        let sub  = end_second -start_second;
                                        let h = Math.floor(sub/60);
                                        let min = sub%60;
                                        let h2   =   h.toString();
                                        let min2   =   min.toString();
                                        let min3 = "";
                                        let h3 = "";
                                        if(h2.length == 1)
                                        {
                                            h3 = "0" +h2;
                                        }
                                        else
                                        {
                                            h3 = h2;
                                        }
                                        if(min2.length == 1)
                                        {
                                            min3 = "0" +min2;
                                        }
                                        else
                                        {
                                            min3 = min2;
                                        }

                                        let result = h3+":"+min3;
                                        tableData_temp.running_time = result;
                                        tableData_c.push(tableData_temp);


                                    }
                                    tableData.tableData_c = tableData_c;
                                }
                                this.tableData.push(tableData);
                                this.TrainRank();

                            }*/
                            for(var i=0;i< res.trainInfos.length ;i++){
                                const tableData = {};
                              //  tableData.train_no = res.trainInfos[i].train_no;
                                tableData.train_number = res.trainInfos[i].train_number;
                                tableData.type = res.trainInfos[i].train_type;
                                tableData.carriages = res.trainInfos[i].train_carriages;
                                tableData.start_station = res.trainInfos[i].train_start_station;
                                tableData.end_station = res.trainInfos[i].train_end_station;
                                tableData.start_time = res.trainInfos[i].train_start_time;
                                tableData.arrive_time = res.trainInfos[i].train_end_time;
                                tableData.running_time = res.trainInfos[i].train_running_time;
                               // tableData.train_arrive_day = res.trainInfos[i].train_arrive_day
                                console.log(tableData)
                                this.tableData.push(tableData);
                            }
                        }else{
                            this.$message({
                                type: 'error',
                                message: '搜索失败，没有对应的列车'
                            });
                        }
                    }

                });
            },
        TrainRank()
        {
                if(this.value1 === false)
                {
                    for(let i = 0 ; i < this.tableData.length ; i++)
                    {
                        for(let j = 0 ; j <this.tableData.length - i -1 ; j++ )
                        {
                            if(this.transferTime(this.tableData[j].start_time) >this.transferTime(this.tableData[j+1].start_time))
                            {
                                let temp = this.tableData[j];
                                this.tableData[j] = this.tableData[j+1];
                                this.tableData[j+1] = temp;
                            }
                        }
                    }
                }
                else
                {
                    for(let i = 0 ; i < this.tableData.length ; i++)
                    {
                        for(let j = 0 ; j <this.tableData.length - i -1 ; j++ )
                        {
                            if(this.transferTime(this.tableData[j].running_time) >this.transferTime(this.tableData[j+1].running_time))
                            {
                                let temp = this.tableData[j];
                                this.tableData[j] = this.tableData[j+1];
                                this.tableData[j+1] = temp;
                            }
                        }
                    }
                }
        },
            transferTime(time)
            {
                let time2 = time.split(":");
                let second =  parseInt(time2[0]) *60   + parseInt(time2[1]);
                return second;
            },


            transfer()
            {
                this.$router.push('/TrainTransfer')
            },
            handelUpdate()
            {
                this.TrainRank();

            },
            async querySearch(queryString, cb) {
                var houseNumberList = this.stationData;
                let results = queryString ? houseNumberList.filter(this.createFilter(queryString)) : houseNumberList;

                clearTimeout(this.timeout);
                this.timeout = setTimeout(() => {
                    cb(results);
                }, 1000 * Math.random());

            },
            createFilter(queryString) {
                return (houseNumber) => {
                    return (houseNumber.value.toLowerCase().indexOf(queryString.toLowerCase()) !== -1);
                };


            },
            handleEdit(index,row) {
                //this.$router.push('/TransferTicketOrder')
                // this.train_start_station = row.
                console.log('列车信息')
                console.log(row)
                this.train_start_station = row.start_station
                this.train_end_station = row.end_station
                this.train_start_time = row.start_time
                this.dialogVisible = true
            },
            close(){
                  if(this.active==2){
                      generateOrderInformation({
                          passenger_data:this.passenger_data,
                          order_money: this.amout_money,
                          train_start_station: this.train_start_station,
                          train_end_station: this.train_end_station,
                          train_start_time: this.train_start_time,
                          order_status: '未支付'
                      })
                  }
                  this.dialogVisible=false;
                  this.passenger_data = []
                  this.active=0;
            },
            next(){
                 this.active++;
            },
            add(){
                     this.innerVisible=true
            },
            addPass() {
                this.passenger_data.push(this.passenger)
                this.passenger={}
                this.innerVisible=false
            },
            handleDelete(index,row){
                  console.log('删除数据')
                 this.passenger_data.splice(index,1)
            },
            payMoney(){
                this.active ++
                this.innerVisible1 = false
                generateOrderInformation({
                    passenger_data:this.passenger_data,
                    order_money: this.amout_money,
                    train_start_station: this.train_start_station,
                    train_end_station: this.train_end_station,
                    train_start_time: this.train_start_time,
                    order_status: '已支付'
                })
            }
        },

    }
</script>

<style lang="less">
	@import '../style/mixin';
    .demo-table-expand {
        font-size: 0;
    }
    .demo-table-expand label {
        width: 90px;
        color: #99a9bf;
    }
    .demo-table-expand .el-form-item {
        margin-right: 0;
        margin-bottom: 0;
        width: 50%;
    }
    .table_container{
        padding: 20px;
    }
    .Pagination{
        display: flex;
        justify-content: flex-start;
        margin-top: 8px;
    }
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
        border-color: #20a0ff;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 120px;
        height: 120px;
        line-height: 120px;
        text-align: center;
    }
    .avatar {
        width: 120px;
        height: 120px;
        display: block;
    }

    .el-scrollbar__wrap {
        overflow-x: hidden!important;
    }

    .text {
        font-size: 14px;
    }

    .item {
        margin-bottom: 18px;
    }

    .clearfix:before,
    .clearfix:after {
        display: table;
        content: "";
    }
    .clearfix:after {
        clear: both
    }

    .box-card {
        width: 480px;
    }

</style>
