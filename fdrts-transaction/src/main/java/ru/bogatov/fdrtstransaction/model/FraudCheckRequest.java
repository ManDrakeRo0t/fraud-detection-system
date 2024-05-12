package ru.bogatov.fdrtstransaction.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FraudCheckRequest {
    Integer category;
    Float amount;
    @JsonProperty("lat")
    Float ccLat;
    @JsonProperty("long")
    Float ccLong;
    @JsonProperty("city_pop")
    Integer cityPop;
    @JsonProperty("unix_time")
    Long unixTime;
    @JsonProperty("merch_lat")
    Float merchLat;
    @JsonProperty("merch_long")
    Float merchLong;
    @JsonProperty("trans_hour")
    Integer transHour;
    @JsonProperty("trans_day")
    Integer transDay;
    @JsonProperty("trans_month")
    Integer transMouth;

}

//# {
//#     "category" : 1,
//#     "amount" : 800,
//#     "lat" : 36.9946,
//#     "long" : -81.7266,
//#     "city_pop" : 885,
//#     "unix_time" : 1325620275,
//#     "merch_lat" : 36.7658904,
//#     "merch_long": -81.951839,
//#     "trans_hour" : 1,
//#     "trans_day" : 0,
//#     "trans_month" : 0,
//# }