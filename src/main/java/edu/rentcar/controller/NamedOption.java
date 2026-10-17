package edu.rentcar.controller;

public record NamedOption(String code, String label) {
    @Override
    public String toString() {
        return label;
    }
}
