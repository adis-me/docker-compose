export function getOfferStats() {
  return fetch(`/api/stats/offers-customers`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${localStorage.getItem('api-token')}`,
    },
  }).then(response => response.json())
    .then(result => {
      console.debug('Stats, {}', result)
      return result
    })
    .catch(reason => {
      console.error(reason)
      throw new Error(reason)
    });
}
