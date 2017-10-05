import 'babel-polyfill';
import { applyMiddleware, createStore as _createStore } from 'redux';
import { composeWithDevTools } from 'redux-devtools-extension';
import { routerMiddleware } from 'react-router-redux';
import createSagaMiddleware from 'redux-saga';

import reducer from './reducers/reducers';
import rootSaga from './saga/rootSaga';

export default function createStore(history) {
    const sagaMiddleware = createSagaMiddleware();
    const middlewares = [routerMiddleware(history), sagaMiddleware];
    const store = _createStore(reducer, composeWithDevTools(applyMiddleware(...middlewares)));
    sagaMiddleware.run(rootSaga);
    return store;
}