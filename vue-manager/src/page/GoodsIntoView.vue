<template>
  <div>
    <head-top></head-top>
    <el-row style="margin-top: 20px;">
      <el-col :span="12" :offset="4">
        <el-form :model="formData" ref="formData" label-width="110px" class="demo-formData">
          <el-form-item label="商品">
            <el-select v-model="categoryForm.categorySelect" :placeholder="selectValue.goodsValue" style="width:100%;">
              <el-option
                v-for="item in categoryForm.categoryList"
                :key="item.goodsValue"
                :label="item.goodsValue"
                :value="item.goodsValue">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="商品状态" style="white-space: nowrap;">
            <el-radio v-model="formData.goodsColors" label="0">红色</el-radio>
            <el-radio v-model="formData.goodsColors" label="1">灰色</el-radio>
            <el-radio v-model="formData.goodsColors" label="2">蓝色</el-radio>
          </el-form-item>
          <el-form-item label="入库件数" prop="goodsName">
            <el-input v-model="formData.goodsNumber"></el-input>
          </el-form-item>
          <el-form-item label="存放区域" prop="goodsType">
            <el-input v-model="formData.area"></el-input>
          </el-form-item>
          <el-form-item class="button_submit">
            <el-button type="primary" @click="submitForm(formData)">立即创建</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import headTop from '@/components/headTop'
  import {Message} from 'element-ui'

  export default {
    data(){
      return {
        categoryForm: {
          categoryList: [],
          categorySelect: {},
        },
        formData: {
          goodsName: '',
          goodsColors: '0',
          goodsLong: '',
          area: '',
          goodsNumber: '',
          goodsType: '',
          userName: ''
        },
        baseUrl:'',
        baseImgPath:''
      }
    },
    created(){
      this.reloadGoods();
    },
    components: {
      headTop,
    },
    computed: {
      selectValue: function (){
        return this.categoryForm.categoryList[this.categoryForm.categorySelect]||{}
      }
    },
    methods: {

      reloadGoods(){
        this.$https.get('/web/goods_categories/list').then((data) =>{
          data.forEach(function(value,i){
            value.goodsValue = value.goodsName + '/' + value.goodsType;
          })
          this.categoryForm.categoryList = data;
          if (this.categoryForm.categoryList.length == 0){
            Message.error({message: '当前尚未添加任何商品，请先添加商品'});
            setTimeout(() =>{
              this.router.push('addGoods');
            },1000);
            return;
          }
          this.categoryForm.categorySelect = data[0];
        },(data) =>{

        }).finally(() =>{

        })
      },
      submitForm(dataSource) {
        this.formData.goodsName = this.categoryForm.categorySelect.goodsName;
        this.formData.goodsType = this.categoryForm.categorySelect.goodsType;
        this.formData.userName = sessionStorage.getItem('loginName');

        console.log(dataSource);
        this.$https.post('/web/intocargos', this.formData, this).then((data) => {
          console.log(data);
          Message.success({message: '商品入库成功'});
          this.formData = {};
          setTimeout(() =>{
            this.$router.push('intoManagement');
          },2000);

        },(data)=>{
          this.params.nonce = data.data?data.data:null;
          console.log(data,'添加商品');
        }).finally((data) => {

          }
        )

      },
    }
  }
</script>

<style lang="less">
  @import '../style/mixin';
  .button_submit{
    text-align: center;
  }
  .input_float_left{
    display: inline;
    float: left;
    width: 100px;
    margin-left: 20px;
  }
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    float: left;
    display: inline;
    margin-left: 10px;
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
  .el-table .info-row {
    background: #c9e5f5;
  }

  .el-table .positive-row {
    background: #e2f0e4;
  }
</style>
