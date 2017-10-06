import React from 'react';

import {connect} from 'react-redux';

@connect(
    state => ({

    }), dispatch => ({
        dispatchAction: dispatch
    }))
export default class Header extends React.Component {

    render() {
        return <div>Header</div>;
    }
}