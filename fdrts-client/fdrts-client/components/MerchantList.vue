<template>
   <div  class="container" style="overflow-y: scroll; height:90vh;" @scroll="handleScroll">
      <b-table striped hover :items="list" :fields="fields" @row-clicked="handleRowClick"></b-table>
   </div>
</template>

<script>
export default {
   name: "MerchantList",
   data () {
      return {
         fields : [{key: "id", label : "Идентификатор"}, {key: "name", label : "Имя"}],
         list : [],
         lastToken: null
      }
   },
   mounted() {
      this.loadMerchants()
   },
   methods : {
      handleRowClick(item , index , event ) {
         //console.log(item)
         let route = this.$router.resolve({path: '/merchants/' + item.name});
         // let route = this.$router.resolve('/link/to/page'); // This also works.
         window.open(route.href, '_blank');
      },
      handleScroll(el) {
         if((el.srcElement.offsetHeight + el.srcElement.scrollTop) >= el.srcElement.scrollHeight) {
            this.loadMerchants()
         }
      },
      loadMerchants() {
         this.$axios.setBaseURL("http://localhost:8080")
         this.$axios.get("/api/merchant/list", {
            params : {
               lastToken : this.lastToken
            }
         }).then((res) => {

            for (let item of res.data.data) {
               this.list.push(item)
            }
            this.lastToken = res.data.lastToken
         })
      }
   }
}
</script>

<style scoped>

</style>
