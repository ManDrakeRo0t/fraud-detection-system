<template>
   <div v-if="isLoaded">

      <DefaultHeader></DefaultHeader>

      <div class="container" >

         <div style="background-color: #a00909;  color: azure ; text-align: center" v-if="this.tran.isFraud">
            Транзакция мошенническая!
         </div>

         <div style="background-color: #e3a323;  color: #0d0e0d; text-align: center" v-else-if="this.tran.isFraud === null">
            Транзакция не была проверена!
         </div>

         <div style="background-color: #47dc15;  color: #0d0e0d; text-align: center" v-else>
            Транзакция легитимная!
         </div>

         <div class="mt-3 md-3">
            <h3>Детали : </h3>
         </div>
         <div style="border-radius: 20px; background-color: #f0f0f0; ">
            <div>
               <span>Клиент : <strong>
                  <router-link :to="/customers/ + this.tran.ccNum" target="_blank">
                     {{this.tran.ccNum}}
            </router-link>
               </strong></span>
            </div>
            <div>
               <span>Продавец : <strong>
               <router-link :to="/merchants/ + this.tran.merchantName" target="_blank">
                     {{this.tran.merchantName}}
            </router-link>
               </strong></span>
            </div>
            <div>
               <span>Сумма : <strong>{{this.tran.amount}}</strong></span>
            </div>
            <div>
               <span>Категория : <strong>{{this.tran.category}}</strong></span>
            </div>
            <div>
               <span>Дата : <strong>{{this.tran.dateTime}}</strong></span>
            </div>
            <div>
               <span>Номер транзакции : <strong>{{this.tran.transNum}}</strong></span>
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
      this.$axios.setBaseURL("http://localhost:8083")
      this.$axios.get("/api/transaction/" + this.id).then((res) => {
         this.tran = res.data
         this.isLoaded = true;
      })
   }
}
</script>


<style scoped>

</style>
