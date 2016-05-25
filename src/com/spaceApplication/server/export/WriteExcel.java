package com.spaceApplication.server.export;

/**
 * Created by Кристина on 24.03.2016.
 */

import com.spaceApplication.server.sampleModel.model.CableSystemModel;
import com.spaceApplication.server.sampleModel.model.RungeKuttaResult;
import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.*;
import jxl.write.*;
import jxl.write.Number;
import jxl.write.biff.RowsExceededException;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Vector;

public class WriteExcel {

    private WritableCellFormat timesBoldUnderline;
    private WritableCellFormat times;
    private WritableCellFormat caption;
    private String inputFile;
    private String defaultInputFile = "defaultReport.xls";
    private File file;

    public String getInputFile(){
        return this.inputFile;
    }

    public void createFileIfNotExists(String inputFile) {
        this.file = new File(inputFile);

        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("Empty File successfully created");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Failed to create File");

                file = new File(defaultInputFile);
            }
        }
        this.inputFile =file.getPath();
    }

    public void write() throws IOException, WriteException {
        WorkbookSettings wbSettings = new WorkbookSettings();

        wbSettings.setLocale(new Locale("en", "EN"));

        WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
        workbook.createSheet("Report", 0);
        WritableSheet excelSheet = workbook.getSheet(0);
        createLabel(excelSheet);
        createContent(excelSheet);

        workbook.write();
        workbook.close();
    }
    public void writeRungeKuttaResult(RungeKuttaResult result) throws IOException, WriteException {
        int col_start = 0;
        int row_start = 3;
        int col_step =2;

        WorkbookSettings wbSettings = new WorkbookSettings();

        wbSettings.setLocale(new Locale("en", "EN"));
        //wbSettings.setLocale(new Locale("rus", "RUS"));

        WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
        workbook.createSheet("Results of Runge Kutta calculation", 0);
        WritableSheet excelSheet1 = workbook.getSheet(0);
        createLabel(excelSheet1);

        addCaption(excelSheet1, 0, 0, "Results of Runge Kutta calculation");
        addCaption(excelSheet1, col_start, 1, "Results");
        addLabel(excelSheet1, col_start, row_start, "Tetta");
        addOneDoubleVector(excelSheet1, result.getTetta(), col_start, row_start+1);

        col_start +=col_step;
        addLabel(excelSheet1, col_start, row_start, "Omega");
        addOneDoubleVector(excelSheet1, result.getOmega(), col_start, row_start+1);

        col_start +=col_step;
        addLabel(excelSheet1, col_start, row_start, "Eps");
        addOneDoubleVector(excelSheet1, result.getEps(), col_start, row_start+1);

        col_start +=col_step;
        addLabel(excelSheet1, col_start, row_start, "A");
        addOneDoubleVector(excelSheet1, result.getA(), col_start, row_start+1);

        col_start +=col_step;
        addLabel(excelSheet1, col_start, row_start, "Ex");
        addOneDoubleVector(excelSheet1, result.getEx(), col_start, row_start+1);

        col_start +=col_step;
        addLabel(excelSheet1, col_start, row_start, "Accuracy");
        addOneDoubleVector(excelSheet1, result.getAccuracy(), col_start, row_start+1);

        col_start +=col_step;
        addLabel(excelSheet1, col_start, row_start, "Iter");
        addOneIntegerVector(excelSheet1, result.getIter(), col_start, row_start+1);

        col_start +=col_step;
        addLabel(excelSheet1, col_start, row_start, "Time");
        addOneDoubleVector(excelSheet1, result.getTime(), col_start, row_start+1);

        col_start +=col_step;
        addLabel(excelSheet1, col_start, row_start, "Step");
        addOneDoubleVector(excelSheet1, result.getStep(), col_start, row_start+1);

        workbook.write();
        workbook.close();
    }

    public void writeFullInfo(CableSystemModel model, RungeKuttaResult result) throws IOException, WriteException {
        int col_start = 0;
        int row_start = 3;
        int col_step =2;
        int row_first = 1;

        WorkbookSettings wbSettings = new WorkbookSettings();

        wbSettings.setLocale(new Locale("en", "EN"));
        //wbSettings.setLocale(new Locale("rus", "RUS"));

        WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
        workbook.createSheet("Initial Data", 0);


        WritableSheet excelSheet = workbook.getSheet(0);
        createLabel(excelSheet);
        // Write a few headers
        addCaption(excelSheet, col_start, 0, "Cable system characteristics");

        addLabel(excelSheet, col_start, row_first, "Mass1");
        addNumber(excelSheet, col_start, ++row_first, model.getM1());

        addLabel(excelSheet, col_start, ++row_first, "Mass2");
        addNumber(excelSheet, col_start,++row_first, model.getM2());

        addLabel(excelSheet, col_start, ++row_first, "L1");
        addNumber(excelSheet, col_start, ++row_first, model.getL1());

        addLabel(excelSheet, col_start, ++row_first, "L2");
        addNumber(excelSheet, col_start, ++row_first, model.getL2());

        addLabel(excelSheet, col_start, ++row_first, "L_p");
        addNumber(excelSheet, col_start, ++row_first, model.getL_p());

        addLabel(excelSheet, col_start, ++row_first, "I");
        addNumber(excelSheet, col_start, ++row_first, model.getI());

        excelSheet.mergeCells(0, 0, excelSheet.getColumns(), 0);

        workbook.createSheet("Results of Runge Kutta calculation ", 1);
        WritableSheet excelSheet1 = workbook.getSheet(1);
        createLabel(excelSheet1);

        addCaption(excelSheet1, 0, 0, "Results of Runge Kutta calculation");
        addCaption(excelSheet1, col_start, 1, "Results");
        addLabel(excelSheet1, col_start, row_start, "Tetta");
        addOneDoubleVector(excelSheet1, result.getConvertedTetta(), col_start, row_start+1);

        col_start +=col_step;
        addLabel(excelSheet1, col_start, row_start, "Omega");
        addOneDoubleVector(excelSheet1, result.getOmega(), col_start, row_start+1);

        col_start +=col_step;
        addLabel(excelSheet1, col_start, row_start, "Eps");
        addOneDoubleVector(excelSheet1, result.getConvertedEps(), col_start, row_start+1);

        col_start +=col_step;
        addLabel(excelSheet1, col_start, row_start, "A");
        addOneDoubleVector(excelSheet1, result.getA(), col_start, row_start+1);

        col_start +=col_step;
        addLabel(excelSheet1, col_start, row_start, "Ex");
        addOneDoubleVector(excelSheet1, result.getEx(), col_start, row_start+1);

        col_start +=col_step;
        addLabel(excelSheet1, col_start, row_start, "Accuracy");
        addOneDoubleVector(excelSheet1, result.getAccuracy(), col_start, row_start+1);

        col_start +=col_step;
        addLabel(excelSheet1, col_start, row_start, "Iter");
        addOneIntegerVector(excelSheet1, result.getIter(), col_start, row_start+1);

        col_start +=col_step;
        addLabel(excelSheet1, col_start, row_start, "Time");
        addOneDoubleVector(excelSheet1, result.getTime(), col_start, row_start+1);

        col_start +=col_step;
        addLabel(excelSheet1, col_start, row_start, "Step");
        addOneDoubleVector(excelSheet1, result.getStep(), col_start, row_start+1);


        excelSheet1.mergeCells(0, 0,excelSheet1.getColumns(), 0);
        excelSheet1.mergeCells(0, 1,excelSheet1.getColumns(), 1);

        workbook.write();
        workbook.close();
    }

    private void createLabel(WritableSheet sheet)
            throws WriteException {
        // Lets create a times font
        WritableFont times12pt = new WritableFont(WritableFont.TIMES, 12);
        // Define the cell format
        times = new WritableCellFormat(times12pt);
        // Lets automatically wrap the cells
        times.setWrap(true);

        caption = new WritableCellFormat(times12pt);
        // Lets automatically wrap the cells
        caption.setWrap(true);
        caption.setAlignment(Alignment.LEFT);
        caption.setBackground(Colour.LIGHT_BLUE);
        caption.setBorder(Border.BOTTOM, BorderLineStyle.HAIR);

        // create a bold font with underlines
        WritableFont times14ptBoldUnderline = new WritableFont(WritableFont.TIMES, 14, WritableFont.BOLD, false,
                UnderlineStyle.SINGLE);
        timesBoldUnderline = new WritableCellFormat(times14ptBoldUnderline);
        // Lets automatically wrap the cells
        timesBoldUnderline.setWrap(true);
        timesBoldUnderline.setAlignment(Alignment.CENTRE);
        timesBoldUnderline.setBackground(jxl.format.Colour.BLUE_GREY);
        timesBoldUnderline.setBorder(Border.ALL, BorderLineStyle.THIN);

        CellView cv = new CellView();
        cv.setFormat(times);
        cv.setFormat(timesBoldUnderline);
        cv.setFormat(caption);
        cv.setAutosize(true);

    }

    private void createContent(WritableSheet sheet) throws WriteException,
            RowsExceededException {
        // Write a few number
        for (int i = 1; i < 10; i++) {
            // First column
            addNumber(sheet, 0, i, i + 10);
            // Second column
            addNumber(sheet, 1, i, i * i);
        }
        // Lets calculate the sum of it
        StringBuffer buf = new StringBuffer();
        buf.append("SUM(A2:A10)");
        Formula f = new Formula(0, 10, buf.toString());
        sheet.addCell(f);
        buf = new StringBuffer();
        buf.append("SUM(B2:B10)");
        f = new Formula(1, 10, buf.toString());
        sheet.addCell(f);

        // now a bit of text
        for (int i = 12; i < 20; i++) {
            // First column
            addLabel(sheet, 0, i, "Boring text " + i);
            // Second column
            addLabel(sheet, 1, i, "Another text");
        }
    }

    private void addOneDoubleVector(WritableSheet sheet, Vector value, int column, int row) throws WriteException,
            RowsExceededException {
        // Write a few number
        for (int i = 0; i < value.size(); i++) {
            // First column
            addNumber(sheet, column, i+row, (double)value.get(i));
        }
    }

    private void addOneIntegerVector(WritableSheet sheet, Vector value, int column, int row) throws WriteException,
            RowsExceededException {
        // Write a few number
        for (int i = 0; i < value.size(); i++) {
            // First column
            addNumber(sheet, column, i+row, (int)value.get(i));
        }
    }

    private void addCaption(WritableSheet sheet, int column, int row, String s)
            throws RowsExceededException, WriteException {
        Label label;
        label = new Label(column, row, s, timesBoldUnderline);
        sheet.addCell(label);
    }

    private void addNumber(WritableSheet sheet, int column, int row,
                           double value) throws WriteException, RowsExceededException {
        Number number;
        number = new Number(column, row, value, times);
        sheet.addCell(number);
    }

    private void addLabel(WritableSheet sheet, int column, int row, String s)
            throws WriteException, RowsExceededException {
        Label label;
       // label = new Label(column, row, s, times);
        label = new Label(column, row, s, caption);
        sheet.addCell(label);
    }

    public static void main(String[] args) throws WriteException, IOException {

        WriteExcel test = new WriteExcel();

        test.createFileIfNotExists("web/files/report_result.xls");
        test.write();
        System.out.println("Please check the result file under " + test.inputFile);
    }
}