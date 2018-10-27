import React, { Component } from 'react';
import { Layout } from 'antd';
import '../css/app.css';
import logo from '../images/logo.png';
import Headerbar from '../components/headerbar';
import Siderbar from '../components/siderbar';
import Box from '../components/box';
import Footertext from '../components/footertext';
const { Content } = Layout;

class Stubox extends Component {
  render() {
    return (
      <Layout className="stupart">
        < Headerbar className="headerbar"/>
        < Siderbar className="siderbar" />
        <Content style={{ padding: '0 50px' }}>
      		<br />
        	<Box />
        </Content>
        < Footertext />
      </Layout>
    );
  }
}

export default Stubox;