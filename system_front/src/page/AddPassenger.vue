<template>
    <div class="fillcontain">
        <meta  http-equiv="Cache-Control" content="no-cache,no-store,must-revlidate">
        <meta  http-equiv="Expires" content="O">
        <meta http-equiv="Pragma" content="no-cache">

        <head-top></head-top>
        <header class="admin_title">添加乘客</header>

        <el-form :rules="rules" class="form_contianer" :model="ChangeForm" ref="ChangeForm">
            <el-form-item prop="passenger_phone_number">
                <el-input v-model="ChangeForm.passenger_phone_number" placeholder="电话号码"><span>dsfsf</span></el-input>
            </el-form-item>
            <el-form-item prop="passenger_real_name">
                <el-input  placeholder="真实姓名" v-model="ChangeForm.passenger_real_name"></el-input>
            </el-form-item>
            <el-form-item prop="passenger_id_number">
                <el-input  placeholder="身份证号" v-model="ChangeForm.passenger_id_number"></el-input>
            </el-form-item>
            <el-form-item prop="user_passenger_type">
                <el-select style="width: 100%;" v-model="ChangeForm.passenger_type" placeholder="请选择乘客类型">
                    <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item prop="passenger_address">
                <el-input  placeholder="乘客地址" v-model="ChangeForm.passenger_address"></el-input>
            </el-form-item>



            <el-form-item>
                <el-button type="primary" @click="submitForm('ChangeForm')" class="submit_btn">添加乘客</el-button>
            </el-form-item>
        </el-form>
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
                    passenger_phone_number: '',
                    passenger_real_name:'',
                    passenger_id_number:'',
                    passenger_type:'',
                    passenger_address:''
/*
{ pattern: /^1[3|5|7|8|9]\d{9}$/, message: '请输入正确的号码格式', trigger: 'blur' }
 */
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
                options: [{
                    value: '选项1',
                    label: '成人'
                }, {
                    value: '选项2',
                    label: '学生'
                }],
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
    .form_contianer{
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
