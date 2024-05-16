<template>
      <div  class="container mt-2" style="flex-direction: column">
        <div style="display: flex; flex-direction: row">
           <div>
              <div class="mb-2">
                 <label class="mr-5" for="delay">Delay</label>
                 <b-form-spinbutton inline
                                    id="delay"
                                    v-model="delay"
                                    min="0"
                                    max="300"
                                    step="5"
                 ></b-form-spinbutton>
              </div>
              <div class="mb-2">
                 <label class="mr-5" for="count">Count</label>
                 <b-form-spinbutton inline
                                    id="count"
                                    v-model="count"
                                    min="0"
                                    max="1000"
                                    step="20"
                 ></b-form-spinbutton>
              </div>
              <div>
                 <label class="mr-3" for="fraud">Fraud level</label>
                 <b-form-spinbutton inline
                                    id="fraud"
                                    v-model="fraud"
                                    min="0"
                                    max="100"
                                    step="10"
                 ></b-form-spinbutton>
              </div>
           </div>
           <div class="ml-5" >
              <b-button @click="emulate" >Emulate</b-button>
           </div>
        </div>
      </div>
</template>

<script>
export default {
   name: "EmulationStarter",
   data () {
      return {
         delay : 50,
         count : 100,
         fraud : 50
      }
   },
   methods : {
      emulate () {
         this.$axios.setBaseURL("http://localhost:8080")
         this.$axios.post("/api/transaction/emulate", {
            fraudPercent : this.fraud,
            transactionsAmount : this.count,
            delay : this.delay
         })
      }
   }
}
</script>


<style scoped>

</style>
