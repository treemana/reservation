import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { login } from '../model/actions';
import { Form, Icon, Input, Button } from 'antd';
const FormItem = Form.Item;

class NormalLoginForm extends Component {
  static propTypes = {
    isLoading: PropTypes.bool,
    loginUserName: PropTypes.string,
    loginError: PropTypes.string
  };
  constructor(props) {
    super(props);
    this.handleSubmit = (e) => {
    e.preventDefault();
    this.props.form.validateFields((err, values) => {
      if (!err) {
        if(values.userName === 'student')
            this.context.router.history.push({pathname:'/student/'});
        else if(values.userName === 'teacher')
            this.context.router.history.push({pathname:'/teacher/'});
      }
    });
  }
  }
  componentDidMount() {
    this.props.login();
  }
  render() {
    const { getFieldDecorator } = this.props.form;
    return (
      <Form onSubmit={this.handleSubmit} className="login-form">
        <FormItem>
          {getFieldDecorator('userName', {
            rules: [{ required: true, message: '请输入您的学号' }],
          })(
            <Input prefix={<Icon type="tag" style={{ color: 'rgba(0,0,0,.25)' }} />} placeholder="学号" />
          )}
        </FormItem>
        <FormItem>
          {getFieldDecorator('text', {
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