import React, { Component } from 'react';
import '../css/app.css';
import { Layout, Menu, Breadcrumb, Icon } from 'antd';
const { Header, Content, Sider, Footer } = Layout;
const SubMenu = Menu.SubMenu;
const MenuItemGroup = Menu.ItemGroup;

class Adsiderbar extends Component {
  handleClick = (e) => {
    console.log('click ', e);
  }

  render() {
    return (
      <Sider width={200} style={{ background: '#fff' }}>
        <Menu
          onClick={this.handleClick}
          style={{ width: 200 }}
          defaultSelectedKeys={['1']}
          defaultOpenKeys={['sub1']}
          mode="inline"
        >
          <Menu.Item key="1"><Icon type="book" />书包柜管理</Menu.Item>
          <Menu.Item key="2"><Icon type="hourglass" />预约时间管理</Menu.Item>
          <Menu.Item key="3"><Icon type="team" />学号管理</Menu.Item>
        </Menu>
      </Sider>
    );
  }
}

export default Adsiderbar;