import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import $ from 'jquery';
import '../css/app.css';
import School from '../images/school.gif';
import { Layout, Menu } from 'antd';


class Headerbar extends Component {
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
        <Layout className="header">
          <Menu
            theme="dark"
            selectedKeys={[this.state.current]}
            mode="horizontal"
            style={{textAlign:"right"}}
          >
          <div className="logo" >
            <a href="http://lib.nefu.edu.cn/" target="view_window"><img src={School} className="school" /></a>
          </div>
            <Menu.Item key="1" onClick={$.cookie("token", "", {expires: -1})}><Link to="/">{this.props.id?'登出':'登录'}</Link></Menu.Item>
            <Menu.Item key="2"><Link to="/help">帮助</Link></Menu.Item>
            <Menu.Item key="3"><Link to="/dev">开发相关</Link></Menu.Item>
          </Menu>
        </Layout>
    );
  }
}

export default Headerbar;