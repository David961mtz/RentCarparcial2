package edu.rentcar.service;

import edu.rentcar.model.Vehicle;
import edu.rentcar.repository.VehicleRepository;

import java.time.Year;
import java.util.List;

public class VehicleService {
    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public int getVehicleCount() {
        return vehicleRepository.count();
    }

    public Vehicle registerVehicle(String brand, String model, String category,
                                   String modelYearText, String dailyRateText) {
        String cleanBrand = requireText(brand, "La marca es obligatoria.");
        String cleanModel = requireText(model, "El modelo es obligatorio.");
        String cleanCategory = requireText(category, "La categoría es obligatoria.");

        int modelYear;
        try {
            modelYear = Integer.parseInt(modelYearText.trim());
        } catch (RuntimeException exception) {
            throw new IllegalArgumentException("El año del modelo debe ser un número válido.");
        }

        int currentYear = Year.now().getValue();
        if (modelYear < 1990 || modelYear > currentYear + 1) {
            throw new IllegalArgumentException(
                    "El año del modelo debe estar entre 1990 y " + (currentYear + 1) + "."
            );
        }

        double dailyRate;
        try {
            dailyRate = Double.parseDouble(dailyRateText.trim());
        } catch (RuntimeException exception) {
            throw new IllegalArgumentException("La tarifa diaria debe ser un número válido.");
        }

        if (!Double.isFinite(dailyRate) || dailyRate <= 0) {
            throw new IllegalArgumentException("La tarifa diaria debe ser mayor que cero.");
        }

        return vehicleRepository.save(
                cleanBrand, cleanModel, cleanCategory, modelYear, dailyRate
        );
    }

    private String requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName);
        }
        return value.trim();
    }
}
