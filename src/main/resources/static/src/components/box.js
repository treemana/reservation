import React, { Component } from 'react';
import '../css/app.css';
import { Card, Row, Col, BackTop, Icon, Avatar, Button, notification, Modal, Input } from 'antd';
import $ from 'jquery';
import { config } from 'jquery.cookie';
import req from '../url';
import b2 from '../images/2b.png';
import b3 from '../images/3b.png';
import n2 from '../images/2n.png';
import n3 from '../images/3n.png';
const Search = Input.Search;
const { Meta } = Card;

class Box extends Component {
  constructor (props) {
    super(props);
    this.state = {
      message: "活动状态加载中",
      endTime: ' ',
      startTime: ' ',
      nowTime: ' ', 
      areaStatus: [1, 1, 1, 1],
      allStatus:1,
      areaNum: [],
      id: this.props.id,
      showMyStatus: false,
      before: 0,
    }

    this.timechanger = (mss) => {
        var days = parseInt(mss / (1000 * 60 * 60 * 24));
        var hours = parseInt((mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        var minutes = parseInt((mss % (1000 * 60 * 60)) / (1000 * 60));
        var seconds = (mss % (1000 * 60)) / 1000;
        return (days + " 天 " + hours + " 小时 " + minutes + " 分钟 " + seconds + " 秒 ");
    }
    this.GetTimeByTimeStr = (dateString) => {
      if (dateString === null) {
        dateString = '2000-11-11 10:10:10';
      }
      var timeArr=dateString.split(" ");
      var d=timeArr[0].split("-");
      var t=timeArr[1].split(":");
      return new Date(d[0],(d[1]-1),d[2],t[0],t[1],t[2]);    
    }
    this.getAreaBox = () => {//获取书包柜剩余数量
      $.ajax({
        method: 'GET',
        url: req+"num",
        headers: {
          'token': $.cookie('token')
        },
        success: function(res) {
          this.setState({
            areaTn: res.data
          });
          var data = [];
          for(let i = 0; i<this.state.areaTn.length;i++)
          {
              data.push(this.state.areaTn[i].num);
          }
          this.setState({
            areaNum: data
          });
        }.bind(this)
      });
    }
    this.getStatus = () => {//获取按钮状态
      $.ajax({
        method: "GET",
        url: req+"areastatus/"+this.state.id,
        contentType: 'application/json;charset=UTF-8',
        headers: {
          'token': $.cookie('token')
        },
        success: function(res) {
          if(res.code === 0) {
            let closeData = [1,1,1,1];
            if(closeData.toString() == res.data.toString()) {
              this.setState({
                allStatus: 1
              });
            }
            else {
              this.setState({
                allStatus: 0
              });
            }
            this.setState({
              areaStatus: res.data
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
    }
    this.orderBox = (key) => {//预约柜子
      
      var data = {
        location: key,
        studentId: this.state.id
      };
      data = JSON.stringify(data);
      $.ajax({
        method: "POST",
        url: req+"box-order",
        contentType: 'application/json;charset=UTF-8',
        headers: {
          'token': $.cookie('token')
        },
        data: data, 
        success: function(res) {
          if( res.code == 0 && res.data == true) {
            notification.open({
              message: '提示',
              description: '已进入预约队列，请点击“查看当前状态”查看！'
            });
            this.getStatus();
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
      $.ajax({
        method: "GET",
        url: req+'?status/studentId='+this.state.id+'&vrifyCode='+value,
        contentType: 'application/json;charset=UTF-8',
        headers: {
          'token': $.cookie('token')
        },
        success: function(res) {
          if(res.code === 0) {
            this.setState({
            showMyStatus: true
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
    }
    this.hide = () => {
      this.setState({
        visible: false,
        showMyStatus: false
      });
    };
  }

  componentDidMount() {
    $.ajax({//开放时间
      method: 'GET',
      url: req+"open-time",
      headers: {
        'token': $.cookie('token')
      },
      success: function(res) {
        if(res.code === 0) {
          this.setState({
            endTime: res.data.endTime,
            startTime: res.data.startTime
          });
        }
        else {
          notification.open({
          message: '提示',
          description: res.message
          });
        }
        $.ajax({//当前时间
          method: 'GET',
          url: req+"time",
          headers: {
            'token': $.cookie('token')
          },
          success: function(res) {
            if(res.code === 0) {
              this.setState({
                nowTime: res.data.nowTime
              });
            var startTime = this.state.startTime;
            startTime = this.GetTimeByTimeStr(startTime);
            var timestamp = startTime.getTime();

            var nowTime = this.state.nowTime;
            nowTime = this.GetTimeByTimeStr(nowTime);
            var timesnow = new Date(nowTime).getTime();

            var endTime = this.state.endTime;
            endTime = this.GetTimeByTimeStr(endTime);
            var timesend = new Date(endTime).getTime();
          
            if(timesnow < timestamp) {
              this.setState({
                mss: timestamp-timesnow,
              });
              var mss = this.state.mss;
              var timer = window.setInterval(
                () => {
                  this.setState({
                    message: '预约活动还未开始，距离开始还有'+this.timechanger(mss-1000)
                  });
                  this.getStatus();
                  mss -= 1000;
                  if(mss <= 0 ){
                    clearInterval(timer);
                  }
                },1000);
              
            }
            else if((timesnow > timestamp) && (timesnow < timesend)) {
              this.setState({
                mss: timesend - timesnow
              });
              var mss = this.state.mss;
              var timer = window.setInterval(//倒计时
                () => {
                    this.setState({
                      message: '预约活动已经开始，距离结束还有'+this.timechanger(mss-1000)
                    });

                  this.getStatus();
                  this.getAreaBox();
                  mss -= 1000;
                  if(mss <= 0 ){
                    clearInterval(timer);
                    this.setState({
                    message: '活动已经结束！'
                  });
                  }
                },1000);
            }
            else if((timesnow > timestamp) && (timesnow > timesend)) {
                  this.setState({
                    message: '活动已经结束！'
                  });
                  this.getStatus();
                  mss -= 1000;
            }
            }
            else {
              notification.open({
              message: '提示',
              description: res.message
              });
            }
          }.bind(this)
        });
      }.bind(this)
    });
    $.ajax({
      method: 'GET',
      url: req+"num",
      headers: {
        'token': $.cookie('token')
      },
      success: function(res) {
        this.setState({
          areaTn: res.data
        });
        var data = [];
        for(let i = 0; i<this.state.areaTn.length;i++)
        {
            data.push(this.state.areaTn[i].num);
        }
        this.setState({
          areaNum: data
        });
      }.bind(this)
    });
  }
  render() {
    const { visible, confirmLoading } = this.state;
    return (
      <Card title={this.state.message} bordered={true} className="areacard">
      <Row style={{textAlign: "right"}} >
        <p><Button type="primary" onClick={this.myStatus}>查看当前状态</Button></p>
      </Row>
      <Row gutter={30}>
          <Col span={12}>
            <Card
              cover={<img alt="example" src={b2} />}
            >
            <p><b>本区域还余{this.state.areaNum[0]}个可预约</b></p>
              <Button type="primary" block disabled={Boolean(this.state.areaStatus[0])} onClick={() => this.orderBox(1)}>预约</Button>
            </Card>
          </Col>
          <Col span={12}>
            <Card
              cover={<img alt="example" src={n2} />}
            >
            <p><b>本区域还余{this.state.areaNum[1]}个可预约</b></p>
              <Button type="primary" block disabled={Boolean(this.state.areaStatus[1])} onClick={() => this.orderBox(2)}>预约</Button>
            </Card>
          </Col>
      </Row>
      <Row gutter={30}>
          <Col span={12}>
            <Card
              cover={<img alt="example" src={b3} />}
            >
            <p><b>本区域还余{this.state.areaNum[2]}个可预约</b></p>
              <Button type="primary" block disabled={Boolean(this.state.areaStatus[2])} onClick={() => this.orderBox(3)}>预约</Button>
            </Card>
          </Col>
          <Col span={12}>
            <Card
              cover={<img alt="example" src={n3} />}
            >
            <p><b>本区域还余{this.state.areaNum[3]}个可预约</b></p>
              <Button type="primary" block disabled={Boolean(this.state.areaStatus[3])} onClick={() => this.orderBox(4)}>预约</Button>
            </Card>
          </Col>
      </Row>
      <Row>
          <Col span={24}>
            <p><Button type="primary" block disabled={Boolean(this.state.allStatus)} onClick={() => this.orderBox(null)}>随机预约</Button></p>
          </Col>
      </Row>
      <Modal 
          title="当前状态"
          visible={visible}
          onOk={this.handleOk}
          confirmLoading={confirmLoading}
          onCancel={this.hide}
          footer= {false}
        >
        <div style= {{display: this.state.showMyStatus?'none':'inline'}}>
        <p><img alt='获取验证码失败' onClick={this.myStatus} src={'data:image/jpeg;base64,'+this.state.code} /></p>
          <Search
              style={{width: '100%'}}
              placeholder='请输入验证码'
              enterButton='确定'
              onSearch={this.sendCode}
            />
        </div>
        <div style= {{display: this.state.showMyStatus?'inline':'none'}}>
          <p><span>排在前面的人还有：</span>{this.state.before}</p>

        </div>
        </Modal>
      </Card>
      
    );
  }
}

export default Box;