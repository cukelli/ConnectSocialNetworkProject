package com.example.rs.ftn.ConnectSocialNetworkProject.configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.example.rs.ftn.ConnectSocialNetworkProject.indexrepository")
public class ElasticSearchConfiguration extends org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration {

    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private int port;

    @Value("${elasticsearch.username}")
    private String userName;

    @Value("${elasticsearch.password}")
    private String password;

    @Override
    public ClientConfiguration clientConfiguration() {
        CertificateFactory cf = null;
        try {
            cf = CertificateFactory.getInstance("X.509");
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        }

        Certificate ca;
        try (InputStream certificateInputStream = new FileInputStream("http_ca.crt")) {
            ca = cf.generateCertificate(certificateInputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException | CertificateException e) {
            throw new RuntimeException(e);
        }

        // Create a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance(keyStoreType);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }
        try {
            keyStore.load(null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        }
        try {
            keyStore.setCertificateEntry("ca", ca);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }

        // Create a TrustManager that trusts the CAs in our KeyStore
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = null;
        try {
            tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        try {
            tmf.init(keyStore);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }
        SSLContext context = null;
        try {
            context = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        try {
            context.init(null, tmf.getTrustManagers(), null);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }
        return ClientConfiguration.builder().
                connectedTo(host + ":" + port)
//                usingSsl(context) //add the generated sha-256 fingerprint
                .withBasicAuth(userName, password).build();
    }
}