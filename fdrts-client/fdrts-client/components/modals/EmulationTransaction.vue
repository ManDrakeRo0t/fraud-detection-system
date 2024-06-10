
<template>
   <div class="modal-overlay-my">
      <div class="modal-my">
         <div class="container" style="width: 80%">
            <div>
               <div class="mb-2" style="display: flex; justify-content: space-between">
                  <label class="" for="delay">Задержка</label>
                  <b-form-spinbutton inline
                                     id="delay"
                                     v-model="delay"
                                     min="0"
                                     max="300"
                                     step="5"
                  ></b-form-spinbutton>
               </div>
               <div class="mb-2" style="display: flex; justify-content: space-between">
                  <label class="" for="count">Кол-во</label>
                  <b-form-spinbutton inline
                                     id="count"
                                     v-model="count"
                                     min="0"
                                     max="1000"
                                     step="20"
                  ></b-form-spinbutton>
               </div>
               <div style="display: flex; justify-content: space-between">
                  <label class="" for="fraud">Мошенничество</label>
                  <b-form-spinbutton inline
                                     id="fraud"
                                     v-model="fraud"
                                     min="0"
                                     max="100"
                                     step="10"
                  ></b-form-spinbutton>
               </div>
            </div>
            <b-button class="mt-3" @click="emulate" variant="primary" >Начать эмуляцию</b-button>
         </div>
      </div>
      <div class="close" @click="$emit('close-modal')">
         <span aria-hidden="true">&times;</span>
      </div>
   </div>
</template>

<script>
export default {
   name: "EmulationTransaction",
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
.modal-overlay-my {
   z-index: 1000;
   position: fixed;
   top: 0;
   bottom: 0;
   left: 0;
   right: 0;
   display: flex;
   justify-content: center;
   background-color: rgba(0, 0, 0, 0.48);
}

.modal-my {
   text-align: center;
   background-color: white;
   height: 400px;
   width: 400px;
   margin-top: 5%;
   padding: 60px 0;
   border-radius: 20px;
   display: flex;
   justify-content: center;
   align-items: center;
   flex-direction: column;
}

.close {
   margin: 5% 0 0 10px;
   cursor: pointer;
}
</style>
