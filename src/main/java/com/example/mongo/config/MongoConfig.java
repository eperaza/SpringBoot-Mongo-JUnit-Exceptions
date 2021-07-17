package com.example.mongo.config;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Collections;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration //config for Mongo template
public class MongoConfig extends AbstractMongoClientConfiguration {
    
    @Value("${ssl.mongodb.javax.net.ssl.keyStore}")
    private String keyStore;
    
    @Override
    protected String getDatabaseName() {
        return "admin";
    }
    
    @Override
    public MongoClient mongoClient(){
        TrustManager[] trustAllCerts = new TrustManager[] {
            new X509TrustManager() {
              public java.security.cert.X509Certificate[] getAcceptedIssuers() {
               return null;
              }
              @Override
              public void checkClientTrusted(X509Certificate[] arg0, String arg1)
               throws CertificateException {}
     
              @Override
              public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {}
    
              }
         };
        System.setProperty ("javax.net.ssl.keyStore", keyStore);
        System.setProperty ("javax.net.ssl.keyStorePassword","changeit"); 
        System.setProperty("javax.net.ssl.trustStore","/etc/ssl/certs/java/cacerts");
        System.setProperty("javax.net.ssl.trustStorePassword","changeit");
        ConnectionString connectionString = new ConnectionString("mongodb://ec2-52-39-1-101.us-west-2.compute.amazonaws.com:27017/admin");
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            
            .applyToSslSettings(builder -> {
                 builder.enabled(true);
                 builder.invalidHostNameAllowed(true);
                 try {
                    SSLContext sc=null;
                    sc = SSLContext.getInstance("SSL");
                    sc.init(null, trustAllCerts, new java.security.SecureRandom());
                    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

                    builder.context(sc);
                } catch (NoSuchAlgorithmException | KeyManagementException e) {
                    e.printStackTrace();
                }

            })
            
            .build();
        // Create all-trusting host name verifier
        HostnameVerifier validHosts = new HostnameVerifier() {
        @Override
        public boolean verify(String arg0, SSLSession arg1) {
            return true;
        }
        };
        // All hosts will be valid
        HttpsURLConnection.setDefaultHostnameVerifier(validHosts);
        return MongoClients.create(mongoClientSettings);
    }/*
    public MongoClientSettings MongoClientSettings(MongoProperties properties, Environment environment) throws NoSuchAlgorithmException {
        System.setProperty ("javax.net.ssl.keyStore","target/test-classes/xxx.pfx");
        System.setProperty ("javax.net.ssl.keyStorePassword","changeit");  
        SSLContext sslContext = SSLContext.getDefault();
        MongoClientSettings.Builder builder = MongoClientSettings.builder();
        builder.applyToSslSettings(b -> {
        b.enabled(true);
        b.context(sslContext);
        });
        new MongoPropertiesClientSettingsBuilderCustomizer(properties, environment).customize(builder);
        return builder.build();
  }*/
    
    @Override
    public Collection<String> getMappingBasePackages() {
        return Collections.singleton("com.example.mongo");
    }
    
}
