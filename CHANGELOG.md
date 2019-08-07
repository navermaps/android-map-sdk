# 3.5.0

Release Date: 2019-08-07

### 새로운 기능

- 배경 지도를 그리지 않는 지도 유형 추가
  - `NaverMap.MapType#None`
- 밝은/어두운 기본 배경색을 각각 상수로 제공
  - `NaverMap#DEFAULT_BACKGROUND_COLOR_LIGHT`, `NaverMap#DEFAULT_BACKGROUND_COLOR_DARK`

### 개선

- `PolygonOverlay`의 외곽선 퀄리티 개선
- 성능 및 데이터 사용량 개선

### 버그 수정

- `PolygonOverlay`의 `holes`를 지정할 경우 일부 영역이 비정상적으로 렌더링되는 오류 수정
- `onMapReady()` 내에서 `locationTrackingMode`를 `Follow`나 `Face`로 지정하면 `NoFollow`로 바뀌는 오류 수정
- 경로의 길이가 매우 긴 경우 `GeometryUtils#getProgress()`의 오차가 큰 오류 수정
- 일부 지도 심벌이 경로선 아래에 나타나는 현상 수정
- 남/북극을 초과하는 영역에 배경색이 적용되지 않는 현상 수정
- 일부 아랍 문자가 노출되지 않는 현상 수정

# 3.4.0

Release Date: 2019-06-07

### 새로운 기능

- 지도가 소멸했는지 여부를 나타내는 속성 추가
  - `NaverMap#isDestroyed`

### 개선

- 성능 및 안정성 개선

### 버그 수정

- `onMapReady()`가 호출되기 전에 액티비티의 상태 저장이 일어날 경우 상태 복구시 크래시가 발생하는 오류 수정
- `MapFragment`가 백스택에 들어있을 경우 지도의 상태가 올바르게 저장되지 않는 오류 수정
- 마커 캡션에 일부 잘못된 이모지가 노출되는 오류 수정
- 액티비티 재시작시 지도 배경이 까맣게 나타나는 현상 수정
- 메모리가 부족할 때 일부 지도 심벌 텍스트가 노출되지 않는 현상 수정

# 3.3.0

Release Date: 2019-04-08

### 새로운 기능

- 유형이 다른 오버레이간의 겹침 우선순위를 지정하는 기능 추가
  - `Overlay#globalZIndex`
- 마커 아이콘에 색상을 덧입히는 기능 추가
  - `Marker#iconTintColor`

### 개선

- 심벌 렌더링 성능 개선

### 버그 수정

- 빌딩 레이어 그룹(`NaverMap#LAYER_GROUP_BUILDING`)이 기본적으로 비활성화되어 있는 오류 수정
- 지도를 터치한 후 지도 뷰 바깥까지 드래그할 경우 카메라가 이상한 위치로 이동하는 오류 수정
- 일부 디바이스에서 지도에 검은 세로줄이 나타나는 오류 수정
- 특정 심벌 피킹시 간혹 크래시가 발생하는 오류 수정

# 3.2.1

Release Date: 2019-02-22

### 개선

- 지도 로딩 속도 및 데이터 사용량 개선

### 버그 수정

- 디바이스 해상도가 `DENSITY_LOW`인 디바이스에서 크래시가 발생하는 오류 수정
- `MapFragment`를 백스택에 넣은 후 액티비티가 백그라운드로 전환되면 크래시가 발생하는 오류 수정
- 액티비티 재시작시 간헐적으로 크래시가 발생하는 오류 수정
- 마커 캡션에 이모지 사용시 간헐적으로 크래시가 발생하는 오류 수정

# 3.2.0

Release Date: 2019-02-14

### 새로운 기능

- `Projection` 인스턴스 없이도 위도, 줌 레벨별 축척을 구할 수 있도록 메서드 추가
  - `Projection#getMetersPerDp()`
- 공공기관용 네이버 클라우드 플랫폼 지원
  - `NaverMapSdk.NaverCloudPlatformGovClient`

### 개선

- 오버레이의 비트맵을 오버레이가 화면에 나타나는 순간에 백그라운드에서 디코딩하도록 개선
- 마커 캡션에 이모지를 넣을 수 있도록 개선

### 버그 수정

- `isHideCollidedSymbol`이 `true`인 마커를 추가/삭제할 때 심벌이 재배치되지 않는 현상 수정
- 줌 변경시 지도 심벌이 겹쳐나오는 현상 수정
- 지도 로딩시 검은 화면이 잠깐 나타났다 사라지는 현상 수정
- 앱이 백그라운드에 있을 때 지도 API를 호출하고 포그라운드로 전환하면 화면이 즉시 갱신되지 않는 오류 수정
- 롤리팝 미만 디바이스에서 `OverlayImage.fromResource()`로 벡터 드로어블을 지정할 경우 예외가 발생하는 오류 수정

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
