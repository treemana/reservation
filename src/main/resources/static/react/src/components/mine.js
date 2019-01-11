import {Component} from 'react';
import '../css/app.css';
import {Avatar, BackTop, Button, Card, Col, Icon, Input, Modal, notification, Row} from 'antd';
import $ from 'jquery';
import {config} from 'jquery.cookie';
import req from '../url';

const Search = Input.Search;
const { Meta } = Card;

class Mine extends Component {
  constructor(props,context) {
    super(props,context);
    this.state={
      current: 'my',
      id: this.props.id,
      before:0
    };
    this.myStatus = () => {//查看我的状态
      this.setState({
        visible: true
      });
      $.ajax({
        method: "GET",
        url: req+'code',
        headers: {
          'token': $.cookie('token')
        },
         xhrFields: {withCredentials: true},
        contentType: 'application/json;charset=UTF-8',
        success: function(res) {
          this.setState({
            code: res.data
          });
        }.bind(this)
      });
    };
    this.sendCode = (value) => {
            this.setState({
            showMyStatus: true
          });
            $.ajax({
              method: "GET",
              url: req+'status?studentId='+this.state.id+'&verifyCode='+value,
              headers: {
                'token': $.cookie('token')
              },
               xhrFields: {withCredentials: true},
              contentType: 'application/json;charset=UTF-8',
              success: function(res) {
                if(res.code === 0) {
                  this.setState({
                  before: res.data
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
    this.hide = () => {
      this.setState({
        visible: false,
        showMyStatus: false
      });
    };   
  }
  componentWillMount () {

  }
  render() {
    const { visible, confirmLoading} = this.state;
    return (
        < Card;
      title = "我的书包柜";
      bordered = {true};
      className = "areacard";
      style = {;
      {
          '0 0 350px'
      }
  }>
  <
      p > < Button;
      type = "primary";
      onClick = {this.myStatus} > 查看当前预约状态 < /Button></;
      p >
      < p > < span > 位置;：<
      /span>{this.props.location}</;
      p >
      < p > < span > 编号;：<
      /span>{this.props.number} </;
      p >
      < Modal;
      title = "当前状态";
      visible = {visible};
      onOk = {this.handleOk};
      confirmLoading = {confirmLoading};
      onCancel = {this.hide};
          footer= {false}
        >
              < div;
      style = {;
      {
          this.state.showMyStatus ? 'none' : 'inline'
      }
  }>
  <
      p;
      style = {;
      {
          "center"
      }
  }><
      img;
      alt = '获取验证码失败';
      onClick = {this.myStatus};
      src = {'data:image/jpeg;base64,'+this.state.code};
      /></;
      p >
      < Search;
      style = {;
      {
          '100%'
      }
  }
      placeholder = '请输入验证码';
      enterButton = '确定';
      onSearch = {this.sendCode};
            />
        </div>
            < div;
      style = {;
      {
          this.state.showMyStatus ? 'inline' : 'none'
      }
  }>
  <
      p > < span > 排在前面的人还有;：<
      /span>{this.state.before}</;
      p >

        </div>
        </Modal>
      < /Card>;

  )
  }
}

export default Mine;