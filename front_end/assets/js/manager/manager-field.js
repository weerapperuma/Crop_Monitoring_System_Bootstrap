
let baseUrlFiled='http://localhost:8080/api/v2/field';
let baseUrlStaff='http://localhost:8080/api/v2/staff';

$(document).ready(function() {
    getAllFileds();
    getNextFiledCode();
    loadAllCropCodes();
    loadAllStaffCodes();
    btnRowClick();

});



// AJAX to load data into the table

function getNextFiledCode(){
    const token = localStorage.getItem('jwtToken');

    $.ajax({
        url:baseUrlFiled+'/generateFieldCode',
        method:'GET',
        contentType: 'application/json',
        headers: {
            "Authorization": `Bearer ${token}`  // Add the JWT token to the Authorization header
        },
        success: function(resp){
            console.log(resp);
            $('#fieldCode').val(resp)
        }
    });
}



function getAllFileds() {

    $("#fieldTableBody").empty();
    const token = localStorage.getItem('jwtToken');
    $.ajax({
        url: baseUrlFiled,
        method: "GET",
        headers: {
            "Authorization": `Bearer ${token}`  // Add the JWT token to the Authorization header
        },
        success: function (res) {
            console.log(res);
            for (var field of res.data) {
                let empPic = field.fieldImageFile ? `data:image/jpeg;base64,${field.fieldImageFile}` : 'path/to/default/image.jpg'; // Use a default image if empPic is empty

                let row = `<tr>

                     <td>${field.fieldCode}</td>
                        <td>${field.fieldName}</td>
                        <td>${field.fieldLocation}</td>
                        <td>${field.size}</td>
                        <td>${field.cropCode}</td>
                        <td>${field.nameOfCrop}</td>
                        <td>${field.staffId}</td>
                        <td><img src="${empPic}" alt="${field.nameOfCrop}'s Picture" style="width: 50px; height: 50px;"/></td>

                    </tr>`;
                $("#fieldTableBody").append(row);

            }
        }

    });

}


function loadAllCropCodes() {
    $('#cropCode').empty();
    const token = localStorage.getItem('jwtToken');
    // return new Promise(function (resolve, reject) {
    var Cus = '';
    $.ajax({
        url: "http://localhost:8080/api/v2/crop",
        method: "GET",
        dataType: "json",//please convert the response into jason
        headers: {
            "Authorization": `Bearer ${token}`  // Add the JWT token to the Authorization header
        },
        success: function (resp) {

            for (const customer of resp) {
                $("#cropCode").empty();
                Cus += '<option value="' + customer.cropCode + '">' + customer.cropCode+ '</option>';

                console.log(typeof resp);
                $("#cropCode").append(Cus);
            }
            //  btnRowClick();
            //rowBack();
        }
    });

}




function loadAllStaffCodes() {
    $('#staffId').empty();
    const token = localStorage.getItem('jwtToken');
    // return new Promise(function (resolve, reject) {
    var Cus = '';
    $.ajax({
        url: baseUrlStaff,
        method: "GET",
        dataType: "json",//please convert the response into jason
        headers: {
            "Authorization": `Bearer ${token}`  // Add the JWT token to the Authorization header
        },
        success: function (resp) {

            for (const customer of resp.data) {
                $("#staffId").empty();
                Cus += '<option value="' + customer.staffId + '">' + customer.staffId+ '</option>';

                console.log(typeof resp);
                $("#staffId").append(Cus);
            }
            //  btnRowClick();
            //rowBack();
        }
    });

}

$('#fieldForm').on('submit', function(e) {
    e.preventDefault(); // Prevent default form submission
    const token = localStorage.getItem('jwtToken');

    var formData = new FormData(this);

    $.ajax({
        type: 'POST',
        url: baseUrlFiled+'/save', // Adjust the URL as per your API
        data: formData,
        contentType: false,
        headers: {
            "Authorization": `Bearer ${token}`  // Add the JWT token to the Authorization header
        },
        processData: false,
        success: function(response) {
            Swal.fire({
                icon: 'success',
                title: 'Saved Successfully',
                text: response.text
            });

            getAllFileds();
            clearFields();
            btnRowClick();

            // loadAllInventory();
        },
        error: function(xhr, status, error) {
            Swal.fire({
                icon: 'error',
                title: 'EROOR',
                text: status,xhr,error
            });
        }
    });
});


$('#deleteFiled').click(function (){
    let fieldID = $("#fieldCode").val();
    const token = localStorage.getItem('jwtToken');

    // Check if fieldID is empty
    if (!fieldID) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Field code is required to delete a field.'
        });
        return;
    }

    $.ajax({
        url: "http://localhost:8080/api/v2/field?fCode="+fieldID,
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
            clearFields();
            getAllFileds();

            getNextFiledCode();
        },
        error: function (ob, status, t) {
            console.error("Error deleting field:", status, t);

            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'Failed to delete the field. Please try again.'
            });
        }
    });
});


function btnRowClick() {
    $('#filedTable').on('click', 'tr', function() {
        // Your existing code here
        let id=$(this).children(":eq(0)").text();
        let name=$(this).children(":eq(1)").text();
        let gender=$(this).children(":eq(2)").text();
        let joindate=$(this).children(":eq(3)").text();
        let level=$(this).children(":eq(4)").text();
        let totp=$(this).children(":eq(5)").text();
        let dob=$(this).children(":eq(6)").text();
        let pic=$(this).children(":eq(7)").text();




        // console.log(id,name,address,contact);

        $('#fieldCode').val(id);
        $('#fieldName').val(name);
        $('#fieldLocation').val(gender);
        $('#size').val(joindate);
        $('#cropCode').val(level);
        $('#nameOfCrop').val(totp);
        $('#staffId').val(dob);
        $('#fieldImage1').val('')

    });
}

function clearFields() {
    $('#fieldCode').val('');
    $('#fieldName').val('');
    $('#fieldLocation').val('');
    $('#size').val('');
    $('#cropCode').val('');
    $('#nameOfCrop').val('');
    $('#staffId').val('');
    $('#fieldImage1').val('');
    getNextFiledCode();
    $('#fieldCode').focus();
}



$('#updatefields').click(function (e) {
    e.preventDefault(); // Prevent the default form submission
    const token = localStorage.getItem('jwtToken');

    // Create a FormData object
    let formData = new FormData();

    // Append form data to the FormData object
    formData.append("fieldCode", $("#fieldCode").val());
    formData.append("fieldName", $("#fieldName").val());
    formData.append("fieldLocation", $("#fieldLocation").val());
    formData.append("size", $("#size").val());
    formData.append("cropCode", $("#cropCode").val() || null); // Optional field
    formData.append("nameOfCrop", $("#nameOfCrop").val() || null); // Optional field
    formData.append("staffId", $("#staffId").val() || null); // Optional field
    formData.append("fieldImageFile", $("#fieldImage1")[0].files[0]); // File upload

    // Retrieve the token from localStorage (adjust this based on where you store your token)


    // Make AJAX request to the update endpoint with authorization header
    $.ajax({
        url: baseUrlFiled+'/update',
        type: "PUT",
        data: formData,
        processData: false, // Required for FormData
        contentType: false, // Required for FormData
        headers: {
            "Authorization": `Bearer ${token}`  // Add the JWT token to the Authorization header
        },
        success: function (response) {
            console.log("Field updated successfully:", response);

            // Optional: Refresh the table after update
            getAllFileds();
            clearFields();

            Swal.fire({
                icon: 'success',
                title: 'Update Successful',
                text: 'The field data has been updated successfully.'
            });
        },
        error: function (xhr, status, error) {
            console.error("Failed to update field:", error);

            Swal.fire({
                icon: 'error',
                title: 'Update Failed',
                text: 'An error occurred while updating the field data. Status: ' + xhr.status
            });
        }
    });
});
