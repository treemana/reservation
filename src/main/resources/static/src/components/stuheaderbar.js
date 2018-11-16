import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import '../css/app.css';
import School from '../images/school.gif';
import { Layout, Menu } from 'antd';
const { SubMenu } = Menu;
const { Header, Content, Sider } = Layout;


class Stuheaderbar extends Component {
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
        <Layout className="header">
          <Menu
            theme="dark"
            selectedKeys={[this.state.current]}
            mode="horizontal"
            style={{textAlign:"right"}}
          >
          <div className="logo" >
            <img src={School} className="school" />
          </div>
            <Menu.Item key="1"><Link to="/">{this.props.id?'登出':'登录'}</Link></Menu.Item>
            <Menu.Item key="2"><Link to={"/stuhelp/"+this.state.id}>帮助</Link></Menu.Item>
            <Menu.Item key="3"><Link to={"/studev/"+this.state.id}>开发相关</Link></Menu.Item>
          </Menu>
        </Layout>
    );
  }
}

export default Stuheaderbar;