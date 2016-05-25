package com.spaceApplication.server.export;

/**
 * Created by Кристина on 24.03.2016.
 */

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ReadExcel {

    private String inputFile;
    private String defaultInputFile = "defaultReport.xls";
    private File file;

    public String getInputFile(){
        return this.inputFile;
    }

    public void openFileIfExists(String inputFile) {
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

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public HashMap<String, String> readRungeKuttaResult(String fileName) throws IOException {
        openFileIfExists(fileName);
        File inputWorkbook = this.file;
        Workbook w;

        HashMap<String, String> initialData = new HashMap();
        initialData.put("Mass1", null);
        initialData.put("Mass2", null);
        initialData.put("L1", null);
        initialData.put("L2", null);
        initialData.put("L_p", null);
        initialData.put("I", null);
        String key;

        try {
            w = Workbook.getWorkbook(inputWorkbook);
            // Get the first
            Sheet sheet = w.getSheet("Initial Data");
            if (sheet == null){
                sheet = w.getSheet(0);
            }
            // Loop over first 10 column and lines

            for (int j = 0; j < sheet.getColumns(); j++) {
                for (int i = 0; i < sheet.getRows(); i++) {
                    Cell cell = sheet.getCell(j, i);
                    CellType type = cell.getType();
                    if (type == CellType.LABEL) {
                        System.out.println("I got a label "
                                + cell.getContents());

                        if (initialData.containsKey(cell.getContents())){
                            key = cell.getContents();
                            i++;
                            cell = sheet.getCell(j,i);
                            if (cell.getType() == CellType.NUMBER){
                                initialData.put(key, cell.getContents());
                                System.out.println("I got a number "
                                        + cell.getContents());
                            }
                        }
                    }
                }
            }
        } catch (BiffException e) {
            e.printStackTrace();
        }

        return initialData;
    }

    public void read() throws IOException  {
        File inputWorkbook = new File(inputFile);
        Workbook w;
        try {
            w = Workbook.getWorkbook(inputWorkbook);
            // Get the first
            Sheet sheet = w.getSheet(0);
            // Loop over first 10 column and lines

            for (int j = 0; j < sheet.getColumns(); j++) {
                for (int i = 0; i < sheet.getRows(); i++) {
                    Cell cell = sheet.getCell(j, i);
                    CellType type = cell.getType();
                    if (type == CellType.LABEL) {
                        System.out.println("I got a label "
                                + cell.getContents());
                    }

                    if (type == CellType.NUMBER) {
                        System.out.println("I got a number "
                                + cell.getContents());
                    }

                }
            }
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ReadExcel test = new ReadExcel();
        String inputFile = "reportTest1.xls";
        HashMap<String, String> initialData = test.readRungeKuttaResult(inputFile);
    }

}