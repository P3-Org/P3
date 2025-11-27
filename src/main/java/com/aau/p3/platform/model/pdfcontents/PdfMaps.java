package com.aau.p3.platform.model.pdfcontents;

import com.aau.p3.climatetool.utilities.RiskAssessment;
import com.aau.p3.platform.model.property.Property;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PdfMaps extends PdfChapter {
    private final double cloudburstMeasured;
    private final String coastalSeverity;
    private final double groundwaterMeasured;
    private final double stormSurgeMeasured;
    private String folderPath;
    private File[] imgFiles;
    List<String> fileNames = new ArrayList<>();

    public PdfMaps(List<RiskAssessment> listOfRisks) {
        this.cloudburstMeasured = listOfRisks.get(0).getMeasurementValue();
        this.coastalSeverity = listOfRisks.get(3).getSeverityString();
        this.groundwaterMeasured = listOfRisks.get(1).getMeasurementValue();
        this.stormSurgeMeasured = listOfRisks.get(2).getMeasurementValue();
    }

    /**
     * Inserts an image into a 2x2 grid on the page.
     *
     * @param document       The PDDocument
     * @param contentStream  The content stream for the current page
     * @param file           The image file to insert
     * @param cellIndex      0..3 representing the cell in the 2x2 grid
     * @throws IOException
     */
    private void insertImage(PDDocument document, PDPageContentStream contentStream, File file, int cellIndex) throws IOException {
        if (file == null || !file.exists()) return;

        PDImageXObject image = PDImageXObject.createFromFile(file.getAbsolutePath(), document);

        // Define page size and margins
        float pageWidth = PDRectangle.A4.getWidth();
        float pageHeight = PDRectangle.A4.getHeight();
        float margin = 50;
        float spacing = 20;

        // Grid layout: 2x2
        float cellWidth = (pageWidth - 2 * margin - spacing) / 2;
        float cellHeight = (pageHeight - 200 - spacing) / 2; // leave space for title

        // Compute row/column from cell index
        int row = cellIndex / 2; // 0 or 1
        int col = cellIndex % 2; // 0 or 1

        float startX = margin + col * (cellWidth + spacing);
        float startY = pageHeight - 200 - row * (cellHeight + spacing); // start below title

        // Scale image to fit cell while keeping aspect ratio
        float scale = Math.min(cellWidth / image.getWidth(), cellHeight / image.getHeight());
        float imgWidth = image.getWidth() * scale;
        float imgHeight = image.getHeight() * scale;

        // Center the image in the cell
        float x = startX + (cellWidth - imgWidth) / 2;
        float y = startY - imgHeight;

        contentStream.drawImage(image, x, y, imgWidth, imgHeight);
    }

    @Override
    public String getTitle() { return "Kort over ejendommen"; }

    @Override
    public void render (PDDocument document, PDPageContentStream contentStream) throws IOException {
        // Write the chapter title
        contentStream.beginText();
        contentStream.setFont(org.apache.pdfbox.pdmodel.font.PDType1Font.HELVETICA_BOLD, 16);
        contentStream.newLineAtOffset(50, 750);
        contentStream.showText(getTitle());
        contentStream.endText();

        // Set the path to the folder containing the images
        folderPath = "src/main/resources/ui/images/pdfMaps";
        File folder = new File(folderPath);
        // Insert all the files into the 'files' array, and sorts alphabetically
        imgFiles = folder.listFiles((dir, name) ->
                name.toLowerCase().matches(".*\\.(png|jpg|jpeg)$")); // filter image files
        if (imgFiles != null) {
            Arrays.sort(imgFiles); // sorts alphabetically by filename
            // If able, add all the names of these to the 'fileNames' list
            for (File file : imgFiles) {
                fileNames.add(file.getName());
            }
        }

        if (fileNames != null) {
            int currentCell = 0;
            for (String name : fileNames) {
                File file = new File(folderPath, name);
                boolean insert = false;

                switch (name) {
                    case "cloudburstMap.png":
                        if (cloudburstMeasured != 999.9) insert = true;
                        break;
                    case "coastalMap.png":
                        if (!coastalSeverity.equals("Ingen risiko")) insert = true;
                        break;
                    case "groundwaterMap.png":
                        insert = true;
                        break;
                    case "stormSurgeMap.png":
                        if (stormSurgeMeasured != 999.9) insert = true;
                        break;

                }

                if (insert && currentCell < 4) {
                    insertImage(document, contentStream, file, currentCell);
                    currentCell++; // move to next cell in the grid
                }
            }
        }
    }
}
