/**
 * Created by Jimmy on 2017/6/22.
 */
if (window.Promise) {
  console.log('浏览器支持Promise');
  Promise.prototype.finally = function (fn) {
    function finFn(valueORreason) {
      fn.call(null)
    }

    this.then(finFn, finFn);
    return this
  };
}
String.prototype.transSpace = function(){
  return this.replace(/\s+/g,"&nbsp;");
}

export function formatDate (date, fmt) {
  if (/(y+)/.test(fmt)) {
    fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length))
  }
  let o = {
    'M+': date.getMonth() + 1,
    'd+': date.getDate(),
    'h+': date.getHours(),
    'm+': date.getMinutes(),
    's+': date.getSeconds()
  }
  for (let k in o) {
    if (new RegExp(`(${k})`).test(fmt)) {
      let str = o[k] + ''
      fmt = fmt.replace(RegExp.$1, RegExp.$1.length === 1 ? str : padLeftZero(str))
    }
  }
  return fmt
}

function padLeftZero (str) {
  return ('00' + str).substr(str.length)
}

Date.prototype.formatDate = function (format) {
  var o = {
    "M+": this.getMonth() + 1, // month
    "d+": this.getDate(), // day
    "h+": this.getHours(), // hour
    "m+": this.getMinutes(), // minute
    "s+": this.getSeconds(), // second
    "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
    "S": this.getMilliseconds()
    // millisecond
  };
  if (/(y+)/.test(format))
    format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
  for (var k in o)
    if (new RegExp("(" + k + ")").test(format))
      format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
  return format;
};
const baseUrl = {
  dev: {
    api: '',
    qnToken: 'http://api-dev.themobiyun.com/comm/qiniu/upload_token'
    // api: 'http://local.themobiyun.com:8088/cs2Api/web'
  },
  production: {
    api: 'http://dev.themobiyun.com/cs2Api/web'
  }
};
const utils = {
  imgUrl:'http://upload.qiniu.com/',
  isHttpReqSucc: function (data) {
    return data.code == 0;
  },
  /**
   * 深度克隆
   * @param obj
   * @returns {*}
   */
  deepClone: function (obj) {
    var result, oClass = this.isClass(obj);
    if (oClass === "Object") {
      result = {};
    } else if (oClass === "Array") {
      result = [];
    } else {
      return obj;
    }
    for (var key in obj) {
      var copy = obj[key];
      if (this.isClass(copy) == "Object") {
        result[key] = this.deepClone(copy);//递归调用
      } else if (this.isClass(copy) == "Array") {
        result[key] = this.deepClone(copy);
      } else {
        result[key] = obj[key];
      }
    }
    return result;
  },
  /**
   * 返回对象类型
   * @param o
   * @returns {*}
   */
  isClass: function (o) {
    if (o === null) return "Null";
    if (o === undefined) return "Undefined";
    return Object.prototype.toString.call(o).slice(8, -1);
  },
  isEmptyObj(obj){
    for(let key in obj){
      return false;
    }
    return true;
  },
  removeEmpty: function (obj) {
    let that = this;
    if (this.isClass(obj) == 'Object') {
      Object.keys(obj).forEach(function (key) {
        (obj[key] && typeof obj[key] === 'object') && that.removeEmpty(obj[key]) ||
        (obj[key] === undefined || obj[key] === null || obj[key] == '') && delete obj[key]
      });
    }
    return obj;

  },
  cookie: {
    set: function (name, value, days) {

      var d = new Date;

      d.setTime(d.getTime() + days);

      window.document.cookie = name + "=" + value + ";path=/;expires=" + d.toGMTString();

    },

    get: function (name) {

      var v = window.document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');

      return v ? v[2] : null;

    },

    delete: function (name) {

      this.set(name, '', -1);

    }
  },
  /**
   * 浅对比获取对象不同属性值
   * @param nObj
   * @param oObj
   * @return {{}}
   */
  getDiffObj(nObj, oObj){
    let obj = {};
    for(let key in nObj){
      if(nObj.hasOwnProperty(key)){
        if(nObj[key] != oObj[key]){
          obj[key] = nObj[key];
        }
      }
    }
    return obj;
  },
  /**
   * 日期处理
   * @param date
   * @return {number}
   */
  transDate(date){
    let time = null;
    if(date){
      time = Math.floor(date.getTime()/1000);
    }
    return time;
  },
  transTime(time){
    let date = null;
    if(time){
      time = new Date(time*1000);
    }
    return time;
  },
  changeTitle: function (title) {//换标题
    document.title = title;
    var mobile = navigator.userAgent.toLowerCase();
    if (/iphone|ipad|ipod/.test(mobile)) {
      var iframe = document.createElement('iframe');
      iframe.style.display = 'none';
      // 替换成站标favicon路径或者任意存在的较小的图片即可
      iframe.setAttribute('src', '/favicon.ico');
      var iframeCallback = function () {
        setTimeout(function () {
          iframe.removeEventListener('load', iframeCallback);
          document.body.removeChild(iframe)
        }, 0)
      };
      iframe.addEventListener('load', iframeCallback);
      document.body.appendChild(iframe);
    }
  },
  expImg(file,options){
    return new Promise((resolve,reject)=>{
      let opts = options || {};
      const isJPG = /(.jpg$$)|(.png$$)/.test(file.raw.name);
      const is1_5M = (file.size<=1.5*1024*1024);
      if (!isJPG) {
        reject('图片格式不符合要求');
      }
      if(!is1_5M){
        reject('图片大小不符合要求');
      }
      let url = window.URL || window.webkitURL;
      if(url){
        let img = new Image();
        img.src = url.createObjectURL(file.raw);
        let height,width;
        img.onload = function () {
          height = this.height;
          width = this.width
          if((opts&&opts.w&&opts.w !== width)||(opts&&opts.h&&opts.h !== height)){
            reject('图片尺寸不符合要求');
          }
          resolve();
        }
      }else{

        resolve();
      }

    })
  },
  convertParams(params){
    for(let key in params){
      if(params[key]===''){
        params[key] = null;
      }
    }
    return params;
  }
};
export default utils;
