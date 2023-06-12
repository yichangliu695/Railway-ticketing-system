<template>
    <div  class="fillcontain">
        <meta  http-equiv="Cache-Control" content="no-cache,no-store,must-revlidate">
        <meta  http-equiv="Expires" content="O">
        <meta http-equiv="Pragma" content="no-cache">

        <head-top></head-top>
        <header class="admin_title">添加车次</header>
         <div class="parent">
        <el-form :rules="rules" class="child" :model="ChangeForm" ref="ChangeForm">
            <el-row :gutter="20">
                <el-col :span="12">
                    <div class="grid-content bg-purple">
                        <el-form-item prop="train_number">
                            <el-input v-model="ChangeForm.train_number" placeholder="车次"><span>dsfsf</span></el-input>
                        </el-form-item>
                    </div>
                </el-col>
                <el-col :span="12">
                    <div class="grid-content bg-purple">
                        <el-form-item prop="train_type">
                            <el-input v-model="ChangeForm.train_type" placeholder="列车类型"><span>dsfsf</span></el-input>
                        </el-form-item>
                    </div>
                </el-col>
                <el-col :span="12">
                    <div class="grid-content bg-purple">
                        <el-form-item prop="train_carriages">
                            <el-input v-model="ChangeForm.train_carriages" placeholder="车箱数"><span>dsfsf</span></el-input>
                        </el-form-item>
                    </div>
                </el-col>
                <el-col :span="12">
                    <div class="grid-content bg-purple">
                        <el-form-item prop="train_start_station">
                            <el-input v-model="ChangeForm.train_start_station" placeholder="始发站"><span>dsfsf</span></el-input>
                        </el-form-item>
                    </div>
                </el-col>
                <el-col :span="12">
                    <div class="grid-content bg-purple">
                        <el-form-item prop="train_end_station">
                            <el-input v-model="ChangeForm.train_end_station" placeholder="终点站"><span>dsfsf</span></el-input>
                        </el-form-item>
                    </div>
                </el-col>
                <el-col :span="12">
                    <div class="grid-content bg-purple">
                        <el-form-item prop="train_start_time">
                            <el-input v-model="ChangeForm.train_start_time" placeholder="开车时间"><span>dsfsf</span></el-input>
                        </el-form-item>
                    </div>
                </el-col>
                <el-col :span="12">
                    <div class="grid-content bg-purple">
                        <el-form-item prop="train_end_time">
                            <el-input v-model="ChangeForm.train_end_time" placeholder="到达时间"><span>dsfsf</span></el-input>
                        </el-form-item>
                    </div>
                </el-col>
                <el-col :span="12">
                    <div class="grid-content bg-purple">
                        <el-form-item prop="train_running_time">
                            <el-input v-model="ChangeForm.train_running_time" placeholder="总时间"><span>dsfsf</span></el-input>
                        </el-form-item>
                    </div>
                </el-col>
                <el-col :span="12">
                    <div class="grid-content bg-purple">
                        <el-form-item prop="train_arrive_day">
                            <el-input v-model="ChangeForm.train_arrive_day" placeholder="到达日期"><span>dsfsf</span></el-input>
                        </el-form-item>
                    </div>
                </el-col>
                <el-col :span="12">
                    <div class="grid-content bg-purple">
                        <el-form-item prop="train_running_type">
                            <el-input v-model="ChangeForm.train_running_type" placeholder="当前状态"><span>dsfsf</span></el-input>
                        </el-form-item>
                    </div>
                </el-col>
            </el-row>

            <el-form-item>
                <el-button style="margin-left: 545px;" type="primary" @click="submitForm('ChangeForm')" class="submit_btn">添加车次</el-button>
            </el-form-item>
        </el-form>
         </div>
    </div>

</template>

<script>
    import headTop from '../components/headTop'
    import {mapState} from 'vuex'
    import {addPassengerInfo} from '@/api/getData'
    import {setCookie,getCookie} from "../config/store_cookie";
    export default {
        data(){
            return {
                tableData:{},
                ChangeForm: {
                    train_number: '',
                    train_type:'',
                    train_carriages:'',
                    train_start_station:'',
                    train_end_station:'',
                    train_start_time: '',
                    train_end_time: '',
                    train_running_time:'',
                    train_arrive_day: '',
                    train_running_type: ''
                },
                rules: {
                    passenger_real_name: [
                        { required: true, message: '请输入内容', trigger: 'blur' }
                    ],
                    passenger_id_number: [
                        { required: true, message: '请输入内容', trigger: 'blur' }
                    ],
                    passenger_type: [
                        { required: true, message: '请输入内容', trigger: 'blur' }
                    ],
                    passenger_address: [
                        { required: true, message: '请输入内容', trigger: 'blur' }
                    ],
                },
                showLogin: true,
            }
        },
        created(){

        },
        components: {
            headTop,
        },
        computed: {
        },
        methods: {
            async submitForm(formName) {
                this.$refs[formName].validate(async (valid) => {
                    if (valid) {

                        const res = await addPassengerInfo({token:getCookie("token"),passenger_phone_number: this.ChangeForm.passenger_phone_number,
                            passenger_real_name:this.ChangeForm.passenger_real_name,passenger_id_number:this.ChangeForm.passenger_id_number,passenger_type:this.ChangeForm.passenger_type
                            , passenger_address:this.ChangeForm. passenger_address})

                        if (res.status == 1) {
                            this.$message({
                                type: 'success',
                                message: '添加成功'
                            });

                            this.initData();
                        }else{
                            this.$message({
                                type: 'error',
                                message: "添加失败"
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
            },
        },
    }
</script>

<style lang="less">
    @import '../style/mixin';
    .explain_text{
        margin-top: 20px;
        text-align: center;
        font-size: 20px;
        color: #333;
    }
    .parent {

        position: relative;
    }
        /*.form_contianer{
            .wh(320px, auto);
            .ctp(100px,400px);
            padding: 25px;
            border-radius: 5px;
            text-align: center;
            background-color: #fff;
            .submit_btn{
                width: 100%;
                font-size: 16px;
            }
        }*/
    .child  {
        margin-left: 10px;
        margin-right: 10px;
        margin-top: 15px;
    }
    .admin_set{
        width: 60%;
        background-color: #F9FAFC;
        min-height: 400px;
        margin: 20px auto 0;
        border-radius: 10px;
        ul > li{
            padding: 20px;
            span{
                color: #666;
            }
        }
    }
    .admin_title{
        margin-top: 20px;
        .sc(24px, #666);
        text-align: center;
    }
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        margin-top: 10px;
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
