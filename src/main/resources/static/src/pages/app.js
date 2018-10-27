import React, { Component } from 'react';
import Login from '../components/login';
import '../css/app.css';
import logo from '../images/logo.png';
import Headerbar from '../components/headerbar';
import Footertext from '../components/footertext';

class App extends Component {
  render() {
    return (
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
    );
  }
}

export default App;