
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class WeatherFetcher {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter latitude: ");
            double latitude = scanner.nextDouble();

            System.out.print("Enter longitude: ");
            double longitude = scanner.nextDouble();

            String apiUrl = String.format(
                    "https://www.7timer.info/bin/astro.php?lon=%s&lat=%s&ac=0&unit=metric&output=json",
                    longitude, latitude);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .GET()
                    .build();

            System.out.println("Sending request to: " + apiUrl);

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();
            if (statusCode == 200) {
                System.out.println("\nWeather data received successfully:");
                System.out.println(response.body());
            } else {
                System.out.println("Error: Received HTTP status code " + statusCode + ".");
                System.out.println("Please check the coordinates and try again.");
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Network error occurred while fetching weather data.");
            System.out.println("Details: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter numeric latitude and longitude values.");
        } finally {
            scanner.close();
        }
    }
}
