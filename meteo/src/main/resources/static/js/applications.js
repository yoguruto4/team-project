/* ===== フィルター機能 ===== */
document.addEventListener("DOMContentLoaded", () => {
  const select = document.getElementById("filterSelect");
  const rows = [...document.querySelectorAll("#applicationTable tbody tr")];

  select.addEventListener("change", () => {
    const value = select.value; // all | pending | rejected
    rows.forEach((tr) => {
      if (value === "all") {
        tr.style.display = "";
      } else {
        tr.style.display = tr.dataset.status === value ? "" : "none";
      }
    });
  });
});

/* ===== ログアウト（必要に応じて編集） ===== */
document.getElementById("logoutBtn")?.addEventListener("click", () => {
  // 認証情報クリア処理があればここで
  window.location.href = "/login.html?logout=1"; // 適宜パスを変更
});
