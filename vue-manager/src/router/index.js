import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

const login = r => require.ensure([], () => r(require('@/page/login')), 'login');
const manage = r => require.ensure([], () => r(require('../NavMnager/index')), 'manage');
const home = r => require.ensure([], () => r(require('../page/home')), 'home');
const IntoManagement = r => require.ensure([], () => r(require('../page/IntoManagement')), 'IntoManagement');
const OutputManagement = r => require.ensure([], () => r(require('../page/OutputManagement')), 'OutputManagement');
const InventoryManagement = r => require.ensure([], () => r(require('../page/InventoryManagement')), 'InventoryManagement');
const OrderList = r => require.ensure([], () => r(require('../page/OrderList')), 'OrderList');

const addGoodsView = r => require.ensure([], () => r(require('../page/AddGoodsView')), 'AddGoodsView');
const GoodsListView = r => require.ensure([], () => r(require('../page/GoodsListView')), 'GoodsListView');
const GoodsIntoView = r => require.ensure([], () => r(require('../page/GoodsIntoView')), 'GoodsIntoView');




const explain = r => require.ensure([], () => r(require('../page/explain')), 'explain');

export default new Router({
  routes: [
    {
      path: '/',
      component: login
    }
    ,{
      path: '/manage',
      component: manage,
      name: '',
      children: [
        {
        path: '',
        component: home,
        meta: [],
      },{
          path: '/goodsList',
          component: GoodsListView,
          meta: ['数据统计', '商品统计'],
        },{
          path: '/intoManagement',
          component: IntoManagement,
          meta: ['数据统计', '入库统计'],
        },{
          path: '/OutputManagement',
          component: OutputManagement,
          meta: ['数据统计', '出库管理'],
        },{
          path: '/InventoryManagement',
          component: InventoryManagement,
          meta: ['数据统计', '库存管理'],
        },{
          path: '/OrderList',
          component: OrderList,
          meta: ['数据统计', '订单管理'],
        },{
          path: '/goodsInto',
          component: GoodsIntoView,
          meta: ['商品管理', '商品入库'],
        },{
          path: '/addGoods',
          component: addGoodsView,
          meta: ['商品管理', '添加商品'],
        },{
          path: '/explain',
          component: explain,
          meta: ['说明', '说明'],
        }
      ]
    }
  ]
})
