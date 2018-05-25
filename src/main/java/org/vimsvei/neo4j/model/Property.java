package org.vimsvei.neo4j.model;

import com.google.gson.Gson;

import java.util.HashMap;

public class Property {

    private HashMap<String, String> hash;

    public Property() {
        this.hash = new HashMap<String, String>();
    }

    public Property(String key, String value) {
        this.hash = new HashMap<String, String>();
        this.hash.put(key, value);
    }

    public void add(String key, String value) {
        this.hash.put(key,value);
    }

    String toJson() {
        return new Gson().toJson(this.hash);
    }
}
