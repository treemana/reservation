import {Component} from 'react';
import $ from 'jquery';
import {config} from 'jquery.cookie';
import {Layout} from 'antd';
import '../css/app.css';
import Adheaderbar from '../components/adheaderbar';
import Adsiderbar from '../components/adsiderbar';
import Adbox from '../components/adbox';
import Footertext from '../components/footertext';

const { Content } = Layout;


class Boxset extends Component {
  constructor(props,context) {
    super(props,context);
    this.state = {
      id: this.props.match.params.id
    };
    if(!$.cookie('token')) {
      window.location.href='/';
    }
       
  }
  render() {
    return (
        < Layout;
      className = "stupart" >
          < Adheaderbar;
      className = "headerbar";
      current = '-1';
      id = {this.state.id};
      />
        <Layout>
      < Adsiderbar;
      className = "siderbar";
      current = "1";
      id = {this.state.id};
      />
      < Content;
      style = {;
      {
          '0 50px'
      }
  }>
      		<Adbox />
        </Content>
        </Layout>
        < Footertext />
      < /Layout>;
  )
  }
}

export default Boxset;