
// var token = localStorage.getItem('token');
let baseUrlVehicles='http://localhost:8080/api/v2/vehicles'


getAllV();
getNextVcodes();
btnRowClickV();
loadAllSCodes();

function getNextVcodes(){
    const token = localStorage.getItem('jwtToken');
    $.ajax({
        url:baseUrlVehicles+'/nextVd',
        method:'GET',
        contentType: 'application/json',
        headers: {
            "Authorization": `Bearer ${token}`  // Add the JWT token to the Authorization header
        },
        success: function(resp){
            console.log(resp);
            $('#vehicleCode').val(resp)
        }
    });
}



function getAllV() {

    $("#vTbody").empty();
    const token = localStorage.getItem('jwtToken');
    $.ajax({
        url: baseUrlVehicles,
        method: "GET",
        contentType: 'application/json',
        headers: {
            "Authorization": `Bearer ${token}`  // Add the JWT token to the Authorization header
        },
        success: function (res) {
            console.log(res);
            for (var f of res.data) {

                let row = `<tr>

                     <th>${f.vehicleCode}</th>
                        <td>${f.licensePlateNumber}</td>
                        <td>${f.vehicleCategory}</td>
                        <td>${f.fuelType}</td>
                        <td>${f.status}</td>
                        <td>${f.allocatedStaffId}</td>
                        <td>${f.remarks}</td>

                    </tr>`;
                $("#vTbody").append(row);

            }
        }

    });

}





function loadAllSCodes() {
    $('#staffId').empty();
    const token = localStorage.getItem('jwtToken');
    // return new Promise(function (resolve, reject) {
    var Cus = '';
    $.ajax({
        url: "http://localhost:8080/api/v2/staff",
        method: "GET",
        dataType: "json", //please convert the response into jason
        headers: {
            "Authorization": `Bearer ${token}`  // Add the JWT token to the Authorization header
        },
        success: function (resp) {

            for (const customer of resp.data) {
                $("#allocatedStaffId").empty();
                Cus += '<option value="' + customer.staffId + '">' + customer.staffId+ '</option>';

                console.log(typeof resp);
                $("#allocatedStaffId").append(Cus);
            }
            //  btnRowClick();
            //rowBack();
        }
    });

}


$('#saveVbtn').click(function() {
    const token = localStorage.getItem('jwtToken');
    // Create a JSON object from the form data
    var formData = $("#vehicleForm").serializeArray();
    var data = {};
    $(formData).each(function(index, obj) {
        data[obj.name] = obj.value;
    });

    console.log(data);
    console.log('Token:', token);

    $.ajax({
        url: baseUrlVehicles, // Make sure this matches your controller endpoint
        method: "POST",
        contentType: 'application/json', // Specify content type
        data: JSON.stringify(data), // Convert data to JSON string
        headers: {
            "Authorization": `Bearer ${token}`  // Add the JWT token to the Authorization header
        },
        success: function(res) {
            Swal.fire({
                icon: 'success',
                title: 'Saved Successfully',
                text: res.text
            });
            getAllV();
            clearFieldsV();
            // clearFieldsStaff();
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


$('#deleteVbtn').click(function (){
    let vCode = $("#vehicleCode").val();
    const token = localStorage.getItem('jwtToken');
    // Check if fieldID is empty
    if (!vCode) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Vcode code is required to delete a field.'
        });
        return;
    }

    $.ajax({
        url: "http://localhost:8080/api/v2/vehicles?vCode="+vCode,
        method: "DELETE",
        headers: {
            "Authorization": `Bearer ${token}`  // Add the JWT token to the Authorization header
        },
        success: function (res) {
            console.log(res);

            Swal.fire({
                icon: 'success',
                title: 'Deleted Successfully',
                text: res.text
            });
            clearFieldsV();
            getAllV();



        },
        error: function (ob, status, t) {
            console.error("Error deleting field:", status, t);

            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'Vehcile to delete the field. Please try again.'
            });
        }
    });
});

$('#updateVbtn').click(function() {
    // Create a JSON object from the form data
    const token = localStorage.getItem('jwtToken');
    var formData = $("#vehicleForm").serializeArray();
    var data = {};
    $(formData).each(function(index, obj) {
        data[obj.name] = obj.value;
    });

    console.log('Data to update:', data);
    console.log('Token:', token);

    $.ajax({
        url: baseUrlVehicles+'/update', // Update endpoint URL
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
                text: 'Vehicle details updated successfully'
            });
            getAllV();// Refresh the list after update
            // clearFields(); // Optionally clear form fields
            clearFieldsV();
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




$(document).ready(function() {
    // Define license plate prefixes for each vehicle category
    const licensePrefixes = {
        CAR: "CA-1234",
        TRUCK: "TR-1234",
        TRACTOR: "TRA-1234",
        VAN: "VA-1234",
        SUV: "SUV-1234",
        CRANE: "CRA-1234"
    };

    // When vehicle category changes, update license plate number
    $("#vehicleCategory").change(function() {
        const selectedCategory = $(this).val();
        const licensePlate = licensePrefixes[selectedCategory] || "";
        $("#licensePlateNumber").val(licensePlate);
    });
});

function btnRowClickV() {
    $('#vTable').on('click', 'tr', function() {
        var headers = $(this).children('th'); // Select header cells for `staffId1`
        var cells = $(this).children('td');    // Select data cells for other fields
        $('#vehicleCode').val(headers.eq(0).text()); // staffId1 from <th>
        $('#licensePlateNumber').val(cells.eq(0).text());  // First Name
        $('#vehicleCategory').val(cells.eq(1).text());   // Last Name
        $('#fuelType').val(cells.eq(2).text()); // Designation
        $('#status').val(cells.eq(3).text());      // Gender
        $('#allocatedStaffId').val(cells.eq(4).text()); // Date of Joining
        $('#remarks').val(cells.eq(5).text()); // Attached Branch


        // Optionally show the form if it’s hidden
        // $('#mainEmployee').show();
    });
}





function clearFieldsV() {
    $('#vehicleCode').val('');
    $('#licensePlateNumber').val('');
    $('#vehicleCategory').val('');
    $('#fuelType').val('');
    $('#status').val('');
    $('#allocatedStaffId').val('');
    $('#remarks').val('');

    getNextVcodes();
    $('#vehicleCode').focus();
}
