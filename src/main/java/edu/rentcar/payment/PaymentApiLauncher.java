package edu.rentcar.payment;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

public final class PaymentApiLauncher {
    private static final String HEALTH_URL = "http://localhost:8080/health";

    private PaymentApiLauncher() {
    }

    public static String ensureRunning() {
        if (isUp()) {
            return "API de pagos lista en http://localhost:8080";
        }

        Path jar = locateJar();
        if (jar == null) {
            return "No se encontró external/payment-api.jar. Ejecútela a mano: java -jar external/payment-api.jar";
        }

        try {
            ProcessBuilder builder = new ProcessBuilder(javaCommand(), "-jar", jar.toAbsolutePath().toString());
            builder.redirectErrorStream(true);
            builder.redirectOutput(ProcessBuilder.Redirect.DISCARD);
            builder.start();
        } catch (IOException exception) {
            return "No se pudo iniciar la API de pagos: " + safeMessage(exception);
        }

        for (int attempt = 0; attempt < 20; attempt++) {
            if (isUp()) {
                return "API de pagos iniciada en http://localhost:8080";
            }
            try {
                Thread.sleep(400);
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                return "La espera de la API de pagos fue interrumpida.";
            }
        }
        return "La API de pagos no respondió. Ábrala en otra terminal: java -jar external/payment-api.jar";
    }

    public static boolean isUp() {
        try {
            HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(2)).build();
            HttpRequest request = HttpRequest.newBuilder(URI.create(HEALTH_URL))
                    .timeout(Duration.ofSeconds(2))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200;
        } catch (IOException | InterruptedException exception) {
            if (exception instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            return false;
        }
    }

    private static Path locateJar() {
        Path[] candidates = {
                Path.of("external", "payment-api.jar"),
                Path.of(System.getProperty("user.dir"), "external", "payment-api.jar")
        };
        for (Path candidate : candidates) {
            if (Files.isRegularFile(candidate)) {
                return candidate;
            }
        }
        return null;
    }

    private static String javaCommand() {
        String home = System.getProperty("java.home");
        if (home == null || home.isBlank()) {
            return "java";
        }
        boolean windows = System.getProperty("os.name", "").toLowerCase().contains("win");
        return Path.of(home, "bin", windows ? "java.exe" : "java").toString();
    }

    private static String safeMessage(Exception exception) {
        String message = exception.getMessage();
        return (message == null || message.isBlank()) ? exception.getClass().getSimpleName() : message;
    }
}
