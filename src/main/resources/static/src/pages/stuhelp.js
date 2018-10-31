import React, { Component } from 'react';
import '../css/app.css';
import logo from '../images/logo.png';
import Stuheaderbar from '../components/stuheaderbar';
import Footertext from '../components/footertext';
import Helper from '../components/helper';
import Siderbar from '../components/siderbar';
import { Layout } from 'antd';
const { Content } = Layout;

class Stuhelp extends Component {
	constructor(props,context) {
        super(props,context);
          this.state = {
            id: this.props.match.params.id,
            current: '2'
        };
    }
  render() {
    return (
      <Layout>
          < Stuheaderbar className="headerbar" current = { this.state.current } id={this.state.id}/>
          < Siderbar className="siderbar" current="-1" id={this.state.id}/>
          <Content style={{ padding: '0 50px' }}>
          <br />
          < Helper />
          </Content>
        <Footertext />
      </Layout>
    );
  }
}

export default Stuhelp;