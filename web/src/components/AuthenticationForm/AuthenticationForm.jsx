import React from 'react';

import './AuthenticationForm.scss';

export default class AuthenticationForm extends React.Component {

    render() {
        return <div className="authenticationForm">
            <div>
                <input type="text"/>
            </div>
            <div>
                <input type="text"/>
            </div>
        </div>;
    }
}