package dto;

import java.util.List;
import lombok.Data;

@Data
public class KaKaoAddressDto {

  private Meta meta;
  private List<Document> documents;

  @Data
  public static class Document {

    private Address address;
    private String address_type;
    //private String road_address;
    private String x;
    private String y;

    @Data
    public static class Address {

      private String address_name;
      private String x;
      private String y;
    }
  }

  @Data
  public static class Meta {

    boolean is_end;
    int pageable_count;
  }
}
