import { combineReducers } from 'redux';
import loginReducer from './login-reducer';
import {
	LOGIN_SUCCESS,
	LOGIN_FAILURE
} from '../actions/login-action';//三种type代表三种操作

const allReducers = loginReducer;
const rootReducer = combineReducers(allReducers);

export default rootReducer;