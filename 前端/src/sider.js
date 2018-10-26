import React, { Component } from 'react';
import Login from './login';
import './app.css';
import logo from './logo.png';
import { Layout, Menu, Breadcrumb, Icon } from 'antd';
import Areacard from './area';
import Footertext from './footer';
const SubMenu = Menu.SubMenu;
const MenuItemGroup = Menu.ItemGroup;
const { Header, Content, Sider, Footer } = Layout;

class Siderbar extends Component {
  state = {
    current: 'mail',
  }

  handleClick = (e) => {
    console.log('click ', e);
    this.setState({
      current: e.key,
    });
  }
  render() {
    return (
    <Layout>
      <Menu
        theme="light"
        onClick={this.handleClick}
        selectedKeys={[this.state.current]}
        mode="horizontal"
      >
        <Menu.Item key="mail">
          <Icon type="pushpin" />书包柜预约
        </Menu.Item>
        <Menu.Item key="my">
          <Icon type="shopping" />我的
        </Menu.Item>
      </Menu>
      <Content style={{ padding: '0 50px' }}>
      <br />
        <Areacard/>
      </Content>
      <Footertext />
    </Layout>
    );
  }
}

export default Siderbar;