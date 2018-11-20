import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { bindActionCreators } from 'redux';
import { Form, Icon, Input, Button, notification } from 'antd';
import $ from 'jquery';
import req from '../url';
import { config } from 'jquery.cookie';
const FormItem = Form.Item;


class NormalLoginForm extends Component {
  constructor (props, context) {
    super(props, context);
    this.handleSubmit =  (e) => {
      e.preventDefault();
      this.props.form.validateFields((err, values) => {
        if (!err) {
          const data = JSON.stringify(values);
          console.log(data);
          $.ajax({
            method: "POST",
            url: req+"login",
            contentType: 'application/json;charset=UTF-8',
            data: data,
            success: function(res) {
              if( res.code === 0 && res.data.type === 0 ) {
                 $.cookie('token', res.data.token);
                    this.context.router.history.push({pathname:'/stubox/'+values.studentId,state:{id: values.studentId}});
                    //存状态
              } 
              else if( res.code === 0 && res.data.type === 1 ) { 
                $.cookie('token', res.data.token);
                  this.context.router.history.push({pathname:'/boxset/'+values.studentId,state:{id: values.studentId}});
              }
              else if( res.code === 1 ) {
                  notification.open({
                    message: '提示',
                    description: res.message
                  });
              }
            }.bind(this)
          });
        }
      });
    };
  }

  render() {
    const { getFieldDecorator } = this.props.form;
    return (
      <Form onSubmit={this.handleSubmit} className="login-form">
        <FormItem>
          {getFieldDecorator('studentId', {
            rules: [{ required: true, message: '请输入您的学号' }],
          })(<Input prefix={<Icon type="tag" style={{ color: 'rgba(0,0,0,.25)' }} />} placeholder="学号" />)}
        </FormItem>
        <FormItem>
          {getFieldDecorator('studentName', {
            rules: [{ required: true, message: '请输入您的姓名' }],
          })(<Input prefix={<Icon type="tags" style={{ color: 'rgba(0,0,0,.25)' }} />} type="text" placeholder="姓名" />)}
        </FormItem>
        <FormItem>
          <Button type="primary" htmlType="submit" className="login-form-button">
            登录
          </Button>
        </FormItem>
      </Form>
    );
  }
}



NormalLoginForm.contextTypes = {router:()=> PropTypes.func.isRequired };
let Login = Form.create()(NormalLoginForm);

export default Login;