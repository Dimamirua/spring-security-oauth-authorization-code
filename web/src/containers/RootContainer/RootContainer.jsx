import React from 'react';

import './RootContainer.scss';
import Header from 'src/containers/Header/Header';

export default class RootContainer extends React.Component {

    render() {
        return <div className="application">
            <Header/>
            <h1>ROOT COMPONENT</h1>
        </div>;

    }
}
