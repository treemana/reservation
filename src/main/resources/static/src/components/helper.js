import React, { Component } from 'react';
import { Layout, Card } from 'antd';
import '../css/app.css';
const { Content } = Layout;

class Helper extends Component {
  render() {
    return (
      <Layout style={{ padding: '24px 24px 24px' }}>
        <Card title="服务相关" bordered={true} className="areacard" style={{ padding: '0 0 450px' }}>
          <p>你尽管抢</p>
        </Card>
      </Layout>
    );
  }
}

export default Helper;