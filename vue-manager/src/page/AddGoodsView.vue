<template>
  <div>
    <head-top></head-top>
    <el-row style="margin-top: 20px;">
      <el-col :span="12" :offset="4">
        <el-form :model="formData" ref="formData" label-width="110px" class="demo-formData">
          <el-form-item label="商品名称" prop="goodsName">
            <el-input v-model="formData.goodsName"></el-input>
          </el-form-item>
          <el-form-item label="商品型号" prop="goodsType">
            <el-input v-model="formData.goodsType"></el-input>
          </el-form-item>
          <el-form-item label="商品价格" prop="goodsPrice">
            <el-input v-model.number="formData.goodsPrice"></el-input>
          </el-form-item>
          <el-form-item label="商品尺寸">
            <div>
              <el-input class="input_float_left" size="small" placeholder="长度 cm" v-model="formData.goodsLong"></el-input>
              <el-input class="input_float_left" size="small" placeholder="宽度 cm" v-model="formData.goodsWidth"></el-input>
              <el-input class="input_float_left" size="small" placeholder="高度 cm" v-model="formData.goodsHeight"></el-input>
            </div>

          </el-form-item>
          <el-form-item label="商品简介" prop="goodsDescription">
            <el-input type="textarea" :rows="0" v-model="formData.goodsDescription"></el-input>
          </el-form-item>
          <el-form-item label="商品状态" style="white-space: nowrap;">
            <el-radio v-model="formData.goodsStatus" label="0">在售</el-radio>
            <el-radio v-model="formData.goodsStatus" label="1">预售</el-radio>
            <el-radio v-model="formData.goodsStatus" label="2">停产</el-radio>
          </el-form-item>
          <el-form-item label="商品图片">
          </el-form-item>
          <el-form-item>
            <el-upload
              class="avatar-uploader"
              :action="baseUrl + '/v1/addimg/shop'"
              :show-file-list="false"
              :on-success="handleShopAvatarScucess"
              :before-upload="beforeAvatarUpload">
              <img v-if="formData.image_path" :src="baseImgPath + formData.image_path" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
            <el-upload
              class="avatar-uploader "
              :action="baseUrl + '/v1/addimg/shop'"
              :show-file-list="false"
              :on-success="handleShopAvatarScucess"
              :before-upload="beforeAvatarUpload">
              <img v-if="formData.image_path" :src="baseImgPath + formData.image_path" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
            <el-upload
              class="avatar-uploader "
              :action="baseUrl + '/v1/addimg/shop'"
              :show-file-list="false"
              :on-success="handleShopAvatarScucess"
              :before-upload="beforeAvatarUpload">
              <img v-if="formData.image_path" :src="baseImgPath + formData.image_path" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
          <el-form-item>
            <el-upload
              class="avatar-uploader"
              :action="baseUrl + '/v1/addimg/shop'"
              :show-file-list="false"
              :on-success="handleShopAvatarScucess"
              :before-upload="beforeAvatarUpload">
              <img v-if="formData.image_path" :src="baseImgPath + formData.image_path" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
            <el-upload
              class="avatar-uploader "
              :action="baseUrl + '/v1/addimg/shop'"
              :show-file-list="false"
              :on-success="handleShopAvatarScucess"
              :before-upload="beforeAvatarUpload">
              <img v-if="formData.image_path" :src="baseImgPath + formData.image_path" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
            <el-upload
              class="avatar-uploader "
              :action="baseUrl + '/v1/addimg/shop'"
              :show-file-list="false"
              :on-success="handleShopAvatarScucess"
              :before-upload="beforeAvatarUpload">
              <img v-if="formData.image_path" :src="baseImgPath + formData.image_path" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
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
        formData: {
          goodsName: '',
          goodsStatus: '0',
          goodsLong: '',
          goodsHeight: '',
          goodsWidth: '',
          goodsDescription: '',
          goodsPrice: '',
          goodsPics: '',
          goodsType: '',
        },
        baseUrl:'',
        baseImgPath:''
      }
    },
    components: {
      headTop,
    },
    mounted(){
    },
    methods: {
      submitForm(dataSource) {

        if (dataSource.goodsName == null || dataSource.goodsName.length == 0) {
          Message.error({message: '商品名称不能为空'});
          return;
        }
        if (dataSource.goodsType == null || dataSource.goodsType.length == 0) {
          Message.error({message: '商品型号不能为空'});
          return;
        }
        console.log(dataSource);
        this.$https.post('/web/goods_categories', this.formData, this).then((data) => {
          console.log(data);
          Message.success({message: '添加商品成功正在跳转商品统计页面'});
          setTimeout(() =>{
            this.$router.push('goodsList');
          },2000);

        },(data)=>{
          this.params.nonce = data.data?data.data:null;
          console.log(data,'添加商品');
        }).finally((data) => {

          }
        )
      },
      handleShopAvatarScucess(res, file) {
        console.log(file);
        if (res.status == 1) {
          this.formData.image_path = res.image_path;
        }else{
          this.$message.error('上传图片失败！');
        }
      },

      beforeAvatarUpload(file) {
        console.log(file);
        const isRightType = (file.type === 'image/jpeg') || (file.type === 'image/png');

        if (!isRightType) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        return isRightType;
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
