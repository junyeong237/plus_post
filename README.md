### **플러스 주차 복습과제**

<details>
<summary>12/12 - 회원 가입 API</summary>

- [x]  닉네임, 비밀번호, 비밀번호 확인을 **request**에서 전달받기
- [x]  닉네임은 최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성하기
- [x]  비밀번호는 최소 4자 이상이며, 닉네임과 같은 값이 포함된 경우 회원가입에 실패로 만들기
- [x]  비밀번호 확인은 비밀번호와 정확하게 일치하기
- [x]  데이터베이스에 존재하는 닉네임을 입력한 채 회원가입 버튼을 누른 경우 "중복된 닉네임입니다." 라는 에러메세지를 **response**에 포함하기
- [x]  회원 가입 버튼을 누르기 전, 같은 닉네임이 존재하는지 "확인" 버튼을 눌러 먼저 유효성 검증부터 할 수 있도록 해보기
- [x]  (챌린지 과제) 데이터베이스에 비밀번호를 평문으로 저장하는 것이 아닌, 단방향 암호화 알고리즘을 이용하여 암호화 해서 저장하도록 하기
- [x]  (챌린지 과제) 회원 가입 시, 이메일 혹은 SNS로 인증 번호를 전달 받고 5분 이내에 해당 인증 번호를 검증해야 회원 가입에 성공하도록 해보기 (redis TTL 특징을 좀 더 파악하기 위함.)
  
</details>

<details>
  
<summary>12/13 - 로그인 API</summary>
  
- [x]  닉네임, 비밀번호를 **request**에서 전달받기
- [x]  로그인 버튼을 누른 경우 닉네임과 비밀번호가 데이터베이스에 등록됐는지 확인한 뒤, 하나라도 맞지 않는 정보가 있다면 "닉네임 또는 패스워드를 확인해주세요."라는 에러 메세지를 **response**에 포함하기
- [x]  로그인 성공 시, 로그인에 성공한 유저의 정보를 JWT를 활용하여 클라이언트에게 Cookie로 전달하기
  
</details>
  
<details>
  
<summary>12/14 - 전체 게시글 목록 조회 API</summary>
  
- [x]  제목, 작성자명(nickname), 작성 날짜를 조회하기
- [x]  작성 날짜 기준으로 내림차순 정렬하기
- [x]  (챌린지 과제) 전체 조회가 아닌 페이징 조회를 할 수 있도록 해보기
- [x]  (챌린지 과제) 페이징 + 커스텀 정렬 기능 구현하기 -> 사용자가 입력한 key와 정렬 기준을 동적으로 입력 받아, 해당 기준에 맞게 데이터를 제공. (예. 작성자명 오름차순 정렬 and 작성 날짜 오름차순 정렬된 결과를 상위 5개만 출력)
  
</details>

<details>
  
<summary>12/15 - 게시글 작성 API</summary>
  
- [x]  토큰을 검사하여, 유효한 토큰일 경우에만 게시글 작성 가능
- [x]  제목(500자 까지 입력 가능), 작성 내용을 입력하기(5000자 까지 입력 가능)
- [x]  (챌린지 과제) 이미지 업로드 가능

  
</details>

<details>

<summary>12/18 - 게시글 조회 API</summary>
  
- [x]  제목, 작성자명(nickname), 작성 날짜, 작성 내용을 조회하기 

</details>

<details>

<summary>12/19 - 게시글 수정 API</summary>
  
- [x]  토큰을 검사하여, 해당 사용자가 작성한 게시글만 수정 가능

</details>

<details>
  
<summary>12/20 - 게시글 삭제 API</summary>
  
- [x]  토큰을 검사하여, 해당 사용자가 작성한 게시글만 삭제 가능
- [x]  (챌린지 과제) 수정된지 90일이 지난 데이터는 자동으로 지우는 스케줄러 기능을 개발해보기. (데이터 삭제 및 백업도 굉장히 중요한 기능인데, 수강생들이 이런 내용을 잘 인지하지 못 함.)

</details>


<details>
  
<summary>12/21 - 댓글 작성 API</summary>
  
- [x]  게시글과 연관 관계를 가진 댓글 테이블 추가
- [x]  토큰을 검사하여, 유효한 토큰일 경우에만 게시글 작성 가능
- [x]  작성 내용을 입력하기
- [ ]  게시글에 대한 좋아요

</details>


<details>
  
<summary>12/22 - 게시글과 댓글 목록 조회 API, 댓글 수정/삭제 API</summary>
  
- [x]  댓글 목록 조회
- [x]  게시글 조회 API 호출시 해당 게시글의 댓글 목록도 응답
- [x]  토큰을 검사하여, 해당 사용자가 작성한 댓글만 수정/삭제 가능
- [ ]  (챌린지 과제) 게시글이 삭제될 때 연관된 댓글도 같이 지우도록 스케줄러 코드 기능 추가

</details>

### **심화코드 개선과제**

- [x]  **12/12 -  Controller, Service 패키지 내 클래스 개선**

- [x]  **12/13 - CustomException 정의 및 SpringAOP 적용**

- [x]  **QueryDSL 을 사용하여 검색 기능 만들기**

- [x]  **Pageable 을 사용하여 페이징 및 정렬 기능 만들기**
 
- [x]  **Controller 테스트 코드 작성하기**

- [x]  **Service 테스트 코드 작성하기**

- [x]  **Repository 테스트 코드 작성하기**

- [x]  **12/21 AWS S3 를 이용해 이미지 업로드 기능 구현하기**

- [ ]  **12/22 - AWS EC2 를 이용해 애플리케이션 .jar 파일 배포하기**




