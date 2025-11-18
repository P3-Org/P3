package com.aau.p3.platform.model.pdfcontents;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;

public class PdfOverview extends PdfChapter {
    String address;

    public PdfOverview(String soughtAddress) {
        this.address = soughtAddress;
    }

    @Override
    public String getTitle (){
        return "Overblik";
    }

    @Override
    public void render(PDDocument document, PDPageContentStream contentStream) throws IOException {
        // Write the chapter title
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
        contentStream.newLineAtOffset(50, 750);
        contentStream.showText(getTitle());
        contentStream.endText();

        // Write address info for exported property
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.newLineAtOffset(50, 720);
        contentStream.showText("Address: " + this.address);
        contentStream.endText();
    }

}
