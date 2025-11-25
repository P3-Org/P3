package com.aau.p3.platform.model.pdfcontents;

import com.aau.p3.Main;
import com.aau.p3.climatetool.utilities.RiskAssessment;
import com.aau.p3.platform.model.property.Property;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PdfClimateState extends PdfChapter{
    private int climateScore;
    private List<String> comments;
    private List<RiskAssessment> risks;
    private List<String> riskTitles = new ArrayList<>() {{
        add("Skybrud: ");
        add("Grundvand: ");
        add("Stromflod: ");
        add("Kysterosion: ");
    }};

    public PdfClimateState(Property currentProperty) {
        this.climateScore = currentProperty.getClimateScore();
        this.risks = currentProperty.getRisks();
        if (currentProperty.getComments() != null && !currentProperty.getComments().isEmpty()) {
            this.comments = currentProperty.getComments();
        }
    }

    @Override
    public String getTitle(){
        return "Klimastand";
    }

    @Override
    public void render(PDDocument document, PDPageContentStream contentStream) throws IOException {
        // General normal font and size for repeated use
        PDFont font = PDType1Font.HELVETICA;
        float fontSize = 12;
        // Writes the chapter title.
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
        contentStream.newLineAtOffset(50, 750);
        contentStream.showText(getTitle());
        contentStream.endText();

        // Writes the climate state, otherwise shown in the tool tab.
        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        contentStream.newLineAtOffset(50, 720);
        contentStream.showText("Estimeret klimastand: " + climateScore);
        contentStream.newLineAtOffset(0, -15);

        // Writes the descriptions for all the risks
        contentStream.setFont(font, fontSize);
        contentStream.newLineAtOffset(0, -15);
        for (int i = 0; i < riskTitles.size(); i++){
            String firstLinePrefix = riskTitles.get(i);
            contentStream.showText(firstLinePrefix);
            contentStream.newLineAtOffset(0, -15);

            float indent = font.getStringWidth(firstLinePrefix) / 1000 * fontSize;

            contentStream.newLineAtOffset(indent, 0);
            contentStream.showText(risks.get(i).getDescription());
            contentStream.newLineAtOffset(-indent, -15);
            contentStream.newLineAtOffset(0, -15);
        }


        // If any comments were given, display those
        if (comments != null && !comments.isEmpty()) {
            contentStream.newLineAtOffset(0, -15);

            for (String comment : comments) {
                String[] lines = comment.split("\\R"); // Split at newlines given by specialist

                if (lines.length > 0) {
                    // First line with "Kommentar: "
                    String firstLinePrefix = "Kommentar: ";
                    contentStream.showText(firstLinePrefix + lines[0]);
                    contentStream.newLineAtOffset(0, -15);

                    // Calculate dynamic indent for subsequent lines
                    float indent = font.getStringWidth(firstLinePrefix) / 1000 * fontSize;

                    for (int j = 1; j < lines.length; j++) {
                        contentStream.newLineAtOffset(indent, 0); // Move right by indent, then show text
                        contentStream.showText(lines[j]);
                        contentStream.newLineAtOffset(-indent, -15); // Move back to original X, down by line height
                    }
                }
            }
        }
        contentStream.endText();
    }
}
