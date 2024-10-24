package web.accessor;

import java.util.Map;

public interface RestAccessor {

    <T, R> R sendPostRequest(String url, T requestBody, Class<R> responseType, Map<String, String> headersMap);

    <R> R sendGetRequest(String url, Class<R> responseType, Map<String, String> headersMap);

}