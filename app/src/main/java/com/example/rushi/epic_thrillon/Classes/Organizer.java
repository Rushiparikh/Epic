package com.example.rushi.epic_thrillon.Classes;

/**
 * Created by dhaval on 30/12/2017.
 */

import java.util.HashMap;
import java.util.Map;

public class Organizer {

    private String activityId;
    private int availability;
    private String id;
    private String orgId;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Organizer(){

    }

    public Organizer(String activityId, int availability, String id, String orgId) {
        this.activityId = activityId;
        this.availability = availability;
        this.id = id;
        this.orgId = orgId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(Integer availability) {
        this.availability = availability;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
