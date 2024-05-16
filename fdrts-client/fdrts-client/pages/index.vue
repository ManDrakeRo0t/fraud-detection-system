<template>
  <div>
    <DefaultHeader/>

   <div style="display: flex">

      <div >
         <div id="map-wrap" style="height: 75vh; width: 125vh">
            <client-only>
               <l-map  :zoom=5 :center="[39.448114, -99.125350]">
                  <l-tile-layer url="http://{s}.tile.osm.org/{z}/{x}/{y}.png"></l-tile-layer>
                  <!--          <l-marker :lat-lng="[55.9464418,8.1277591]"></l-marker>-->
                  <l-marker :key="marker.id" v-for="marker in $store.state.transactionClient.transactionMap"  :lat-lng="getCords(marker)" >

                     <l-icon>
                        <router-link :to="/transaction/ + marker.id" target="_blank">
                           <span v-bind:class="getClass(marker)" class="fade-in fade-out" >
                 </span>
                        </router-link>

                     </l-icon>
                  </l-marker>
               </l-map>
            </client-only>
         </div>

      </div>

      <div style="width: 100%" >

         <TransactionRealTime></TransactionRealTime>

      </div>

   </div>



  </div>
</template>

<script>



import TransactionRealTime from "~/components/charts/main/TransactionRealTime.vue";

export default {
  name: 'IndexPage',
   components: {TransactionRealTime},
  data() {
    return {

    }
  },
   beforeMount() {
     this.$store.dispatch('transactionClient/connect')
   },
   computed: {

   },
   methods: {
      test() {
         console.log("END END")
     },
      getClass(item) {
         if (item.isFraud === null) {
            return "notValidated"
         }
        return item.isFraud ? "fraud" : "legit"
      },
      getCords(item) {
        return L.latLng(item.ccLat, item.ccLong)
      },
  }
}
</script>

<style>

.leaflet-control-attribution {
  display: none;
}

.fraud {
   height: 12px;
   width: 12px;
   background-color: #a00909;
   border-radius: 50%;
   display: inline-block;
}

.legit {
   height: 12px;
   width: 12px;
   background-color: #47dc15;
   border-radius: 50%;
   display: inline-block;
}

.notValidated {
   height: 12px;
   width: 12px;
   background-color: #e3a323;
   border-radius: 50%;
   display: inline-block;
}
@keyframes fadeIn {
   from {
      opacity: 0;
      transform: scale(0);
   }
   to {
      opacity: 1;
      transform: scale(1);
   }
}
@keyframes fadeOut {
   from {
      opacity: 1;
      transform: scale(1);
   }
   to {
      opacity: 0;
      transform: scale(0);
   }
}

.fade-out {
   animation: fadeOut 0.7s;
}
.fade-in {
   animation: fadeIn 1s;
}

</style>
