# Lv0 ~ Lv2 설계

---

## API 명세서

|    기능    | Method |         URL         |      request       |  response   |      상태코드       |
|:--------:|:------:|:-------------------:|:------------------:|:-----------:|:---------------:|
|  일정 생성   |  POST  |     /api/plans      | CreatePlanRequest  |   PlanDto   |  201(created)   |
| 전체 일정 조회 |  GET   |     /api/plans      | GetPlanListRequest | PlanListDto |     200(ok)     |
| 선택 일정 조회 |  GET   | /api/plans/{planId} |   GetPlanRequest   |   PlanDto   |     200(ok)     |
|  일정 수정   | PATCH  | /api/plans/{planId} | UpdatePlanRequest  |   PlanDto   |     200(ok)     |
|  일정 삭제   | DELETE | /api/plans/{planId} | DeletePlanRequest  |      -      | 204(no-content) |

---

## ERD 설계

**Plan Table**

|    컬럼명     |   자료형    |            제약조건             |   설명   |
|:----------:|:--------:|:---------------------------:|:------:|
|  plan_id   |   int    | PRIMARY KEY, AUTO_INCREMENT | 일정 ID  |
| plan_text  | varchar  |          NOT NULL           | 일정 내용  |
| user_name  | varchar  |          NOT NULL           | 사용자 이름 |
|  password  | varchar  |          NOT NULL           |  비밀번호  |
| created_at | datetime |          NOT NULL           | 생성 시각  |
| updated_at | datetime |          NOT NULL           | 수정 시각  |

[다이어그램 링크](https://dbdiagram.io/d/682b832d1227bdcb4e0637b8)

[schedule.sql](schedule.sql)
---