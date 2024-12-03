
// Function to show the corresponding section and hide others
const sidebar =document.querySelector('.sidebar');

const togglebtn =document.querySelector('.toggle-btn');

/*togglebtn.addEventListener('click',()=>{
  sidebar.classList.toggle('active')
})*/

/*
function showSection(sectionId) {
  // Hide all sections
  $('#dashboardSection, #fieldSection, #userSection, #emailSection,#vehicleSection ,#equipmentSection,#cropSection ,#logSection ,#userSection2' ).hide();

  // Show the selected section
  $(sectionId).show();
}
// Sidebar link clicks
$('#dashboardLink').on('click', function() {
  showSection('#dashboardSection');
});

$('#filedLink').on('click', function() {
  showSection('#fieldSection');
});

$('#vehiclesLink').on('click', function() {
  showSection('#vehicle Section');
 // window.location.href = 'manager-vehicle.html';
});

$('#equipmentLink').on('click', function() {
  showSection('#equipmentSection');
});

$('#userLink').on('click', function() {
  showSection('#userSection');
});

$('#emailLink').on('click', function() {
  showSection('#emailSection');
});

$('#cropLink').on('click', function() {
  showSection('#cropSection');
});

$('#detailLink').on('click', function() {
  showSection('#logSection');
});

$('#user2Link').on('click', function() {
  showSection('#userSection2');
});

$('#logoutLink').on('click', function() {
  // Handle logout logic (e.g., redirect to login page)
  window.location.href = 'logout.html'; // Replace with your logout URL
});
*/

/*$(document).ready(function () {
  // Navigate to another page on button click
  $("#btnDash").click(function () {
    window.location.href = "../../pages/manager/manager-dash.html"; // Navigate to Page 2
  });
});*/

$(document).ready(function () {
  // Navigate to another page on button click
  $("#btnVehicle").click(function () {
    window.location.href = "../../pages/manager/manager-vehicle.html"; // Navigate to Page 2
  });
});

$(document).ready(function () {
  // Navigate to another page on button click
  $("#btnStaff").click(function () {
    window.location.href = "../../pages/manager/manager-staff.html"; // Navigate to Page 2
  });
});

$(document).ready(function () {
  // Navigate to another page on button click
  $("#btnEquipment").click(function () {
    window.location.href = "../../pages/manager/manager-equipment.html"; // Navigate to Page 2
  });
});

$(document).ready(function () {
  // Navigate to another page on button click
  $("#btnCrop").click(function () {
    window.location.href = "../../pages/manager/manager-crop.html"; // Navigate to Page 2
  });
});

$(document).ready(function () {
  // Navigate to another page on button click
  $("#btnField").click(function () {
    window.location.href = "../../pages/manager/manager-field.html"; // Navigate to Page 2
  });
});

$(document).ready(function () {
  // Navigate to another page on button click
  $("#btnUser").click(function () {
    window.location.href = "../../pages/manager/manager-user.html"; // Navigate to Page 2
  });
});

$(document).ready(function () {
  // Navigate to another page on button click
  $("#btnLog").click(function () {
    window.location.href = "../../pages/manager/manager-log.html"; // Navigate to Page 2
  });
});
