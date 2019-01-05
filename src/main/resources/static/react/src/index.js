import ReactDOM from 'react-dom';
import {HashRouter, Route, Switch} from 'react-router-dom';
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

ReactDOM.render(
< HashRouter;
className = "search" >
      	<Switch>
    < Route;
exact;
path = "/";
component = {App} > < /Route>
    < Route;
path = "/stubox/:id";
component = {Stubox} > < /Route>
    < Route;
path = "/stumine/:id";
component = {Stumine} > < /Route>
    < Route;
path = "/boxset/:id";
component = {Boxset} > < /Route>
    < Route;
path = "/areaset/:id";
component = {Areaset} > < /Route>
    < Route;
path = "/timeset/:id";
component = {Timeset} > < /Route>
    < Route;
path = "/idset/:id";
component = {Idset} > < /Route>
    < Route;
path = "/help";
component = {Help} > < /Route>
    < Route;
path = "/stuhelp/:id";
component = {Stuhelp} > < /Route>
    < Route;
path = "/adhelp/:id";
component = {Adhelp} > < /Route>
    < Route;
path = "/dev";
component = {Dev} > < /Route>
    < Route;
path = "/studev/:id";
component = {Studev} > < /Route>
    < Route;
path = "/addev/:id";
component = {Addev} > < /Route>
  	    </Switch>
    < /HashRouter>, ;
document.getElementById('root');
)