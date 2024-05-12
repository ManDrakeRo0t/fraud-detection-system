<template>
    <div>
      <div style="text-align: center; font-size: large; ">
        Fraud detection real-time system
      </div>
    <b-nav tabs align="center">
      <b-nav-item exact exact-active-class="active" to="/">Map</b-nav-item>
      <b-nav-item exact exact-active-class="active" to="/customers" >Customers</b-nav-item>
      <b-nav-item>Link with a long name </b-nav-item>
      <b-nav-item disabled>Disabled</b-nav-item>
    </b-nav>
</div>
</template>

<script>
    export default {
        name: "default-header",
        data() {
          return {
            color : "#286CBB"
          }
        }
    }
</script>

<style scoped>

</style>
