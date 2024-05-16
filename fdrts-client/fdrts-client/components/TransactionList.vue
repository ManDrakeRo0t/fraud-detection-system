<template>
   <div style="overflow-y: scroll; height:50vh;">
      <b-table striped hover :items="transactions" :fields="fields" @row-clicked="handleRowClick"></b-table>
   </div>
</template>


<script>
export default {
   name: "TransactionList",
   props: ["transactions"],
   data () {
      return {
         fields : [{ key :"isFraud", sortable: true}, "ccNum", "merchantName", "category", "amount", "dateTime"]
      }
   },
   methods : {
      handleRowClick(item , index , event ) {
         //console.log(item)
         let route = this.$router.resolve({path: '/transaction/' + item.id});
         // let route = this.$router.resolve('/link/to/page'); // This also works.
         window.open(route.href, '_blank');
      },
   }
}
</script>

<style scoped>

</style>
