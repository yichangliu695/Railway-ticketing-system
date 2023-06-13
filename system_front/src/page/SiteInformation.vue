<template>
    <div class="fillcontain">
        <meta  http-equiv="Cache-Control" content="no-cache,no-store,must-revlidate">
        <meta  http-equiv="Expires" content="O">
        <meta http-equiv="Pragma" content="no-cache">

        <head-top></head-top>
        <div class="table_container">
            <el-form :model="searchForm"  ref="searchForm">
                <el-row :gutter="20" style="margin-top: 10px">
                    <el-col :span="12"><div class="grid-content bg-purple">
                        <!--<el-input v-model="searchForm.train_number" placeholder="请输入要搜索的列车号">-->

                        <!--</el-input>-->
                        <el-autocomplete
                            class="inline-input"
                            v-model="searchForm.train_number"
                            :fetch-suggestions="querySearch"
                            placeholder="请输入要搜索的列车号"
                            :trigger-on-focus="true"
                            @select="handleSelect"
                        ></el-autocomplete>
                    </div>
                    </el-col>
                    <el-col :span="6"><div class="grid-content bg-purple">
                        <el-button type="primary" round  @click="submitForm('searchForm')">搜索</el-button>
                    </div>
                    </el-col>
                    <el-col :span="6"><div class="grid-content bg-purple">
                        <el-button type="primary" round  @click="showAll">显示全部列车</el-button>
                    </div>
                    </el-col>
                </el-row>
            </el-form>
            <el-table
                :data="tableData"
                highlight-current-row
                style="width: 100% ;margin-top: 20px">
                <el-table-column

                    property="train_number"
                    label="车次"
                >
                </el-table-column>
<!--                <el-table-column
                    property="train_type"
                    label="列车类型"
                >
                </el-table-column>-->
<!--                <el-table-column
                    property="train_carriages"
                    label="车厢数"
                >
                </el-table-column>-->
                <el-table-column
                    property="train_start_station"
                    label="始发站">
                </el-table-column>
                <el-table-column
                    property="train_end_station"
                    label="终点站">
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button
                            size="mini"
                            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button
                            size="mini"
                            type="danger"
                            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
<!--                <el-table-column>
                    <el-button type="primary">删除</el-button>
                </el-table-column>-->
<!--                <el-table-column
                    property="train_start_time"
                    label="开车时间">
                </el-table-column>
                <el-table-column
                    property="train_end_time"
                    label="到达时间">
                </el-table-column>
                <el-table-column
                    property="train_running_time"
                    label="总时间">
                </el-table-column>
                <el-table-column
                    property="train_arrive_day"
                    label="到达日期">
                </el-table-column>-->
            </el-table>
            <div class="Pagination" style="text-align: left;margin-top: 10px;">
                <!--<el-pagination-->
                <!--@size-change="handleSizeChange"-->
                <!--@current-change="handleCurrentChange"-->
                <!--:current-page="currentPage"-->
                <!--:page-size="20"-->
                <!--layout="total, prev, pager, next"-->
                <!--:total="count">-->
                <!--</el-pagination>-->
                <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-size="limit"
                    layout="total, prev, pager, next, jumper"
                    :total="count">
                </el-pagination>
            </div>
            <el-dialog
                title="车次修改"
                :visible.sync="dialogVisible1"
                width="50%"
                :close-on-click-modal="false"
                >
                <el-form :model="changSiteInfo" ref="changSiteInfo">
                    <el-form-item prop="train_number" >
                        <el-input    v-model="changSiteInfo.train_number"  placeholder="车次"  ></el-input>
                    </el-form-item>
                    <el-form-item prop="train_start_station">
                        <el-input  placeholder="始发站" v-model="changSiteInfo.train_start_station"></el-input>
                    </el-form-item>
                    <el-form-item prop="train_end_station">
                        <el-input  placeholder="终点站" v-model="changSiteInfo.train_end_station"></el-input>
                    </el-form-item>
<!--                    <el-form-item prop="user_gender">
                        <el-input  placeholder="性别" v-model="ChangeForm.user_gender"></el-input>
                    </el-form-item>
                    <el-form-item prop="user_id_number">
                        <el-input  placeholder="身份证号" v-model="ChangeForm.user_id_number"></el-input>
                    </el-form-item>
                    <el-form-item prop="user_address">
                        <el-input  placeholder="地址" v-model="ChangeForm.user_address"></el-input>
                    </el-form-item>


                    <el-form-item>
                        <el-button type="primary" @click="submitForm('ChangeForm')" class="submit_btn">修改</el-button>
                    </el-form-item>-->
                </el-form>
                <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible1 = false">取 消</el-button>
    <el-button type="primary" @click="submit()">确 定</el-button>
  </span>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    import headTop from '../components/headTop'
    import {getUserList, getTrainInfoData,SearchTrainInfoData,getAllTrainNumber,changeSiteInfo,deleteTrainInfo} from '@/api/getData'
    import {getCookie} from "../config/store_cookie";
    export default {
        data(){
            return {
                tableData: [
                ],searchForm:
                    {
                        train_number: ''
                    }
                ,
                currentRow: null,
                offset: 0,
                limit:20 ,
                count: 0,
                currentPage: 1,
                trainData:['D1644','D1634'],
                dialogVisible1: false,
                changSiteInfo: {
                    train_number: '',
                    train_start_station: '',
                    train_end_station: ''
                },
                formName: 'changSiteInfo'

            }
        },
        components: {
            headTop,
        },
        created(){
           this.initData();

        },
        methods: {
             initData(){
               /* try{

                    const res = await getAllTrainNumber()
                    this.trainData = res.dataLists;
                    const TrainInfoData = await getTrainInfoData({offset: 0, limit: 2141});
                    if (TrainInfoData.status == 1) {

                        this.tableData = TrainInfoData.trainInfos;
                        this.count = TrainInfoData.trainInfos.length;
                        this.getLists();
                    }else{
                        throw new Error('获取数据失败');
                    }
                    this.getLists();
                }catch(err){
                    console.log('获取数据失败', err);
                }*/
                this.getLists()
                 //this.getInfoList()
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                this.offset = (val - 1)*this.limit;
                this.getLists()
            },
            async getLists(){
                const TrainInfoData = await getTrainInfoData({offset: this.offset, limit: this.limit});
                this.tableData = [];
                console.log('列车信息')
                console.log(TrainInfoData)
                for(var i = 0 ; i < TrainInfoData.trainInfos.length ; i++ )
                {
                    const tableData = {};
                    tableData.train_no = TrainInfoData.trainInfos[i].train_no;
                    tableData.train_number = TrainInfoData.trainInfos[i].train_number;
                    tableData.train_type = TrainInfoData.trainInfos[i].train_type;
                    tableData.train_carriages = TrainInfoData.trainInfos[i].train_carriages;
                    tableData.train_start_station = TrainInfoData.trainInfos[i].train_start_station;
                    tableData.train_end_station = TrainInfoData.trainInfos[i].train_end_station;
                    tableData.train_start_time = TrainInfoData.trainInfos[i].train_start_time;
                    tableData.train_end_time = TrainInfoData.trainInfos[i].train_end_time;
                    tableData.train_running_time = TrainInfoData.trainInfos[i].train_running_time;
                    tableData.train_arrive_day = TrainInfoData.trainInfos[i].train_arrive_day
                    this.tableData.push(tableData);
                }
                this.$forceUpdate();
                console.log(this.tableData)
            },
            getInfoList () {
                  /* console.log('axios请求')
                    this.$axios.get('http://localhost:8080/train/traininfo', {
                        offset: this.offset,
                        limit: this.limit
                    }).then(response => {
                        console.log(axios)
                        console.log( response.data)
                    }, error => {
                        console.log('错误', error.message)
                    })*/
                },
            async submitForm(formName) {
                this.$refs[formName].validate(async (valid) => {
                    if (valid) {
                        const res = await SearchTrainInfoData({train_number:this.searchForm.train_number})
                        if (res.status == 1) {
                            this.$message({
                                type: 'success',
                                message: '搜索成功'
                            });
                            this.tableData = []
                            const tableData = {};
                            tableData.train_no = res.trainInfo['train_no'];
                            tableData.train_number = res.trainInfo['train_number'];
                            tableData.train_type = res.trainInfo['train_type'];
                            tableData.train_carriages = res.trainInfo['train_carriages;']
                            tableData.train_start_station = res.trainInfo['train_start_station'];
                            tableData.train_end_station = res.trainInfo['train_end_station'];
                            tableData.train_start_time = res.trainInfo['train_start_time'];
                            tableData.train_end_time =res.trainInfo['train_end_time'];
                            tableData.train_running_time = res.trainInfo['train_running_time'];
                            tableData.train_arrive_day = res.trainInfo['train_arrive_day'];
                            this.tableData.push(tableData);
                           // this.count=1;

                        }else{
                            this.$message({
                                type: 'error',
                                message: '搜索失败，没有对应的列车'
                            });
                        }
                    }

                });
            },
            async showAll()
            {
                this.initData()
            },
            async querySearch(queryString, cb) {
                var houseNumberList = this.trainData;
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
            handleEdit (index,row) {
                this.dialogVisible1 = true
                this.changSiteInfo.train_number = row.train_number
                this.changSiteInfo.train_start_station = row.train_start_station
                this.changSiteInfo.train_end_station = row.train_end_station
            },
             handleDelete (index,row) {
                this.tableData.splice(index,1);
                const res =  deleteTrainInfo({
                    train_number: row.train_number
                })
                 this.initData();
            },
            async submit () {
                 this.dialogVisible1 = false
                this.$refs[this.formName].validate(async (valid) => {
                    if (valid) {
                        const res = await changeSiteInfo({
                            train_number:this.changSiteInfo.train_number,
                            train_start_station: this.changSiteInfo.train_start_station,
                            train_end_station: this.changSiteInfo.train_end_station
                        })
                        this.initData();
                        if (res.status == 1) {
                            this.$message({
                                type: 'success',
                                message: '修改成功'
                            });

                            this.initData();
                        }else{
                            this.$message({
                                type: 'error',
                                message: res.message
                            });
                        }
                    } else {
                        this.$notify.error({
                            title: '错误',
                            message: '请输入正确的用户名密码',
                            offset: 100
                        });
                        return false;
                    }
                });
                 this.initData()
            }
        },
    }
</script>

<style lang="less">
    @import '../style/mixin';
    .table_container{
        padding: 20px;
    }
    .form_contianer{
        .wh(320px, auto);
        .ctp(100px,400px);
        padding: 25px;
        border-radius: 5px;
        text-align: center;
        /*background-color: #fff;*/
        .submit_btn{
            width: 100%;
            font-size: 16px;
        }
    }
</style>
