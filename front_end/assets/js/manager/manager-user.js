// var token = localStorage.getItem('token');

$(document).ready(function() {

    fetchAllUsers();
    btnRowClickuser();

});


function fetchAllUsers() {
    const token = localStorage.getItem('jwtToken');

    $.ajax({
        url: 'http://localhost:8080/api/v2/manage', // Adjust URL as needed
        type: 'GET',
        contentType: 'application/json',
        headers: {
            "Authorization": `Bearer ${token}`  // Add the JWT token to the Authorization header
        },

        success: function(response) {
            // Log the response to inspect its structure
            console.log(response);  // Check the exact structure of the response

            // Access usersList based on the structure of the response
            const usersList = response.data ? response.data.usersList : response.usersList;
            console.log(usersList)

            if (Array.isArray(usersList)) {  // Ensure it's an array
                for (var f of usersList) {
                    let row = `<tr>
                <td>${f.id}</td>
                <td>${f.email}</td>
                <td>${'MANAGER'}</td>
              
            </tr>`;
                    $("#userTboady").append(row);
                }
            } else {
                console.error("Expected usersList to be an array", response);
            }
        },

        error: function() {
            alert('Error fetching user data.');
        }
    });

    btnRowClickuser();
}



function btnRowClickuser() {

    $('#userTboady').on('click', 'tr', function() {
        const email = $(this).find('td:eq(0)').text(); // Get email from the first cell

        // Populate input fields
        $('#id').val(email);

    });
}

$('#deleteUser').click(function (){
    const token = localStorage.getItem('jwtToken');

    // $('#tbCustomer').empty();
    let uCode = $("#id").val();
    let r=confirm("Are you sure you want to delete this user?");
    $.ajax({
        url:"http://localhost:8080/api/v2/manage?uCode="+uCode,
        method:"DELETE",
        headers: {
            "Authorization": `Bearer ${token}`  // Add the JWT token to the Authorization header
        },
        // data:data ,
        success:function (res){
            if(r){
                console.log(res)
                Swal.fire({
                    icon: 'success',
                    title: 'Removed Successfully',
                    text: ''
                });
                alert("Succes Fully deleted");
                fetchAllUsers();
                clearInputFields();
            }
        },
        error:function (ob,status,t){
            console.log(ob);
            console.log(status);
            console.log(t);
            Swal.fire({
                icon: 'error',
                title: 'canot do that',
                text: ''
            });

        }
    })
});

// Call the remove function to attach the event listener




// Initial fetch of users
function clearInputFields() {
    $('#id').val('');

}




