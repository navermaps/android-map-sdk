# NAVER Map Android SDK Demo

## How To Run

- 네이버 지도 SDK를 사용하기 위해서는 네이버 클라우드 플랫폼에서 클라이언트 ID를 발급받고, 발급받은 ID를 SDK에 지정해야 합니다.
- 클라이언트 ID 발급
  1. [네이버 클라우드 플랫폼](https://www.ncloud.com)에 로그인한 후 [콘솔](https://console.ncloud.com)에 들어갑니다.
  2. **Services**에서 **Application Services** 하위의 **Maps**를 선택합니다.
  3. **Application 등록**을 선택하고 **API 선택** 하위의 **Dynamic Map**을 체크합니다.
  4. Android 앱 패키지 이름에 네이버 지도 SDK를 사용하고자 하는 앱의 패키지명을 추가하고 등록합니다.
  5. 등록한 애플리케이션의 인증 정보를 선택해 키 ID를 확인합니다.
- 클라이언트 ID 지정
  - [`ncp_key_id.xml`](app/src/main/res/values/ncp_key_id.xml)의 `naver_map_sdk_ncp_key_id`에 발급받은 클라이언트 ID를 입력합니다.
- 자세한 내용은 [시작하기](https://navermaps.github.io/android-map-sdk/guide/1.html)를 참고하십시오.

## Reference

- [개발 가이드](https://navermaps.github.io/android-map-sdk/)
- [API 레퍼런스](https://navermaps.github.io/android-map-sdk/reference/)
- [Changelog](https://github.com/navermaps/android-map-sdk/blob/master/CHANGELOG.md)

## License

Copyright (c) 2018-2021 NAVER Corp.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
