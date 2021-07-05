package gst.data.master;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Iterator;

public class Main {
    int cName;
    int gstnum;
    int taxableAmt;
    int sgst;
    int cgst;
    int igst;
    int totalAmt;
    int billAmt;

    public static void main(String[] args)  throws IOException  {
//        Main obj = new Main();
//        obj.assignCellNum();
//        File file = new File("TEXT1.xlsx");   //creating a new file instance
//        obj.calculateFile(file);

    }

    private void assignCellNum() {
        cName = 1;
        gstnum = 2;
        taxableAmt = 6;
        sgst = 7;
        cgst = 8;
        igst = 9;
        totalAmt = 10;
        billAmt = 11;
    }



    File calculateFile(File file) throws IOException {
        String filename = "Final.xls";
        int i = 0;
        short rowSr = 0;
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet outSheet = workbook.createSheet("Sheet1");
        String currGstNum = "", currCompanyName = "";
        Double currTaxableAmt = 0.0, currSgst = 0.0, currCgst = 0.0, currIgst = 0.0, currTotalAmt = 0.0, currBillAmt = 0.0;
        FileInputStream fis = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet = wb.getSheetAt(0);//creating a Sheet object to retrieve object
        Iterator<Row> itr = sheet.iterator();
        while (itr.hasNext()) {
            Row row = itr.next();
            if (i == 0) {
                i++;
                continue;
            }
            int size = row.getPhysicalNumberOfCells();
            if (currGstNum == "") {
                currGstNum = row.getCell(gstnum).toString();
                currTaxableAmt = Double.parseDouble(row.getCell(taxableAmt).toString());
                currSgst = Double.parseDouble(row.getCell(sgst).toString());
                currCgst = Double.parseDouble(row.getCell(cgst).toString());
                currIgst = Double.parseDouble(row.getCell(igst).toString());
                currTotalAmt = Double.parseDouble(row.getCell(totalAmt).toString());
                currBillAmt = Double.parseDouble(row.getCell(billAmt).toString());
            } else if (currGstNum.equals(row.getCell(gstnum).toString())) {
                currCompanyName = row.getCell(cName).toString();
                currTaxableAmt += Double.parseDouble(row.getCell(taxableAmt).toString());
                currSgst += Double.parseDouble(row.getCell(sgst).toString());
                currCgst += Double.parseDouble(row.getCell(cgst).toString());
                currIgst += Double.parseDouble(row.getCell(igst).toString());
                currTotalAmt += Double.parseDouble(row.getCell(totalAmt).toString());
                currBillAmt += Double.parseDouble(row.getCell(billAmt).toString());
            } else {
                Object[] data = new Object[]{String.valueOf(rowSr), currCompanyName, currGstNum, String.format("%.2f",currTaxableAmt), String.format("%.2f",currSgst), String.format("%.2f",currCgst),
                        String.format("%.2f",currIgst), String.format("%.2f",currTotalAmt), String.format("%.2f",currBillAmt)};
                XSSFRow rowhead = outSheet.createRow(rowSr);
                rowSr++;
                int cellid = 0;
                for (Object obj : data) {
                    Cell cell = rowhead.createCell(cellid++);
                    cell.setCellValue((String) obj);
                }
                FileOutputStream fileOut = new FileOutputStream(new File(filename));
                workbook.write(fileOut);
                fileOut.close();

            }
        }
        return new File(filename);
    }
}



