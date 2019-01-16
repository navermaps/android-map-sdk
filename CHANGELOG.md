# 3.1.0

Release Date: 2019-01-16

### 새로운 기능

- 마커 아이콘과 다른 마커 간 겹침시 숨김 옵션 추가
  - `Marker#isHideCollidedMarkers`
  - `Marker#isForceShowIcon`
- 특정 실내지도 뷰를 노출하도록 요청하는 기능 추가
  - `NaverMap#requestIndoorView()`
- `FusedLocationSource`에 사용자의 마지막 위치를 반환하는 메서드 추가
  - `FusedLocationSource#getLastLocation()`
- 사용자의 위치 변경에 대한 이벤트 리스너 추가
  - `NaverMap.OnLocationChangeListener`
  - `NaverMap#addOnLocationChangeListener()`, `NaverMap#removeOnLocationChangeListener()`
- 지도 컨트롤을 커스텀하게 배치할 수 있도록 기본 컨트롤을 API로 제공
  - `com.naver.maps.map.widget` 패키지
- 네이버 로고 위치 변경 기능 제공
  - `UiSettings#logoGravity`, `NaverMapOptions#logoGravity()`
  - `UiSettings#setLogoMargin()`, `NaverMapOptions#logoMargin()`
- 경로선에서 특정한 좌표에 가장 근접한 지점의 진척률을 반환하는 유틸리티 메서드 추가
  - `GeometryUtils#getProgress()`
- 콘텐츠 패딩을 `NaverMap` 객체 생성 전에 지정 가능하도록 `NaverMapOptions`에 옵션 추가
  - `NaverMapOptions#contentPadding()`
- 렌더링 FPS 제한 기능 추가
  - `NaverMap#fpsLimit`, `NaverMapOptions#fpsLimit()`

### 개선

- 메인 스레드가 아닌 다른 스레드에서 오버레이의 메서드 호출시 즉시 예외가 발생하도록 변경
- `@Nullable`인 오버레이 게터/세터 일부의 널 가능성 애너테이션을 `@NonNull`로 변경
  - 파라메터 타입을 `@NonNull`로 변경: `Marker#setCaptionText()`, `Marker#setSubCaptionText()` 
  - 반환 타입을 `@NonNull`로 변경: `GroundOverlay#getImage()`, `InfoWindow#getAdapter()` 
- 마커 캡션으로 잘못된 문자를 지정하더라도 크래시가 발생하지 않도록 개선
- `CircleOverlay`의 테두리 퀄리티 개선
- SDK 내에서 사용하는 문자열에 다국어 대응 추가
- SDK 사용 인증시 인터넷 연결이 되지 않을 경우 인증 실패로 간주하지 않고 연결을 기다리도록 개선

### 버그 수정

- `InfoWindow#open()`을 여러 마커에 대해 호출시 크래시가 발생하는 오류 수정
- `MapType`이 `Satellite`일 때 지상, 폴리라인, 폴리곤, 경로선 오버레이가 나타나지 않는 오류 수정
- 폴리곤 오버레이의 `holes`를 먼저 지정하고 `coords`를 나중에 지정하면 내부 홀이 그려지지 않는 오류 수정
- 액티비티 재시작시 콘텐츠 패딩이 보존되지 않는 오류 수정
- 간헐적으로 일부 심벌 캐릭터가 이상한 문자로 노출되는 현상 수정
- 줌 레벨 변경시 심벌 사이즈가 부자연스럽게 커졌다 작아지는 현상 수정
- 마커 추가시 페이드 인 애니메이션이 무조건 발생하는 현상 수정
- 일부 구형 디바이스에서 지도 배경이 깨지는 현상 수정

# 3.0.0

Release Date: 2018-11-12
