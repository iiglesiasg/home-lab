/*package elastic.client;

import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.nio.client.HttpAsyncClient;
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
public class HttpAsyncClientBuilderFactory {

    private final ElasticSearchSettings elasticSearchSettings;

    @Replaces(HttpAsyncClientBuilder.class)
    @Primary
    @Singleton
    public HttpAsyncClientBuilder builder() {

        HttpHost hosts = new HttpHost(
                elasticSearchSettings.getHost(),
                elasticSearchSettings.getPort(),
                "https"
        );
        RestClientBuilder builder = RestClient.builder(hosts);

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "xxbr87vztcjb5kl265z5ghk7"));
*/
        /*builder.setHttpClientConfigCallback((httpClientBuilder) ->
                httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
                                 .setSSLHostnameVerifier(((hostname, session) -> true))
                                 .setSSLContext(sslContext())
        );

        return builder;*/
       // ConnectionConfig config = ConnectionConfig.DEFAULT;
  //              return HttpAsyncClientBuilder.create()
                //.setDefaultConnectionConfig(config)
    /*            .setDefaultCredentialsProvider(credentialsProvider)
                .setSSLHostnameVerifier(((hostname, session) -> true))
                .setSSLContext(sslContext());
    }

    private static SSLContext sslContext() {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            TrustManager[] trustAll = new TrustManager[] {new TrustAllCertificates()};
            sslContext.init(null, trustAll, new java.security.SecureRandom());
            //sslContext.init(null, trustManagers(crtData.getBytes()), null);
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
}
*/