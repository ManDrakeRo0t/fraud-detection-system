<template>
   <apexchart type="pie" ref="pieCategory" width="350" :options="chartOptions" :series="series"></apexchart>
</template>


<script>
export default {
   name: "TransactionCategoriesPie",
   props: ["transactions"],
   data () {
      return {
         series: [],
         chartOptions: {}
      }
   },
   mounted() {

      let res = Object.groupBy(this.transactions, ({category}) => category)


      let labels = []
      let data = []

      for (const i in res) {
         labels.push(i)
         data.push(res[i].length)
      }

      this.series = data
      this.chartOptions = {
         chart: {
            width: 350,
            type: 'pie',
         },
         labels : labels,
         responsive: [{
            breakpoint: 480,
            options: {
               chart: {
                  width: 350
               },
               legend: {
                  position: 'bottom'
               }
            }
         }]
      }

   }
}
</script>





<style scoped>

</style>
