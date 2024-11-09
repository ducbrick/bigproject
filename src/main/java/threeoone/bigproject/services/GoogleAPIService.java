package threeoone.bigproject.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import threeoone.bigproject.entities.Document;
import threeoone.bigproject.util.Alerts;

/**
 * Service class for interacting with the Google Books API.
 *
 * @author HUY1902
 */
@Setter
@Getter
@Service
public class GoogleAPIService {

  @Value("${google.api.url}")
  private String googleBooksURL;

  @Value("${google.api.timeout}")
  private int googleBooksTimeout;

  /**
   * Finds a book by its ISBN using the Google Books API.
   *
   * @param isbn the ISBN of the book to search for.
   * @return a {@link Document} representing the book details; otherwise, null if not found
   */
  public Document findBookByISBN(String isbn) {
    JsonObject response = getJson(googleBooksURL + "isbn:" + isbn);
    if(response == null) {
      return null;
    }
    if(response.get("totalItems").getAsInt() == 0) {
      Alerts.showAlertWarning("Error!!!", "Not found");
      return null;
    }
    JsonObject info = response.get("items").getAsJsonArray().get(0).getAsJsonObject().get("volumeInfo").getAsJsonObject();
    return new Document(info.get("title").getAsString(), info.get("description").getAsString());
  }

  /**
   * Creates and configures a {@link RestTemplate} instance with custom timeout settings.
   * <p>
   * The timeout settings are configured using the {@link SimpleClientHttpRequestFactory} based on the
   * {@code googleBooksTimeout} value.
   * </p>
   *
   * @return a configured {@link RestTemplate} instance.
   */
  public RestTemplate getRestTemplate() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setRequestFactory(new SimpleClientHttpRequestFactory());
    SimpleClientHttpRequestFactory requestFactory = (SimpleClientHttpRequestFactory) restTemplate.getRequestFactory();
    requestFactory.setReadTimeout(googleBooksTimeout);
    requestFactory.setConnectTimeout(googleBooksTimeout);

    return restTemplate;
  }

  /**
   * Fetches a JSON response from the given URL.
   * Using customized {@link RestTemplate}
   *
   * @param url the URL to fetch the JSON from.
   * @return a {@link JsonObject} containing the response data.
   */
  public JsonObject getJson(String url) {
    ResponseEntity<String> response = null;
    try {
      response = getRestTemplate().getForEntity(url, String.class);
    } catch (RestClientException e) {
      Alerts.showAlertWarning("Error!!!", e.getMessage());
      return null;
    }

    if (!response.getStatusCode().is2xxSuccessful()) {
      Alerts.showAlertWarning("Error!!!" + response.getStatusCode(), response.getBody());
      return null;
    }

    return new Gson().fromJson(response.getBody(), JsonObject.class);
  }

}
