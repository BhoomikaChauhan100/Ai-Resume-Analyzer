package com.example.ai_resume_analyzer.serviceimpl;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import com.example.ai_resume_analyzer.service.PdfService;

@Service
public class PdfServiceImpl implements PdfService {

    @Override
    public String extractText(String filePath) {

        try {

            File file = new File(filePath);

            PDDocument document = Loader.loadPDF(file);

            PDFTextStripper stripper = new PDFTextStripper();

            String text = stripper.getText(document);

            document.close();

            return text;

        } catch (IOException e) {

            throw new RuntimeException("Unable to read PDF");

        }

    }

}