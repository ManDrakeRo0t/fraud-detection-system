
<template>
   <div class="modal-overlay-my">
      <div v-if="isLoaded" class="modal-my">
         <div class="container" style="width: 80%">
            <div class="row mt-2">
               <input type="text" style="margin-bottom: 10px" id="parentName" v-model="ccNum" class="form-control"
                      placeholder="ccNum" required autofocus>
            </div>
            <div class="row mt-2">
               <input type="text" style="margin-bottom: 10px" id="parentName" v-model="merchantName" class="form-control"
                      placeholder="merchant Name" required autofocus>
            </div>
            <div class="row mt-2">
               <input type="number" style="margin-bottom: 10px" id="parentName" v-model="amount" class="form-control"
                      placeholder="Amount" required autofocus>
            </div>
            <div class="row mt-2">
               <b-form-select v-model="category" :options="categories"></b-form-select>
            </div>
            <div class="row mt-2">
               <b-time v-model="dateTime"
                  id="ex-disabled-readonly"
               ></b-time>
            </div>
            <b-form-checkbox
               id="checkbox-1"
               v-model="validate"
               name="checkbox-1"
               value="accepted"
               unchecked-value="not_accepted"
            >
               Validate
            </b-form-checkbox>
            <b-button class="mt-3" @click="create" variant="primary" >create</b-button>
         </div>
      </div>
      <div class="close" @click="$emit('close-modal')">
         <span aria-hidden="true">&times;</span>
      </div>
   </div>
</template>

<script>
export default {
   name: "TransactionCreation",
   data () {
      return {
         ccNum: "",
         merchantName : "",
         dateTime : "",
         category : "",
         amount : 0,
         validate : false,
         isLoaded : false,
         categories : []
      }
   },
   mounted() {
      this.loadCategories()
   },
   methods : {
      create() {
         let dateTime = ""
         if (this.dateTime !== "") {
            let date = new Date()
            dateTime += date.toISOString().substring(0, 10)
            dateTime += "T" + this.dateTime + "Z"
         }
         this.$axios.setBaseURL("http://localhost:8080")
         this.$axios.post("/api/transaction", {
            ccNum : this.ccNum,
            merchantName : this.merchantName,
            category : this.category,
            amount : this.amount,
            validate : this.validate === "accepted",
            dateTime : dateTime
         })
         this.$emit('close-modal')
      },
      loadCategories () {
         this.$axios.setBaseURL("http://localhost:8080")
         this.$axios.get("/api/transaction/categories", {
         }).then((res) => {
            this.categories = res.data
            this.isLoaded = true;
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
   height: 600px;
   width: 700px;
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
