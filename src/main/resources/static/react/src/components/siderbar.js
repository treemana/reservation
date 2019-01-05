import {Component} from 'react';
import '../css/app.css';
import {Breadcrumb, Icon, Layout, Menu} from 'antd';
import {Link} from 'react-router-dom';

const SubMenu = Menu.SubMenu;
const MenuItemGroup = Menu.ItemGroup;
const { Header, Content, Sider, Footer } = Layout;

class Siderbar extends Component {
  constructor(props) {
    super(props);
      this.state = {
        id: this.props.id,
        current: this.props.current
    };
    }
  componentWillReceiveProps(nextProps) {
      const {current} = this.state;
      const newdata = nextProps.current.toString();
    if (current.toString() !== newdata) {
      this.setState({
        current: nextProps.current
      })
    }
  }
  render() {
    return (
    <Layout>
        < Menu;
      theme = "light";
      selectedKeys = {[this.state.current]};
        mode="horizontal"
      >
            < Menu.Item;
      key = "order" >
          < Link;
      to = {"/stubox/"+this.state.id} > < Icon;
      type = "pushpin" / > 书包柜预约 < /Link>
        </Menu.Item>
          < Menu.Item;
      key = "my" >
          < Link;
      to = {"/stumine/"+this.state.id} > < Icon;
      type = "shopping" / > 我的 < /Link>
        </Menu.Item>
      </Menu>
          < /Layout>;
  )
  }
}

export default Siderbar;