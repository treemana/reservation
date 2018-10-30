import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import { createStore, applyMiddleware, compose } from 'redux';// createStore接受reducer生成stote compose合并生成store其他数据 applyMiddleware接受thunk解决redux异步问题
import thunk from 'redux-thunk';//实现异步action
import { Provider } from 'react-redux';
//import { composeWithDevTools } from 'redux-devtools-extension';
import { createLogger } from 'redux-logger';
import rootReducer from './model/reducers';
import { BrowserRouter, Route, Switch } from 'react-router-dom';

import './css/app.css';
import App from './pages/app';
import Stubox from './pages/stubox';
import Stumine from './pages/stumine';
import Boxset from './pages/boxset';
import Timeset from './pages/timeset';
import Idset from './pages/idset';
import Help from './pages/help';
import Dev from './pages/dev';

const store = createStore(
	rootReducer,
	compose(
		applyMiddleware(thunk, createLogger),
		window.devToolsExtension ? window.devToolsExtension() : f => f
	)

	//composeWithDevTools()
);

ReactDOM.render(
	<Provider store={store}>
	  <BrowserRouter className="search">
      	<Switch>
  	   	  <Route exact path="/" component={App} ></Route>
  	   	  <Route path="/stubox" component={Stubox} ></Route>
          <Route path="/stumine" component={Stumine} ></Route>
          <Route path="/boxset" component={Boxset} ></Route>
          <Route path="/timeset" component={Timeset} ></Route>
          <Route path="/idset" component={Idset} ></Route>
          <Route path="/help" component={Help} ></Route>
          <Route path="/dev" component={Dev} ></Route>
  	    </Switch>
  	  </BrowserRouter>
	</Provider>, 
	document.getElementById('root')
)