package excel;

import dto.ASCenterDto;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelCreator {

  private static String filePath = "C:\\dev";
  private static String fileNm = "address.xlsx";
  private static String fileNewNm = "address_v2.xlsx";

  //엑셀 파일 생성
  public static void make(List<ASCenterDto> dtoList) {
    try (XSSFWorkbook workbook = new XSSFWorkbook()) {
      // 빈 Sheet를 생성
      XSSFSheet sheet = workbook.createSheet("map data");
      XSSFRow row;
      for (int i = 0; i < dtoList.size(); i++) {
        row = sheet.createRow(i);
        row.createCell(0).setCellValue(dtoList.get(i).getCompany_nm());
        row.createCell(1).setCellValue(dtoList.get(i).getBrand_nm());
        row.createCell(2).setCellValue(dtoList.get(i).getCenter_nm());
        row.createCell(3).setCellValue(dtoList.get(i).getType());
        row.createCell(4).setCellValue(dtoList.get(i).getAddress());
        row.createCell(5).setCellValue(dtoList.get(i).getContact());
        row.createCell(6).setCellValue(dtoList.get(i).getBusiness_hours());
        row.createCell(7).setCellValue(dtoList.get(i).getRemarks());
        row.createCell(8).setCellValue(dtoList.get(i).getY());
        row.createCell(9).setCellValue(dtoList.get(i).getX());
      }

      createFile(workbook);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static List<ASCenterDto> read() throws IOException {
    return read(filePath, fileNm);
  }

  //엑셀 파일 조회
  public static List<ASCenterDto> read(String filePath, String fileNm) throws IOException {
    FileInputStream file = new FileInputStream(new File(filePath, fileNm));
    // 엑셀 파일로 Workbook instance를 생성한다.
    XSSFWorkbook workbook = new XSSFWorkbook(file);
    // workbook의 첫번째 sheet를 가저온다.
    XSSFSheet sheet = workbook.getSheetAt(0);

    List<ASCenterDto> list = new ArrayList<>();
    for (Row row : sheet) {
      ASCenterDto dto = ASCenterDto.builder()
                                   .company_nm(getValue(row, 0))
                                   .brand_nm(getValue(row, 1))
                                   .center_nm(getValue(row, 2))
                                   .type(getValue(row, 3))
                                   .address(getValue(row, 4))
                                   .contact(getValue(row, 5))
                                   .business_hours(getValue(row, 6))
                                   .remarks(getValue(row, 7))
                                   .build();
      if (row.getRowNum() == 0) {
        dto.setX("경도");
        dto.setY("위도");
      }

      list.add(dto);
    }
    return list;
  }

  private static String getValue(Row row, int index) {
    return String.valueOf(row.getCell(index));
  }

  private static void createFile(XSSFWorkbook workbook) {
    File file = new File(filePath, fileNewNm);
    try (FileOutputStream fos = new FileOutputStream(file)) {
      workbook.write(fos);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
