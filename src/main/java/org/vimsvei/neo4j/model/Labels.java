package org.vimsvei.neo4j.model;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Labels {
    private List<String> list;

    public Labels() {
        this.list = new ArrayList<String>();
    }

    public Labels (String label) {
        this.list = new ArrayList<String>();
        this.list.add(label);
    }

    public void add(String label) {
        this.list.add(label);
    }

    String toJson() {
        return new Gson().toJson(this.list);
    }

}
