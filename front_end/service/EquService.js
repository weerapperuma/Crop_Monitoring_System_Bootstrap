import {getCookie} from "./ToeknService.js";

export function getAllEqu(){
    return new Promise((resolve, reject) => {
        $.ajax({
            url : "http://localhost:5055/greenshadow/api/v1/equipment",
            type : "GET",
            headers: {
                Authorization: "Bearer " + getCookie("authToken"),
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

export function saveEqu(equ){
    return new Promise((resolve, reject) => {
        $.ajax({
            url : "http://localhost:5055/greenshadow/api/v1/equipment",
            type : "POST",
            headers: {
                Authorization: "Bearer " + getCookie("authToken"),
            },
            data : JSON.stringify(equ),
            contentType: "application/json",
            success: function(result){
                resolve(result);
            },
            error: function(xhr, status, error){
                reject(error);
            },
        })
    })
}

export function getEqu(id){
    return new Promise((resolve, reject) => {
        $.ajax({
            url : `http://localhost:5055/greenshadow/api/v1/equipment/${id}`,
            type : "GET",
            headers: {
                Authorization: "Bearer " + getCookie("authToken"),
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

export function updateEqu(equ, staffId, fieldCode,equ_id) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: `http://localhost:5055/greenshadow/api/v1/equipment/${equ_id}?staffIds=${staffId}&fieldCode=${fieldCode}`,
            type: "PATCH",
            headers: {
                Authorization: "Bearer " + getCookie("authToken"),
            },
            data: JSON.stringify(equ),
            contentType: "application/json",
            success: function(result) {
                resolve(result);
            },
            error: function(xhr, status, error) {
                reject(error);
            },
        });
    });
}

export function deleteEqu(equ_id) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: `http://localhost:5055/greenshadow/api/v1/equipment/${equ_id}`,
            type: "DELETE",
            headers: {
                Authorization: "Bearer " + getCookie("authToken"),
            },
            success: function (result) {
                resolve(result);
            },
            error: function (xhr, status, error) {
                reject(error);
            }
        })
    })
}