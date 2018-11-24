import {Component} from 'react';
import {Layout} from 'antd';
import $ from 'jquery';
import '../css/app.css';
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
    if(!$.cookie('token')) {
      window.location.href='/';
    }  
  }
  componentDidMount() {
  }
  render() {
    return (
        < Layout;
      className = "stupart" >
          < Stuheaderbar;
      className = "headerbar";
      current = '-1';
      id = {this.state.id};
      />
      < Siderbar;
      className = "siderbar";
      current = "order";
      id = {this.state.id};
      />
      < Content;
      style = {;
      {
          '0 50px'
      }
  }>
      		<br />
      < Box;
      id = {this.state.id};
      />
        </Content>
        < Footertext />
      < /Layout>;
  )
  }
}

export default Stubox;