# SNU
## Second Screen 선행 기술을 활용한 웹 앱 개발

### 프로젝트 배경
- 오래 전부터 N스크린에 대한 연구 활동이 활발하게 진행되고 있으나 실제 널리 활용되고 있지 못함
- N스크린 기술이 널리 보급되지 못한 배경에는, 1) 이용하기 어려움, 2) 안정적이지 않음, 3) 호환성 문제 등이 있음
- 기존의 Mobile, TV에 더해 VR, Car, Signage 등의 스크린이 늘어남에 따라 향후 N스크린 기술은 더 중요해질 것으로 전망
- 모든 기기에서 활용 가능한 웹(브라우저) 기술은 위의 문제점들을 해결할 수 있는 잠재력을 지니고 있음

### 프로젝트 목표
- LG Mindless 프레임워크의 특징 및 장점을 잘 살릴 수 있는 웹 애플리케이션 설계 및 개발 (HTML/CSS/JS, Node.js)

### Second Screen
#### W3C Second Screen 표준 기술
- Presentation API
 - 서로 다른 두 기기간 연결이 되어 한 기기의 웹 앱에서 URL을 다른 기기로 보내서 실행시키며 연동
 - Spec: http://w3c.github.io/presentation-api/ 
 - 설명 페이지
   - MDN 튜토리얼 및 간단한 예제: https://developer.mozilla.org/en-US/docs/Web/API/Presentation_API
    - 튜토리얼 (Intel Developer Zone): https://software.intel.com/en-us/html5/hub/blogs/presentation-api-tutorial 
 - 브라우저 지원 현황
    - Chrome: Default로 지원
    - Firefox 개발 진행 중
 - 데모
    - https://googlechrome.github.io/samples/presentation-api/   (정상 동작 확인)
    - https://storage.googleapis.com/presentation-api/index.html  (정상 동작 확인)
  
- Remote Playback API
 - Media Element(<video>)를 웹 앱에서 다른 기기로 보내서 재생시키고 조작 가능
 - Spec: https://w3c.github.io/remote-playback/ 
 - Github: https://github.com/w3c/remote-playback/ 
 - 브라우저 지원 현황
    - Chrome (56 버전부터 지원)
    - Firefox

### LG Mindless 프레임워크
- 사용자가 모바일 브라우저를 열어 주변 기기 화면에 출력된 URL을 입력하기만 하면 바로 기기간 연결 및 앱 실행 완료
- 애플리케이션에서 Mindless 프레임워크에서 제공하는 API를 사용
- Mindless 프레임워크는 웹 앱에서 활용 가능한 JS 라이브러리, 클라우드/주변기기와의 연결을 위한 모듈로 구성 (Node.js)
