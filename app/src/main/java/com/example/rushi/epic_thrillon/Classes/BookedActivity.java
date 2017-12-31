package com.example.rushi.epic_thrillon.Classes;

/**
 * Created by dhaval on 22/12/2017.
 */

import java.util.HashMap;
import java.util.Map;

public class BookedActivity {


    private String id;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    public BookedActivity(){
        
    }

    public BookedActivity(String id) {

        this.id =id;
    }
    public String getId() {
        return id;
    }


    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}