package web.accessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import web.exception.InvalidRequestException;

import java.util.Map;

@Service
public class RestAccessorImpl implements RestAccessor {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public <T, R> R sendPostRequest(String url, T requestBody, Class<R> responseType, Map<String, String> headersMap) {
        HttpHeaders headers = new HttpHeaders();
        if (headersMap != null) {
            headersMap.forEach(headers::set);
        }

        if (!headers.containsKey(HttpHeaders.CONTENT_TYPE)) {
            headers.setContentType(MediaType.APPLICATION_JSON);
        }
        HttpEntity<T> entity;
        if (requestBody != null) {
            entity = new HttpEntity<>(requestBody, headers);
        } else {
            entity = new HttpEntity<>(headers);
        }

        ResponseEntity<R> response = restTemplate.postForEntity(url, entity, responseType);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new InvalidRequestException("Ошибка : HTTP код : " + response.getStatusCode());
        }
    }

    @Override
    public <R> R sendGetRequest(String url, Class<R> responseType, Map<String, String> headersMap) {
        HttpHeaders headers = new HttpHeaders();

        if (headersMap != null) {
            headersMap.forEach(headers::set);
        }

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<R> response = restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new InvalidRequestException("Ошибка : HTTP код : " + response.getStatusCode());
        }
    }
}