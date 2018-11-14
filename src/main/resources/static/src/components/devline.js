import React, { Component } from 'react';
import { Timeline, Card } from 'antd';

class Devline extends Component {
	render () {
		return (
		      <Card title="版本历程" bordered={true} className="areacard" style={{ padding: '0 0 450px' }}>
		          <Timeline mode="alternate">
				    <Timeline.Item>2018.10.28 系统设计完成</Timeline.Item>
				    <Timeline.Item>2018.11.04 开发完成,Alpha版发布</Timeline.Item>
				    <Timeline.Item>2018.11.10 正式版本发布,版本号:V1.0<br />开发人员(字母序)<br />Hunter(开发代号):<br />东北林业大学,本硕老学长,负责服务端基础运维及项目对接<br />曹铭洋:<br />东北林业大学,计算机科学与技术专业,2016-02班,负责系统权限管理模块开发<br />陈秋婷:<br />东北林业大学,计算机科学与技术专业,2016-06班,负责系统管理模块开发<br />陈卫弥:<br />东北林业大学,计算机科学与技术专业,2016-02班,负责排队算法及书包柜分配<br />賀賀(开发代号):<br />东北林业大学,计算机科学与技术专业,2016-06班,负责UI设计及前端页面开发<br />彭冲:<br />东北林业大学,计算机科学与技术专业,2016-02班,负责数据库管理及系统压测</Timeline.Item>
				  </Timeline>
		      </Card>
		);
	}
}

export default Devline;