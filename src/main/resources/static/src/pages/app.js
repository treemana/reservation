import React, { Component } from 'react';
import Login from '../components/login';
import '../css/app.css';
import logo from '../images/logo.png';
import Headerbar from '../components/headerbar';
import Footertext from '../components/footertext';
import { Provider } from 'react-redux';//提供数据支持,挂到父组件上，然后子组件皆可收到数据
import store from './model';//导出store对象

class App extends Component {
  render() {
    return (
      <Provider store={store}>
      <div className="App">
        <div className="cons">
        < Headerbar className="headerbar"/>
        <img src={logo} alt="" className="logopic" />
        <div>
          < Login />
        </div>
      </div>
      <Footertext />
      </div>
      </Provider>
    );
  }
}

export default App;