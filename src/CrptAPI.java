import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class CrptAPI {
    private final long requestTime;
    private final int requestLimit;
    private final ReentrantLock lock = new ReentrantLock();
    private long lastRequestTime;
    private int requestCount;
    final URL url = new URL("https://ismp.crpt.ru/api/v3/lk/documents/create");

    public CrptAPI(int timeUnit, int requestLimit) throws MalformedURLException {
        this.requestLimit = requestLimit;
        this.requestTime = timeUnit;
        this.lastRequestTime = 0;
        this.requestCount = 0;
    }

    public void createDocument(Object document, String signature) {
        lock.lock();
        try {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastRequestTime >= requestTime) {
                requestCount = 0;
                lastRequestTime = currentTime;
            }

            requestCount++;
            if (requestCount > requestLimit) {
                long waitTime = lastRequestTime + requestTime - currentTime;
                TimeUnit.MILLISECONDS.sleep(waitTime);
                lastRequestTime = System.currentTimeMillis();
                requestCount = 1;
            }
            requestPOST(document, signature);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void requestPOST(Object document,String signature) throws URISyntaxException {
            String url = "https://ismp.crpt.ru/api/v3/lk/documents/create";
            HttpClient httpClient = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build();
            String jsonInputString = document.toString();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonInputString))
                    .build();
            try{
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            HttpHeaders headers = response.headers();
            String responseBody = response.body();
            System.out.println("Status Code: " + statusCode);
            System.out.println("Headers: " + headers);
            System.out.println("Response Body: " + responseBody);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
