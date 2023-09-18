package org.example.constant;

public enum ActionType {
    BID("B"), ASK("S");

    private final String shortName;

    ActionType(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }
}
