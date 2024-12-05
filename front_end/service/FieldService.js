import { getCookie } from "./ToeknService.js";

export function getAllField(){
    return new Promise((resolve, reject) => {
        $.ajax({
            url: "http://localhost:5055/greenshadow/api/v1/field",
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
        })
    })
}

export function saveField(formData) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: "http://localhost:5055/greenshadow/api/v1/field",
            type: "POST",
            headers: {
                Authorization: "Bearer " + getCookie("authToken"), // Ensure token is valid
            },
            data: formData,
            processData: false,  // Prevent jQuery from processing the data
            contentType: false,  // Let FormData set the correct content type
            success: function(result) {
                resolve(result);
            },
            error: function(xhr, status, error) {
                reject(error);
            }
        });
    })
}

export function getField(field_code){
    return new Promise((resolve, reject) => {
        $.ajax({
            url: `http://localhost:5055/greenshadow/api/v1/field/${field_code}`,
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
        })
    })
}

export function deleteField(field_code){
    return new Promise((resolve, reject) => {
        $.ajax({
            url: `http://localhost:5055/greenshadow/api/v1/field/${field_code}`,
            type: "DELETE",
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
        })
    })
}

export function updateField(formData, field_code,staffId){
    return new Promise((resolve, reject) => {
        $.ajax({
            url: `http://localhost:5055/greenshadow/api/v1/field/${field_code}?staffIds=${staffId}`,
            type: "PATCH",
            headers: {
                Authorization: "Bearer " + getCookie("authToken"), // Ensure token is valid
            },
            data: formData,
            processData: false,  // Prevent jQuery from processing the data
            contentType: false,  // Let FormData set the correct content type
            success: function(result) {
                resolve(result);
            },
            error: function(xhr, status, error) {
                reject(error);
            }
        });
    })
}