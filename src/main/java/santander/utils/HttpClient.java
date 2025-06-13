package santander.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpClient {

    @Value("${santander.url}")
    private String url;

    @Autowired
    RestTemplate template;

    public <T> T callInternally(Class<T> clazz) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<T> response = template.exchange(url, HttpMethod.GET, entity, clazz);

        return response.getBody();
    }
}
