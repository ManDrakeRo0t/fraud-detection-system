<template>
   <div>
      <DefaultHeader>

      </DefaultHeader>

      <div class="container" v-if="isLoaded" >

         <div class="mt-4 md-4">
            <h4>Информация о пользователе :</h4>
         </div>

         <div style="border-radius: 20px; background-color: #f0f0f0; display: flex; flex-direction: row; justify-content: space-between">
            <div class="mt-5">
               <div>
                  <span>Номер : <strong>{{this.customer.ccNum}}</strong></span>
               </div>
               <div>
                  <span>Имя : <strong>{{this.customer.firstName}} {{this.customer.lastName}}</strong></span>
               </div>
               <div>
                  <span>Адрес : <strong>[ {{this.customer.state}} {{this.customer.zip}} ] {{this.customer.city}} {{this.customer.street}}</strong></span>
               </div>
               <div>
                  <span>Место работы : <strong>{{this.customer.job}}</strong></span>
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
            <h4>Транзакции : </h4>
            <TransactionList :transactions="transactions" ></TransactionList>
         </div>



      </div>
   </div>
</template>

<script>
import TransactionPie from "~/components/charts/main/TransactionPie.vue";
import TransactionCategoriesPie from "~/components/charts/main/TransactionCategoriesPie.vue";

export default {
   name: "_id.vue",
   components: {TransactionCategoriesPie, TransactionPie},
   data () {
      return {
         ccNum : null,
         isLoaded : false,
         customer : {
            ccNum : "",
         },
         transactions : []
      }
   },
   async beforeMount() {
      this.ccNum = this.$route.params.id
      this.$axios.setBaseURL("http://localhost:8080")
      this.$axios.get("/api/customer/" + this.ccNum).then((res) => {
         this.customer = res.data
      })
      this.$axios.setBaseURL("http://localhost:8083")
      this.$axios.get("/api/transaction/customer/" + this.ccNum).then((res) => {
         this.transactions = res.data
         this.isLoaded = true
      })
   }
}
</script>


<style scoped>

</style>
