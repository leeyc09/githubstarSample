# githubstarSample

### 사용 라이브러리
---
  - Android Support 라이브러리
  - guava

 DB
  - Android Architecture 라이브러리 - ROOM

 HTTP
  - retrofit2
  - okhttp3
  - gson

 프레임워크
  - rxjava2
  - rxbinding

  로그
  - timber

  뷰 인젝터
  - butterknife

  이미지
  - fresco

### 프레임워크
---
  - JAVA

### 설계
---
 MVP기반에 Model이 Repository로 확장 되어 있는 구조로 설계되어 있다. 구글 android-architecture의 팩키지 형태를 기반으로 구성되어 있다.

 `Repository` - Data를 관리한다. 구글 아키텍처 기준으로 캐싱정책이 같이 들어가 있는 구조인데 과제에는 캐싱 정책을 빼고 리모트와 로컬만 구분하였다.
 동일한 메소드를 사용하는 형태를 위해 DataSource 인터페이스를 두었다.

 `View` - UI의 처리를 담당한다.

 `Presenter` - 화면에 대한 비즈니스 로직을 처리한다.


 메인 엑티비티 - 탭 레이아웃 - 두개의 프레그먼트로 구성되어 있으며 로컬과 리모트 화면이 큰 차이가 없어 동일한 메소드 명을 사용하기 위해 공통의 인터페이스를 사용하였다.
 통신 후 Data가공 및 이벤트 전달에는 ReativeX가 사용 되었다.



### 향후 개선 사항
---
 - 잘 사용한 RX인가에 대한 고찰 필요
 일부 데이터 싱크를 위해 사용되었는데 접근에 대한 고찰이 필요하다.

 - 테스트코드 작성
 QA팀에서 전담하다보니 테스트 코드 작성에 취약했다. 올해 공부해야할 것 중 하나

 - Dagger2에 대한 이해 및 적용
 정확한 이해 없이 사용하기 싫어서 과감하게 제거 했다. 보일러 플레이트 코드 및 차후 테스트
 작성 용이를 위해서 적용해야

 - 코틀린으로 전환
 null처리, 뷰 인젠션 등 코드를 줄이고 안정성을 위해 코틀린으로 전환이 필요하다.

 - Android Architecture mvvm, livedata, paging의 적용

