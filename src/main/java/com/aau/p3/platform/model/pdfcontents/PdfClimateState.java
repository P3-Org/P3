package com.aau.p3.platform.model.pdfcontents;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.json.JSONArray;

import java.io.IOException;

public class PdfClimateState extends PdfChapter{
    private String comments;

    public PdfClimateState(int overallScore, String comments, Map<String, String> climateFields) {
        super("Klimastand");
        this.overallScore = overallScore;
        this.comments = comments;
        this.climateFields = climateFields;
    }


    @Override
    public String getTitle(){
        return "Klimastand";
    }

    @Override
    public void render(PDDocument document, PDPageContentStream contentStream) throws IOException {

        // Writes the chapter title.
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
        contentStream.newLineAtOffset(50, 750);
        contentStream.showText(getTitle());
        contentStream.endText();

        // Writes the climate data, otherwise shown in the tool tab.
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.newLineAtOffset(50, 750);
        contentStream.showText("Estimeret klimastand: ");
        // If any comments were given, display those
        if (comments != null && !comments.isEmpty()) {
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Comments: " + comments);
        }
        contentStream.endText();
    }

}
