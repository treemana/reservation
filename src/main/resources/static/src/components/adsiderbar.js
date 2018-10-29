import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import '../css/app.css';
import { Layout, Menu, Breadcrumb, Icon } from 'antd';
const { Header, Content, Sider, Footer } = Layout;
const SubMenu = Menu.SubMenu;
const MenuItemGroup = Menu.ItemGroup;

class Adsiderbar extends Component {
  constructor(props) {
    super(props);
      this.state = {
        current: this.props.current,
    };
    }
  componentWillReceiveProps(nextProps) {
    const { current } = this.state
    const newdata = nextProps.current.toString()
    if (current.toString() !== newdata) {
      this.setState({
        current: nextProps.current
      })
    }
  }
  render() {
    return (
      <Sider width={200} style={{ background: '#fff' }}>
        <Menu
          style={{ width: 200 }}
          selectedKeys={[this.state.current]}
          mode="inline"
        >
          <Menu.Item key="1"><Link to="/boxset"><Icon type="book" />书包柜管理</Link></Menu.Item>
          <Menu.Item key="2"><Link to="/timeset"><Icon type="hourglass" />预约时间管理</Link></Menu.Item>
          <Menu.Item key="3"><Link to="/idset"><Icon type="team" />学号管理</Link></Menu.Item>
        </Menu>
      </Sider>
    );
  }
}

export default Adsiderbar;