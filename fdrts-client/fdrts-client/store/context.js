export const state = () => (
   {
      customer: "",
      merchant: "",
   }
)

export const mutations = {

   setCustomer(state, customer) {
      state.customer = customer
   },

   setMerchant(state, merchant) {
      state.merchant = merchant
   },

}

export const actions = {

   saveCustomer(context, customer) {

      context.commit("setCustomer", customer)

   },

   saveMerchant(context, merchant) {

      context.commit("setMerchant", merchant)

   },
}
