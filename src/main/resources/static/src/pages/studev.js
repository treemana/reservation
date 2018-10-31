import React, { Component } from 'react';
import '../css/app.css';
import logo from '../images/logo.png';
import Stuheaderbar from '../components/stuheaderbar';
import Siderbar from '../components/siderbar';
import Footertext from '../components/footertext';
import Devline from '../components/devline';
import { Layout } from 'antd';
const { Content } = Layout;

class Studev extends Component {
  constructor(props,context) {
        super(props,context);
          this.state = {
            current: '3',
            id: this.props.match.params.id
        };
    }
  render() {
    return (
      <Layout>
          < Stuheaderbar className="headerbar" current = { this.state.current } id={this.state.id}/>
          < Siderbar className="siderbar" current="-1" id={this.state.id}/>
          <Content style={{ padding: '0 50px' }}>
          <br />
          < Devline />
          </Content>
        <Footertext />
      </Layout>
    );
  }
}

export default Studev;