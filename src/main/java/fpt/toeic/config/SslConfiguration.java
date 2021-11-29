package fpt.toeic.config;
//

//import org.apache.http.client.HttpClient;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.ssl.SSLContextBuilder;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.core.io.Resource;
//
//import javax.net.ssl.SSLContext;
//
//@Configuration
public class SslConfiguration {
//    @Value("classpath:keystore/keystore")
//    private Resource trustStore;
//
//    @Value("admin123")
//    private String keyStorePassword;
//    @Bean
//    public RestTemplate restTemplate() throws Exception {
//        SSLContext sslContext = new SSLContextBuilder()
//            .loadTrustMaterial(trustStore.getURL(), keyStorePassword.toCharArray())
//            .build();
//        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
//        HttpClient httpClient = HttpClients.custom()
//            .setSSLSocketFactory(socketFactory)
//            .build();
//        HttpComponentsClientHttpRequestFactory factory =
//            new HttpComponentsClientHttpRequestFactory(httpClient);
//        return new RestTemplate(factory);
//    }
}
