'use strict';

let socket;

if (window.WebSocket) {
  socket = new WebSocket('ws://localhost:8080/javaee7/chat');
  socket.onmessage = (event) => {
    let chat = document.getElementById('chat');
    chat.innerHTML = chat.innerHTML + event.data + '<br/>';
  };
} else {
  alert('Your browser does not support Websockets.');
}

function send(message) {
  if (!window.WebSocket) {
    return false;
  }
  if (socket.readyState === WebSocket.OPEN) {
    socket.send(message);
  } else {
    alert('The socket is not open.');
  }
  return false;
}