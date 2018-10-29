import React, { Component } from 'react';
import { Layout } from 'antd';
import '../css/app.css';
import logo from '../images/logo.png';
import Headerbar from '../components/headerbar';
import Adsiderbar from '../components/adsiderbar';
import Adbox from '../components/adbox';
import Footertext from '../components/footertext';
const { Content } = Layout;

class Boxset extends Component {
  render() {
    return (
      <Layout className="stupart">
        < Headerbar className="headerbar"/>
        <Layout>
        < Adsiderbar className="siderbar" current="1"/>
        <Content style={{ padding: '0 50px' }}>
      		<Adbox />
        </Content>
        </Layout>
        < Footertext />
      </Layout>
    );
  }
}

export default Boxset;