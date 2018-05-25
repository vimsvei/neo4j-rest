package org.vimsvei.neo4j.rest;

import org.apache.http.HttpEntity;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.vimsvei.error.ErrorMessage;
import org.vimsvei.neo4j.model.Node;

import java.io.IOException;

public class RestConnector {

    private String host;
    private Integer port;

    private CredentialsProvider credentials;

    public RestConnector(String host, Integer port) {
        this.host = host;
        this.port = port;
        this.credentials = getCredentials("neo4j", "");
    }

    public RestConnector(String host, Integer port, String user, String password) {
        this.host = host;
        this.port = port;
        this.credentials = getCredentials(user, password);
    }

    private CredentialsProvider getCredentials(String user, String password) {
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(this.host, this.port),
                new UsernamePasswordCredentials(user, password)
        );
        return credsProvider;
    }

    private String getBaseUri() {
        return "";
    }

    public String get(String uri) throws Exception {
        String responseString = "";
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCredentialsProvider(this.credentials)
                .build();
        try {
            HttpGet request = new HttpGet(uri);
            request.setHeader("Accept","application/json; charset=UTF-8");
            CloseableHttpResponse response = httpClient.execute(request);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    responseString = EntityUtils.toString(entity, "UTF-8");
                }
            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
        return responseString;
    }

    public String post(String url, StringEntity params) throws Exception {
        String responseString = "";
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCredentialsProvider(this.credentials)
                .build();
        try {
            HttpPost request = new HttpPost(url);
            request.setHeader("Accept","application/json; charset=UTF-8");
            params.setContentType("application/json");
            request.setEntity(params);
            CloseableHttpResponse response = httpClient.execute(request);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    responseString = EntityUtils.toString(entity, "UTF-8");
                }
            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
        return responseString;
    }
}
