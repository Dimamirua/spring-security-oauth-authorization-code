import React from 'react';
import Header from '../../../src/containers/Header/Header';

describe('(Container) Header', () => {
    let wrapper;

    beforeEach(() => {

    });

    it('Should have header',()=>{
        wrapper = mount(<Header/>);
        expect(wrapper.find('Header').to.have.length(1));
    });
});