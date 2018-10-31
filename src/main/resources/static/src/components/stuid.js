import React, { Component } from 'react';
import $ from 'jquery';
import req from '../url';
import '../css/app.css';
import { Card, Row, Col, BackTop, Icon, Avatar, Button, InputNumber, Layout, Table, Divider, Tag, notification } from 'antd';
const { Meta } = Card;
const columns = [{
  title: '姓名',
  dataIndex: 'name',
  key: 'name',
  
}, {
  title: '学号',
  dataIndex: 'studentId',
  key: 'studentId',
}, 
{
  title: '操作',
  key: 'action',
  render: (text, record) => (
    <span>
      <a href="javascript:;">删除{record.name}</a>
    </span>
  )
}];


class Stuid extends Component {
  constructor (props) {
    super(props);
    this.state = {
      data: [{
        key: '1',
        name: 'John Brown',
        studentId: 32
      }, {
        key: '2',
        name: 'John',
        studentId: 32
      }, {
        key: '3',
        name: 'Brown',
        studentId: 32
      }],
      count:3,
      startGrade: 2015,
      endGrade: 2016
    }
    this.handleAdd = () => {
      const { count, data } = this.state;
      const newData = {
        key: count,
        name: `新增${count}`,
        studentId: 32
      };
      this.setState({
        data: [...data, newData],
        count: count + 1,
      });
    }
    this.handleClick = () => {
      console.log(this.state);
      // $.post(req+'open-grades',{
      //   startGrade: this.state.startGrade,
      //   endGrade: this.state.endGrade
      // }, function(res){
      //   if(res.code==0) {
      //     notification.open({
      //       messstudentId: '提示',
      //       description: '修改成功！',
      //     });
      //   }
      //   else {
      //     notification.open({
      //       messstudentId: '提示',
      //       description: '修改失败！',
      //     });
      //   }
      // });
    }
  }
	render () {
		return (
		<Layout style={{ padding: '24px 24px 24px' }}>
		    <Card title="可参与预约的学生" bordered={true} className="areacard" style={{ padding: '0 0 50px' }}>
		      <InputNumber defaultValue={this.state.startGrade} onChange={(value)=>this.setState({startGrade:value})}/> 级 至 <InputNumber defaultValue={this.state.endGrade} onChange={(value)=>this.setState({endGrade:value})} /> 级<Button type="primary" style={{marginLeft: '20px'}} onClick={this.handleClick}>保存</Button>
        </Card>
		    <Card title="黑名单" bordered={true} className="areacard" style={{ padding: '0 0 24px' }}>
		      <div>
          <Button onClick={this.handleAdd} type="primary" style={{ marginBottom: 16 }}>
           添加
          </Button>
			    <Table columns={columns} dataSource={this.state.data} pagination={{position: 'none'}} />
			  </div>
		    </Card>
		 </Layout>    
		);
	}
}

export default Stuid;