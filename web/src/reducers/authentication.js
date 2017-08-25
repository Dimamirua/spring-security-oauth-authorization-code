import { fromJS } from 'immutable';

const initialState = fromJS({});


export default function reducer(state = initialState, action = {}) {
    switch (action.type) {
        default:
            return state;
    }

}