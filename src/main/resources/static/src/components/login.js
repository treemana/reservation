import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { Form, Icon, Input, Button, notification } from 'antd';
import $ from 'jquery';
const FormItem = Form.Item;

class NormalLoginForm extends Component {
  static propTypes = {
    token: PropTypes.string,
    type: PropTypes.string
  };
  constructor(props) {
    super(props);
    this.handleSubmit = (e) => {
      e.preventDefault();
      this.props.form.validateFields((err, values) => {
        if (!err) {
          this.context.router.history.push({pathname:'/'+values.number});
        //   const data = JSON.stringify(values);
        //   console.log(data);
        //   $.post("http://lib.nefu.edu.cn/api/login", 
        //     data, function (res) {
        //       if( res.code === 0 && res.type === 'S' ) {
        //          $.cookie('token', res.data.token)
        //                   this.context.router.history.push({pathname:'/student'});
        //                   //存状态
        //               } 
        //       else if( res.code === 0 && res.type === 'T' ){ 
        //                 this.context.router.history.push({pathname:'/teacher'});
        //       }
        //       else
        //        {
        //           notification.open({
        //               message: '提示',
        //               description: '学号与姓名不匹配！',
        //             });
        //         }
        // });
        }
      });
    }
  }
  
  render() {
    const { getFieldDecorator } = this.props.form;
    return (
      <Form onSubmit={this.handleSubmit} className="login-form">
        <FormItem>
          {getFieldDecorator('number', {
            rules: [{ required: true, message: '请输入您的学号' }],
          })(
            <Input prefix={<Icon type="tag" style={{ color: 'rgba(0,0,0,.25)' }} />} placeholder="学号" />
          )}
        </FormItem>
        <FormItem>
          {getFieldDecorator('studentId', {
            rules: [{ required: true, message: '请输入您的姓名' }],
          })(
            <Input prefix={<Icon type="tags" style={{ color: 'rgba(0,0,0,.25)' }} />} type="text" placeholder="姓名" />
          )}
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
const Login = Form.create()(NormalLoginForm);
export default Login;