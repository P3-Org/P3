package com.aau.p3.platform.model.pdfcontents;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static com.aau.p3.platform.controller.MainController.getMapPicture;

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

        // UTF-8 decode the address stored, for proper display in report
        String encodedAddress = this.address;
        String decodedAddress = URLDecoder.decode(encodedAddress, StandardCharsets.UTF_8);
        // Write address info for exported property
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.newLineAtOffset(50, 720);
        contentStream.showText("Address: " + decodedAddress);
        contentStream.endText();

        // Map Image
        BufferedImage mapImage = getMapPicture();

        if (mapImage != null) {
            PDImageXObject pdImage = LosslessFactory.createFromImage(document, mapImage);

            float pageWidth = document.getPage(0).getMediaBox().getWidth();
            float margin = 50;
            float availableWidth = pageWidth - 2 * margin;

            float scale = availableWidth / mapImage.getWidth();
            float imageWidth = mapImage.getWidth() * scale;
            float imageHeight = mapImage.getHeight() * scale;


            float x = margin;
            float y = 680 - imageHeight;

            contentStream.drawImage(pdImage, x, y, imageWidth, imageHeight);
        }

    }

}
