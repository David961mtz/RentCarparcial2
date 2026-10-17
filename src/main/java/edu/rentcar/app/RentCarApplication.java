package edu.rentcar.app;

import edu.rentcar.payment.PaymentApiLauncher;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class RentCarApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        String paymentStatus = PaymentApiLauncher.ensureRunning();
        FXMLLoader loader = new FXMLLoader(
                RentCarApplication.class.getResource("/edu/rentcar/view/main-view.fxml")
        );
        Scene scene = new Scene(loader.load(), 1100, 700);
        stage.setTitle("RentCar - Gestión de alquileres");
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();

        if (!PaymentApiLauncher.isUp()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("API de pagos");
            alert.setHeaderText("No se pudo conectar con la API de pagos");
            alert.setContentText(paymentStatus
                    + "\n\nSin esa API las reservaciones quedarán como Fallida.");
            alert.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
