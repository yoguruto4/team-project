// ログインページのJavaScript
console.log("login.js loaded");
document.addEventListener('DOMContentLoaded', function() {
    // フォーム要素を取得
    const loginForm = document.querySelector('form');
    const emailInput = document.querySelector('#username');
    const passwordInput = document.querySelector('#password');
    const loginButton = document.querySelector('.btn-login');
    // フォーム送信時の処理
    if (loginForm) {
        loginForm.addEventListener('submit', function(e) {
            // ボタンを無効化してダブルクリックを防止
            loginButton.disabled = true;
            loginButton.textContent = 'ログイン中...';
            // バリデーション
            if (!emailInput.value || !passwordInput.value) {
                e.preventDefault();
                loginButton.disabled = false;
                loginButton.textContent = 'ログイン';
                return;
            }
        });
    }
    // エンターキーでログイン
    if (passwordInput) {
        passwordInput.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                loginForm.submit();
            }
        });
    }
    // アラートメッセージの自動フェードアウト
    window.addEventListener('DOMContentLoaded', () => {
        document.querySelectorAll('.alert').forEach(el => {
            setTimeout(() => el.style.opacity = '0', 2000);
        });
    });
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(function(alert) {
        setTimeout(function() {
            alert.style.opacity = '0';
            alert.style.transition = 'opacity 0.5s';
            setTimeout(function() {
                alert.style.display = 'none';
            }, 500);
        }, 5000); // 5秒後にフェードアウト
    });
});