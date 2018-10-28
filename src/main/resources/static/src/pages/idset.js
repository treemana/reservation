import React, { Component } from 'react';
import { Layout } from 'antd';
import '../css/app.css';
import logo from '../images/logo.png';
import Headerbar from '../components/headerbar';
import Adsiderbar from '../components/adsiderbar';
import Stuid from '../components/stuid';
import Footertext from '../components/footertext';
const { Content } = Layout;

class Stubox extends Component {
  render() {
    return (
      <Layout className="stupart">
        < Headerbar className="headerbar"/>
        <Layout>
        < Adsiderbar className="siderbar" />
        <Content style={{ padding: '0 50px' }}>
          <Stuid />
        </Content>
        </Layout>
        < Footertext />
      </Layout>
    );
  }
}

export default Stubox;