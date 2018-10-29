import React, { Component } from 'react';
import { Layout } from 'antd';
import '../css/app.css';
import logo from '../images/logo.png';
import Headerbar from '../components/headerbar';
import Adsiderbar from '../components/adsiderbar';
import Time from '../components/time';
import Footertext from '../components/footertext';
const { Content } = Layout;

class Timeset extends Component {
  render() {
    return (
      <Layout className="stupart">
        < Headerbar className="headerbar"/>
        <Layout>
        < Adsiderbar className="siderbar" current="2"/>
        <Content style={{ padding: '0 50px' }}>
        <br />
      		<Time />
        </Content>
        </Layout>
        < Footertext />
      </Layout>
    );
  }
}

export default Timeset;