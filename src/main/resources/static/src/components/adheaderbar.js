import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import '../css/app.css';
import School from '../images/school.gif';
import { Layout, Menu } from 'antd';
const { SubMenu } = Menu;
const { Header, Content, Sider } = Layout;


class Adheaderbar extends Component {
  constructor(props) {
    super(props);
      this.state = {
        current: this.props.current,
        id: this.props.id
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
        <Header className="header">
          <div className="logo" >
            <img src={School} className="school" />
          </div>
          <Menu
            theme="dark"
            selectedKeys={[this.state.current]}
            mode="horizontal"
            style={{ lineHeight: '64px' }}
          >
            <Menu.Item key="1"><Link to="/">{this.props.id?'登出':'登录'}</Link></Menu.Item>
            <Menu.Item key="2"><Link to={"/adhelp/"+this.state.id}>帮助</Link></Menu.Item>
            <Menu.Item key="3"><Link to={"/addev/"+this.state.id}>开发人员</Link></Menu.Item>
          </Menu>
        </Header>
    );
  }
}

export default Adheaderbar;