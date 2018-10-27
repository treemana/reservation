import React, { Component } from 'react';
import '../css/app.css';
import { Card, Row, Col, BackTop, Icon, Avatar, Button, InputNumber, Layout, Table, Divider, Tag } from 'antd';
const { Meta } = Card;
const columns = [{
  title: 'Name',
  dataIndex: 'name',
  key: 'name',
  render: text => <a href="javascript:;">{text}</a>,
}, {
  title: 'Age',
  dataIndex: 'age',
  key: 'age',
}, {
  title: 'Address',
  dataIndex: 'address',
  key: 'address',
}, {
  title: 'Tags',
  key: 'tags',
  dataIndex: 'tags',
  render: tags => (
    <span>
      {tags.map(tag => <Tag color="blue" key={tag}>{tag}</Tag>)}
    </span>
  ),
}, {
  title: 'Action',
  key: 'action',
  render: (text, record) => (
    <span>
      <a href="javascript:;">Invite {record.name}</a>
      <Divider type="vertical" />
      <a href="javascript:;">Delete</a>
    </span>
  ),
}];

const data = [{
  key: '1',
  name: 'John Brown',
  age: 32,
  address: 'New York No. 1 Lake Park',
  tags: ['nice', 'developer'],
}, {
  key: '2',
  name: 'Jim Green',
  age: 42,
  address: 'London No. 1 Lake Park',
  tags: ['loser'],
}, {
  key: '3',
  name: 'Joe Black',
  age: 32,
  address: 'Sidney No. 1 Lake Park',
  tags: ['cool', 'teacher'],
}];

function onChange(value) {
  console.log('changed', value);
}

class Stuid extends Component {
	render () {
		return (
		<Layout style={{ padding: '24px 24px 24px' }}>
		    <Card title="可参与预约的学生" bordered={true} className="areacard" style={{ padding: '0 0 50px' }}>
		      <InputNumber defaultValue={2016} onChange={onChange} />级 至 <InputNumber defaultValue={2017} onChange={onChange} />级
		    </Card>
		    <Card title="黑名单" bordered={true} className="areacard" style={{ padding: '0 0 24px' }}>
		      <div>
			    <Table columns={columns} dataSource={data} />
			  </div>
		    </Card>
		 </Layout>    
		);
	}
}

export default Stuid;