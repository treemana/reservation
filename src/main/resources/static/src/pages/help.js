import React, { Component } from 'react';
import '../css/app.css';
import logo from '../images/logo.png';
import Headerbar from '../components/headerbar';
import Footertext from '../components/footertext';
import Helper from '../components/helper';
import { Layout } from 'antd';
const { Content } = Layout;

class Help extends Component {
	constructor(props,context) {
        super(props,context);
          this.state = {
            current: '2',
        };
    }
  render() {
    return (
      <Layout className="App">
          < Headerbar className="headerbar" current = { this.state.current }/>
          < Helper />
        <Footertext />
      </Layout>
    );
  }
}

export default Help;