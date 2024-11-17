//package com.poiop;
//
//import org.apache.poi.xwpf.usermodel.*;
//import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.math.BigInteger;
//
//public class CreateStyledDocument {
//    public static void main(String[] args) {
//        try (XWPFDocument doc = new XWPFDocument()) {
//            // 添加自定义样式
//            addCustomHeadingStyle(doc, "Heading1", 1, 24, "Calibri");
//            addCustomHeadingStyle(doc, "Heading2", 2, 20, "Calibri");
//            addCustomHeadingStyle(doc, "Heading3", 3, 18, "Calibri");
//
//            // 创建一级标题
//            createStyledParagraph(doc, "Heading1", "一级标题");
//
//            // 创建二级标题
//            createStyledParagraph(doc, "Heading2", "二级标题");
//
//            // 创建三级标题
//            createStyledParagraph(doc, "Heading3", "三级标题");
//
//            // 保存文档
//            try (FileOutputStream out = new FileOutputStream("StyledHeadings.docx")) {
//                doc.write(out);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void addCustomHeadingStyle(XWPFDocument doc, String styleId, int level, int fontSize, String fontName) {
//        XWPFStyles styles = doc.createStyles();
//
//        CTStyle ctStyle = CTStyle.Factory.newInstance();
//        ctStyle.setStyleId(styleId);
//
//        CTString styleName = CTString.Factory.newInstance();
//        styleName.setVal("Heading " + level);
//        ctStyle.setName(styleName);
//
//        CTPPr ppr = CTPPr.Factory.newInstance();
//        CTSpacing spacing = CTSpacing.Factory.newInstance();
//        spacing.setAfter(BigInteger.valueOf(200));  // 设置段后间距
//        spacing.setBefore(BigInteger.valueOf(200)); // 设置段前间距
//        ppr.setSpacing(spacing);
//        ctStyle.setPPr(ppr);
//
//        CTFonts fonts = CTFonts.Factory.newInstance();
//        fonts.setAscii(fontName);
//        fonts.setHAnsi(fontName);
//
//        CTHpsMeasure size = CTHpsMeasure.Factory.newInstance();
//        size.setVal(BigInteger.valueOf(2 * fontSize));  // Word大小以半点为单位
//
//        CTRPr rpr = CTRPr.Factory.newInstance();
//        rpr.setRFonts(fonts);
//        rpr.setSz(size);
//        rpr.setSzCs(size);
//
//        ctStyle.setRPr(rpr);
//
//        XWPFStyle style = new XWPFStyle(ctStyle);  // 注意这里的构造函数调用
//        style.setType(STStyleType.PARAGRAPH);
//        styles.addStyle(style);
//    }
//
//    private static void createStyledParagraph(XWPFDocument doc, String styleId, String text) {
//        XWPFParagraph paragraph = doc.createParagraph();
//        paragraph.setStyle(styleId);
//        XWPFRun run = paragraph.createRun();
//        run.setText(text);
//    }
//}
