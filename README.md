# Lv0 ~ Lv2 설계

---

## API 명세서

|    기능    | Method |         URL         |        request        |      response       |      상태코드       |
|:--------:|:------:|:-------------------:|:---------------------:|:-------------------:|:---------------:|
|  일정 생성   |  POST  |     /api/plans      | CreatePlanRequestDto  |   PlanResponseDto   |  201(created)   |
| 전체 일정 조회 |  GET   |     /api/plans      | GetPlanListRequestDto | PlanListResponseDto |     200(ok)     |
| 선택 일정 조회 |  GET   | /api/plans/{planId} |           -           |   PlanResponseDto   |     200(ok)     |
|  일정 수정   | PATCH  | /api/plans/{planId} | UpdatePlanRequestDto  |   PlanResponseDto   |     200(ok)     |
|  일정 삭제   | DELETE | /api/plans/{planId} | DeletePlanRequestDto  |          -          | 204(no-content) |

---

## ERD 설계

**Plan Table**

|    컬럼명     |     자료형      |            제약조건             |   설명   |
|:----------:|:------------:|:---------------------------:|:------:|
|  plan_id   |    bigint    | PRIMARY KEY, AUTO_INCREMENT | 일정 ID  |
| plan_text  | varchar(500) |          NOT NULL           | 일정 내용  |
| user_name  | varchar(50)  |          NOT NULL           | 사용자 이름 |
|  password  | varchar(50)  |          NOT NULL           |  비밀번호  |
| created_at |   datetime   |          NOT NULL           | 생성 시각  |
| updated_at |   datetime   |          NOT NULL           | 수정 시각  |

[다이어그램 링크](https://dbdiagram.io/d/682b832d1227bdcb4e0637b8)

[schedule.sql](schedule.sql)
---