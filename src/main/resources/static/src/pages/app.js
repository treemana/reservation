import React, { Component } from 'react';
import PropTypes from 'prop-types';
import Login from '../components/login';
import '../css/app.css';
import logo from '../images/logo.png';
import Headerbar from '../components/headerbar';
import Footertext from '../components/footertext';


class App extends Component {
  constructor(props,context) {
        super(props,context);
          this.state = {
            current: '1',
        };
    }
  render() {
    return (
      <div className="App">
        <div className="cons">
        < Headerbar className="headerbar" current = { this.state.current }/>
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