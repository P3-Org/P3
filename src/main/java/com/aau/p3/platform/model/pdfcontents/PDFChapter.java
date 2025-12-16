package com.aau.p3.platform.model.pdfcontents;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import java.io.IOException;

/**
 * Abstract class that sets guidelines for what a chapter in the PDF should contain.
 * Namely, a title and a render method to configure the chapter contents.
 */
public abstract class PDFChapter {
    public abstract String getTitle();
    public abstract void render(PDDocument document, PDPageContentStream contentStream) throws IOException;
}
