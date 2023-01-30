const request = require('supertest');
const baseURL = 'http://0.0.0.0:5000';

describe( '/sumar/n1/n2', () => {
    it( 'Should return text with addition operation result.', async () => {
        const response = await request( baseURL ).get( '/sumar/1/2' );
        const text = response.text;
        expect( text ).toBe( 'Suma = 3' );
    } );
} ); 