import router from '@/router';

export function profile() {
  return fetch('/api/profile', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${localStorage.getItem('api-token')}`,
    },
  }).then(response => {
    console.log(response)
    if (!response.ok) {
      return Promise.reject("Unauthorized, clearing session")
    } else {
      return Promise.resolve(response.json())
    }
  })
    .then(result => {
      console.debug('profile response, %s', result)
      return result
    })
    .catch(reason => {
      console.warn(reason)
      localStorage.removeItem('api-token');
      return router.push({name: 'home'});
    });
}
