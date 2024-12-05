import { getCookie } from "./ToeknService.js";

export function getAllStaff() {
  return new Promise((resolve, reject) => {
    $.ajax({
      url: "http://localhost:5055/greenshadow/api/v1/staff",
      type: "GET",
      contentType: "application/json",
      headers :{
        'Authorization': 'Bearer ' + getCookie('authToken')
      },
      success: function (result) {
        console.log(result);
        resolve(result);
      },
      error: function (xhr, status, error) {
        reject(error);
      },
    });
  });
}

export function addStaff(staff) {
  return new Promise((resolve, reject) => {
    $.ajax({
      url: "http://localhost:5055/greenshadow/api/v1/staff",
      type: "POST",
      contentType: "application/json",
      headers: {
        Authorization: "Bearer " + getCookie("authToken"),
      },
      data: JSON.stringify(staff),
      success: function (result) {
        console.log(result);
        resolve(result);
      },
      error: function (xhr, status, error) {
        reject(error);
      },
    });
  });
}

export function getStaffMember(staff_id){
  return new Promise((resolve, reject) => {
    $.ajax({
      url: `http://localhost:5055/greenshadow/api/v1/staff/${staff_id}`,
      type: "GET",
      contentType: "application/json",
      headers: {
        Authorization: "Bearer " + getCookie("authToken"),
      },
      success: function (result) {
        console.log(result);
        resolve(result);
      },
      error: function (xhr, status, error) {
        reject(error);
      },
    });
  });
}

export function updateStaff(staff_id, staff){
  return new Promise((resolve, reject) => {
    $.ajax({
      url: `http://localhost:5055/greenshadow/api/v1/staff/${staff_id}`,
      type: "PATCH",
      contentType: "application/json",
      headers: {
        Authorization: "Bearer " + getCookie("authToken"),
      },
      data: JSON.stringify(staff),
      success: function (result) {
        console.log(result);
        resolve(result);
      },
      error: function (xhr, status, error) {
        reject(error);
      },
    });
  });
}

export function deleteStaff(staff_id){
  return new Promise((resolve, reject) => {
    $.ajax({
      url: `http://localhost:5055/greenshadow/api/v1/staff/${staff_id}`,
      type: "DELETE",
      contentType: "application/json",
      headers: {
        Authorization: "Bearer " + getCookie("authToken"),
      },
      success: function (result) {
        console.log(result);
        resolve(result);
      },
      error: function (xhr, status, error) {
        reject(error);
      },
    });
  });
}