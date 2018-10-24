import React, { Component } from 'react';
import Login from './login';
import './app.css';
import logo from './logo.png';

class App extends Component {
  render() {
    return (
      <div className="App">
      <img src={logo} alt="" className="logo" />
      <div>
      < Login />
      </div>
      </div>
    );
  }
}

export default App;