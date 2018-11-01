import React, { Component } from 'react';
import { Card } from 'antd';
import '../css/app.css';

class Helper extends Component {
  render() {
    return (
        <Card title="服务相关" bordered={true} className="areacard" style={{ padding: '0 0 450px' }}>
          <p>你尽管抢</p>
        </Card>
    );
  }
}

export default Helper;