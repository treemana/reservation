import React, { Component } from 'react';
import { Layout } from 'antd';
import $ from 'jquery';
import { config } from 'jquery.cookie';
import '../css/app.css';
import logo from '../images/logo.png';
import Adheaderbar from '../components/adheaderbar';
import Adsiderbar from '../components/adsiderbar';
import Area from '../components/area';
import Footertext from '../components/footertext';
const { Content } = Layout;

class Areaset extends Component {
  constructor(props,context) {
    super(props,context);
    this.state = {
      id: this.props.match.params.id
    };
    if(!$.cookie('token')) {
          window.location.href='/';
        }
  }
  render() {
    return (
      <Layout className="stupart">
       < Adheaderbar className="headerbar" current = '-1' id={this.state.id}/>
        <Layout>
        < Adsiderbar className="siderbar" current="3" id={this.state.id}/>
        <Content style={{ padding: '0 50px' }}>
          <Area />
        </Content>
        </Layout>
        < Footertext />
      </Layout>
    );
  }
}

export default Areaset;