$("#loginForm").on("submit", function(event) {
    event.preventDefault();

    const signinData = {
        email: $("#email").val(),
        password: $("#password").val()
    };

    // Make AJAX request to backend
    $.ajax({
        url: 'http://localhost:8080/api/v2/auth/signin',  // Adjust URL to match your server
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(signinData),
        success: function(response) {
            // Assuming response contains the JWT token
            if (response.token) {
                // Store token in localStorage (or sessionStorage)
                localStorage.setItem('jwtToken', response.token);

                // Show success alert with SweetAlert
                Swal.fire({
                    icon: 'success',
                    title: 'Login Successful!',
                    text: 'You are now logged in.',
                    //confirmButtonText: 'Proceed',
                    //timer: 2000 // Optionally, set a timer to auto close the alert
                }).then(() => {
                    // Redirect to protected page after the alert closes
                    window.location.href = 'pages/manager/manager-dash.html';
                });

                // Clear the form
                $("#loginForm")[0].reset();
                // getAllCrops();

                // Optionally call LoadAll or other functions to load protected data
                //

            } else {
                // Show error alert if token is not returned
                Swal.fire({
                    icon: 'error',
                    title: 'Error!',
                    text: 'Failed to retrieve token.',
                    confirmButtonText: 'Try Again'
                });
            }
        },
        error: function(xhr, status, error) {
            // Show error alert if AJAX request fails
            Swal.fire({
                icon: 'error',
                title: 'Sign In Failed!',
                text: 'Please check your credentials.',
                confirmButtonText: 'Retry'
            });
        }
    });
});