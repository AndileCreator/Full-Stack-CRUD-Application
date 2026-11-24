
const userForm = document.getElementById("userForm");

userForm.addEventListener("submit", async function (event) {

    event.preventDefault();

    // Collect user information
    const userData = {
        userName: document.getElementById("userName").value,
        email: document.getElementById("email").value,
    };

    try {

        // Send data to backend API
        const response = await fetch("/api/users", {
            method: "POST",

            headers: {
                "Content-Type": "application/json",
            },

            body: JSON.stringify(userData),
        });

        const responseText = await response.text();

        console.log("Status:", response.status);
        console.log("Response:", responseText);

        userForm.reset();
        // Check server response
        if (!response.ok) {
            throw new Error(
                `Server error ${response.status}: ${responseText}`

            );

        }

        // Convert response into JSON
        const result = JSON.parse(responseText);

        // Display success message
        document.getElementById("message").innerText =
            "User saved successfully! : " + result.userName;

        console.log(result.userName);

        // Clear registration form
        userForm.reset();

    } catch (error) {

        console.error("Error:", error);

        document.getElementById("message").innerText =
            error.message;
    }
});
