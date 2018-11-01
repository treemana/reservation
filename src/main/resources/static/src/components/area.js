import React, { Component } from 'react';
import $ from 'jquery';
import req from '../url';
import '../css/app.css';
import { Card, Row, Col, Checkbox, Icon, Avatar, Button, InputNumber, Layout, Table, Divider, Tag, notification, Input, Popconfirm } from 'antd';
const { Meta } = Card;
const CheckboxGroup = Checkbox.Group;

class Area extends Component {
  constructor (props) {
    super(props);
    this.plainOptions = ['二楼北', '二楼南', '三楼北', '三楼南'];
    this.state = {
      checkedList: [],
      indeterminate: true,
      checkAll: false,
    };
    this.onChange = (checkedList) => {
      this.setState({
        checkedList,
        indeterminate: !!checkedList.length && (checkedList.length < this.plainOptions.length),
        checkAll: checkedList.length === this.plainOptions.length,
      });
    };
    this.onCheckAllChange = (e) => {
      this.setState({
        checkedList: e.target.checked ? this.plainOptions : [],
        indeterminate: false,
        checkAll: e.target.checked,
      });
    };
    this.handleClick = () => {
      console.log(this.state);
      var areaindex = [];
      for(let i = 0; i<this.state.checkedList.length;i++)
      {
        areaindex.push(this.plainOptions.indexOf(this.state.checkedList[i])+1);
      }
      console.log(areaindex);
      // $.ajax({
      //     type: 'PUT',
      //     url: req+'open-area',
      //     data: areaindex,
      //     dataType: "json",
      //     success: function(res) {
      //       if(res.code == 0)
      //       {
      //         notification.open({
      //           messstudentId: '提示',
      //           description: '修改成功！'
      //         });
      //       }
      //       else {
      //         notification.open({
      //           messstudentId: '提示',
      //           description: '修改失败！'
      //         });
      //       }
      //     }
      //  });
    }
  }
  componentDidMount () {
    this.setState({checkedList: ['二楼北', '二楼南']});
    // $.get(req+'open-area',null,function(res) {
    //   if(res.code == 0) {
    //     var data = res.data;
    //     var list = [];
    //     for(let i = 0;i<data.length;i++)
    //     {
    //       if(data[i].configValue == 1)
    //         list.push(this.plainOptions[data[i].systemId-1]);
    //     }
    //     this.setState({checkedList: list});
    //   }
    // }.bind(this));
  }
  render () {
    return (
    <Layout style={{ padding: '24px 24px 24px' }}>
        <Card title="开放预约的区域" bordered={true} className="areacard" style={{ padding: '0 0 250px' }}>
          <div style={{ borderBottom: '1px solid #E9E9E9' }}>
              <Checkbox
                indeterminate={this.state.indeterminate}
                onChange={this.onCheckAllChange}
                checked={this.state.checkAll}
              >
                全选
              </Checkbox>
            </div>
            <br />
            <CheckboxGroup options={this.plainOptions} value={this.state.checkedList} onChange={this.onChange} /><Button type="primary" style={{marginLeft: '20px'}} onClick={this.handleClick}>保存</Button>
        </Card>
     </Layout>    
    );
  }
}

export default Area;