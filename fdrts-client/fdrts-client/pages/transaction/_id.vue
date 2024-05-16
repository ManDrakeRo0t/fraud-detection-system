<template>
   <div v-if="isLoaded">

      <DefaultHeader></DefaultHeader>

      <div class="container" >

         <div style="background-color: #a00909;  color: azure ; text-align: center" v-if="this.tran.isFraud">
            This is fraud transaction!
         </div>

         <div style="background-color: #e3a323;  color: #0d0e0d; text-align: center" v-else-if="this.tran.isFraud === null">
            This is not a verified transaction!
         </div>

         <div style="background-color: #47dc15;  color: #0d0e0d; text-align: center" v-else>
            This is legit transaction!
         </div>

         <div class="mt-3 md-3">
            <h3>Transaction details : </h3>
         </div>
         <div style="border-radius: 20px; background-color: #f0f0f0; ">
            <div>
               <span>From customer : <strong>
                  <router-link :to="/customers/ + this.tran.ccNum" target="_blank">
                     {{this.tran.ccNum}}
            </router-link>
               </strong></span>
            </div>
            <div>
               <span>To merchant : <strong>
               <router-link :to="/merchants/ + this.tran.merchantName" target="_blank">
                     {{this.tran.merchantName}}
            </router-link>
               </strong></span>
            </div>
            <div>
               <span>Amount : <strong>{{this.tran.amount}}</strong></span>
            </div>
            <div>
               <span>Category : <strong>{{this.tran.category}}</strong></span>
            </div>
            <div>
               <span>Date : <strong>{{this.tran.dateTime}}</strong></span>
            </div>
            <div>
               <span>Transaction num : <strong>{{this.tran.transNum}}</strong></span>
            </div>
         </div>
      </div>
   </div>
</template>

<script>
export default {
   name: "_id.vue",
   data () {
      return {
         id : null,
         isLoaded : false,
         tran : {
            id : "",
            isFraud : false,
         }
      }
   },
   beforeMount() {
      this.id = this.$route.params.id
      this.$axios.setBaseURL("http://localhost:8081")
      this.$axios.get("/api/transaction/" + this.id).then((res) => {
         this.tran = res.data
         this.isLoaded = true;
      })
   }
}
</script>


<style scoped>

</style>
