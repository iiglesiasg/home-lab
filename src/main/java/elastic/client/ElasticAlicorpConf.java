package elastic.client;

import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Primary;
import io.micronaut.core.annotation.Introspected;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import javax.inject.Singleton;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@Slf4j
@Factory
@RequiredArgsConstructor
@Introspected
public class ElasticAlicorpConf {

    private final ElasticSearchSettings elasticSearchSettings;

    @Primary
    @Singleton
    RestClientBuilder clientBuilder(){
        HttpHost hosts = new HttpHost(
                "192.168.1.133",
                30474,
                "https"
        );
        RestClientBuilder builder = RestClient.builder(hosts);

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "xxbr87vztcjb5kl265z5ghk7"));

        builder.setHttpClientConfigCallback((httpClientBuilder) ->
                httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
                        .setSSLHostnameVerifier(((hostname, session) -> true))
                        .setSSLContext(sslContext())
        );

        return builder;
    }

    private static SSLContext sslContext() {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            TrustManager[] trustAll = new TrustManager[] {new ElasticAlicorpConf.TrustAllCertificates()};
            sslContext.init(null, trustAll, new java.security.SecureRandom());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            log.error("Error occurred loading SSLContext", e);
        }
        return sslContext;
    }

    private static class TrustAllCertificates implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] certs, String authType) {
        }

        public void checkServerTrusted(X509Certificate[] certs, String authType) {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    @Primary
    @Singleton
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(
                this.clientBuilder()
        );
    }

}
