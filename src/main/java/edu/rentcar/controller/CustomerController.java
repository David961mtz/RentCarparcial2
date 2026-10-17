
package edu.rentcar.controller;

import edu.rentcar.app.ApplicationServices;
import edu.rentcar.model.Customer;
import edu.rentcar.service.CustomerService;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CustomerController {

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, String> customerIdColumn;

    @FXML
    private TableColumn<Customer, String> customerNameColumn;

    @FXML
    private TableColumn<Customer, String> customerEmailColumn;

    @FXML
    private TableColumn<Customer, Boolean> customerAuthorizedColumn;

    @FXML
    private TextField customerNameField;

    @FXML
    private TextField customerEmailField;

    @FXML
    private CheckBox customerAuthorizedCheck;

    private final CustomerService customerService =
            ApplicationServices.customerService();

    @FXML
    public void initialize() {
        customerIdColumn.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        customerNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("fullName"));

        customerEmailColumn.setCellValueFactory(
                new PropertyValueFactory<>("email"));

        customerAuthorizedColumn.setCellValueFactory(
                new PropertyValueFactory<>("authorized"));

        // Cargar los clientes desde el repositorio compartido.
        refreshCustomers();

        // Mostrar los datos del cliente seleccionado en el formulario.
        customerTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldCustomer, selectedCustomer) -> {
                    if (selectedCustomer != null) {
                        customerNameField.setText(
                                selectedCustomer.getFullName());

                        customerEmailField.setText(
                                selectedCustomer.getEmail());

                        customerAuthorizedCheck.setSelected(
                                selectedCustomer.isAuthorized());
                    }
                });
    }

    @FXML
    private void registerCustomer() {
        try {
            customerService.registerCustomer(
                    customerNameField.getText(),
                    customerEmailField.getText(),
                    customerAuthorizedCheck.isSelected()
            );

            refreshCustomers();
            clearCustomerForm();

            showAlert(
                    Alert.AlertType.INFORMATION,
                    "Registro exitoso",
                    "El cliente se registró correctamente."
            );

        } catch (IllegalArgumentException e) {
            showAlert(
                    Alert.AlertType.WARNING,
                    "Datos inválidos",
                    e.getMessage()
            );
        } catch (Exception e) {
            showAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "No se pudo registrar el cliente: " + e.getMessage()
            );
        }
    }

    @FXML
    private void updateCustomer() {
        Customer selectedCustomer =
                customerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            showAlert(
                    Alert.AlertType.WARNING,
                    "Selecciona un cliente",
                    "Selecciona un cliente de la tabla antes de editarlo."
            );
            return;
        }

        try {
            customerService.updateCustomer(
                    selectedCustomer,
                    customerNameField.getText(),
                    customerEmailField.getText(),
                    customerAuthorizedCheck.isSelected()
            );

            refreshCustomers();
            clearCustomerForm();

            showAlert(
                    Alert.AlertType.INFORMATION,
                    "Actualización exitosa",
                    "Los datos del cliente se actualizaron correctamente."
            );

        } catch (IllegalArgumentException e) {
            showAlert(
                    Alert.AlertType.WARNING,
                    "Datos inválidos",
                    e.getMessage()
            );
        } catch (Exception e) {
            showAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "No se pudo actualizar el cliente: " + e.getMessage()
            );
        }
    }

    @FXML
    private void deleteCustomer() {
        Customer selectedCustomer =
                customerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            showAlert(
                    Alert.AlertType.WARNING,
                    "Selecciona un cliente",
                    "Selecciona un cliente de la tabla antes de eliminarlo."
            );
            return;
        }

        try {
            customerService.deleteCustomer(selectedCustomer);

            refreshCustomers();
            clearCustomerForm();

            showAlert(
                    Alert.AlertType.INFORMATION,
                    "Eliminación exitosa",
                    "El cliente se eliminó correctamente."
            );

        } catch (Exception e) {
            showAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "No se pudo eliminar el cliente: " + e.getMessage()
            );
        }
    }

    @FXML
    private void clearCustomerForm() {
        customerTable.getSelectionModel().clearSelection();
        customerNameField.clear();
        customerEmailField.clear();
        customerAuthorizedCheck.setSelected(true);
    }

    private void refreshCustomers() {
        customerTable.getItems().setAll(
                customerService.getAllCustomers()
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
}

