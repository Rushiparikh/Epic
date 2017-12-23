package com.example.rushi.epic_thrillon.Classes;

/**
 * Created by dhaval on 22/12/2017.
 */


import java.util.HashMap;
import java.util.Map;

public class Service {

    private Boolean food;
    private Boolean hotel;
    private Boolean travelling;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Service() {
    }

    public Service(Boolean food, Boolean hotel, Boolean travelling) {
        this.food = food;
        this.hotel = hotel;
        this.travelling = travelling;
    }

    public Boolean getFood() {
        return food;
    }

    public void setFood(Boolean food) {
        this.food = food;
    }

    public Boolean getHotel() {
        return hotel;
    }

    public void setHotel(Boolean hotel) {
        this.hotel = hotel;
    }

    public Boolean getTravelling() {
        return travelling;
    }

    public void setTravelling(Boolean travelling) {
        this.travelling = travelling;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
