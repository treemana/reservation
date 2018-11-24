import {Component} from 'react';
import $ from 'jquery';
import {config} from 'jquery.cookie';
import req from '../url';
import '../css/app.css';
import {
    Avatar,
    Button,
    Card,
    Checkbox,
    Col,
    Divider,
    Icon,
    Input,
    InputNumber,
    Layout,
    notification,
    Popconfirm,
    Row,
    Table,
    Tag
} from 'antd';

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
      var areaindex = [];
      for(let i = 0; i<this.state.checkedList.length;i++)
      {
        areaindex.push(this.plainOptions.indexOf(this.state.checkedList[i])+1);
      }
      $.ajax({
          type: 'PUT',
          url: req+'open-area',
          data: JSON.stringify(areaindex),
          contentType: 'application/json;charset=UTF-8',
          headers: {
            'token': $.cookie('token')
          },
          success: function(res) {
            if(res.code == 0)
            {
              notification.open({
                  message: '提示',
                description: '修改成功！'
              });
                setTimeout(() => {
                    window.location.reload();
                }, 2500);
            }
            else {
              notification.open({
                  message: '提示',
                  description: res.message
              });
            }
          }.bind(this)
       });
    }
  }
  componentDidMount () {
    $.ajax({
          type: 'GET',
          url: req+'open-area',
          contentType: 'application/json;charset=UTF-8',
          headers: {
            'token': $.cookie('token')
          },
          success: function(res) {
            if(res.code == 0) {
              var data = res.data;
              var list = [];
              for(let i = 0;i<data.length;i++)
              {
                  if (data[i].configValue == 0)
                  list.push(this.plainOptions[data[i].systemId-1]);
              }
              this.setState({checkedList: list});
            }
          }.bind(this)
       });
  }
  render () {
    return (
        < Layout;
      style = {;
      {
          '24px 24px 24px'
      }
  }>
  <
      Card;
      title = "开放预约的区域";
      bordered = {true};
      className = "areacard";
      style = {;
      {
          '0 0 250px'
      }
  }>
  <
      div;
      style = {;
      {
          '1px solid #E9E9E9'
      }
  }>
  <
      Checkbox;
      indeterminate = {this.state.indeterminate};
      onChange = {this.onCheckAllChange};
                checked={this.state.checkAll}
              >
                全选
              </Checkbox>
            </div>
            <br />
                    < CheckboxGroup;
      options = {this.plainOptions};
      value = {this.state.checkedList};
      onChange = {this.onChange};
      /><Button type="primary" style={{marginLeft: '20px'}} onClick={this.handleClick}>保存</;
      Button >
        </Card>
      < /Layout>    ;

  )
  }
}

export default Area;