import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
export const state = () => (
   {
      isConnected: false,
      socket: null,
      client: null,
      transactions: [],
      transactionsTime : [],
      transactionMap : [],
      removeFraudTransactions : true
   }
)

export const mutations = {

   setRemoveFraudTransactions(state) {
      state.removeFraudTransactions = !state.removeFraudTransactions
   },

   setConnected(state, flag) {
      state.isConnected = flag
   },

   addTransactionWithTIme(state, event) {
      state.transactionsTime.push(event)
   },

   removeOldTransactions(state) {
      state.transactionsTime = state.transactionsTime.filter(item => item.time > new Date(Date.now() - 1000 * 25))
   },

   addTransaction(state, event) {
      state.transactions.push(event)
   },

   setConnection(state, {socket, client}) {
      state.socket = socket
      state.client = client
   },

   addTransactionToMap(state, transaction) {
      state.transactionMap.push(transaction)
   },

   removeTransactionFromMap(state, transaction) {
      state.transactionMap.splice(state.transactionMap.indexOf(transaction), 1);
   }

}


export const actions = {

   handleTransaction(context, event) {

      let transaction = JSON.parse(event.body)

      context.commit("addTransactionToMap", transaction)

      setTimeout(() => {
         context.commit("removeTransactionFromMap", transaction)
      }, 5000)

      context.commit("addTransaction", transaction)

      context.commit("addTransactionWithTIme", {...transaction, time : new Date()})

      context.commit("removeOldTransactions")
   },

   connect(context) {
      if (!context.state.isConnected) {
         let socket = new SockJS("http://localhost:8083/ws");
         let client = Stomp.over(socket);
         client.debug = () => {};
         socket.debug = () => {};
         // state.socket = new SockJS("http://localhost:8081/ws");
         // state.client = Stomp.over(state.socket);
         client.connect(
            {},
            frame => {
               context.commit("setConnected", true)
               // commit("setConnection" ,{client, socket})
               console.log(frame)
               client.subscribe(
                  "/topic/events.transactions", tick => {
                     context.dispatch("handleTransaction", tick)
                  }
               )
            },
            error => {
               console.log(error);
               context.commit("setConnected", false)
            }
         )
      }
   }

}


export  const getters = {
   transactionsForMap: (state) => {
      return state.transactionMap
   },

   transactions: (state) => {
      return state.transactions
   }
}
