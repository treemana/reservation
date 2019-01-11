import {Component} from 'react';
import '../css/app.css';
import {Avatar, BackTop, Button, Card, Col, DatePicker, Icon, Layout, notification, Row, TimePicker} from 'antd';
import moment from 'moment';
import 'moment/locale/zh-cn';
import locale from 'antd/lib/date-picker/locale/zh_CN';
import $ from 'jquery';
import {config} from 'jquery.cookie';
import req from '../url';

const { Meta } = Card;
const { RangePicker } = DatePicker;

moment.locale('zh-cn');

class Time extends Component {
  constructor (props) {
    super(props);
    this.state = { 
      open: false,
      startTime: "点击设置",
      endTime: "点击设置"
    };
    this.handleOpenChange = (open) => {
      this.setState({ open });
    };
    this.handleClose = () => this.setState({ open: false });
    this.onChange = (value, dateString) => {
      this.setState({
        startTime: dateString[0],
        endTime: dateString[1]
      })
    };
    this.onOk = (value) => {
       $.ajax({
          type: 'PUT',
          url: req+'open-time',
          contentType: 'application/json;charset=UTF-8',
          headers: {
            'token': $.cookie('token')
          },
          data: JSON.stringify({
              startTime: this.state.startTime,
              endTime: this.state.endTime
          }),
          success: function(res) {
            if(res.code === 0)
            {
              notification.open({
                message: '提示',
                description: '修改成功！'
              });
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
          url: req+'open-time',
          contentType: 'application/json;charset=UTF-8',
          headers: {
            'token': $.cookie('token')
          },
          success: function(res) {
            if(res.code == 0) {
              var data = res.data;
              this.setState({
                startTime: data.startTime,
                endTime: data.endTime
              });
            }
          }.bind(this)
       });
  }
  render() {
    return (
        < Layout;
      style = {;
      {
          '24px 24px 24px'
      }
  }>
  <
      Card;
      title = "可预约时间设置";
      bordered = {true};
      className = "areacard";
      style = {;
      {
          '0 0 350px'
      }
  }>
      <div>

      < RangePicker;
      locale = {locale};
      showTime = {;
      {
          'HH:mm:ss'
      }
  }
      format = "YYYY-MM-DD HH:mm:ss";
      placeholder = {[this.state.startTime, this.state.endTime;
  ]
  }
      onChange = {this.onChange};
      onOk = {this.onOk};
	    />
	  </div>
    </Card>
        < /Layout>    ;

  )
  }
}

export default Time;