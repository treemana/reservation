import React, { Component } from 'react';
import '../css/app.css';
import { Card, Row, Col, BackTop, Icon, Avatar, Button, TimePicker, DatePicker, Layout } from 'antd';
import moment from 'moment';
import 'moment/locale/zh-cn';
import locale from 'antd/lib/date-picker/locale/zh_CN';
const { Meta } = Card;
const { RangePicker } = DatePicker;

moment.locale('zh-cn');
function onChange(value, dateString) {
  console.log('Selected Time: ', value);
  console.log('Formatted Selected Time: ', dateString);
}

function onOk(value) {
  console.log('onOk: ', value);
}

class Time extends React.Component {
  state = { open: false };

  handleOpenChange = (open) => {
    this.setState({ open });
  }

  handleClose = () => this.setState({ open: false })

  render() {
    return (
    <Layout style={{ padding: '24px 24px 24px' }}>
    <Card title="可预约时间设置" bordered={true} className="areacard" style={{ padding: '0 0 450px' }}>
      <div>
	    
	    <RangePicker locale={locale}
	      showTime={{ format: 'HH:mm:ss' }}
	      format="YYYY-MM-DD HH:mm:ss"
	      placeholder={['开始时间', '结束时间']}
	      onChange={onChange}
	      onOk={onOk}
	    />
	  </div>
    </Card>
    </Layout>    
    );
  }
}

export default Time;