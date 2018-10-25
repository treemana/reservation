import React, { Component } from 'react';
import Login from './login';
import './app.css';
import logo from './logo.png';
import Headerbar from './header';
import Footertext from './footer';

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