package org.vimsvei.neo4j.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.http.entity.StringEntity;
import org.vimsvei.neo4j.rest.RestConnector;

public class GraphDatabase {

    private String host;
    private Integer port;

    private RestConnector connector;

    private String version;
    private String urlNode;
    private String urlRelationship;
    private String urlNodeLabels;
    private String urlIndexes;
    private String urlCypher;


    public GraphDatabase() {
        this.host = "http://localhost";
        this.port = 7474;
        this.connector = new RestConnector(this.host, this.port);
    }

    public GraphDatabase(String host, Integer port) {
        this.host = host;
        this.port = port;
        this.connector = new RestConnector(this.host, this.port);
        getServiceRoot();
    }

    public GraphDatabase(String host, Integer port, String user, String password) {
        this.host = host;
        this.port = port;
        this.connector = new RestConnector(this.host, this.port, user, password);
        getServiceRoot();
    }

    public Node createNode(Node node) {
        String response = "";
        try {
            StringEntity params = new StringEntity(node.property.toJson());
            response = connector.post(this.urlNode, params);
            if (!response.isEmpty()) {
                JsonElement root = new JsonParser().parse(response);
                node.setId(root.getAsJsonObject().get("metadata").getAsJsonObject().get("id").getAsInt());
                node.setUrlSelf(root.getAsJsonObject().get("self").getAsString());
                node.setUrlCreateRelationship(root.getAsJsonObject().get("create_relationship").getAsString());
                node.setUrlLabels(root.getAsJsonObject().get("labels").getAsString());
                addLabel(node, node.label);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return node;
    }

    public void addLabel(Node node, Labels labels) {
        try {
            StringEntity params = new StringEntity(labels.toJson());
            String response = connector.post(node.getUrlLabels(), params);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public Relationship createRelationship(Relationship relationship) {
        return relationship;
    }

    private String getBaseUrl() {
        return String.format("{0}:{1}/db/data", this.host, this.port.toString());
    }

    private void getServiceRoot() {
        String response = "";
        try {
            response = connector.get(getBaseUrl());
            JsonElement root = new JsonParser().parse(response);

            this.version = root.getAsJsonObject().get("neo4j_version").getAsString();
            this.urlNode = root.getAsJsonObject().get("node").getAsString();
            this.urlRelationship = root.getAsJsonObject().get("relationship").getAsString();
            this.urlNodeLabels = root.getAsJsonObject().get("node_labels").getAsString();
            this.urlIndexes = root.getAsJsonObject().get("indexes").getAsString();
            this.urlCypher = root.getAsJsonObject().get("cypher").getAsString();

        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }
}
