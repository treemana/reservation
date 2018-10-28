import React, { Component } from 'react';
import '../css/app.css';
import { Layout, Menu, Breadcrumb, Icon } from 'antd';
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
    </Layout>
    );
  }
}

export default Siderbar;