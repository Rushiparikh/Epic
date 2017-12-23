package com.example.rushi.epic_thrillon.Classes;

/**
 * Created by dhaval on 22/12/2017.
 */
import java.util.HashMap;
import java.util.Map;

public class Destination {

    private String image;
    private String destId;
    private String destName;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Destination() {
    }

    public Destination(String image, String destId, String destName) {
        this.image = image;
        this.destId = destId;
        this.destName = destName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDestId() {
        return destId;
    }

    public void setDestId(String destId) {
        this.destId = destId;
    }

    public String getDestName() {
        return destName;
    }

    public void setDestName(String destName) {
        this.destName = destName;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}