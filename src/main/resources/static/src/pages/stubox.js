import React, { Component } from 'react';
import { Layout } from 'antd';
import '../css/app.css';
import logo from '../images/logo.png';
import Stuheaderbar from '../components/stuheaderbar';
import Siderbar from '../components/siderbar';
import Box from '../components/box';
import Footertext from '../components/footertext';
const { Content } = Layout;

class Stubox extends Component {
  constructor(props,context) {
    super(props,context);
    this.state = {
      id: this.props.match.params.id
    };   
  }
  componentDidMount() {
    //this.setState({location: 1, number: 123});
       // $.get(req+'info/'+this.state.id, null, function (ret) {

       //  this.setState({data:ret.data});
       //  var data = [];
       //      data[0]=this.state.friendsex[1].value;
       //      data[1]=this.state.friendsex[0].value;
        
       // }.bind(this));
   console.log(this.state.id);
  }
  render() {
    return (
      <Layout className="stupart">
        < Stuheaderbar className="headerbar" current = { this.state.current } id={this.state.id}/>
        < Siderbar className="siderbar" current="order" id={this.state.id}/>
        <Content style={{ padding: '0 50px' }}>
      		<br />
        	<Box />
        </Content>
        < Footertext />
      </Layout>
    );
  }
}

export default Stubox;