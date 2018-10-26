import React, { Component } from 'react';
import './app.css';
import { Card, Row, Col, BackTop, Icon, Avatar, Button } from 'antd';
import b2 from './2b.png';
import b3 from './3b.png';
import n2 from './2n.png';
import n3 from './3n.png';
const { Meta } = Card;

class Areacard extends Component {
  render() {
    return (
      <Card title=" 预约还未开始" bordered={true} className="areacard">
      <Row gutter={30}>
          <Col span={12}>
            <Card
              cover={<img alt="example" src={b2} />}
            >
            <p><b>本区域还余234个可预约</b></p>
              <Button type="primary" block>预约</Button>
            </Card>
          </Col>
          <Col span={12}>
            <Card
              cover={<img alt="example" src={n2} />}
            >
            <p><b>本区域还余234个可预约</b></p>
              <Button type="primary" block>预约</Button>
            </Card>
          </Col>
      </Row>
      <Row gutter={30}>
          <Col span={12}>
            <Card
              cover={<img alt="example" src={b3} />}
            >
            <p><b>本区域还余234个可预约</b></p>
              <Button type="primary" block>预约</Button>
            </Card>
          </Col>
          <Col span={12}>
            <Card
              cover={<img alt="example" src={n3} />}
            >
            <p><b>本区域还余234个可预约</b></p>
              <Button type="primary" block>预约</Button>
            </Card>
          </Col>
      </Row>
      <Row>
          <Col span={24}>
            <p><Button type="primary" block>随机预约</Button></p>
          </Col>
      </Row>
      </Card>
      
    );
  }
}

export default Areacard;