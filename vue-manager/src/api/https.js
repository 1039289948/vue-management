/**
 * Created by Jimmy on 2018/1/1.
 */
import axios from 'axios';
// import qs from 'qs';
import {Message} from 'element-ui'
import { baseUrl } from '../config/env'
import utils from '../api/utils'

const codes = {
  "Nonexistent User": "用户不存在",
  "Password Is Not Correct":  "密码不正确",
  "Three Times": "密码不正确",
  "VerifyCode Error": "验证码错误",
  "Login Required": "请重新登录",
  "Token Required": "请重新登录",
  "User Locked":"由于连续5次密码错误，帐号锁定5分钟",
  "User Frozened":"由于连续多次密码错误，帐号已被冻结，请联系管理员",
};

let instance = axios.create({
    baseURL: baseUrl,
    // withCredentials: true,
    crossDomain: true
});
instance.interceptors.request.use(
    config => {
        // 在发送请求之前做某件事
        console.log('接口请求URL' + ':: ' + config.url);
        if ((config.method === "post" || config.method === "put" || config.method === "delete") && config.url.indexOf('upload') == -1) {
            config.headers['Content-Type'] = 'application/json;';

        }
        if (sessionStorage.getItem('token-yotado-web') != null){
          config.headers['token-yotado-web'] = sessionStorage.getItem('token-yotado-web')
        }
        return config;
    },
    error => {
      Message.error({message: error.data.error.message});
      console.log('接口请求ERROR_01' + ':: ' + error.data.error.message);
      return Promise.reject(error.data.error.message);
    }
);
instance.interceptors.response.use(response => {
    let {code, data, msg} = response.data;
    console.log(response.headers);
    console.log(response.headers['token-yotado-web']);
  if(response.headers['token-yotado-web']){
      sessionStorage.setItem('token-yotado-web',response.headers['token-yotado-web']);
      console.log('接口请求TOEKEN' + ':: ' + response.headers['token-yotado-web']);
    }
    if (code === 0 || code === 200) {
        console.log('接口SUCCESS:: ' + code);
        return Promise.resolve(data || {});
    } else if (code === 1 || code == 'Web Login Required' || code == 'Web Login Overtime') {
      Message.error({message: codes[msg] || msg});
      console.log('接口请求ERROR_02' + ':: ' + codes[msg] || msg);
      setTimeout(() =>{
        window.location.href = '/';
      },2000);

    } else {
      Message.error({message: codes[msg] || msg || '请求失败，请重试'});
      console.log(response);
      console.log(code);
      console.log('接口请求ERROR_03' + ':: ' + codes[msg] || msg || '请求失败，请重试');
      // window.location.href = '/'
      return Promise.reject({
            msg: msg || '请求失败',
            data: data || null,
            code
        });
    }
}, error => {
    if (error.response && error.response.data && (error.response.data.code == 'Web Login Required' || error.response.data.code == 'Web Login Overtime')) {

      Message.error({message: '请重新登录'});
      // window.location.href = '/'
      console.log('接口请求ERROR_04' + ':: ' + '请重新登录');

      // window.VUE_SCOPE.$message.error('请重新登录');
      //   window.VUE_SCOPE.$router.push({name: 'login'});
    } else {
      Message.error({message: '网络异常，请重试'});
      console.log('接口请求ERROR_05' + ':: ' + '网络异常，请重试');
      // window.VUE_SCOPE.$message.error('网络异常，请重试');
    }
    return Promise.reject(error);
});
export default {
    get: (url, params) => {
        params = params ? utils.removeEmpty(params) : {};
        return new Promise((resolve, reject) => {
            instance.get(url, {params}).then(res => {
                resolve(res);
            }, res => {
                reject(res);
            });
        })
    },
    post: (url, params, configs = {}) => {
        params = utils.convertParams(params);
        return new Promise((resolve, reject) => {
            instance.post(url, params, configs).then(res => {
                resolve(res);
            }, res => {
                reject(res);
            });
        })
    },
    put: (url, params) => {
        if (url.indexOf('activities') == -1) {
            params = utils.convertParams(params);
        }
        // params = utils.convertParams(params);
        return new Promise((resolve, reject) => {
            instance.put(url, params).then(res => {
                resolve(res);
            }, res => {
                reject(res);
            });
        })
    },
    del: (url) => {
        return new Promise((resolve, reject) => {
            instance.delete(url).then(res => {
                resolve(res.data);
            }, res => {
                reject(res);
            });
        })
    },
    upload: (url, formData, onProcess) => {
        return new Promise((resolve, reject) => {
            axios({
                url: `${configs.BaseUrl}/upload`,
                method: 'post',
                headers: {'Content-Type': 'multipart/form-data'},
                onUploadProgress: function (progressEvent) {
                    if (progressEvent.lengthComputable) {
                        onProcess&&onProcess(progressEvent);
                    }
                },
                data: formData
            }).then(res => {
                resolve(res.data.data||{});
            }, res => {
              Message.error({message: '上传失败'});
              // window.VUE_SCOPE.$message.error('上传失败');
                reject(res);
            })
        })

    },
    axios: axios
}
