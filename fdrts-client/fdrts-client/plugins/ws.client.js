import SockJS from "sockjs-client";

let socket;
let isConnected = false;
let events = [];
export function initConnection() {

   let destTopic = "/topic/events.transactions"

   const stompConfig = {

      connectHeaders: {},

      brokerURL: "ws://localhost:8083/ws",
      reconnectDelay: 200,

      onConnect: function (frame) {
         // The return object has a method called `unsubscribe`
         const subscription = socket.subscribe( destTopic , function (message) {
            const payload = JSON.parse(message.body);
            console.log(payload)
            events.push(payload)
         });
      }
   };

      if (!isConnected) {
         socket = new SockJS("ws://localhost:8083/ws");
         socket.activate()
         isConnected = true;
      }

      return socket;
}


