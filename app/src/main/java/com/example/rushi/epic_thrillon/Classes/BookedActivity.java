package com.example.rushi.epic_thrillon.Classes;

/**
 * Created by dhaval on 22/12/2017.
 */

import java.util.HashMap;
import java.util.Map;

public class BookedActivity {

    private String actId;
    private String orgId;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public BookedActivity(String actId, String orgId) {
        this.actId = actId;
        this.orgId = orgId;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
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