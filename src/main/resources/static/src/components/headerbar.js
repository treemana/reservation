import React, { Component } from 'react';
import '../css/app.css';
import School from '../images/school.gif';
import { Layout, Menu } from 'antd';
const { SubMenu } = Menu;
const { Header, Content, Sider } = Layout;

class Headerbar extends Component {
    render() {
    return (
        <Header className="header">
          <div className="logo" >
            <img src={School} className="school" />
          </div>
          <Menu
            theme="dark"
            mode="horizontal"
            defaultSelectedKeys={['1']}
            style={{ lineHeight: '64px' }}
          >
            <Menu.Item key="1">登录</Menu.Item>
            <Menu.Item key="2">帮助</Menu.Item>
            <Menu.Item key="3">开发人员</Menu.Item>
          </Menu>
        </Header>
    );
  }
}

export default Headerbar;