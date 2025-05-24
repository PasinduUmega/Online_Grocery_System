// 📦 Handles user sign up
export async function registerUser(name, email, password, confirmPassword) {
    if (password !== confirmPassword) {
        alert("Passwords do not match!");
        return;
    }

    const user = {
        name: name,
        email: email,
        password: password
    };

    try {
        const res = await fetch("http://localhost:8080/api/users/register", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(user)
        });

        if (res.ok) {
            alert("Account created successfully!");
            return true;
        } else {
            const msg = await res.text();
            alert("Registration failed: " + msg);
        }
    } catch (err) {
        alert("Error connecting to server.");
        console.error(err);
    }
    return false;
}

// 📦 Handles user login
export async function loginUser(email, password) {
    const credentials = {
        email: email,
        password: password
    };

    try {
        const res = await fetch("http://localhost:8080/api/users/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(credentials)
        });

        if (res.ok) {
            const user = await res.json();
            alert("Welcome, " + user.name);
            // localStorage.setItem("user", JSON.stringify(user)); // Optional
            return user;
        } else {
            const msg = await res.text();
            alert("Login failed: " + msg);
        }
    } catch (err) {
        alert("Error connecting to server.");
        console.error(err);
    }
    return null;
}
  