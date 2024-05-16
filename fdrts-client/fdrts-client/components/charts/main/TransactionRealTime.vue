<template>
   <div id="chart" >
      <apexchart class="mt-5" type="line" height="300"  ref="chartFraud" :options="chartOptionsFraud" :series="series"></apexchart>

      <apexchart class="mt-5" type="line" height="300" ref="chartLegit" :options="chartOptionsLegit" :series="series"></apexchart>
   </div>
</template>


<script>
export default {
   name: "TransactionRealTime",
   mounted() {
      const me = this;
      this.interval = window.setInterval(() => {

         this.updateData()
         me.$refs.chartFraud.updateSeries([
            {
               data : this.dataFraud
            }
         ])

         me.$refs.chartLegit.updateSeries([
            {
               data : this.dataLegit
            }
         ])

      }, 2000)
   },
   destroyed() {
      window.clearInterval(this.interval)
   },
   methods : {
      updateData () {
         let realNow = Date.now()
         let now = new Date(realNow - 1000);
         let nowPast = new Date(realNow - 3000);
         let transactions = this.$store.state.transactionClient.transactionsTime
         let nowTransactions = transactions.filter(item => item.time > nowPast && item.time <= now)
         let fraudCount = nowTransactions.filter(item => item.isFraud === true).length
         let legitCount = nowTransactions.filter(item => item.isFraud === false).length
         //let legitCount = nowTransactions.length - fraudCount
         // console.log(nowPast.getSeconds() + " - " + now.getSeconds() + " : "+ count)
         // console.log(fraudCount, legitCount)
         this.dataFraud.push(
            {
               x : now,
               y : fraudCount
            }
         )
         this.dataLegit.push(
            {
               x : now,
               y : legitCount
            }
         )

         if (this.dataFraud.length > 10) {

            this.dataLegit = this.dataLegit.slice(this.dataLegit.length - 10)
            this.dataFraud = this.dataFraud.slice(this.dataFraud.length - 10)
         }
      }
   },
   data() {
      return {
         interval : null,
         start : new Date(),

         dataFraud : [],
         dataLegit : [],
         series : [
         ],
         chartOptionsFraud: {
            colors : [
               '#fa2e3f',
            ],
            xaxis: {
               type: 'datetime',
               range: 15000,
            },
            yaxis: {
               max: 30,
               min : 0,
               stepSize : 10,
               forceNiceScale : false
            },
            chart: {
               id: 'realtimeFraud',
               height: 350,
               type: 'line',
               toolbar: {
                  show: false
               },
               zoom: {
                  enabled: false
               },
               stroke: {
                  curve: 'smooth'
               },
               title: {
                  text: 'Dynamic Updating Chart',
                  align: 'left'
               },
               markers: {
                  size: 0
               },
               animations: {
                  enabled: true,
                  easing: 'linear',
                  dynamicAnimation: {
                     speed: 2000
                  }
               },
               legend: {
                  show: false
               },
            },
            grid: {
               borderColor: '#f1f1f1',
            }
         },
         chartOptionsLegit: {
            colors : [
               '#3cfa2e'
            ],
            xaxis: {
               type: 'datetime',
               range: 15000,
            },
            yaxis: {
               max: 30,
               min : 0,
               stepSize : 10,
               forceNiceScale : false
            },
            chart: {
               id: 'realtimeLegit',
               height: 350,
               type: 'line',
               toolbar: {
                  show: false
               },
               zoom: {
                  enabled: false
               },
               stroke: {
                  curve: 'smooth'
               },
               title: {
                  text: 'Dynamic Updating Chart',
                  align: 'left'
               },
               markers: {
                  size: 0
               },
               animations: {
                  enabled: true,
                  easing: 'linear',
                  dynamicAnimation: {
                     speed: 2000
                  }
               },
               legend: {
                  show: false
               },
            },
            grid: {
               borderColor: '#f1f1f1',
            }
         },

      }

   },
}
</script>

<style scoped>

</style>
