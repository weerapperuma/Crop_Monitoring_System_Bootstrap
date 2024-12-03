
// var token = localStorage.getItem('token');

let baseUrlStaff='http://localhost:8080/api/v2/staff';
let baseUrlVehicles='http://localhost:8080/api/v2/vehicles'

/*getAllStaff();
loadAllfiledCodes();
loadAllVCodes();
getNextStaffCode();
btnRowClickStaff();*/

$(document).ready(function() {
    getAllStaff();
    loadAllfiledCodes();
    loadAllVCodes();
    getNextStaffCode();
    btnRowClickStaff();
});



function getNextStaffCode(){
    const token = localStorage.getItem('jwtToken');
    $.ajax({
        url:baseUrlStaff+'/nextId',
        method:'GET',
        contentType: 'application/json',
        headers: {
            "Authorization": `Bearer ${token}`  // Add the JWT token to the Authorization header
        },

        success: function(resp){
            console.log(resp);
            $('#staffId1').val(resp)
        }
    });
}


/*function getNextStaffCode(){
    const token = localStorage.getItem('jwtToken');
    console.log("Token:", token); // Add this line to verify token

    if (!token) {
        console.error("No JWT token found. Please log in.");
        return; // Exit if no token
    }

    $.ajax({
        url: baseUrlStaff+'/nextId',
        method: 'GET',
        contentType: 'application/json',
        headers: {
            "Authorization": `Bearer ${token}`
        },
        success: function(resp){
            console.log("Next Staff ID Response:", resp);
            $('#staffId1').val(resp)
        },
        error: function(xhr, status, error) {
            console.error("Error fetching next staff ID:", error);
            console.log("XHR Status:", status);
            console.log("XHR Response:", xhr.responseText);
        }
    });
}*/

function getAllStaff() {
    const token = localStorage.getItem('jwtToken');

    $("#staffTboady").empty();
    $.ajax({
        url: baseUrlStaff,
        method: "GET",
        headers: {
            "Authorization": `Bearer ${token}`  // Add the JWT token to the Authorization header
        },

        success: function (res) {
            console.log(res);
            for (var r of res.data) {
                let row = `<tr>
                    <th>${r.staffId}</th>
                    <td>${r.firstName}</td>
                    <td>${r.lastName}</td>
                    <td>${r.designation}</td>
                    <td>${r.gender}</td>
                    <td>${r.joinedDate}</td>
                    <td>${r.dob}</td>
                    <td>${r.addressLine1}</td>
                    <td>${r.addressLine2}</td>
                    <td>${r.addressLine3}</td>
                    <td>${r.addressLine4}</td>
                    <td>${r.addressLine5}</td>
                    <td>${r.contactNo}</td>
                    <td>${r.email}</td>
                    <td>${r.members}</td>
                     <td>${r.fieldCode}</td>
                     <td>${r.vcode}</td>
                    
                    </tr>`;
                $("#staffTboady").append(row);

            }
        }

    });

}


$('#savestaffbtn').click(function() {
    // Create a JSON object from the form data
    const token = localStorage.getItem('jwtToken');
    var formData = $("#staffForm").serializeArray();
    var data = {};
    $(formData).each(function(index, obj) {
        data[obj.name] = obj.value;
    });

    console.log(data);
    // console.log('Token:', token);

    $.ajax({
        url: baseUrlStaff, // Make sure this matches your controller endpoint
        method: "POST",
        contentType: 'application/json', // Specify content type
        headers: {
            "Authorization": `Bearer ${token}`  // Add the JWT token to the Authorization header
        },

        data: JSON.stringify(data), // Convert data to JSON string
        success: function(res) {


            Swal.fire({
                icon: 'success',
                title: 'Saved Successfully',
                text: res.text
            });
            getAllStaff();
            clearFieldsStaff();
            // clearFeilds();
        },
        error: function(ob, txtStatus, error) {
            alert(txtStatus);
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: ob.responseText // Show the error response text
            });
        }
    });
});

function loadAllfiledCodes() {
    $("#fieldCode1").empty();
    const token = localStorage.getItem('jwtToken');
    // return new Promise(function (resolve, reject) {
    var Cus = '';
    $.ajax({
        url: "http://localhost:8080/api/v2/field",
        method: "GET",
        dataType: "json",//please convert the response into jason
        headers: {
            "Authorization": `Bearer ${token}`  // Add the JWT token to the Authorization header
        },

        success: function (resp) {

            for (const customer of resp.data) {
                $("#fieldCode1").empty();
                Cus += '<option value="' + customer.fieldCode + '">' + customer.fieldCode+ '</option>';

                console.log(typeof resp);
                $("#fieldCode1").append(Cus);
            }
            //  btnRowClick();
            //rowBack();
        }
    });

}


$('#deletestaffbtn').click(function (){

    let staffId = $("#staffId1").val();
    const token = localStorage.getItem('jwtToken');
    $.ajax({
        url:"http://localhost:8080/api/v2/staff?sCode="+staffId,
        method:"DELETE",
        headers: {
            "Authorization": `Bearer ${token}`  // Add the JWT token to the Authorization header
        },

        success:function (res) {
            console.log(res)
            getAllStaff();
            clearFieldsStaff();


            Swal.fire({
                icon: 'success',
                title: 'Delete Successfully',
                text: res.text
            });


        },
        error:function (ob,status,t){
            console.log(ob);
            console.log(status);
            console.log(t);

        }
    })
});

$('#updatestaffbtn').click(function() {
    // Create a JSON object from the form data
    var formData = $("#staffForm").serializeArray();
    const token = localStorage.getItem('jwtToken');
    var data = {};
    $(formData).each(function(index, obj) {
        data[obj.name] = obj.value;
    });

    console.log('Data to update:', data);
    //  console.log('Token:', token);

    $.ajax({
        url: baseUrlStaff+'/update', // Update endpoint URL
        method: "PUT",
        contentType: 'application/json', // Specify content type
        data: JSON.stringify(data), // Convert data to JSON string
        headers: {
            "Authorization": `Bearer ${token}`  // Add the JWT token to the Authorization header
        },
        success: function(res) {
            Swal.fire({
                icon: 'success',
                title: 'Updated Successfully',
                text: 'Staff details updated successfully'
            });
            getAllStaff(); // Refresh the list after update
            // clearFields(); // Optionally clear form fields
            clearFieldsStaff();
        },
        error: function(ob, txtStatus, error) {
            alert(txtStatus);
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: ob.responseText // Show the error response text
            });
        }
    });
});


function loadAllVCodes() {
    $("#vCode").empty();
    const token = localStorage.getItem('jwtToken');
    // return new Promise(function (resolve, reject) {
    var Cus = '';
    $.ajax({
        url: baseUrlVehicles,
        method: "GET",
        dataType: "json",//please convert the response into jason
        headers: {
            "Authorization": `Bearer ${token}`  // Add the JWT token to the Authorization header
        },

        success: function (resp) {

            for (const customer of resp.data) {
                $("#vCode").empty();
                Cus += '<option value="' + customer.vehicleCode + '">' + customer.vehicleCode+ '</option>';

                console.log(typeof resp);
                $("#vCode").append(Cus);
            }
            //  btnRowClick();
            //rowBack();
        }
    });

}

function btnRowClickStaff() {
    $('#staffTable').on('click', 'tr', function() {
        var headers = $(this).children('th'); // Select header cells for `staffId1`
        var cells = $(this).children('td');    // Select data cells for other fields
        $('#staffId1').val(headers.eq(0).text()); // staffId1 from <th>
        $('#firstName').val(cells.eq(0).text());  // First Name
        $('#lastName').val(cells.eq(1).text());   // Last Name
        $('#designation').val(cells.eq(2).text()); // Designation
        $('#gender').val(cells.eq(3).text());      // Gender
        $('#joinedDate').val(cells.eq(4).text()); // Date of Joining
        $('#dob').val(cells.eq(5).text()); // Attached Branch
        $('#addressLine1').val(cells.eq(6).text()); // Address Line 1
        $('#addressLine2').val(cells.eq(7).text()); // Address Line 2
        $('#addressLine3').val(cells.eq(8).text()); // Address Line 3
        $('#addressLine4').val(cells.eq(9).text()); // Address Line 4
        $('#addressLine5').val(cells.eq(10).text()); // Address Line 5
        $('#contactNo').val(cells.eq(11).text()); // Contact No
        $('#email1').val(cells.eq(12).text()); // Email
        $('#members').val(cells.eq(13).text()); // Emergency Contact
        $('#fieldCode1').val(cells.eq(14).text()); // Emergency Contact Person
        $('#vCode').val(cells.eq(15).text()); // Vehicle Code

        // Optionally show the form if it’s hidden
        // $('#mainEmployee').show();
    });
}




function clearFieldsStaff() {
    $('#staffId1').val('');
    $('#firstName').val('');
    $('#lastName').val('');
    $('#designation').val('');
    $('#gender').val('');
    $('#joinedDate').val('');
    $('#dob').val('');
    $('#addressLine1').val('');
    $('#addressLine2').val('');
    $('#addressLine3').val('');
    $('#addressLine4').val('');
    $('#addressLine5').val('');
    $('#contactNo').val('');
    $('#email1').val('');
    $('#members').val('');
    $('#fieldCode1').val('');
    $('#vCode').val('');
    getNextStaffCode();
    $("#staffId1").focus();
}

