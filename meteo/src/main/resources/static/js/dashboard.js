// ログアウト処理
document.getElementById("logoutBtn").addEventListener("click", () => {
  alert("ログアウト処理を呼び出しますか？");
  // location.href = '/logout'; // 実際のルーティングに合わせて変更
});

// 各ボタン遷移（プレースホルダー）
document.getElementById("createDocBtn").addEventListener("click", (e) => {
  e.preventDefault();
  alert("申請書作成画面へ遷移します");
  // location.href = '/document/create';
});
document.getElementById("manageDocBtn").addEventListener("click", (e) => {
  e.preventDefault();
  alert("申請書管理画面へ遷移します");
});
document.getElementById("completedListBtn").addEventListener("click", (e) => {
  e.preventDefault();
  alert("申請済みリストへ遷移します");
});
document.getElementById("dashboardBtn").addEventListener("click", (e) => {
  e.preventDefault();
  alert("ダッシュボードへ遷移します");
});
document.getElementById("logoutBtn")?.addEventListener("click", () => {
  // ログイン画面へ遷移し、?logout フラグを付与
  window.location.href = "/login.html?logout=1";
});
