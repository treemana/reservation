import { combineReducers } from 'redux';

import {
	LOGIN_LOADING,
	LOGIN_SUCCESS,
	LOGIN_FAILURE
} from '../actions';//三种type代表三种操作

export let initialState = {//初始化状态
	entities: {//存放登录后获取的当前登录用户的数据，实体。
		loginUser: null
	},
	loginPageData: {//存放登录页面用到的数据
		loading: false,
		error: null
	}
};

let reducers = combineReducers({//初始化数据+更新操作
	entities: function(state = {}, action) {
		switch(action.type) {
			case LOGIN_SUCCESS:
				return {...state, loginUser: action.payload};//浅复制，创建新对象，不修改原对象
			default:
				return state;//未成功的话实体依旧是初始值null
		}
	},
	loginPageData: function(state = {}, action) {
		switch(action.type) {
			case LOGIN_LOADING:
				return {...state, loading: action.payload};
			case LOGIN_FAILURE:
				return {...state, error: action.payload};
			case LOGIN_SUCCESS:
				return {...state, data: action.payload};
			default:
				return state;
		}
	}
})

export default reducers;