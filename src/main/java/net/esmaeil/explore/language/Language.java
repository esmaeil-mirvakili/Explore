package net.esmaeil.explore.language;

import javafx.geometry.NodeOrientation;

public enum Language {
    ENGLISH("English", NodeOrientation.LEFT_TO_RIGHT),
    PERSIAN("پارسی", NodeOrientation.RIGHT_TO_LEFT);

    private final String localName;
    private final NodeOrientation orientation;

    Language(String localName, NodeOrientation orientation) {
        this.localName = localName;
        this.orientation = orientation;
    }

    public String getLocalName() {
        return localName;
    }

    public NodeOrientation getOrientation() {
        return orientation;
    }
}
