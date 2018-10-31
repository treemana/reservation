import React, { Component } from 'react';
import '../css/app.css';
import { Card, Row, Col, BackTop, Icon, Avatar, Button } from 'antd';
const { Meta } = Card;

class Mine extends Component {
  constructor(props,context) {
    super(props,context);
    this.state={
      current: 'my',
      id: this.props.id
    };   
  }
  componentWillMount () {
  	
  }
  render() {
    return (
      <Card title="我的书包柜" bordered={true} className="areacard" style={{ padding: '0 0 350px' }}>
        <p><span>位置：</span>{this.props.location?this.props.location:'无'}</p>
        <p><span>编号：</span>{this.props.number?this.props.number:'无'} </p>
      </Card>
      
    );
  }
}

export default Mine;