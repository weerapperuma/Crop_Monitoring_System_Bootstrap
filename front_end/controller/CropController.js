import {deleteCrop, getAllCrops, getCrop, saveCrop, updateCrop} from "../service/CropService.js";
import {showAlerts} from "./DashboardController.js";
import {getAllField} from "../service/FieldService.js";
import {checkAccess} from "../util/AccessController.js";

var targetCropCode = null;

$(document).ready(function () {
    $('.add-crop-btn').click(async function(){
        const access = await checkAccess("crop")
        if (access) {
            $('#save-crop-popup').addClass('d-flex');
            loadDataToSavePopup()
        }
    })
    $('#save-crop-popup img').click(function(){
        $('#save-crop-popup').removeClass('d-flex');
    })
    $("#card-set").on("click", ".crop-card .action > :nth-child(1)",async function () {
        const access = await checkAccess("crop")
        if (access) {
            $('#update-crop-popup').addClass('d-flex');
            targetCropCode = $(this).data("id");
            loadCropDataToUpdatePopup(targetCropCode);
        }
    });

    $('#update-crop-popup img').click(function(){
        $('#update-crop-popup').removeClass('d-flex')
    })

    $("#card-set").on("click", ".crop-card .action > :nth-child(3)", function () {
        $('#view-crop-popup').addClass('d-flex');
        targetCropCode = $(this).data("id");
        loadDataViewCrop()
    });

    $('#view-crop-popup img').click(function(){
        $('#view-crop-popup').removeClass('d-flex')
    })

    $("#card-set").on("click", ".crop-card .action > :nth-child(2)",async function () {
        const access = await checkAccess("crop")
        if (access) {
            targetCropCode = $(this).data("id");
            deleteCropFrom()
        }
    });

    loadTable()
})

function loadTable(){
    const table = $('#card-set')

    getAllCrops().then((result) => {
        table.empty()
        result.forEach((crop) => {

            table.append(

                `
                <div class="crop-card p-3 bg-white shadow rounded-4">
        <div
          class="w-100 h-auto object-fit-cover overflow-hidden rounded-4 rounded-bottom-0"
        >
          <img
            class="w-100 h-100"
            src=${base64ToImageURL(crop.cropImage)}
            alt=""
          />
        </div>
        <div class="d-flex justify-content-between mt-3">
          <div>
            <h3>Crop Code</h3>
            <h2>${dataRefactor(crop.cropCode,17)}</h2>
          </div>
          <div>
            <h3>Crop Season</h3>
            <h2>${dataRefactor(crop.cropSeason,5)}</h2>
          </div>
        </div>
        <div class="mt-3 d-flex justify-content-between align-items-center">
          <div>
            <h3>Crop Name</h3>
            <h2>${dataRefactor(crop.cropCommonName,8)}</h2>
          </div>
          <div class="d-flex align-items-center gap-3 action">
            <svg data-id="${crop.cropCode}"
              xmlns="http://www.w3.org/2000/svg"
              width="16"
              height="22"
              viewBox="0 0 16 22"
              fill="none"
            >
              <path
                d="M8 5V8L12 4L8 0V3C3.58 3 0 6.58 0 11C0 12.57 0.46 14.025 1.24 15.26L2.7 13.8C2.255 12.965 2 12.015 2 11C2 7.685 4.685 5 8 5ZM14.76 6.74L13.3 8.2C13.745 9.035 14 9.985 14 11C14 14.315 11.315 17 8 17V14L4 18L8 22V19C12.42 19 16 15.42 16 11C16 9.43 15.54 7.975 14.76 6.74Z"
                fill="#9A9A9A"
              />
            </svg>
            <svg data-id="${crop.cropCode}"
              xmlns="http://www.w3.org/2000/svg"
              width="16"
              height="19"
              viewBox="0 0 16 19"
              fill="none"
            >
              <path
                d="M14.8885 6.78675L14.5525 16.0492C14.5239 16.8425 14.1569 17.5944 13.5295 18.1453C12.9021 18.6962 12.0636 19.0028 11.192 19H4.80714C3.93613 19.0028 3.09814 18.6967 2.47082 18.1464C1.8435 17.5961 1.47616 16.845 1.44668 16.0523L1.11063 6.78675C1.10328 6.58385 1.18474 6.38659 1.33709 6.23839C1.48945 6.09018 1.70021 6.00315 1.92302 5.99646C2.14584 5.98976 2.36244 6.06394 2.5252 6.20268C2.68795 6.34142 2.78351 6.53335 2.79086 6.73626L3.12691 16.001C3.14364 16.3965 3.32803 16.7707 3.64134 17.045C3.95465 17.3194 4.3725 17.4725 4.80714 17.4722H11.192C11.6272 17.4724 12.0456 17.3189 12.359 17.0439C12.6724 16.769 12.8564 16.394 12.8722 15.9979L13.2083 6.73626C13.2156 6.53335 13.3112 6.34142 13.474 6.20268C13.6367 6.06394 13.8533 5.98976 14.0761 5.99646C14.2989 6.00315 14.5097 6.09018 14.6621 6.23839C14.8144 6.38659 14.8959 6.58385 14.8885 6.78675ZM16 3.70437C16 3.90727 15.9115 4.10187 15.7539 4.24534C15.5964 4.38882 15.3827 4.46942 15.1599 4.46942H0.840115C0.617303 4.46942 0.403616 4.38882 0.246064 4.24534C0.0885117 4.10187 0 3.90727 0 3.70437C0 3.50147 0.0885117 3.30687 0.246064 3.1634C0.403616 3.01993 0.617303 2.93932 0.840115 2.93932H3.44447C3.71066 2.93998 3.9676 2.85042 4.16528 2.68808C4.36296 2.52574 4.48724 2.30223 4.51394 2.06105C4.57594 1.49528 4.86701 0.970801 5.33044 0.589789C5.79388 0.208777 6.39648 -0.00147143 7.02085 7.75214e-06H8.97831C9.60268 -0.00147143 10.2053 0.208777 10.6687 0.589789C11.1322 0.970801 11.4232 1.49528 11.4852 2.06105C11.5119 2.30223 11.6362 2.52574 11.8339 2.68808C12.0316 2.85042 12.2885 2.93998 12.5547 2.93932H15.159C15.3819 2.93932 15.5955 3.01993 15.7531 3.1634C15.9106 3.30687 15.9992 3.50147 15.9992 3.70437H16ZM5.97238 2.93932H10.0285C9.91806 2.70961 9.84586 2.4662 9.81423 2.21712C9.79342 2.02854 9.69648 1.85371 9.5422 1.7265C9.38792 1.59929 9.18728 1.52876 8.97915 1.52857H7.02169C6.81356 1.52876 6.61292 1.59929 6.45864 1.7265C6.30436 1.85371 6.20742 2.02854 6.18661 2.21712C6.15471 2.46624 6.08307 2.70965 5.97238 2.93932ZM6.81838 14.5306V8.01618C6.81838 7.81328 6.72987 7.61869 6.57231 7.47521C6.41476 7.33174 6.20107 7.25114 5.97826 7.25114C5.75545 7.25114 5.54176 7.33174 5.38421 7.47521C5.22666 7.61869 5.13815 7.81328 5.13815 8.01618V14.5336C5.13815 14.7365 5.22666 14.9311 5.38421 15.0746C5.54176 15.2181 5.75545 15.2987 5.97826 15.2987C6.20107 15.2987 6.41476 15.2181 6.57231 15.0746C6.72987 14.9311 6.81838 14.7365 6.81838 14.5336V14.5306ZM10.8627 14.5306V8.01618C10.8627 7.81328 10.7742 7.61869 10.6166 7.47521C10.4591 7.33174 10.2454 7.25114 10.0226 7.25114C9.79977 7.25114 9.58608 7.33174 9.42853 7.47521C9.27097 7.61869 9.18246 7.81328 9.18246 8.01618V14.5336C9.18246 14.7365 9.27097 14.9311 9.42853 15.0746C9.58608 15.2181 9.79977 15.2987 10.0226 15.2987C10.2454 15.2987 10.4591 15.2181 10.6166 15.0746C10.7742 14.9311 10.8627 14.7365 10.8627 14.5336V14.5306Z"
                fill="#9A9A9A"
              />
            </svg>
            <svg data-id="${crop.cropCode}"
              xmlns="http://www.w3.org/2000/svg"
              width="20"
              height="14"
              viewBox="0 0 20 14"
              fill="none"
            >
              <path
                d="M10 0C16.25 0 20 7 20 7C20 7 16.25 14 10 14C3.75 14 0 7 0 7C0 7 3.75 0 10 0ZM10 1.27273C5.6 1.27273 2.4975 5.41036 1.46875 7C2.49625 8.58836 5.59875 12.7273 10 12.7273C14.4 12.7273 17.5025 8.58964 18.5312 7C17.5037 5.41164 14.4012 1.27273 10 1.27273ZM10 2.54545C11.1603 2.54545 12.2731 3.01477 13.0936 3.85016C13.9141 4.68555 14.375 5.81858 14.375 7C14.375 8.18142 13.9141 9.31445 13.0936 10.1498C12.2731 10.9852 11.1603 11.4545 10 11.4545C8.83968 11.4545 7.72688 10.9852 6.90641 10.1498C6.08594 9.31445 5.625 8.18142 5.625 7C5.625 5.81858 6.08594 4.68555 6.90641 3.85016C7.72688 3.01477 8.83968 2.54545 10 2.54545ZM10 3.81818C9.1715 3.81918 8.37721 4.15473 7.79138 4.75122C7.20554 5.34771 6.87598 6.15644 6.875 7C6.875 8.75382 8.27625 10.1818 10 10.1818C11.7238 10.1818 13.125 8.75382 13.125 7C13.125 5.24618 11.7238 3.81818 10 3.81818Z"
                fill="#9A9A9A"
              />
            </svg>
          </div>
        </div>
      </div>
                
                `

            )

        })
    })
}

function dataRefactor(data, maxLength) {
    if (data && typeof data === "string" && data.length > maxLength) {
        return data.substring(0, maxLength) + " ...";
    }
    return data;
}

function base64ToImageURL(base64Data) {
    return `data:image/png;base64,${base64Data}`;
}

$('#save-crop-popup button').click(function () {
    const cropName = $('#save-crop-popup .crop-name-text').val();
    const cropScientificName = $('#save-crop-popup .crop-scientific-text').val();
    const cropSeason = $('#save-crop-popup .crop-session-text').val();
    const cropType = $('#save-crop-popup .crop-type-text').val();
    const image = $('#save-crop-popup .img-input')[0];
    const fieldCode = $('#save-crop-popup .field-combo').val()

    const formData = new FormData();
    formData.append("cropName", cropName);
    formData.append("cropScientificName", cropScientificName);
    formData.append("cropSeason", cropSeason);
    formData.append("cropType", cropType);
    formData.append("cropImage", image.files[0]);

    console.log(image.files[0]);

    if (!validateCrop(cropName, cropScientificName, cropSeason, cropType, image)) {
        return;
    }

    saveCrop(formData, fieldCode)
        .then((result) => {
            $('#save-crop-popup').removeClass('d-flex');
            showAlerts("Crop saved successfully", "success");
            loadTable(); // Reload the table
        })
        .catch((error) => {
            console.error("Error saving crop:", error);
        });
});

function loadDataToSavePopup(){
    getAllField().then((result) => {
        $('#save-crop-popup .field-combo').empty()
        result.forEach((field) => {
            $('#save-crop-popup .field-combo').append(
                `<option value="${field.fieldCode}">${field.fieldCode} , ${field.fieldName}</option>`
            )
        })
    })
}

function validateCrop(cropName, cropScientificName, cropSeason, cropType, image) {
    const alphaRegex = /^[a-zA-Z\s]+$/;
    const alphaNumericRegex = /^[a-zA-Z0-9\s]+$/;

    function validateField(field, fieldName, regex) {
        if (!field || field.trim() === "") {
            showAlerts(`${fieldName} is required.`, "error");
            return false;
        } else if (!regex.test(field)) {
            showAlerts(`${fieldName} must be valid.`, "error");
            return false;
        } else if (field[0] !== field[0].toUpperCase()) {
            showAlerts(`${fieldName} must start with a capital letter.`, "error");
            return false;
        }
        return true;
    }

    if (!validateField(cropName, "Crop name", alphaRegex)) return false;
    if (!validateField(cropScientificName, "Crop scientific name", alphaNumericRegex)) return false;
    if (!validateField(cropSeason, "Crop season", alphaRegex)) return false;
    if (!validateField(cropType, "Crop type", alphaRegex)) return false;

    if (!image || image.files.length === 0) {
        showAlerts("Crop image is required.", "error");
        return false;
    }

    return true;
}

function loadCropDataToUpdatePopup(cropCode) {
    getCrop(cropCode).then((crop) => {
        $('#update-crop-popup .crop-name-text').val(crop.cropCommonName);
        $('#update-crop-popup .crop-type-text').val(crop.category);
        $('#update-crop-popup .crop-scientific-text').val(crop.cropScientificName);
        $('#update-crop-popup .crop-session-text').val(crop.cropSeason);
        $('#update-crop-popup .field-combo').val(crop.fieldCode).select();

        const file = base64ToFile(base64ToImageURL(crop.cropImage), "crop-image.png");
        console.log(file);

        const dataTransfer = new DataTransfer();
        dataTransfer.items.add(file);
        $('#update-crop-popup .img-input')[0].files = dataTransfer.files;


        getAllField().then((result) => {
            $('#update-crop-popup .field-combo').empty()
            result.forEach((field) => {
                $('#update-crop-popup .field-combo').append(
                    `<option value="${field.fieldCode}">${field.fieldCode} , ${field.fieldName}</option>`
                )
            })
        }).catch((error) => {
            console.error("Error loading field data:", error);
        })

    }).catch((error) => {
        console.error("Error loading crop data:", error);
    });
}

function base64ToFile(base64String, fileName) {
    const arr = base64String.split(',');
    const mime = arr[0].match(/:(.*?);/)[1];
    const bstr = atob(arr[1]);
    let n = bstr.length;
    const u8arr = new Uint8Array(n);

    while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
    }

    return new File([u8arr], fileName, { type: mime });
}

$('#update-crop-popup button').click(function () {
    const cropName = $('#update-crop-popup .crop-name-text').val();
    const cropScientificName = $('#update-crop-popup .crop-scientific-text').val();
    const cropSeason = $('#update-crop-popup .crop-session-text').val();
    const cropType = $('#update-crop-popup .crop-type-text').val();
    const image = $('#update-crop-popup .img-input')[0];
    const fieldCode = $('#update-crop-popup .field-combo').val();

    const formData = new FormData();
    formData.append("cropName", cropName);
    formData.append("cropType", cropType);
    formData.append("cropSeason", cropSeason);
    formData.append("cropScientificName", cropScientificName);
    formData.append("cropImage", image.files[0]);
    formData.append("FieldCode", fieldCode);

    if (!validateCrop(cropName, cropScientificName, cropSeason, cropType, image)) {
        return;
    }

    updateCrop(targetCropCode, formData)
        .then((result) => {
            $('#update-crop-popup').removeClass('d-flex');
            showAlerts("Crop updated successfully", "success");
            loadTable(); // Reload the table
        })
        .catch((error) => {
            console.error("Error updating crop:", error);
        });
});

function loadDataViewCrop(){
    getCrop(targetCropCode).then((crop) => {
        console.log(crop)
        $('#view-crop-popup .crop-code-text').val(crop.cropCode);
        $('#view-crop-popup .crop-name-text').val(crop.cropCommonName);
        $('#view-crop-popup .crop-scientific-name-text').val(crop.cropScientificName);
        $('#view-crop-popup .crop-season-text').val(crop.cropSeason);
        $('#view-crop-popup .crop-type-text').val(crop.category);
        $('#view-crop-popup .field-id-text').val(crop.fieldCode);
        $('#view-crop-popup .crop-Img').attr('src',base64ToImageURL(crop.cropImage))
    }).catch((error) => {
        console.error("Error loading crop data:", error);
    });
}

function deleteCropFrom(){

    Swal.fire({
        title: "Are you sure?",
        text: "Do you want to delete this Crop?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#d33",
        cancelButtonColor: "#3085d6",
        confirmButtonText: "Yes, delete it!",
        cancelButtonText: "Cancel",
    }).then((result) => {
        if (result.isConfirmed) {
            deleteCrop(targetCropCode)
                .then((result) => {
                    loadTable();
                    Swal.fire("Deleted!", "Crop has been deleted.", "success");
                })
                .catch((error) => {
                    console.log(error);
                });
        } else if (result.dismiss === Swal.DismissReason.cancel) {
            Swal.fire("Cancelled", "Your item is safe.", "info");
        }
    });
}

$('.search-input').on("keyup", function (){
    let search = $(this).val();
    $('#card-set .crop-card').each(function (){
        let logCode = $(this).find('h2').text().toLowerCase();
        if (logCode.includes(search.toLowerCase())){
            $(this).show();
        } else {
            $(this).hide();
        }
    })
})