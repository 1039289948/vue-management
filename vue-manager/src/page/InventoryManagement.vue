<template>
  <div class="fillcontain">
    <head-top></head-top>
    <div class="table_container">
      <el-table
        :data="tableData"
        @expand='expand'
        :expand-row-keys='expendRow'
        :row-key="row => row.index"
        style="width: 100%">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="用户名" >
                <span>{{ props.row.user_name }}</span>
              </el-form-item>
              <el-form-item label="店铺名称">
                <span>{{ props.row.restaurant_name }}</span>
              </el-form-item>
              <el-form-item label="收货地址">
                <span>{{ props.row.address }}</span>
              </el-form-item>
              <el-form-item label="店铺 ID">
                <span>{{ props.row.restaurant_id }}</span>
              </el-form-item>
              <el-form-item label="店铺地址">
                <span>{{ props.row.restaurant_address }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column
          label="编码"
          prop="id">
        </el-table-column>
        <el-table-column
          label="品名"
          prop="total_amount">
        </el-table-column>
        <el-table-column
          label="重量"
          prop="status">
        </el-table-column>
        <el-table-column
          label="在库天数"
          prop="status">
        </el-table-column>
        <el-table-column
          label="客户名称"
          prop="status">
        </el-table-column>
        <el-table-column
          label="入库成本"
          prop="status">
        </el-table-column>
        <el-table-column
          label="滞库成本"
          prop="status">
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
    </div>
  </div>
</template>

<script>
  import headTop from '../components/headTop'
  import {getIntoManagementList} from '@/api/getData'

  export default {
    data(){
      return {
        tableData: [],
        currentPage: 1,
        expendRow: [],
        count: 0,
      }
    },

    components: {
      headTop,
    },

    created(){
      this.initData();
    },

    methods: {

      async initData(){
        try{
          const countData = await getIntoManagementList();
          console.log('获取数据');
          console.log(countData);
          if (countData.code == 0) {
            console.log('获取数据成功');
            this.tableData = countData.data;
            console.log(this.tableData);
            this.count = this.tableData.length;
          }else{
            throw new Error('获取数据失败');
          }
        }catch(err){
          // console.log('获取数据失败', err);
        }
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

      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
      },
      handleCurrentChange(val) {
        this.currentPage = val;
        this.offset = (val - 1)*this.limit;
        this.getIntoManagementList()
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
</style>
