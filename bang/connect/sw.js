self.addEventListener('install', function(event) {
  self.skipWaiting();
  console.log('Installed', event);
});
self.addEventListener('activate', function(event) {
  console.log('Activated', event);
});
// self.registration.showNotification('hi', {
//     body: 'hi',
//     tag: 'simple-push-demo-notification',
//     timeout: 3000
// });
// self.addEventListener('push', function() {
//     if (!(self.Notification && self.Notification.permission === 'granted')) {
//         console.log('ssibal');
//         return;
//     }
//     var title = "Life changes";
//     var message = who + " loses life " + much + " by " + by;
//     var notification = new self.registration.Notification(title, {
//         body: message,
//         tag: 'simple-push-demo-notification',
//         timeout: 3000
//     });
// });
