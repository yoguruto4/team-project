// ログインページのJavaScript
console.log("login.js loaded");

document.addEventListener("DOMContentLoaded", function () {
  // フォーム要素を取得
  const loginForm = document.querySelector("form");
  const emailInput = document.querySelector("#username");
  const passwordInput = document.querySelector("#password");
  const loginButton = document.querySelector(".btn-login");

  // フォーム送信時の処理
  if (loginForm) {
    loginForm.addEventListener("submit", function (e) {
      // ボタンを無効化してダブルクリックを防止
      loginButton.disabled = true;
      loginButton.textContent = "ログイン中...";

      // バリデーション（どちらかが空なら送信しない）
      if (!emailInput.value || !passwordInput.value) {
        e.preventDefault();
        loginButton.disabled = false;
        loginButton.textContent = "ログイン";
        return;
      }
    });
  }

  // 「Enter」キーでログインできるようにする
  if (passwordInput) {
    passwordInput.addEventListener("keypress", function (e) {
      if (e.key === "Enter") {
        loginForm.submit();
      }
    });
  }

  // アラートメッセージの自動フェードアウト（ロード済みでも処理）
  document.querySelectorAll(".alert").forEach((el) => {
    setTimeout(() => (el.style.opacity = "0"), 2000); // 2 秒で透明に
  });

  // ページ内のアラートを 5 秒後にフェードアウトしてから DOM から削除
  const alerts = document.querySelectorAll(".alert");
  alerts.forEach(function (alert) {
    setTimeout(function () {
      alert.style.opacity = "0";
      alert.style.transition = "opacity 0.5s";
      setTimeout(function () {
        alert.style.display = "none";
      }, 500);
    }, 5000); // 5秒後にフェードアウト
  });
});

document.addEventListener("DOMContentLoaded", () => {
  const params = new URLSearchParams(window.location.search);
  if (params.has("logout")) {
    const flash = document.getElementById("flashMessage");
    flash.textContent = "ログアウトしました";
    flash.classList.remove("hidden");
    flash.classList.add("show");

    // 3 秒でフェードアウト → display:none
    setTimeout(() => {
      flash.classList.remove("show");
      setTimeout(() => flash.classList.add("hidden"), 300);
    }, 3000);
  }
});
