# 도전 기능 설계

---

## API 명세서

|      기능      | Method |         URL         |       request        |      response       |      상태코드       |
|:------------:|:------:|:-------------------:|:--------------------:|:-------------------:|:---------------:|
| 사용자 생성(회원가입) |  POST  | /api/users/register | CreateUserRequestDto | RegisterResponseDto |     200(ok)     |
|     로그인      |  POST  |  /api/users/login   |   LoginRequestDto    |          -          | 204(no-content) |
|     로그아웃     |  POST  |  /api/users/logout  |          -           |          -          | 204(no-content) |
| 본인 사용자 정보 조회 |  GET   |     /api/users      |  세션 활용, 로그인된 사용자 식별  |   UserResponseDto   |     200(ok)     |
|  사용자 정보 변경   | PATCH  |     /api/users      | UpdateUserRequestDto |   UserResponseDto   |     200(ok)     |
|  사용자 삭제(탈퇴)  | DELETE |      api/users      | DeleteUserRequestDto |          -          | 204(no-content) |
|    일정 생성     |  POST  |     /api/plans      | CreatePlanRequestDto |   PlanResponseDto   |  201(created)   |
|   전체 일정 조회   |  GET   |     /api/plans      |          -           | PlanListResponseDto |     200(ok)     |
| 특정 사용자 일정 조회 |  GET   |    /api/plans/me    |          -           | PlanListResponseDto |     200(ok)     |
|   선택 일정 조회   |  GET   | /api/plans/{planId} |          -           |   PlanResponseDto   |     200(ok)     |
|    일정 수정     | PATCH  | /api/plans/{planId} | UpdatePlanRequestDto |   PlanResponseDto   |     200(ok)     |
|    일정 삭제     | DELETE | /api/plans/{planId} | DeletePlanRequestDto |          -          | 204(no-content) |

---

## ERD 설계

**Plans Table**

|    컬럼명     |     자료형      |                           제약조건                           |     설명     |
|:----------:|:------------:|:--------------------------------------------------------:|:----------:|
|  plan_id   |    bigint    |               PRIMARY KEY, AUTO_INCREMENT                |   일정 ID    |
| plan_text  | varchar(500) |                         NOT NULL                         |   일정 내용    |
| anonymity  |   boolean    |                       DEFAULT true                       |   익명 여부    |
| created_at |   datetime   |                         NOT NULL                         |   생성 시각    |
| updated_at |   datetime   |                         NOT NULL                         |   수정 시각    |
|  user_id   |    bigint    | FK(user_id) REFERENCES users(user_id) ON DELETE SET NULL | 사용자 ID 외래키 |

**Users Table**

|    컬럼명     |     자료형     |            제약조건             |   설명   |
|:----------:|:-----------:|:---------------------------:|:------:|
|  user_id   |   bigint    | PRIMARY KEY, AUTO_INCREMENT | 사용자 ID |
| user_name  | varchar(50) |          NOT NULL           | 사용자 이름 |
|   email    | varchar(50) |          NOT NULL           |  이메일   |
|  password  | varchar(50) |          NOT NULL           |  비밀번호  |
| created_at |  datetime   |          NOT NULL           | 생성 시각  |
| updated_at |  datetime   |          NOT NULL           | 수정 시각  |

[다이어그램 링크](https://dbdiagram.io/d/682f7792b9f7446da3bf79e4)

[schedule.sql](schedule.sql)
---