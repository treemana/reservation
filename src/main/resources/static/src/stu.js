import React, { Component } from 'react';
import Login from './login';
import './app.css';
import logo from './logo.png';
import Headerbar from './header';
import Siderbar from './sider';

class Stupart extends Component {
  render() {
    return (
      <div className="stupart">
        < Headerbar className="headerbar"/>
        < Siderbar className="siderbar" />
      </div>
    );
  }
}

export default Stupart;