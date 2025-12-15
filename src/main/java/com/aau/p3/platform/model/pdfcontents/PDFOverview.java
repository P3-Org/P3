package com.aau.p3.platform.model.pdfcontents;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class PDFOverview extends PDFChapter {
    String address;

    /**
     * Constructor to create a chapter, making use of the sought (becomes current) property through its address.
     */
    public PDFOverview(String soughtAddress) {
        this.address = soughtAddress;
    }

    /**
     * Method to provide the title of the chapter.
     * @return The title "Overblik".
     */
    @Override
    public String getTitle (){
        return "Overblik";
    }

    /**
     * Method to render the contents of the chapter following the title.
     * @param document The PDDocument in which the following content will be inserted and formatted as instructed.
     * @param contentStream The sequence of instructions on how to construct the pages of the PDF.
     */
    @Override
    public void render(PDDocument document, PDPageContentStream contentStream) throws IOException {
        // Write the chapter title.
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
        contentStream.newLineAtOffset(50, 750);
        contentStream.showText(getTitle());
        contentStream.endText();

        // UTF-8 decode the address stored, for readable display in the document.
        String encodedAddress = this.address;
        String decodedAddress = URLDecoder.decode(encodedAddress, StandardCharsets.UTF_8);

        // Write decoded address for property.
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.newLineAtOffset(50, 720);
        contentStream.showText("Address: " + decodedAddress);
        contentStream.endText();
    }
}
