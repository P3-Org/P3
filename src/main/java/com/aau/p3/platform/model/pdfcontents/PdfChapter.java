package com.aau.p3.platform.model.pdfcontents;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import java.io.IOException;

public abstract class PdfChapter {

    // Method that forces all sections to declare a title.
    public abstract String getTitle();

    // Method here, to ensure all subclasses will configure a way to be inserted for document.
    public abstract void render(PDDocument document, PDPageContentStream contentStream) throws IOException;

}
