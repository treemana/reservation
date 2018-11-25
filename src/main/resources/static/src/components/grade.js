import React, { Component } from 'react';
import $ from 'jquery';
import { config } from 'jquery.cookie';
import req from '../url';
import '../css/app.css';
import { Card, Row, Col, BackTop, Icon, Avatar, Button, InputNumber, Layout, Table, Divider, Tag, notification, Input, Popconfirm } from 'antd';
const { Meta } = Card;
const Search = Input.Search;

class Grade extends Component {
  constructor (props) {
    super(props);
    this.state = {
     
    };
    this.handleClick = () => {
       $.ajax({
          type: 'PUT',
          url: req+'open-grades',
          contentType: 'application/json;charset=UTF-8',
          headers: {
            'token': $.cookie('token')
          },
          data: JSON.stringify({
              startGrade: this.state.startGrade,
              endGrade: this.state.endGrade
          }),
          success: function(res) {
            if(res.code === 0)
            {
              notification.open({
                message: '提示',
                description: '修改成功！'
              });
              this.setState({
                startGrade: this.state.startGrade,
                endGrade: this.state.endGrade
              })
            }
            else {
              notification.open({
                message: '提示',
                description: res.message
              });
            }
          }.bind(this)
       });
    };
  }
  componentDidMount() {
    $.ajax({
          type: 'GET',
          url: req+'open-grades',
          contentType: 'application/json;charset=UTF-8',
          headers: {
            'token': $.cookie('token')
          },
          success: function(res) {
            if(res.code == 0) {
              var data = res.data;
              this.setState({
                startGrade: parseInt(data.startGrade),
                endGrade: parseInt(data.endGrade)
              });
            }
          }.bind(this)
       });
  }
  render() {
    return (
    <Card title="可参与预约的学生" bordered={true} className="areacard" style={{ padding: '0 0 50px' }}>
          <InputNumber value={this.state.startGrade} onChange={(value)=>this.setState({startGrade:value})}/> 级 至  <InputNumber value={this.state.endGrade} onChange={(value)=>this.setState({endGrade:value})} /> 级<Button type="primary" style={{marginLeft: '20px'}} onClick={this.handleClick}>保存</Button>
    </Card>  
       
    );
  }
}

export default Grade;