<template>
   <div>
      <apexchart type="pie" width="300" :options="chartOptions" :series="series"></apexchart>
   </div>
</template>

<script>
export default {
   name: "TransactionPie",
   props: ["transactions"],
   data() {
      return {
         series: [],
         legitCount: 0,
         fraudCount: 0,
         chartOptions: {
            colors : [
               '#47dc15',
               '#fa2e3f',
            ],
            chart: {
               width: 300,
               type: 'pie',
            },
            labels: ['Legit', 'Fraud'],
            responsive: [{
               breakpoint: 480,
               options: {
                  chart: {
                     width: 200
                  },
                  legend: {
                     position: 'bottom'
                  }
               }
            }]
         },
      }
   },
   mounted() {
      this.legitCount = this.transactions.filter(item => item.isFraud === false).length
      this.fraudCount = this.transactions.filter(item => item.isFraud === true).length
      this.series.push(this.legitCount)
      this.series.push(this.fraudCount)
   }
}
</script>


<style scoped>

</style>
