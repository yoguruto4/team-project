# Controller 構成一覧（命名は英語、説明は日本語）

## 1. AuthController

- POST `/login`
  - ユーザーのログイン処理（管理者 or 一般）を行い、ロールに応じたダッシュボードにリダイレクト

---

## 2. DashboardController

- GET `/dashboard`
  - ログインユーザーの権限に応じて、ダッシュボード画面を表示（管理者・一般共通）

---

## 3. FormSelectionController

- GET `/form`
  - 申請書の種類（交通費 / 有給 / テンプレート）を選択する画面を表示

---

## 4. TravelExpenseController

- GET `/form/travel-expense`
  - 交通費申請フォームを表示
- POST `/form/travel-expense`
  - 交通費申請内容をデータベースに登録

---

## 5. PaidLeaveRequestController

- GET `/form/paid-leave`
  - 有給申請フォームを表示
- POST `/form/paid-leave`
  - 有給申請内容をデータベースに登録

---

## 6. TravelExpenseTemplateController

- GET `/form/template`
  - 交通費テンプレートの作成画面を表示
- POST `/form/template`
  - テンプレート情報を保存（再利用用）

---

## 7. DocumentDetailController

- GET `/document/{id}`
  - 申請 ID に応じて、申請の詳細情報を表示（申請種別で表示内容を分岐）
- GET `/document/travel-expense/{id}`
  - 指定された交通費申請の詳細を表示
- GET `/document/paid-leave/{id}`
  - 指定された有給申請の詳細を表示

---

## 8. NotificationController

- GET `/notifications`
  - ユーザー宛の通知一覧を表示
- PUT `/notifications/{id}/read`
  - 通知を既読に更新し、該当申請詳細画面に遷移

---

## 9. AdminDocumentController

- GET `/admin/documents`
  - 管理者用の申請書一覧画面を表示
- POST `/admin/documents/travel-expense`
  - 管理者による交通費申請の承認／却下処理
- POST `/admin/documents/paid-leave`
  - 管理者による有給申請の承認／却下処理
- PUT `/admin/documents/{id}/status`
  - 承認ステータス（承認／却下）を更新
