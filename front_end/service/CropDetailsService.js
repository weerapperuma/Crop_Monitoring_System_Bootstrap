import {getCookie} from "./ToeknService.js";

export function getAllCropDetails() {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: "http://localhost:5055/greenshadow/api/v1/cropDetails",
            type: "GET",
            headers: {
                Authorization: "Bearer " + getCookie("authToken"),
            },
            success: function(result) {
                resolve(result);
            },
            error: function(xhr, status, error) {
                reject(error);
            },
        });
    });
}

export function saveCropDetails(param, cropDetails){
    const url = `http://localhost:5055/greenshadow/api/v1/cropDetails?fieldCode=${param.fieldCode}&cropCode=${param.cropCode}&staffId=${param.staffId}`;
    return new Promise((resolve, reject) => {
        $.ajax({
            url: url,
            type: "POST",
            headers: {
                Authorization: "Bearer " + getCookie("authToken"),
            },
            data: cropDetails,
            processData: false,
            contentType: false,
            success: function(result) {
                resolve(result);
            },
            error: function(xhr, status, error) {
                reject(error);
            },
        });
    });
}

export function getCropDetails(logCode){
    return new Promise((resolve, reject) => {
        $.ajax({
            url : `http://localhost:5055/greenshadow/api/v1/cropDetails/${logCode}`,
            type : "GET",
            headers: {
                Authorization: "Bearer " + getCookie("authToken")
            },
            success: function(result){
                resolve(result);
            },
            error: function(xhr, status, error){
                reject(error);
            },
        })
    })
}

export function updateCropDetails(logCode, cropDetails){
    return new Promise((resolve, reject) =>{
        $.ajax({
            url : `http://localhost:5055/greenshadow/api/v1/cropDetails/${logCode}`,
            type : "PATCH",
            headers: {
                Authorization: "Bearer " + getCookie("authToken")
            },
            data : cropDetails,
            processData: false,
            contentType: false,
            success: function(result){
                resolve(result);
            },
            error: function(xhr, status, error){
                reject(error);
            },
        })
    })
}

export function deleteCropDetails(logCode){
    return new Promise((resolve, reject) =>{
        $.ajax({
            url : `http://localhost:5055/greenshadow/api/v1/cropDetails/${logCode}`,
            type : "DELETE",
            headers: {
                Authorization: "Bearer " + getCookie("authToken")
            },
            success: function(result){
                resolve(result);
            },
            error: function(xhr, status, error){
                reject(error);
            },
        })
    })
}