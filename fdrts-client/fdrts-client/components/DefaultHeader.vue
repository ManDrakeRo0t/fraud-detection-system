<template>
   <div>

      <CustomerCreation @close-modal="customerCreationModal=false" v-if="customerCreationModal"></CustomerCreation>

      <MerchantCreation @close-modal="merchantCreationModal=false" v-if="merchantCreationModal"></MerchantCreation>

      <TransactionCreation @close-modal="transactionCreationModal=false" v-if="transactionCreationModal" ></TransactionCreation>

      <EmulationTransaction @close-modal="transactionEmulationModal=false" v-if="transactionEmulationModal"></EmulationTransaction>

      <div style="text-align: center; font-size: large; ">
         <h4>FDRTS Обнаружение мошенничества в реальном времени <span class="legit" v-if="$store.state.transactionClient.isConnected">
         </span>
            <span class="fraud" v-else>
         </span></h4>
      </div>
      <b-nav tabs align="center">
         <b-nav-item exact exact-active-class="active" to="/">Карта</b-nav-item>
         <b-nav-item exact exact-active-class="active" to="/customers">Клиенты</b-nav-item>
         <b-nav-item exact exact-active-class="active" to="/merchants">Продавцы</b-nav-item>
         <b-nav-item-dropdown
            id="my-nav-dropdown"
            text="Действия"
            toggle-class="nav-link-custom"
            right
         >
            <b-dropdown-item @click="customerCreationModal=true">Создать клиента</b-dropdown-item>
            <b-dropdown-item @click="merchantCreationModal=true">Создать продавца</b-dropdown-item>
            <b-dropdown-item @click="transactionCreationModal=true">Создать транзакцию</b-dropdown-item>
            <b-dropdown-divider></b-dropdown-divider>
            <b-dropdown-item @click="transactionEmulationModal=true">Эмуляция активности</b-dropdown-item>
         </b-nav-item-dropdown>
      </b-nav>
   </div>
</template>

<script>
import CustomerCreation from "~/components/modals/CustomerCreation.vue";
import MerchantCreation from "~/components/modals/MerchantCreation.vue";
import TransactionCreation from "~/components/modals/TransactionCreation.vue";
import EmulationTransaction from "~/components/modals/EmulationTransaction.vue";

export default {
   name: "default-header",
   components: {EmulationTransaction, TransactionCreation, MerchantCreation, CustomerCreation},
   data() {
      return {
         customerCreationModal: false,
         merchantCreationModal: false,
         transactionCreationModal: false,
         transactionEmulationModal: false,
      }
   }
}
</script>

<style scoped>
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


</style>
