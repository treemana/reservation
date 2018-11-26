import React, { Component } from 'react';
import { Timeline, Row, Col, Card } from 'antd';

class Devline extends Component {
	render () {
		return (
		      <Card title="版本历程" bordered={true} style={{ textAlign: 'left' }}>
		      <div style={{ textAlign: 'left' }}>
		      	  <p>开发人员(字母序)</p>
		      	  <Row>
		      	  <Col span={3} style={{wordWrap:"break-word",wordBreak:"break-all"}}>
		      	  Hunter(开发代号)</Col>
		      	  <Col span={21}>
		      	  ：东北林业大学，本硕老学长，负责服务端基础运维及项目对接</Col>
		      	  </Row>
		      	  <Row>
		      	  <Col span={3}>
		      	  曹铭洋</Col>
		      	  <Col span={21}>
		      	  ：东北林业大学，计算机科学与技术专业，2016-02班，负责系统权限管理模块开发</Col>
		      	  </Row>
		      	  <Row>
		      	  <Col span={3}>
		      	  陈秋婷</Col>
		      	  <Col span={21}>
		      	  ：东北林业大学，计算机科学与技术专业，2016-06班，负责系统管理模块开发</Col>
		      	  </Row>
		      	  <Row>
		      	  <Col span={3}>
		      	  陈卫弥</Col>
		      	  <Col span={21}>
		      	  ：东北林业大学，计算机科学与技术专业，2016-02班，负责排队算法及书包柜分配</Col>
		      	  </Row>
		      	  <Row>
		      	  <Col span={3}>
		      	  賀賀(开发代号)</Col>
		      	  <Col span={21}>
		      	  ：东北林业大学，计算机科学与技术专业，2016-06班，负责UI设计及前端页面开发</Col>
		      	  </Row>
		      	  <Row>
		      	  <Col span={3}>
		      	  彭冲</Col>
		      	  <Col span={21}>
		      	  ：东北林业大学，计算机科学与技术专业，2016-02班，负责数据库管理及系统压测</Col>
		      	  </Row>
		      	  <br />
		      	  <div style={{ borderBottom: '1px solid #E9E9E9' }} /><br />
		          <Timeline mode="alternate">
		          	<Timeline.Item>2018.11.10 正式版本发布,版本号:V1.0</Timeline.Item>
		          	<Timeline.Item>2018.11.04 开发完成,Alpha版发布</Timeline.Item>
				    <Timeline.Item>2018.10.28 系统设计完成</Timeline.Item>
				  </Timeline>
				</div>
		      </Card>
		);
	}
}

export default Devline;