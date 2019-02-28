<template>
  <div class="manage_page fillcontain">
    <el-row style="height: 100%;">
      <el-col :span="4"  style="min-height: 100%; background-color: #324057;">
        <el-menu :default-active="defaultActive" style="min-height: 100%;" theme="dark" router>
          <el-menu-item index="manage"><i class="el-icon-menu"></i>首页</el-menu-item>
          <el-submenu index="2">
            <template slot="title"><i class="el-icon-document"></i>数据统计</template>
            <el-menu-item index="goodsList">商品统计</el-menu-item>
            <el-menu-item index="intoManagement">入库统计</el-menu-item>
            <el-menu-item index="OutputManagement">出库统计</el-menu-item>
            <el-menu-item index="InventoryManagement">库存统计</el-menu-item>
            <el-menu-item index="OrderList">订单统计</el-menu-item>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title"><i class="el-icon-plus"></i>商品管理</template>
            <el-menu-item index="addGoods">添加商品</el-menu-item>
            <el-menu-item index="goodsInto">商品入库</el-menu-item>
          </el-submenu>
          <el-submenu index="4">
            <template slot="title"><i class="el-icon-star-on"></i>后台管理</template>
            <el-menu-item index="visitor">成员权限分配</el-menu-item>
            <el-menu-item index="newMember">后台数据更改</el-menu-item>
          </el-submenu>
          <el-submenu index="5">
            <template slot="title"><i class="el-icon-warning"></i>说明</template>
            <el-menu-item index="explain">说明</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-col>
      <el-col :span="20" style="height: 100%;overflow: auto;">
        <keep-alive>
          <router-view></router-view>
        </keep-alive>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  export default {
    computed: {
      defaultActive: function(){
        return this.$route.path.replace('/', '');
      }
    },
  }
</script>


<style lang="less" scoped>
  @import '../style/mixin';
  .manage_page{

  }
</style>
