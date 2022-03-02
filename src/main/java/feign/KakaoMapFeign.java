package feign;

import dto.KaKaoAddressDto;

public interface KakaoMapFeign {

  @RequestLine("GET /v2/local/search/address.json?query={query}")
  @Headers("Authorization: KakaoAK f3e703b92f3d04729dfdd4ac2912309f")
  KaKaoAddressDto address(@Param("query") String query);
}
