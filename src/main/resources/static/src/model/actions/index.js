//在ActionCreator里面完成数据的获取与处理工作，并且通过向store发送各个组合的action，从而达到控制界面展示的内容实现交互
//actionCreator被某个组件调用后会向store发送action，然后被reducer处理，reducer定义在model/reducers/index.js中
//dispatch负责数据的修改

export const LOGIN_LOADING = 'LOGIN_LOADING';//post登陆
export const LOGIN_SUCCESS = 'LOGIN_SUCCESS';//登陆成功
export const LOGIN_FAILURE = 'LOGIN_FAILURE';//登录失败

//三个普通的actionCreator
export funcion loginLoading(loading) {
	return {
		type: LOGIN_LOADING,
		payload: loading 
	}
}

export funcion loginSuccess(data) {
	return {
		type: LOGIN_SUCCESS,
		payload: data
	}
}

export funcion loginFailure(error) {
	return {
		type: LOGIN_FAILURE,
		payload: error 
	}
}

//异步actionCreator
export function login() {
	return function (dispatch) {//闭包了
		dispatch(loginLoading(true));
		fetch('goschool/123')
			.then((response) => {
				dispatch(loginLoading(false));
				//这里格式化数据
				dispatch(loginSuccess(response));
			})
			.catch((error) => {
				dispatch(loginLoading(false));
				dispatch(loginFailure(error));
			});
	}
}