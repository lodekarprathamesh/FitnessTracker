

function toggleForm() {
    const loginBox = document.getElementById("login-box");
    const registerBox = document.getElementById("register-box");
    const formTitle = document.getElementById("formTitle");
    // const toggleBtn = document.querySelector("button[onclick='toggleForm()']")
    const change = document.getElementById("change");


    const isLoginVisible = !loginBox.classList.contains("hidden");

    if (isLoginVisible) {
        loginBox.classList.add("hidden");
        registerBox.classList.remove("hidden");
        formTitle.innerText = "Register";
        change.style.display = "none";
    } else {
        loginBox.classList.remove("hidden");
        registerBox.classList.add("hidden");
        formTitle.innerText = "Login";
        change.style.display = "flex";
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

document.getElementById("loginForm").addEventListener("submit", (event)=>{

    event.preventDefault();
    document.querySelector(".spinner-border").classList.remove('hidden');
    document.querySelector(".spinner-border").style.display = "inline-block";
    let btn = document.getElementById("loginBtn");
    btn.disabled = true;
    document.getElementById("btnText").innerText='';

    setTimeout(()=>{
        document.querySelector(".spinner-border").style.display = "none";
        btn.disabled = false;
        document.getElementById("btnText").innerText='Login';
        event.target.submit();
    },1000);

})

document.getElementById("registerForm").addEventListener("submit", (event)=>{

    event.preventDefault();

    const password = document.getElementById("password").value;
    const confirm = document.getElementById("confirmPassword").value;

    if(password !== confirm) {
        alert("Passwords do not match.");
        return;
    }


    document.querySelector(".regisSpin").style.display = "inline-block";
    let btn = document.getElementById("regisBtn");
    btn.disabled = true;
    document.getElementById("btnTextSub").innerText='';

    setTimeout(()=>{
        document.querySelector(".regisSpin").style.display = "none";
        btn.disabled = false;
        document.getElementById("btnTextSub").innerText="submit";
        event.target.submit();

    },1000);

})





