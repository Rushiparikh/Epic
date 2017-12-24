package com.example.rushi.epic_thrillon.Classes;

import java.util.Map;

/**
 * Created by rushi on 12/24/2017.
 */

import java.util.HashMap;
import java.util.Map;

public class Activities {

        private String id;
        private String image;
        private String name;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Activities() {

    }

    public Activities(String id, String image, String name) {
        this.id = id;
        this.image = image;
        this.name = name;
    }

    public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }
 }

