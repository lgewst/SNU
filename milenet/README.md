## Mindless Service running on Server (Cloud)

### 설명
1. Mindless Framework를 활용한 웹 앱 동작을 서버의 중재를 통해 하려면 서버가 존재해야 합니다.
2. Node.js 환경에서 express, socket.io를 사용해서 구현되어 있습니다.

### 실행 방법

1. Node.js를 설치합니다.
   - 다운 페이지: https://nodejs.org/ko/download/ 
2. 'npm install express', 'npm install socket.io' 명령어 실행을 통해 관련 라이브러리를 설치합니다.
3. 'node milenet' 명령어 실행을 통해 MileNet을 실행합니다.
   - milenet.js가 있는 디렉토리에서 해당 명령을 실행합니다.
4. 해당 MileNet에 연결하기 위해서는 milenet.js의 웹 소켓 연결 주소 수정이 필요합니다.
    - 예시: socket = new io.connect("ws://MILENET URL?id=...);
5. 웹 소켓 연결 주소에는 다음의 get parameters가 입력되어야 그에 따른 URL 발급이 가능합니다.
    - Get Parameters List: id, author, version, appurl
    - e.g. ws://mile.cafe24app.com?id=chat&author=webstd&version=0.1&appurl=http://mileurl.com/demo/

### 링크
1. 직접 Node.js 실행 환경을 구축하지 않아도 아래 주소를 통해 편리하게 MileNet 서비스 활용이 가능합니다.
    - ws://mile.cafe24app.com?id=...
2. MileNet 서비스 연결 및 활용은 웹 앱에서 직접 하지 않고 Mindless Framework에서 해야 합니다.
