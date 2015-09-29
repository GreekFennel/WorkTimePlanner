package wtp.io;


import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import wtp.model.Activity;
import wtp.model.Project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 29.09.2015.
 */
public class PDFWriter {

    static final float pageMargin = PDPage.PAGE_SIZE_A4.getUpperRightX()/10;

    static final float lowerLeftX = PDPage.PAGE_SIZE_A4.getLowerLeftX()+pageMargin;
    static final float upperRightX = PDPage.PAGE_SIZE_A4.getUpperRightX()-pageMargin;
    static final float lowerLeftY = PDPage.PAGE_SIZE_A4.getLowerLeftY()+pageMargin;
    static final float upperRightY = PDPage.PAGE_SIZE_A4.getUpperRightY()-pageMargin;

    static final float width = upperRightX-lowerLeftX;
    static final float heigth = upperRightY - lowerLeftY;


    static final PDFont font = PDType1Font.HELVETICA;
    static final float textSize = 12;
    static final float realTextSize=9;
    static final float textMargin=4;
    static final float rowHeight =realTextSize+2*textMargin;

    public static void write(Project p, File file) throws IOException {
        PDDocument doc = new PDDocument();
        PDPage page = addPage(doc);

        List<Activity> activityList = new ArrayList<Activity>(p.getActivities());
        activityList.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));

        float borderTop = upperRightY;
        for(Activity activity:activityList){
            borderTop -=rowHeight;
            if (borderTop < (lowerLeftY+rowHeight)){//neue Seite
                drawVerticalLines(page, doc,borderTop);
                page = addPage(doc);
                borderTop = upperRightY - rowHeight;
            }
            drawRow(page,doc,activity,borderTop);
        }
        drawVerticalLines(page,doc,borderTop-rowHeight);

        try {
            doc.save(file);
            doc.close();
        } catch (COSVisitorException e) {
            e.printStackTrace();
        }
    }

    private static void drawHeader(PDPage page, PDDocument document) throws IOException {
        PDPageContentStream cs = new PDPageContentStream(document,page);
        cs.setNonStrokingColor(0,0,0);
        cs.setFont(font, textSize);

        cs.beginText();
        cs.moveTextPositionByAmount(lowerLeftX + textMargin, upperRightY - rowHeight +textMargin);
        cs.drawString("Date");
        cs.moveTextPositionByAmount(width / 3, 0);
        cs.drawString("Description");
        cs.moveTextPositionByAmount(width/3,0);
        cs.drawString("Amount (min)");
        cs.endText();
        cs.close();
    }

    private static PDPage addPage(PDDocument document) throws IOException {
        PDPage page = new PDPage();
        document.addPage(page);
        drawHeader(page,document);
        return page;
    }

    private static void drawRow(PDPage page,PDDocument document, Activity activity, float borderTop) throws IOException {
        PDPageContentStream cs = new PDPageContentStream(document,page, true,true);
        cs.setNonStrokingColor(0,0,0);
        cs.setFont(font, textSize);

        cs.beginText();
        cs.moveTextPositionByAmount(lowerLeftX+textMargin, borderTop - rowHeight + textMargin);
        cs.drawString(activity.getDate().toString());
        cs.moveTextPositionByAmount(width / 3, 0);
        cs.drawString(activity.getDescription());
        cs.moveTextPositionByAmount(width / 3, 0);
        cs.drawString(activity.getDurationInMinutes()+"");
        cs.endText();
        cs.drawLine(lowerLeftX,borderTop,upperRightX,borderTop);
        cs.close();

    }

    private static void drawVerticalLines(PDPage page, PDDocument document, float lowerEnd) throws IOException {
        PDPageContentStream cs = new PDPageContentStream(document, page, true, true);
        cs.setNonStrokingColor(0,0,0);
        cs.setFont(font, textSize);

        cs.drawLine(lowerLeftX+width/3,lowerEnd,lowerLeftX+width/3,upperRightY);
        cs.drawLine(lowerLeftX+width*2/3,lowerEnd,lowerLeftX+width*2/3,upperRightY);
        cs.close();
    }

}
