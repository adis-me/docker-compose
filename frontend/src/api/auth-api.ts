export function authenticate(username: string, password: string) {
  localStorage.removeItem('api-token')

  return fetch('/api/sign-in', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({username: username, password: password}),
  }).then(response => response.json())
    .then(result => {
      console.debug('persisting token from response, %s', result.token)
      localStorage.setItem('api-token', result.token)
    })
    .catch(reason => {
      console.error(reason)
      throw new Error(reason)
    });
}
