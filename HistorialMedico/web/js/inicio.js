const deleteTags = (node) => {

    const container = document.querySelector(node);
    const list = Array.from(container.querySelectorAll(".z-label"));
    list.forEach((e) => e.classList.remove("z-label"));
};

const openLogin = () => {
    const login = document.querySelector(".btnLogin");
    const modal = document.querySelector(".login_wrapper");
    const loginContainer = document.querySelector(".login_container_id");
    const forgot = document.querySelector(".forgot");

    const openModal = () => {
        loginContainer.style.display = "flex";
    };

    const closeModal = (e) => {
        if (!modal.contains(e.target) && e.target !== login) {
            loginContainer.style.display = "none";
        }
    };
    login.addEventListener("click", openModal);
    document.addEventListener("click", (e) => closeModal(e));
    forgot.addEventListener("click", () => {
        loginContainer.style.display = "none";
    });
};

const showPassword = () => {
    const password = document.querySelector(".input_password");
    const icon = document.querySelector(".icon_eye");

    const toggleShowPass = () => {
        if (password.type === "password") {
            password.type = "text";
            icon.classList.remove("fa-eye");
            icon.classList.add("fa-eye-slash");
        } else {
            password.type = "password";
            icon.classList.remove("fa-eye-slash");
            icon.classList.add("fa-eye");
        }
    };

    icon.addEventListener("click", toggleShowPass);
};

const stopLoading = () => {
    const loading = document.querySelector(".loading_inicio");
    loading.style.display = "none";
};

const login = () => {
    const loginA = document.querySelector(".op1");
    const loginB = document.querySelector(".op2");
    const empresa = document.querySelector(".empresa");
    const candidato = document.querySelector(".candidato");

    loginA.addEventListener("click", () => {
        empresa.style.display = "block";
        candidato.style.display = "none";
        loginA.classList.add("opSelect");
        loginB.classList.remove("opSelect");
    });
    loginB.addEventListener("click", () => {
        empresa.style.display = "none";
        candidato.style.display = "block";
        loginA.classList.remove("opSelect");
        loginB.classList.add("opSelect");
    });
};




function addcl() {
    let parent = this.parentNode.parentNode;
    parent.classList.add("focus");
}

function remcl() {
    let parent = this.parentNode.parentNode;
    if (this.value == "") {
        parent.classList.remove("focus");
    }
}

(() => {
    zk.afterMount(function () {

//            deleteTags(".login_container"); //inicio.zul
//            showPassword();
//            stopLoading();
        login();
        const inputs = document.querySelectorAll(".input");
        console.log(inputs);
        inputs.forEach(input => {
            input.addEventListener("focus", addcl);
            input.addEventListener("blur", remcl);
        });


    });
})();
