package org.vimsvei.neo4j.model;

public class Node {

    public Property property;
    public Labels label;

    transient private Integer id;

    transient private String urlLabels;

    transient private String urlCreateRelationship;

    transient private String urlSelf;

    public Node(){
        this.property = new Property();
    }

    public Node(Property property) {
        this.property = property;
    }

    void setId(Integer id) {
        this.id = id;
    }

    void setUrlSelf(String self) {
        this.urlSelf = self;
    }

    void setUrlCreateRelationship(String create_relationship){
        this.urlCreateRelationship = create_relationship;
    }

    void setUrlLabels(String labels) {
        this.urlLabels = labels;
    }

    String getUrlLabels() {
        return urlLabels;
    }
}
