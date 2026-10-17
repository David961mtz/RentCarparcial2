package edu.rentcar.modality;

public class FixedCoveragePolicy implements CoveragePolicy {
    private final String description;

    public FixedCoveragePolicy(String description) {
        this.description = description;
    }

    @Override
    public String description() {
        return description;
    }
}
