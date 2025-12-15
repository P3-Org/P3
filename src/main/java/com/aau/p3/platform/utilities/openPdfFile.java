package com.aau.p3.platform.utilities;

import java.awt.*;
import java.io.File;
import java.io.IOException;


public class openPdfFile {
    /**
     * Method that automatically opens the pdf "report.pdf".
     */
    public static void openPdf() {
     if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File("report.pdf");
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                System.out.println("File not found! " + ex);
            }
        }
    }
}
