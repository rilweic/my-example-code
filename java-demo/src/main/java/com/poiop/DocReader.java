package com.poiop;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileInputStream;
import java.io.IOException;

public class DocReader {
    public static void main(String[] args) {
        String filePath = "/Users/lichao/IdeaProjects/big-data/java-demo/src/main/resources/tmp2.docx";

        try (FileInputStream fis = new FileInputStream(filePath);
             XWPFDocument document = new XWPFDocument(fis)) {

            for (XWPFParagraph paragraph : document.getParagraphs()) {
                if (isLikelyHeading(paragraph)) {
                    System.out.println("可能的标题: " + paragraph.getText());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static boolean isLikelyHeading(XWPFParagraph paragraph) {
        if (!paragraph.getRuns().isEmpty()) {
            XWPFRun run = paragraph.getRuns().get(0);
            return run.isBold() && run.getFontSize() >= 14; // 根据需要调整这些条件
        }
        return false;
    }
}
