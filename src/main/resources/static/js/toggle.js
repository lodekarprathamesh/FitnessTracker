function toggleForm() {
    const loginBox = document.getElementById("login-box");
    const registerBox = document.getElementById("register-box");
    const formTitle = document.getElementById("formTitle");
    const toggleBtn = document.querySelector("button[onclick='toggleForm()']");

    const isLoginVisible = !loginBox.classList.contains("hidden");

    if (isLoginVisible) {
        loginBox.classList.add("hidden");
        registerBox.classList.remove("hidden");
        formTitle.innerText = "Register";
        toggleBtn.innerText = "Switch to Login";
    } else {
        loginBox.classList.remove("hidden");
        registerBox.classList.add("hidden");
        formTitle.innerText = "Login";
        toggleBtn.innerText = "Switch to Register";
    }
}

function checkPassword(){
    const pass = document.getElementById("password");
    const confirm = document.getElementById("confirmPassword");

    if (pass.value != confirm.value) {
        event.preventDefault(); // prevent form submission
        alert("Passwords do not match.");
        return false;
    }

    return true;

}
