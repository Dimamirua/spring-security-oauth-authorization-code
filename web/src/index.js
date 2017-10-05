import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { Router } from 'react-router';
import { syncHistoryWithStore } from 'react-router-redux';
import { createHashHistory } from 'history';

import createStore from 'src/createStore';
import getRoutes from 'src/routes';

const hashHistory = createHashHistory();
const store = createStore(hashHistory);
const history = syncHistoryWithStore(hashHistory, store);
const router = (
    <Router history={history}>
        {getRoutes()}
    </Router>);

ReactDOM.render(<Provider store={store}>{router}</Provider>, document.getElementById('app'));