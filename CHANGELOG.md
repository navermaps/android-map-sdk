# 3.10.1

Release Date: 2020-11-06

### 개선

- 네트워크가 원활하지 않을 때 인증 타임아웃이 지나치게 자주 발생하는 현상 수정

# 3.10.0

Release Date: 2020-10-14

### 새로운 기능

- 콘텐츠 패딩을 포함한 영역을 반환하는 `NaverMap#coveringBounds` / `NaverMap#coveringRegion` 추가

### 개선

- 성능 및 안정성 개선

### 버그 수정

- `MapView`를 윈도우에서 제거한 후 다시 추가하면 크래시가 발생하는 오류 수정
- 콘텐츠 패딩을 변경한 후 `OnCameraIdleListener#onCameraIdle()`이 호출되지 않는 오류 수정
- 지도가 처음 로딩될 때 검은 화면이 나오는 현상 수정
- 카메라를 움직일 때 줌 레벨을 변경하지 않더라도 미세하게 변경되는 현상 수정
- `isHideCollidedSymbols`가 `true`인 마커와 심벌이 겹칠 때 간혹 심벌이 나타나는 현상 수정

# 3.9.1

Release Date: 2020-08-21

### 개선

- 지도에 나타나는 심벌의 밀도 개선

### 버그 수정

- 지도를 클릭하면 카메라 이동이 없어도 `OnCameraIdleListener#onCameraIdle()`이 호출되는 현상 수정

# 3.9.0

Release Date: 2020-07-21

### 새로운 기능

- 마커 보조 캡션의 최소/최대 줌 레벨을 주 캡션과 별도로 지정할 수 있도록 속성 제공
  - `Marker#subCaptionMinZoom` / `Marker#subCaptionMaxZoom`

### 개선

- 타일에 변경이 없으면 `TileCoverHelper.Listener#onTileChanged()`가 호출되지 않도록 개선

### 버그 수정

- 지적편집도 활성화시 지도를 틸트하지 않아도 3D 건물이 보이는 현상 수정
- 스크롤 제스처를 비활성화해도 일부 제스처에 의해 카메라의 위치가 변경되는 현상 수정
- `MapView`가 생성되자마자 `onDestroyView()`가 불리면 크래시가 발생하는 오류 수정

# 3.8.0

Release Date: 2020-04-10

### 새로운 기능

- `FusedLocationSource`가 활성화되어 있는지에 대한 상태 속성 추가
  - `FusedLocationSource#isActivated`
- `FusedLocationSource`가 활성화되기 직전에 후킹하는 기능 추가
  - `FusedLocationSource.ActivationHook`, `FusedLocationSource#activationHook`

### 개선

- 마커간 우선순위가 동일할 경우 아이콘이 먼저 자리를 잡은 후 캡션이 자리를 잡도록 개선
- 마커 캡션의 오프셋에 음수를 지정할 수 있도록 개선
- 카메라 이동이 연속적으로 일어날 때에는 `OnCameraIdleListener#onCameraIdle()`이 호출되지 않도록 개선

### 버그 수정

- `Marker#subCaptionRequestedWidth`를 변경해도 보조 캡션의 너비가 즉시 변경되지 않는 현상 수정
- `LatLngBounds`가 클 때 `CameraUtils.getFittableZoom()` 및 `CameraUpdate.fitBounds()`에 오차가 발생하는 오류 수정
- 인터넷이 오프라인일 때 캐시가 되어있더라도 심벌이 나타나지 않는 현상 수정

# 3.7.1

Release Date: 2020-03-09

### 버그 수정

- `OnCameraIdleListener#onCameraIdle()`이 자주 호출되는 현상 수정

# 3.7.0

Release Date: 2020-01-29

### 새로운 기능

- 지도의 줌 레벨과 오버레이의 최소/최대 줌 레벨이 동일할 때 오버레이를 보일지 여부를 지정하는 기능 추가
  - `Overlay#isMinZoomInclusive` / `Overlay#isMaxZoomInclusive`
- 특정 영역이 화면에 온전히 보이는 최대 줌 레벨을 반환하는 유틸리티 메서드 추가
  - `CameraUtils#getFittableZoom()`

### 개선

- 경로선 진척률을 음수로 지정할 수 있도록 개선
  - `PathOverlay#progress` / `MultipartPathOverlay#progress`

### 버그 수정

- 지도를 터치하면 카메라가 이동하지 않았는데도 `OnCameraIdleListener#onCameraIdle()`이 호출되는 오류 수정
- 카메라의 영역을 제한하고 영역 바깥쪽에서 제스처로 지도를 스크롤하면 카메라가 잘못된 위치로 이동하는 현상 수정

# 3.6.2

Release Date: 2019-12-02

### 버그 수정

- 정보 창을 마커에 열 경우 간헐적으로 지도와 마커 간의 싱크가 맞지 않는 현상 수정
- 경로선이 간헐적으로 심벌 위에 나타나는 현상 수정
- 일부 도로 번호가 비뚤게 나타나는 현상 수정
- 일부 지도 옵션 변경시 `OnOptionChangeListener#onOptionChange()`가 호출되지 않는 오류 수정

# 3.6.1

Release Date: 2019-09-30

### 버그 수정

- 지도 데이터가 올바르게 캐시되지 않는 오류 수정
- `CameraUpdate.Finish`/`CancelCallback` 내에서 `Finish`/`CancelCallback`을 지정하면 무시되는 오류 수정

# 3.6.0

Release Date: 2019-09-20

### 새로운 기능

- 마커 캡션의 최소/최대 줌 레벨을 아이콘과 별도로 지정할 수 있는 기능 추가
  - `Marker#captionMaxZoom`, `captionMinZoom`
- 마커 캡션이 다른 요소와 겹칠 경우 동적으로 다른 곳에 배치하는 옵션 추가
  - `Marker#getCaptionAligns()` / `setCaptionAligns()`
- 마커가 충돌되어 사라지더라도 자신의 영역을 유지하도록 하는 옵션 추가
  - `Marker#isOccupySpaceOnCollision`
- 경로선과 겹치는 마커, 캡션, 지도 심벌을 숨기는 기능 추가
  - `PathOverlay#isHideCollidedSymbols` / `isHideCollidedMarkers` / `isHideCollidedCaptions`
  - `MultipartPathOverlay#isHideCollidedSymbols` / `isHideCollidedMarkers` / `isHideCollidedCaptions`
- 카메라 이동 트랜지션 취소 시 원인을 전달할 수 있도록 `reason` 파라메터 추가
  - `NaverMap#cancelTransitions(int)`

### 개선

- `GroundOverlay`, `InfoWindow`에 더 큰 이미지를 사용할 수 있도록 개선
- 메모리 사용량 개선

### 버그 수정

- `PolylineOverlay`의 `capType`, `joinType`이 적용되지 않는 오류 수정
- `NaverMapOptions#useTextureView(true)` 지정시 크래시가 발생하는 오류 수정
- `onDestroy()`를 부르기 전에 지도 뷰가 윈도우에서 떨어져나갈 경우 ANR이 발생하는 현상 수정
- `PolylineOverlay`에 일부 크랙이 발생하는 현상 수정
- `extent`를 지정하고 지도를 빠르게 패닝하면 지도가 잘못된 방향으로 이동하는 현상 수정
- 마커가 충돌되어 사라지는 중에 충돌하지 않는 곳으로 위치를 변경하면 하면 페이드인이 일어나는 현상 수정
- 지도 회전시 일부 심벌이 번쩍이는 현상 수정
- 지도 뷰의 높이가 작을 때 경로선 패턴이 나타나지 않는 현상 수정
- 높이가 너비보다 큰 이미지를 경로선 패턴으로 사용하면 패턴이 부자연스럽게 나타나는 현상 수정

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
