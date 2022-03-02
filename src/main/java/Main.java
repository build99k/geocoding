import dto.ASCenterDto;
import dto.KaKaoAddressDto;
import dto.KaKaoAddressDto.Document;
import excel.ExcelCreator;
import feign.Feign;
import feign.KakaoMapFeign;
import feign.gson.GsonDecoder;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableFeignClients
public class Main {

  public static void main(String[] args) throws IOException {

    KakaoMapFeign kakaoMap = Feign.builder()
                                  .decoder(new GsonDecoder())
                                  .target(KakaoMapFeign.class, "https://dapi.kakao.com");

    //엑셀파일 read 후 위경도 정보 조회
    List<ASCenterDto> dtoList = ExcelCreator.read().stream().peek(dto -> {
      //API 호출
      KaKaoAddressDto kaKaoAddress = kakaoMap.address(dto.getAddress());
      //위경도 셋팅
      Optional<Document> doc = kaKaoAddress.getDocuments().stream().findFirst();
      if (doc.isPresent()) {
        dto.setX(doc.get().getX());
        dto.setY(doc.get().getY());
      }
    }).collect(Collectors.toList());

    //엑셀파일 생성
    ExcelCreator.make(dtoList);
    //dtoList.forEach(System.out::println);
  }
}
