import React, { Component } from 'react';
import Login from './login';
import './css/app.css';
import logo from './images/logo.png';
import Headerbar from './components/header';
import Siderbar from './components/sider';

class Stubox extends Component {
  render() {
    return (
      <div className="stupart">
        < Headerbar className="headerbar"/>
        < Siderbar className="siderbar" />
      </div>
    );
  }
}

export default Stubox;