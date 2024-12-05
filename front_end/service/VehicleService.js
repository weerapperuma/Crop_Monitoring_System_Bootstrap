import{getCookie} from "../service/ToeknService.js"

export function getAllVehicles() {
  return new Promise((resolve, reject) => {
    $.ajax({
      url: "http://localhost:5055/greenshadow/api/v1/vehicle",
      type: "GET",
      contentType: "application/json",
      headers: {
        Authorization: "Bearer " + getCookie("authToken"),
      },
      success: function (result) {
        console.log(result);
        resolve(result); // resolving with the response result
      },
      error: function (xhr, status, error) {
        reject(error); // rejecting on error
      },
    });
  });
}

export function addVehicle(vehicle) {
  console.log(vehicle)
  return new Promise((resolve, reject) => {
    $.ajax({
      url: "http://localhost:5055/greenshadow/api/v1/vehicle",
      type: "POST",
      contentType: "application/json",
      data: JSON.stringify(vehicle),
      headers: {
        Authorization: "Bearer " + getCookie("authToken"),
      },
      success: function (result) {
        console.log(result);
        resolve(result); // resolving with the response result
      },
      error: function (xhr, status, error) {
        reject(error); // rejecting on error
      },
    });
  });
}

export function getVehicle(id){
  return new Promise((resolve, reject) => {
    $.ajax({
      url: `http://localhost:5055/greenshadow/api/v1/vehicle/${id}`,
      type: "GET",
      contentType: "application/json",
      headers: {
        Authorization: "Bearer " + getCookie("authToken"),
      },
      success: function (result) {
        resolve(result);
      },
      error: function (xhr, status, error) {
        reject(error);
      },
    });
  })
}

export function updateVehicle(vehicle_id, vehicle, staff_id) {
  return new Promise((resolve, reject) => {
    $.ajax({
      url: `http://localhost:5055/greenshadow/api/v1/vehicle/${vehicle_id}?staffId=${staff_id}`,
      method: "PATCH",
      contentType: "application/json",
      headers: {
        Authorization: "Bearer " + getCookie("authToken"),
      },
      data: JSON.stringify(vehicle),
      success: function (result) {
        resolve(result);
      },
      error: function (xhr, status, error) {
        reject(error);
      },
    });
  });
}

export function deleteVehicle(vehicle_id){
  return new Promise((resolve,reject) => {
    $.ajax({
      url: `http://localhost:5055/greenshadow/api/v1/vehicle/${vehicle_id}`,
      method : "DELETE",
      contentType : "application/json",
      headers : {
        Authorization : "Bearer " + getCookie("authToken")
      },
      success : function(result){
        resolve(result)
      },
      error : function(xhr, status, error){
        reject(error)
      }
    });
  })
}