import React, { Component } from 'react';
import '../css/app.css';
import { Card, Row, Col, BackTop, Icon, Avatar, Button, Table, Layout, Popconfirm } from 'antd';
const { Meta } = Card;
const columns = [{
  title: '区域',
  dataIndex: 'location',
}, {
  title: '编号',
  dataIndex: 'number',
}, {
  title: '状态',
  dataIndex: 'status',
},{
  title: '使用者学号',
  dataIndex: 'studentId'
},{
  title: '操作',
  key: 'action',
  render: (text, record) => (
    <Popconfirm title="确定要删除吗?" onConfirm={() => this.handleDelete(record.studentId)} okText="确定" cancelText="取消">
      <a href="javascript:;">删除</a>
    </Popconfirm>
  )
}];

const data = [];
for (let i = 0; i < 46; i++) {
  data.push({
    key: i,
    name: `Edward King ${i}`,
    age: 32,
    address: `London, Park Lane no. ${i}`,
  });
}

class Adbox extends Component {
  state = {
    selectedRowKeys: [], // Check here to configure the default column
    loading: false,
  };

  start = () => {
    this.setState({ loading: true });
    // ajax request after empty completing
    setTimeout(() => {
      this.setState({
        selectedRowKeys: [],
        loading: false,
      });
    }, 1000);
  }

  onSelectChange = (selectedRowKeys) => {
    console.log('selectedRowKeys changed: ', selectedRowKeys);
    this.setState({ selectedRowKeys });
  }

  render() {
    const { loading, selectedRowKeys } = this.state;
    const rowSelection = {
      selectedRowKeys,
      onChange: this.onSelectChange,
    };
    const hasSelected = selectedRowKeys.length > 0;
    return (
      <Layout style={{ padding: '24px 30px 24px' }}>
        <Card style={{ marginBottom: 16 }}>
          <Button
            type="primary"
            onClick={this.start}
            disabled={!hasSelected}
            loading={loading}
          >
            Reload
          </Button>
          <span style={{ marginLeft: 8 }}>
            {hasSelected ? `Selected ${selectedRowKeys.length} items` : ''}
          </span>
        <Table rowSelection={rowSelection} columns={columns} dataSource={data} />
        </Card>
      </Layout>
    );
  }
}

export default Adbox;