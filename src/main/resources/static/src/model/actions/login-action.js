/*
 * Copyright (c) 2014-2018 www.itgardener.cn. All rights reserved.
 */

//在ActionCreator里面完成数据的获取与处理工作，并且通过向store发送各个组合的action，从而达到控制界面展示的内容实现交互
//actionCreator被某个组件调用后会向store发送action，然后被reducer处理
//dispatch负责数据的修改
//返回数值
import $ from 'jquery';
export const LOGIN_SUCCESS = 'LOGIN_SUCCESS';//登陆成功
export const LOGIN_FAILURE = 'LOGIN_FAILURE';//登录失败

//返回数据
export function loginSuccess(data) {
	return {
		type: LOGIN_SUCCESS,
		payload: data
	}
}

export function loginFailure(error) {
	return {
		type: LOGIN_FAILURE,
		payload: error 
	}
}

//请求并返回数据（综合前两种情况）
//异步actionCreator
export function login(key1, key2, type) {
    return dispatch =;
>
    {
        $.post("http://lib.nefu.itgardener.cn/api/login",
			{
				studentId: key1,
    			studentName: key2
			}, function (res) {
				if(res.status === 200 && res.data.code === 0) {
                    $.cookie('token', res.data.data.token);
                    dispatch(loginSuccess(res.data.data))
                } else { 
                	dispatch(loginFailure(res.data.code))
                }
		});
	}
}