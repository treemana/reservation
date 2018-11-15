import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import { HashRouter, Route, Switch } from 'react-router-dom';
import './css/app.css';
import App from './pages/app';
import Stubox from './pages/stubox';
import Stumine from './pages/stumine';
import Boxset from './pages/boxset';
import Timeset from './pages/timeset';
import Areaset from './pages/areaset';
import Idset from './pages/idset';
import Help from './pages/help';
import Adhelp from './pages/adhelp';
import Stuhelp from './pages/stuhelp';
import Dev from './pages/dev';
import Addev from './pages/addev';
import Studev from './pages/studev';
import $ from 'jquery';
import { config } from 'jquery.cookie';

ReactDOM.render(
	  <HashRouter className="search">
      	<Switch>
  	   	  <Route exact path="/" component={App} ></Route>
  	   	  <Route path="/stubox/:id" component={$.cookie('token')?Stubox:App} ></Route>
          <Route path="/stumine/:id" component={$.cookie('token')?Stumine:App} ></Route>
          <Route path="/boxset/:id" component={$.cookie('token')?Boxset:App} ></Route>
          <Route path="/areaset/:id" component={$.cookie('token')?Areaset:App} ></Route>
          <Route path="/timeset/:id" component={$.cookie('token')?Timeset:App} ></Route>
          <Route path="/idset/:id" component={$.cookie('token')?Idset:App} ></Route>
          <Route path="/help" component={Help} ></Route>
          <Route path="/stuhelp/:id" component={$.cookie('token')?Stuhelp:Help} ></Route>
          <Route path="/adhelp/:id" component={$.cookie('token')?Adhelp:Help} ></Route>
          <Route path="/dev" component={Dev} ></Route>
          <Route path="/studev/:id" component={$.cookie('token')?Studev:Dev} ></Route>
          <Route path="/addev/:id" component={$.cookie('token')?Addev:Dev} ></Route>
  	    </Switch>
  	  </HashRouter>, 
	document.getElementById('root')
)