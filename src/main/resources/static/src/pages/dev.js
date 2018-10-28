import React, { Component } from 'react';
import '../css/app.css';
import logo from '../images/logo.png';
import Headerbar from '../components/headerbar';
import Footertext from '../components/footertext';
import Devline from '../components/devline';
import { Layout } from 'antd';
const { Content } = Layout;

class Help extends Component {
  render() {
    return (
      <Layout className="App">
          < Headerbar className="headerbar"/>
          < Devline />
        <Footertext />
      </Layout>
    );
  }
}

export default Help;