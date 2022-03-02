package dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ASCenterDto {

  private String company_nm;
  private String brand_nm;
  private String center_nm;
  private String type;
  private String address; //주소
  private String contact;
  private String business_hours;
  private String remarks;

  private String x; //경도
  private String y; //위도
}
