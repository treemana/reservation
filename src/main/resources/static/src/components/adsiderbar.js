import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import '../css/app.css';
import { Layout, Menu, Icon } from 'antd';
const { Sider } = Layout;

class Adsiderbar extends Component {
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
      <Sider width={200} style={{ background: '#fff' }}>
        <Menu
          style={{ width: 200 }}
          selectedKeys={[this.state.current]}
          mode="inline"
        >
          <Menu.Item key="1"><Link to={"/boxset/"+this.state.id}><Icon type="book" />书包柜管理</Link></Menu.Item>
          <Menu.Item key="2"><Link to={"/timeset/"+this.state.id}><Icon type="hourglass" />预约时间管理</Link></Menu.Item>
          <Menu.Item key="3"><Link to={"/areaset/"+this.state.id}><Icon type="appstore" />区域管理</Link></Menu.Item>
          <Menu.Item key="4"><Link to={"/idset/"+this.state.id}><Icon type="team" />学号管理</Link></Menu.Item>
        </Menu>
      </Sider>
    );
  }
}

export default Adsiderbar;