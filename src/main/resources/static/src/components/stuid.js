import React, { Component } from 'react';
import $ from 'jquery';
import req from '../url';
import '../css/app.css';
import { Card, Row, Col, BackTop, Icon, Avatar, Button, InputNumber, Layout, Table, Divider, Tag, notification, Input, Popconfirm } from 'antd';
const { Meta } = Card;
const Search = Input.Search;

class Stuid extends Component {
  constructor (props) {
    super(props);
    this.columns = [{
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
              <Popconfirm title="确定要删除吗?" onConfirm={() => this.handleDelete(record.studentId)} okText="确定" cancelText="取消">
                <a href="javascript:;">删除</a>
              </Popconfirm>
      )
    }]
    this.state = {
      startGrade: 2015,
      endGrade: 2016
    }
    this.handleDelete = (key) => {
      console.log(key);
      // $.delete(req+'list/'+key,null,function(res) {
      //   if(res.code == 0) {
      //     const data = [...this.state.data];
      //     this.setState({ data: data.filter(item => item.studentId !== key) });
      //   }
      //   else {
      //     notification.open({
      //       messstudentId: '提示',
      //       description: '添加成功！'
      //     });
      //   }
      // });
      
    }
    this.handleAdd = (value) => {
      console.log(value);
      // $.post(req+list,{
      //   studentId: value
      // },function(res) {
      //   if(res.code == 0) {
      //     notification.open({
      //       messstudentId: '提示',
      //       description: '添加成功！'
      //     });
      //   }
      //   else {
      //     notification.open({
      //       messstudentId: '提示',
      //       description: '添加失败！'
      //     })
      //   }
      // });
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
  componentDidMount() {
    this.setState({
      data: [
        {
          studentId: 123,
          name: "name"
        }
      ]
    });
    // $.get(req+'list',null,function(res) {
    //   var data = [];
    //   if(res.code == 0) {
    //     var data = res.data;
    //     this.setState(data: data);
    //   }
    // }.bind(this));
  }
	render () {
		return (
		<Layout style={{ padding: '24px 24px 24px' }}>
		    <Card title="可参与预约的学生" bordered={true} className="areacard" style={{ padding: '0 0 50px' }}>
		      <InputNumber defaultValue={this.state.startGrade} onChange={(value)=>this.setState({startGrade:value})}/> 级 至 <InputNumber defaultValue={this.state.endGrade} onChange={(value)=>this.setState({endGrade:value})} /> 级<Button type="primary" style={{marginLeft: '20px'}} onClick={this.handleClick}>保存</Button>
        </Card>
		    <Card title="黑名单" bordered={true} className="areacard" style={{ padding: '0 0 24px' }}>
		      <div>
           <Search
              style={{width: "300px"}}
              placeholder="请输入学生学号"
              enterButton="添加到黑名单"
              onSearch={value => this.handleAdd(value)}
            />
			    <Table columns={this.columns} dataSource={this.state.data} pagination={{position: 'none'}} />
			  </div>
		    </Card>
		 </Layout>    
		);
	}
}

export default Stuid;