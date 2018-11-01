import React, { Component } from 'react';
import { Timeline, Card } from 'antd';

class Devline extends Component {
	render () {
		return (
		      <Card title="版本历程" bordered={true} className="areacard" style={{ padding: '0 0 450px' }}>
		          <Timeline mode="alternate">
				    <Timeline.Item>1.0 2018-10-27<p>前端：贺妍</p></Timeline.Item>
				    <Timeline.Item>1.5 2018-10-27<p>前端：贺妍</p></Timeline.Item>
				  </Timeline>
		      </Card>
		);
	}
}

export default Devline;