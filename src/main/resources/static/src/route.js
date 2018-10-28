import React, { Component } from 'react';
import './css/app.css';
import App from './pages/app';
import Stubox from './pages/stubox';
import Stumine from './pages/stumine';
import Boxset from './pages/boxset';
import Timeset from './pages/timeset';
import Idset from './pages/idset';
import Help from './pages/help';
import Dev from './pages/dev';
import { BrowserRouter, Route, Switch } from 'react-router-dom';

class MainRouter extends Component {
  render() {
    return (
      <BrowserRouter className="search">
      	<Switch>
  	   	  <Route exact path="/" component={App} ></Route>
  	   	  <Route path="/stubox" component={Stubox} ></Route>
          <Route path="/stumine" component={Stumine} ></Route>
          <Route path="/boxset" component={Boxset} ></Route>
          <Route path="/timeset" component={Timeset} ></Route>
          <Route path="/idset" component={Idset} ></Route>
          <Route path="/help" component={Help} ></Route>
          <Route path="/dev" component={Dev} ></Route>
  	    </Switch>
  	  </BrowserRouter>
    );
  }
}

export default MainRouter;
