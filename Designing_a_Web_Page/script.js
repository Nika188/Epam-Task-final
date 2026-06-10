 const form = document.getElementById("registrationForm");

form.addEventListener("submit", function (event) {
    event.preventDefault();

    let valid = true;

    document.querySelectorAll(".error").forEach(e => e.textContent = "");
    document.getElementById("successMessage").textContent = "";

    const name = document.getElementById("name").value.trim();
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value;

    if (name.length < 3) {
        document.getElementById("nameError").textContent =
            "Name must be at least 3 characters long.";
        valid = false;
    }

    if (!email.includes("@")) {
        document.getElementById("emailError").textContent =
            "Please enter a valid email address.";
        valid = false;
    }

    if (password.length < 6) {
        document.getElementById("passwordError").textContent =
            "Password must be at least 6 characters long.";
        valid = false;
    }

    if (valid) {
        document.getElementById("successMessage").textContent =
            "Registration successful!";
        form.reset();
    }
});