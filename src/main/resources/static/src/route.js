import React, { Component } from 'react';
import './app.css';
import App from './app';
import Stupart from './stu';
import { BrowserRouter, Route, Switch } from 'react-router-dom';

class MainRouter extends Component {
  render() {
    return (
      <BrowserRouter className="search">
      	<Switch>
  	   	  <Route exact path="/" component={App} ></Route>
  	   	  <Route path="/student" component={Stupart} ></Route>
          <Route path="/teacher" component={App} ></Route>
  	    </Switch>
  	  </BrowserRouter>
    );
  }
}

export default MainRouter;
