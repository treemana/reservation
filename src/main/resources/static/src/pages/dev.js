import React, { Component } from 'react';
import '../css/app.css';
import logo from '../images/logo.png';
import Headerbar from '../components/headerbar';
import Footertext from '../components/footertext';
import Devline from '../components/devline';
import { Layout } from 'antd';
const { Content } = Layout;

class Help extends Component {
	constructor(props,context) {
        super(props,context);
          this.state = {
            current: '3',
        };
    }
  render() {
    return (
      <Layout className="App">
          < Headerbar className="headerbar" current = { this.state.current }/>
          <Content style={{ padding: '24px 24px 0 24px'}}>
          < Devline />
          </Content>
        <Footertext />
      </Layout>
    );
  }
}

export default Help;