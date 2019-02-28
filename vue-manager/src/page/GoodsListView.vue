<template>
  <div class="fillcontain">
    <head-top></head-top>
    <div style="display: inline">
      <el-input style="width: 200px;height: 20px;margin-left: 10px;margin-top: 20px;" placeholder="请输入名称或型号查找"></el-input>
      <el-button>搜索</el-button>
    </div>
    <div class="table_container">
      <el-table
        :data="tableData"
        @expand='expand'
        :expand-row-keys='expendRow'
        :row-key="row => row.index"
        style="width: 100%">
        <el-table-column
          label="商品名称"
          prop="goodsName">
        </el-table-column>
        <el-table-column
          label="商品型号"
          prop="goodsType">
        </el-table-column>
        <el-table-column
          label="长*宽*高"
          prop="goodsSize">
        </el-table-column>
        <el-table-column
          label="日期"
          prop="createdAt"
          :formatter = "createAtFormat">
        </el-table-column>
        <el-table-column
          label="状态"
          prop="goodsStatus"
          :formatter = "stateFormat">
        </el-table-column>
        <el-table-column
          label="价格"
          prop="goodsPrice">
        </el-table-column>
        <el-table-column label="操作" width="200">
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
      </el-table>
      <div class="Pagination" style="text-align: left;margin-top: 10px;">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-size="20"
          layout="total, prev, pager, next"
          :total="count">
        </el-pagination>
      </div>
      <el-dialog title="修改商品信息" v-model="dialogFormVisible">
        <div style="margin-bottom: 10px">
          <span>{{selectTable.goodsName}}</span>
          <span>/</span>
          <span>{{selectTable.goodsType}}</span>
        </div>

        <el-form :model="selectTable">
          <el-form-item label="商品价格" label-width="100px">
            <el-input style="width: 500px" v-model="selectTable.goodsPrice" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="商品尺寸" label-width="100px">
            <div>
              <el-input class="input_float_left" size="small" placeholder="长度 cm" v-model="selectTable.goodsLong"></el-input>
              <el-input class="input_float_left" size="small" placeholder="宽度 cm" v-model="selectTable.goodsWidth"></el-input>
              <el-input class="input_float_left" size="small" placeholder="高度 cm" v-model="selectTable.goodsHeight"></el-input>
            </div>
          </el-form-item>

          <el-form-item label="商品简介" label-width="100px">
            <el-input style="width: 500px" type="textarea" :rows="0" v-model="selectTable.goodsDescription"></el-input>
          </el-form-item>
          <el-form-item label="商品状态" style="white-space: nowrap;" label-width="100px">
            <el-radio v-model="selectTable.goodsStatus" label=0>在售</el-radio>
            <el-radio v-model="selectTable.goodsStatus" label=1>预售</el-radio>
            <el-radio v-model="selectTable.goodsStatus" label=2>停产</el-radio>
          </el-form-item>

          <el-form-item label="商铺图片" label-width="100px">
            <el-upload
              class="avatar-uploader"
              :action="baseUrl + '/v1/addimg/shop'"
              :show-file-list="false"
              :on-success="handleServiceAvatarScucess"
              :before-upload="beforeAvatarUpload">
              <img v-if="selectTable.image_path" :src="baseImgPath + selectTable.image_path" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="updateShop">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
  import headTop from '../components/headTop'
  import {formatDate} from '../api/utils'
  export default {
    data(){
      return {
        baseUrl:'',
        tableData: [],
        currentPage: 1,
        expendRow: [],
        count: 0,
        dialogFormVisible: false,
        selectTable: {}
      }
    },

    components: {
      headTop,
    },

    created(){
      this.initData();
    },

    methods: {
      //获取商品列表
      initData(){
        this.$https.get('/web/goods_categories/list').then((data) =>{
          console.log(data);
          this.tableData = data;
          this.count = this.tableData.length;

        },(data) =>{

        }).finally(() =>{

        })
      },
      expand(row, status){
        if (status) {
          this.getSelectItemData(row)
        }else{
          const index = this.expendRow.indexOf(row.index);
          this.expendRow.splice(index, 1)
        }
      },

      async getSelectItemData(row, type){

      },
      //翻页
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
      },
      //翻页
      handleCurrentChange(val) {
        this.currentPage = val;
        this.offset = (val - 1)*this.limit;
        this.getIntoManagementList()
      },
      //商品状态初始化
      stateFormat(row, column) {
        console.log(row.goodsStatus)
        if (row.goodsStatus === 0) {
          return '在售'
        } else if (row.goodsStatus === 1) {
          return '预售'
        } else if (row.goodsStatus === 2) {
          return '停产'
        }
      },
      //商品创建时间初始化
      createAtFormat(row, column){
        return formatDate(new Date(row.createdAt), 'yyyy-MM-dd hh:mm')
      },
      //编辑商品
      handleEdit(index, row) {
        console.log(index);
        console.log(row);
        this.selectTable = row;
        this.dialogFormVisible = true;
      },
      //更新商品
      updateShop(){

      },
      handleServiceAvatarScucess(res, file) {
        if (res.status == 1) {
          this.selectTable.image_path = res.image_path;
        }else{
          this.$message.error('上传图片失败！');
        }
      },
      beforeAvatarUpload(file) {
        const isRightType = (file.type === 'image/jpeg') || (file.type === 'image/png');

        if (!isRightType) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        return isRightType;
      },


    },

  }
</script>

<style lang="less">
  @import '../style/mixin';
  .table_container{
    padding: 20px;
  }
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
  .input_float_left{
    display: inline;
    float: left;
    width: 100px;
    margin-left: 20px;
  }


</style>
