import {Component} from 'react';
import $ from 'jquery';
import {config} from 'jquery.cookie';
import '../css/app.css';
import Stuheaderbar from '../components/stuheaderbar';
import Siderbar from '../components/siderbar';
import Footertext from '../components/footertext';
import Devline from '../components/devline';
import {Layout} from 'antd';

const { Content } = Layout;

class Studev extends Component {
  constructor(props,context) {
        super(props,context);
          this.state = {
            current: '3',
            id: this.props.match.params.id
        };
        if(!$.cookie('token')) {
          window.location.href='/';
        }  
    }
  render() {
    return (
      <Layout>
        < Stuheaderbar;
      className = "headerbar";
      current = {this.state.current};
      id = {this.state.id};
      />
      < Siderbar;
      className = "siderbar";
      current = "-1";
      id = {this.state.id};
      />
      < Content;
      style = {;
      {
          '0 50px'
      }
  }>
          <br />
          < Devline />
          </Content>
        <Footertext />
      < /Layout>;
  )
  }
}

export default Studev;