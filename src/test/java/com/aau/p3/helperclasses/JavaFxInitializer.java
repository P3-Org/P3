package com.aau.p3.helperclasses;

import javafx.application.Platform;

public class JavaFxInitializer {
    private static boolean started = false;

    public static void init() {
        if (!started) {
            Platform.startup(() -> {});
            started = true;
        }
    }
}