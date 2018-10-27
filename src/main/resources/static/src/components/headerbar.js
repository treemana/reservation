import React, { Component } from 'react';
import '../css/app.css';
import { Layout, Menu } from 'antd';
const { SubMenu } = Menu;
const { Header, Content, Sider } = Layout;

class Headerbar extends Component {
    render() {
    return (
        <Header className="header">
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