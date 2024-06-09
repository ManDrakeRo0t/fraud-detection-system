<template>
   <div class="modal-overlay-my">
      <div class="modal-my">
         <div class="container" style="width: 80%">
            <div class="row mt-2">
               <input type="text" style="margin-bottom: 10px" id="parentName" v-model="firstName" class="form-control"
                      placeholder="Имя" required autofocus>
            </div>
            <div class="row mt-2">
               <input type="text" style="margin-bottom: 10px" id="parentName" v-model="lastName" class="form-control"
                      placeholder="Фамилия" required autofocus>
            </div>
            <div class="row mt-2">
               <input type="text" style="margin-bottom: 10px" id="parentName" v-model="job" class="form-control"
                      placeholder="Место работы" required autofocus>
            </div>
            <div class="row mt-2">
               <b-form-datepicker id="example-datepicker" v-model="dob" class="mb-2"></b-form-datepicker>
            </div>
            <div id="map-wrap2" style="height: 25vh; width: 60vh">
               <client-only>
                  <l-map @dblclick="onMapClick" :zoom=5 :center="[39.448114, -99.125350]">
                     <l-tile-layer url="http://{s}.tile.osm.org/{z}/{x}/{y}.png"></l-tile-layer>
                     <l-marker
                        v-if="position"
                        :lat-lng.sync="position"
                        visible
                     ></l-marker>
                  </l-map>
               </client-only>
            </div>
            <b-button class="mt-3" @click="create" variant="primary" >Создать</b-button>
         </div>
      </div>
      <div class="close" @click="$emit('close-modal')">
         <span aria-hidden="true">&times;</span>
      </div>
   </div>
</template>

<script>
export default {
   name: "CustomerCreation",
   data() {
      return {
         position: null,
         address: null,
         firstName : "",
         lastName : "",
         job : "",
         dob : ""
      }

   },
   methods: {
      makeid(length) {
         let result = '';
         const characters = '0123456789';
         const charactersLength = characters.length;
         let counter = 0;
         while (counter < length) {
            result += characters.charAt(Math.floor(Math.random() * charactersLength));
            counter += 1;
         }
         return result;
      },
      onMapClick(loc) {
         console.log(loc)
         this.position = loc.latlng
         this.getAddress()
      },
      async getAddress() {
         try {
            const {lat, lng} = this.position;
            const result = await fetch(
               `https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=${lat}&lon=${lng}`
            );
            if (result.status === 200) {
               const body = await result.json();
               this.address = body
               console.log(body)
            }
         } catch (e) {
            console.error("Reverse Geocode Error->", e);
         }
      },
      create() {
         this.$axios.setBaseURL("http://localhost:8080")
         this.$axios.post("/api/customer", {
            ccNum : this.makeid(20),
            firstName : this.firstName,
            lastName : this.lastName,
            gender : false,
            birthday : this.dob,
            street : this.address.address.road,
            city  : this.address.address.city,
            state : this.address.address.state,
            zip : this.address.address.zip,
            job : this.job,
            cityPop : 10000,
            lat : this.address.lat,
            long : this.address.lon,
            migrated : false
         }).then((res) => {
            this.$emit('close-modal')
            let route = this.$router.resolve({path: '/customers/' + res.data.ccNum});
            // let route = this.$router.resolve('/link/to/page'); // This also works.
            window.open(route.href, '_blank');
         })
      },
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
