package edu.rentcar.modality;

public class FixedAssistancePolicy implements AssistancePolicy {
    private final String description;

    public FixedAssistancePolicy(String description) {
        this.description = description;
    }

    @Override
    public String description() {
        return description;
    }
}
