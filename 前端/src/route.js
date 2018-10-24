import React, { Component } from 'react';
import './app.css';
import App from './app';
import { BrowserRouter, Route, Switch } from 'react-router-dom';

class MainRouter extends Component {
  render() {
    return (
      <BrowserRouter className="search">
      	<Switch>
  	   	  <Route exact path="/" component={App} ></Route>
  	   	  <Route exact path="/student" component={App} ></Route>
  	    </Switch>
  	  </BrowserRouter>
    );
  }
}

export default MainRouter;
