import React from 'react';
import { Route } from 'react-router';

import RootContainer from './containers/RootContainer/RootContainer';

export default function getRoutes() {

    return (
        <Route path="/" component={RootContainer}/>
    );

}
