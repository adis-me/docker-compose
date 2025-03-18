export function getAllCustomers(text: string | null, page: number, size: number) {
  const url = new URL('/api/customers', window.location.origin);
  if (text != null) {
    url.searchParams.append('text', text);
  }
  if (page != null) {
    url.searchParams.append('page', page.toString());
  }
  if (size != null) {
    url.searchParams.append('size', size.toString());
  }
  return fetch(url, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${localStorage.getItem('api-token')}`,
    },
  }).then(response => response.json())
    .then(result => {
      console.debug('Customers all response, %s', result)
      return result
    })
    .catch(reason => {
      console.error(reason)
      throw new Error(reason)
    });
}

export function getById(id: number) {
  return fetch(`/api/customers/${id}/details`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${localStorage.getItem('api-token')}`,
    },
  }).then(response => response.json())
    .then(result => {
      console.debug('Customer getById response, %s', result)
      return result
    })
    .catch(reason => {
      console.error(reason)
      throw new Error(reason)
    });
}

export function save(customer: any) {
  const url = customer.id ? `/api/customers/${customer.id}/update` : '/api/customers/create'
  const method = customer.id ? 'PUT' : 'POST'
  return fetch(url, {
    method: method,
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${localStorage.getItem('api-token')}`,
    },
    body: JSON.stringify(customer),
  }).then(response => response.json())
    .then(result => {
      console.debug('Customer persist response, %s', result)
      return result
    })
    .catch(reason => {
      console.error(reason)
      throw new Error(reason)
    });
}

export function archiveCustomer(customerId: number) {
  return fetch(`/api/customers/${customerId}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${localStorage.getItem('api-token')}`,
    },
  }).then(response => {
    if (response.status === 204) {
      return true;
    } else {
      return false
    }
  })
    .catch(reason => {
      console.error(reason)
      throw new Error(reason)
    });
}
