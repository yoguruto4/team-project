// ログアウト
document.getElementById("logoutBtn")?.addEventListener("click", () => {
  // Spring Security を使う場合は POST で /logout へ
  location.href = "/logout";
});

// 申請書作成
document.getElementById("createDocBtn")?.addEventListener("click", (e) => {
  e.preventDefault();
  location.href = "/document/create";
});

// 申請済みリスト
document.getElementById("completedListBtn")?.addEventListener("click", (e) => {
  e.preventDefault();
  location.href = "/document/completed";
});
