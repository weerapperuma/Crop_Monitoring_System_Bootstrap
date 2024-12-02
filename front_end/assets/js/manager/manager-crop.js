

$(document).ready(function() {
    getNextCropCode();
    getAllCrops();
    loadAllfiledCodes1();
    btnRowClickCrop();

});


function loadAllfiledCodes1() {
    $("#fieldCodes").empty();
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
                $("#fieldCodes").empty();
                Cus += '<option value="' + customer.fieldCode + '">' + customer.fieldCode+ '</option>';

                console.log(typeof resp);
                $("#fieldCodes").append(Cus);
            }
            //  btnRowClick();
            //rowBack();
        }
    });

}

function getNextCropCode() {
    const token = localStorage.getItem('jwtToken');
    $.ajax({
        url: 'http://localhost:8080/api/v2/crop/cropCode',
        method: 'GET',
        dataType: 'json',  // Ensure the response is expected to be in JSON format
        headers: {
            "Authorization": `Bearer ${token}`  // Add the JWT token to the Authorization header
        },
        success: function (resp) {
            console.log(resp);  // Check the response to make sure it's the correct JSON object
            $('#cropCode1').val(resp.cropCode);  // Set the crop code to the input field
        },
        error: function (err) {
            console.error('Error fetching crop code:', err);
        }
    });
}


function getAllCrops() {
    $("#cropTableBody").empty();
    const token = localStorage.getItem('jwtToken');

    $.ajax({
        url: 'http://localhost:8080/api/v2/crop',
        method: "GET",
        dataType: "json",
        headers: {
            "Authorization": `Bearer ${token}`  // Add the JWT token to the Authorization header
        },
        success: function (res) {
            console.log(res);
            for (var crop of res) {
                // Assuming cropImage contains the image filename or path
                let cropImageUrl = crop.cropImage ? `/img/${crop.cropImage}` : 'assets/img/Background.jpg';  // Fallback image if no cropImage

                // Log the final URL for debugging
                console.log(`Image URL: ${cropImageUrl}`);

                // Create the table row
                let row = `<tr>
                    <td>${crop.cropCode}</td>
                    <td>${crop.cropCommonName}</td>
                    <td>${crop.cropScientificName}</td>
                    <td><img src="${cropImageUrl}"></td>
                    <td>${crop.category}</td>
                    <td>${crop.qty}</td>
                    <td>${crop.cropSeason}</td>
                    <td>${crop.fieldCodes}</td>
                    <td>${crop.filedNames}</td>
                </tr>`;
                $("#cropTableBody").append(row);
            }
        }
    });
}


$('#cropForm').on('submit', function(e) {
    e.preventDefault(); // Prevent default form submission
    const token = localStorage.getItem('jwtToken');

    var formData = new FormData(this);

    // Log formData contents to see if everything is correct
    for (let pair of formData.entries()) {
        console.log(pair[0]+ ': '+ pair[1]);
    }

    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/api/v2/crop',
        data: formData,
        contentType: false,   // Tell jQuery not to set Content-Type, as it's automatically handled by FormData
        processData: false,   // Don't let jQuery process the data, as FormData does this natively
        headers: {
            "Authorization": `Bearer ${token}`  // Add the JWT token to the Authorization header
        },
        success: function(response) {
            Swal.fire({
                icon: 'success',
                title: 'Saved Successfully',
                text: response.text
            });
            clearFieldsCrops();
            getAllCrops();
        },
        error: function(xhr, status, error) {
            Swal.fire({
                icon: 'error',
                title: 'ERROR',
                text: "NOT SAVED"
            });
            console.error(error);
        }
    });
});

function btnRowClickCrop() {
    $('#cropTable').on('click', 'tr', function() {
        // Your existing code here
        let id=$(this).children(":eq(0)").text();
        let name=$(this).children(":eq(1)").text();
        let gender=$(this).children(":eq(2)").text();
        let joindate=$(this).children(":eq(3)").text();
        let level=$(this).children(":eq(4)").text();
        let totp=$(this).children(":eq(5)").text();
        let dob=$(this).children(":eq(6)").text();
        let pic=$(this).children(":eq(7)").text();
        let nam=$(this).children(":eq(8)").text();




        // console.log(id,name,address,contact);

        $('#cropCode1').val(id);
        $('#cropCommonName').val(name);
        $('#cropScientificName').val(gender);
        $('#cropImage').val('');
        $('#category').val(level);
        $('#qty').val(totp);
        $('#cropSeason').val(dob);
        $('#fieldCodes').val(pic)
        $('#filedNames').val(nam)


    });
}


$('#deleteCropBtn').click(function (){
    let crop = $("#cropCode1").val();
    const token = localStorage.getItem('jwtToken');

    // Check if fieldID is empty
    if (!crop) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Field code is required to delete a field.'
        });
        return;
    }

    $.ajax({
        url: "http://localhost:8080/api/v2/crop?cCode="+crop,
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
            clearFieldsCrops();
            getAllCrops();
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

function clearFieldsCrops() {
    $('#cropCode1').val('');
    $('#cropCommonName').val('');
    $('#cropScientificName').val('');
    $('#cropImage').val('');
    $('#category').val('');
    $('#qty').val('');
    $('#cropSeason').val('');
    $('#fieldCodes').val('');
    $('#filedNames').val('');


    getNextCropCode();
    $('#cropCode1').focus();
}

$('#updaeCropbutton').on('click', function(e) {
    e.preventDefault(); // Prevent form submission on button click

    const token = localStorage.getItem('jwtToken');
    const cropCode = $('#cropCode1').val(); // Get the crop code from the input field

    if (!cropCode) {
        Swal.fire({
            icon: 'error',
            title: 'Crop Code Required',
            text: 'Please enter the crop code to update.'
        });
        return;
    }

    var formData = new FormData($('#cropForm')[0]);

    // Log form data contents for debugging
    for (let pair of formData.entries()) {
        console.log(pair[0] + ': ' + pair[1]);
    }

    $.ajax({
        type: 'PUT',
        url: `http://localhost:8080/api/v2/crop/${cropCode}`, // Use the cropCode for the specific update
        data: formData,
        contentType: false,   // Let FormData handle the content type automatically
        processData: false,   // Don't process data as jQuery normally does
        headers: {
            "Authorization": `Bearer ${token}` // Include JWT token for authorization
        },
        success: function(response) {
            Swal.fire({
                icon: 'success',
                title: 'Updated Successfully',
                text: response.text || 'Crop updated successfully.'
            });
            clearFieldsCrops(); // Clear the form fields (assuming this function is defined)
            getAllCrops(); // Reload crops (assuming this function is defined)
        },
        error: function(xhr, status, error) {
            Swal.fire({
                icon: 'error',
                title: 'ERROR',
                text: xhr.responseText || "Failed to update crop."
            });
            console.error(error);
        }
    });
});

