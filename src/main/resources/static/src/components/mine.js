import React, { Component } from 'react';
import '../css/app.css';
import { Card, Row, Col, BackTop, Icon, Avatar, Button } from 'antd';
const { Meta } = Card;

class Mine extends Component {
  render() {
    return (
      <Card title="我的书包柜" bordered={true} className="areacard">
        <p><span>位置：</span>二楼北 2345 </p>
        <p><span>状态：</span>可用 </p>
      </Card>
      
    );
  }
}

export default Mine;