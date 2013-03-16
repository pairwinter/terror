package com.pairwinter.test;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;

/**
 * Created with IntelliJ IDEA.
 * User: pairwinter
 * Date: 13-3-16
 * Time: 上午10:53
 * To change this template use File | Settings | File Templates.
 */
public class ParseExcel {
      public static void main(String argv[]) throws Exception{
          HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream("e:/songzhen/data.xls"));
          HSSFSheet sheet = workbook.getSheetAt(0);
          int rowNum = sheet.getLastRowNum();
          HSSFRow row = sheet.getRow(0);
          int cellNum = row.getLastCellNum();
          HSSFCell cell = null;
          StringBuilder stringBuilder = new StringBuilder();
          for(int i = 1;i<482;i++){
              row = sheet.getRow(i);
              for(int j=1;j<13;j++){
                  System.out.print(i+"*"+j+"\n");
                  cell = row.getCell(j);
                  if(null == cell){
                      stringBuilder.append("");
                  }else if(cell.getCellType() == HSSFCell.CELL_TYPE_STRING){
                      stringBuilder.append(row.getCell(j).getStringCellValue());
                  }else if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
                      int value = (int) cell.getNumericCellValue();
                      stringBuilder.append(value);
                  }
                  stringBuilder.append("=========");
                  if(j==cellNum-1){
                      stringBuilder.append("\n");
                  }
              }
          }
          System.out.print(stringBuilder.toString());
      }
}
