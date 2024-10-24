export class Api {
    
    constructor() {
      this.baseUrl = "http://localhost:8787/api/v1";
    }
  
    async request(method, endpoint, headers = {}, body = null) {
      const url = this.baseUrl + endpoint;
  
      const options = {
        method: method.toUpperCase(),
        headers: headers,
      };
  
      if (body) {
          options.body = JSON.stringify(body);
      }
  
      try {
          return await fetch(url, options);
      } catch (error) {
        console.error('Ошибка при выполнении запроса:', error);
        throw error;
      }
    }
  
    get(endpoint, headers = {}) {
      return this.request('GET', endpoint, headers);
    }
  
    post(endpoint, headers = {}, body = null) {
      if (!headers || Object.keys(headers).length === 0) {
        headers = {
          'Content-Type': 'application/json',
        };
      }
      return this.request('POST', endpoint, headers, body);
    }
  
    put(endpoint, headers = {}, body = null) {
      return this.request('PUT', endpoint, headers, body);
    }
  
    patch(endpoint, headers = {}, body = null) {
      return this.request('PATCH', endpoint, headers, body);
    }
  
    delete(endpoint, headers = {}, body = null) {
      return this.request('DELETE', endpoint, headers, body);
    }
  }