
package edu.rentcar.controller;

import edu.rentcar.app.ApplicationServices;
import edu.rentcar.builder.RentalRequestBuilder;
import edu.rentcar.catalog.CatalogItem;
import edu.rentcar.catalog.ServiceCatalog;
import edu.rentcar.facade.RentalProcessResult;
import edu.rentcar.model.Customer;
import edu.rentcar.model.RentalRequest;
import edu.rentcar.model.Reservation;
import edu.rentcar.model.ReservationStatus;
import edu.rentcar.model.Vehicle;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class ReservationController {

    @FXML private ComboBox<CustomerItem> customerCombo;
    @FXML private ComboBox<VehicleItem> vehicleCombo;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private ComboBox<String> modalityCombo;
    @FXML private CheckBox gpsCheck;
    @FXML private CheckBox childSeatCheck;
    @FXML private CheckBox extraDriverCheck;
    @FXML private ComboBox<PackageItem> packageCombo;
    @FXML private ComboBox<String> paymentTokenCombo;
    @FXML private Label resultLabel;

    @FXML private TableView<Reservation> reservationTable;
    @FXML private TableColumn<Reservation, String> reservationIdColumn;
    @FXML private TableColumn<Reservation, String> reservationCustomerColumn;
    @FXML private TableColumn<Reservation, String> reservationVehicleColumn;
    @FXML private TableColumn<Reservation, LocalDate> reservationStartColumn;
    @FXML private TableColumn<Reservation, LocalDate> reservationEndColumn;
    @FXML private TableColumn<Reservation, String> reservationModalityColumn;
    @FXML private TableColumn<Reservation, Double> reservationTotalColumn;
    @FXML private TableColumn<Reservation, ReservationStatus> reservationStatusColumn;
    @FXML private TableColumn<Reservation, String> reservationMessageColumn;

    @FXML
    public void initialize() {
        configureReservationTable();

        refreshCustomers();
        refreshVehicles();

        modalityCombo.getItems().setAll(
                "Economy", "Executive", "Premium"
        );

        packageCombo.getItems().setAll(
                new PackageItem("None", null),
                new PackageItem("Travel kit", ServiceCatalog.TRAVEL_KIT),
                new PackageItem("Family pack", ServiceCatalog.FAMILY_PACK)
        );

        paymentTokenCombo.getItems().setAll(
                "tok_approved",
                "tok_declined",
                "tok_api_error"
        );

        modalityCombo.getSelectionModel().selectFirst();
        packageCombo.getSelectionModel().selectFirst();
        paymentTokenCombo.getSelectionModel().selectFirst();

        startDatePicker.setValue(LocalDate.now().plusDays(1));
        endDatePicker.setValue(LocalDate.now().plusDays(4));

        refreshReservations();
    }

    private void configureReservationTable() {
        reservationIdColumn.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        reservationCustomerColumn.setCellValueFactory(
                new PropertyValueFactory<>("customerName"));

        reservationVehicleColumn.setCellValueFactory(
                new PropertyValueFactory<>("vehicleName"));

        reservationStartColumn.setCellValueFactory(
                new PropertyValueFactory<>("startDate"));

        reservationEndColumn.setCellValueFactory(
                new PropertyValueFactory<>("endDate"));

        reservationModalityColumn.setCellValueFactory(
                new PropertyValueFactory<>("modality"));

        reservationTotalColumn.setCellValueFactory(
                new PropertyValueFactory<>("totalCost"));

        reservationStatusColumn.setCellValueFactory(
                new PropertyValueFactory<>("status"));

        reservationMessageColumn.setCellValueFactory(
                new PropertyValueFactory<>("message"));
    }

    /**
     * Carga los clientes desde el servicio compartido.
     * No utiliza SampleData, porque eso volvería a cargar
     * los clientes de ejemplo aunque hayan sido eliminados.
     */
    private void refreshCustomers() {
        customerCombo.getItems().clear();

        for (Customer customer :
                ApplicationServices.customerService().getAllCustomers()) {

            customerCombo.getItems().add(
                    new CustomerItem(customer)
            );
        }

        if (!customerCombo.getItems().isEmpty()) {
            customerCombo.getSelectionModel().selectFirst();
        }
    }

    private void refreshVehicles() {
        vehicleCombo.getItems().clear();

        for (Vehicle vehicle :
                ApplicationServices.vehicleService().getAllVehicles()) {

            vehicleCombo.getItems().add(
                    new VehicleItem(vehicle)
            );
        }

        if (!vehicleCombo.getItems().isEmpty()) {
            vehicleCombo.getSelectionModel().selectFirst();
        }
    }

    @FXML
    private void confirmReservation() {
        try {
            CustomerItem customerItem = customerCombo.getValue();
            VehicleItem vehicleItem = vehicleCombo.getValue();

            if (customerItem == null) {
                throw new IllegalArgumentException(
                        "No hay clientes disponibles. Registre un cliente primero."
                );
            }

            if (vehicleItem == null) {
                throw new IllegalArgumentException(
                        "No hay vehículos disponibles."
                );
            }

            if (startDatePicker.getValue() == null
                    || endDatePicker.getValue() == null) {
                throw new IllegalArgumentException(
                        "Seleccione las fechas de inicio y finalización."
                );
            }

            if (modalityCombo.getValue() == null) {
                throw new IllegalArgumentException(
                        "Seleccione una modalidad de alquiler."
                );
            }

            if (paymentTokenCombo.getValue() == null) {
                throw new IllegalArgumentException(
                        "Seleccione un método o token de pago."
                );
            }

            RentalRequestBuilder builder = new RentalRequestBuilder()
                    .customer(customerItem.customer())
                    .vehicle(vehicleItem.vehicle())
                    .startDate(startDatePicker.getValue())
                    .endDate(endDatePicker.getValue())
                    .modality(modalityCombo.getValue());

            if (gpsCheck.isSelected()) {
                builder.addExtra(ServiceCatalog.GPS);
            }

            if (childSeatCheck.isSelected()) {
                builder.addExtra(ServiceCatalog.CHILD_SEAT);
            }

            if (extraDriverCheck.isSelected()) {
                builder.addExtra(ServiceCatalog.EXTRA_DRIVER);
            }

            PackageItem packageItem = packageCombo.getValue();

            if (packageItem != null && packageItem.item() != null) {
                builder.addExtra(packageItem.item());
            }

            RentalRequest request = builder.build();

            RentalProcessResult result =
                    ApplicationServices.rentalFacade()
                            .registerReservation(
                                    request,
                                    paymentTokenCombo.getValue()
                            );

            refreshReservations();
            resultLabel.setText(result.getMessage());

            Alert.AlertType alertType = result.isSuccess()
                    ? Alert.AlertType.INFORMATION
                    : Alert.AlertType.WARNING;

            showAlert(
                    alertType,
                    result.isSuccess()
                            ? "Reservation confirmed"
                            : "Reservation not confirmed",
                    result.getMessage()
            );

        } catch (IllegalArgumentException exception) {
            resultLabel.setText(exception.getMessage());

            showAlert(
                    Alert.AlertType.WARNING,
                    "Check rental information",
                    exception.getMessage()
            );
        } catch (Exception exception) {
            String message = "No se pudo procesar la reserva: "
                    + exception.getMessage();

            resultLabel.setText(message);

            showAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    message
            );
        }
    }

    private void refreshReservations() {
        reservationTable.getItems().setAll(
                ApplicationServices.rentalFacade().getReservations()
        );
    }

    private void showAlert(
            Alert.AlertType type,
            String title,
            String message) {

        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public record CustomerItem(Customer customer) {
        @Override
        public String toString() {
            return customer.getId() + " - "
                    + customer.getFullName()
                    + (customer.isAuthorized()
                    ? ""
                    : " (not authorized)");
        }
    }

    public record VehicleItem(Vehicle vehicle) {
        @Override
        public String toString() {
            return vehicle.getId() + " - "
                    + vehicle.getDisplayName();
        }
    }

    public record PackageItem(String label, CatalogItem item) {
        @Override
        public String toString() {
            return label;
        }
    }
}

