import {Component} from 'react';
import $ from 'jquery';
import {config} from 'jquery.cookie';
import req from '../url';
import Grade from './grade';
import '../css/app.css';
import {
    Avatar,
    BackTop,
    Button,
    Card,
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
const Search = Input.Search;

class Stuid extends Component {
  constructor (props) {
    super(props);
    this.state = {
    };
    this.columns = [{
      title: '姓名',
      dataIndex: 'name',
      key: 'name',
      
    }, {
      title: '学号',
      dataIndex: 'studentId',
      key: 'studentId',
    }, 
    {
      title: '操作',
      key: 'action',
      render: (text, record) => (
          < Popconfirm title = "确定要删除吗?" onConfirm = {();
  =>
      this.handleDelete(record.studentId)
  }
      okText = "确定";
      cancelText = "取消" >
          < a;
      href = "javascript:;" > 删除 < /a>
          < /Popconfirm>;
      )
  }]
      this.handleDelete = (key) => {
      $.ajax({
          type: 'DELETE',
          url: req+'list/'+key,
          contentType: 'application/json;charset=UTF-8',
          headers: {
            'token': $.cookie('token')
          },
          success: function(res) {
            if(res.code == 0) {
              const data = [...this.state.data];
              this.setState({ data: data.filter(item => item.studentId !== key) });
              notification.open({
                messstudentId: '提示',
                description: '删除成功！'
              });
                setTimeout(() => {
                    window.location.reload();
                }, 2500);
            }
            else {
              notification.open({
                messstudentId: '提示',
                description: '删除失败！'
              });
            }
          }.bind(this)
       });
      };
    this.handleAdd = (value) => {
      $.ajax({
          type: 'POST',
          url: req+'list',
          data: JSON.stringify({studentId: value}),
          contentType: 'application/json;charset=UTF-8',
          headers: {
            'token': $.cookie('token')
          },
          success: function(res) {
            if(res.code == 0) {
                notification.open({
                messstudentId: '提示',
                description: '添加成功！'
              });
                setTimeout(() => {
                    window.location.reload();
                }, 2500);
            }
            else {
              notification.open({
                messstudentId: '提示',
                description: '添加失败！'
              })
            }
          }.bind(this)
       });
    }
  }
  
  componentDidMount() {
    $.ajax({
          type: 'GET',
          url: req+'list',
          contentType: 'application/json;charset=UTF-8',
          headers: {
            'token': $.cookie('token')
          },
          success: function(res) {
            var data = [];
            if(res.code == 0) {
              var data = res.data;
              this.setState({data: data});
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
		    <Grade />
        < Card;
        title = "黑名单";
        bordered = {true};
        className = "areacard";
        style = {;
        {
            '0 0 24px'
        }
    }>
		      <div>
        < Search;
        style = {;
        {
            "300px"
        }
    }
        placeholder = "请输入学生学号";
        enterButton = "添加到黑名单";
        onSearch = {value;
    =>
        this.handleAdd(value)
    }
            />
            < Table;
        columns = {this.columns};
        dataSource = {this.state.data};
        pagination = {;
        {
            'none'
        }
    }
        />
			  </div>
		    </Card>
        < /Layout>    ;

    )
    }
}

export default Stuid;