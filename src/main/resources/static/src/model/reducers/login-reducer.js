//reducer的作用是匹配action进行数据操作并返回数据状态
import  { LOGIN_SUCCESS, LOGIN_FAILURE }  from '../actions/login-action';

const initialState = {//初始化状态
  code: null,
  token: null,
  type: null
};

//更新数据
export default function loginReducer(state=initialState, action){
    switch(action.type) {
      case LOGIN_FAILURE:
        return {...state, code: action.payload};
      case LOGIN_SUCCESS:
        return {...state, data: action.payload};
      default:
        return state;
  }
};