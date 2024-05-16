<template>
   <div>

      <DefaultHeader>

      </DefaultHeader>

      <div class="container" v-if="isLoaded" >

         <div class="mt-4 md-4">
            <h4>Merchant details :</h4>
         </div>

         <div style="border-radius: 20px; background-color: #f0f0f0;  display: flex; flex-direction: row; justify-content: space-between">
            <div class="mt-5">
               <div>
                  <span>Id : <strong>{{this.merchant.id}}</strong></span>
               </div>
               <div>
                  <span>Name : <strong>{{this.merchant.name}} </strong></span>
               </div>
            </div>

            <div>
               <TransactionPie :transactions="transactions"></TransactionPie>
            </div>

            <div>
               <TransactionCategoriesPie :transactions="transactions" ></TransactionCategoriesPie>
            </div>
         </div>

         <div class="mt-4" >
            <h4>Transactions : </h4>
            <TransactionList :transactions="transactions" ></TransactionList>
         </div>



      </div>

   </div>
</template>

<script>
import TransactionCategoriesPie from "~/components/charts/main/TransactionCategoriesPie.vue";
import TransactionPie from "~/components/charts/main/TransactionPie.vue";

export default {
   name: "_id.vue",
   components: {TransactionPie, TransactionCategoriesPie},
   data () {
      return {
         merchant : {},
         id : null,
         isLoaded : false,
         transactions : []
      }
   },
   async beforeMount() {
      this.id = this.$route.params.id
      this.$axios.setBaseURL("http://localhost:8080")
      this.$axios.get("/api/merchant/" + this.id).then((res) => {
         this.merchant = res.data
      })
      this.$axios.setBaseURL("http://localhost:8081")
      this.$axios.get("/api/transaction/merchant/" + this.id).then((res) => {
         this.transactions = res.data
         this.isLoaded = true
      })
   }
}
</script>

<style scoped>

</style>
