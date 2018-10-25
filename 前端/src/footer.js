import React, { Component } from 'react';
import { Layout } from 'antd';
const { Footer } = Layout;

class Footertext extends Component {
    render() {
    return (
        <Footer style={{ textAlign: 'center' }}>
        	Copyright © 2012-2017 东北林业大学 图书馆 网络系统部. All Rights Reserved.<br />
        	哈公网监备2301001951号
    	</Footer>
    );
  }
}

export default Footertext;