package test;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringWriter;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: pairwinter
 * Date: 13-3-16
 * Time: 上午10:53
 * To change this template use File | Settings | File Templates.
 */
public class ParseExcel {
    public static int rowNum = 482,cellNum = 13;
    public static void main(String argv[]) throws Exception{
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream("e:/songzhen/data.xls"));
        HSSFSheet sheet = workbook.getSheetAt(0);
        HSSFRow row = null;
        List<Company> companies = new ArrayList<Company>();
        for(int i = 1;i<rowNum;i++){
            row = sheet.getRow(i);
            Company company = ParseExcel.parseRow(row, i);
            String boothNum = company.getBoothNum();
            if(boothNum.indexOf("、")>-1){
                boothNum = boothNum.replace("、",",");
            }
            if(boothNum.indexOf(",")>-1){
                String boothNumStr = "";
                String[] boothNums = boothNum.split(",");
                String baseBoothNum = boothNums[0];
                for(int j=0;j<boothNums.length;j++){
                    String boothNum1 =boothNums[j].trim();
                    if(j>=1){
                        if(boothNum1.length()<baseBoothNum.length()){
                            boothNum1 = baseBoothNum.substring(0,baseBoothNum.length()-boothNum1.length()) + boothNum1;
                        }
                        boothNums[j] = boothNum1;
                    }
                    boothNumStr += boothNum1 + (j!=boothNums.length-1?",":"");
//                    Company company1 = new Company();
//                    company1.setBoothNum(boothNum1);
//                    company1.setNameChina(company.getNameChina());
//                    company1.setNameEnglish(company.getNameEnglish());
//                    company1.setTypes(company.getTypes());
//                    company1.setAddress(company.getAddress());
//                    company1.setPostcode(company.getPostcode());
//                    company1.setPhoneNum(company.getPhoneNum());
//                    company1.setFaxNum(company.getFaxNum());
//                    company1.setEmail(company.getEmail());
//                    company1.setWebsite(company.getWebsite());
//                    company1.setSummaryChina(company.getSummaryChina());
//                    company1.setSummaryEnglish(company.getSummaryEnglish());
//                    companies.add(company1);
                }
                company.setBoothNum(boothNumStr);
            }
            companies.add(company);
        }
        System.out.print(companies.size());
        ObjectMapper mapper = new ObjectMapper();
        StringWriter sw = new StringWriter();
        File file = new File("e:/songzhen/data.json");
        file.createNewFile();
        mapper.writeValue(file,companies);
        mapper.writeValue(sw,companies);
        System.out.print(mapper.writerWithDefaultPrettyPrinter().toString());

        Set<String> typeSet = new HashSet<String>();
        String types = "";
        for(Company company : companies){
            if(company.getTypes() !=null){
                for(String type : company.getTypes()){
                    type = type.trim();
                    if(type.length()>0 && !typeSet.contains(type)){
                        typeSet.add(type);
                        types += "<option value='"+type+"'>"+type+"</option>\n";
                    }
                }
            }
        }
        System.out.print(types);
    }

    private static Company parseRow(HSSFRow row,int index) throws Exception{
        Company company = new Company();
        for(int j=1;j<cellNum;j++){
            parseCell(index,company,row.getCell(j),j);
        }
        return company;
    }
    private static void parseCell(int rowNum,Company company,HSSFCell cell,int index) throws Exception{
        String value = "";
        if(cell==null){
            System.out.print("row"+rowNum+",cell"+index+":blank!\n");
        }else{
            if(cell.getCellType() == HSSFCell.CELL_TYPE_STRING){
                value = cell.getStringCellValue();
            }else if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
                int intValue = (int) cell.getNumericCellValue();
                value = intValue+"";
            }
            value = value.trim();
        }
        switch (index){
            case 1:company.setBoothNum(value);
            case 2: company.setNameChina(value);
            case 3: company.setNameEnglish(value);
            case 4:
                String types[] = value.split(";");
                for(int i=0;i<types.length;i++){
                    types[i] = types[i].trim();
                }
                company.setTypes(Arrays.asList(types));
            case 5: company.setAddress(value);
            case 6: company.setPostcode(value);
            case 7: company.setPhoneNum(value);
            case 8: company.setFaxNum(value);
            case 9: company.setEmail(value);
            case 10: company.setWebsite(value);
            case 11: company.setSummaryChina(value);
            case 12: company.setSummaryEnglish(value);
        }
    }

}
