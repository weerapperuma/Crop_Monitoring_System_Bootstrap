import {getAllStaff} from "../service/StaffService.js";
import {getAllField} from "../service/FieldService.js";
import {getAllCrops} from "../service/CropService.js";
import {getAllVehicles} from "../service/VehicleService.js";
import {getAllEqu} from "../service/EquService.js";

$(document).ready(function () {
    // Fetch and display counts
    getAllStaff().then((data) => {
        $(".staff-count h1").text(data.length);
    });

    getAllField().then((data) => {
        $(".field-count h1").text(data.length);
    });

    getAllCrops().then((data) => {
        $(".crop-count h1").text(data.length);
    });

    getAllVehicles().then((data) => {
        $(".vehicle-count h1").text(data.length);

        // Calculate available and unavailable vehicles based on 'status'
        const availableVehicles = data.filter((vehicle) => vehicle.status === "Available").length;
        const unavailableVehicles = data.length - availableVehicles;

        // Initialize the pie chart with dynamic vehicle data
        initPieChart(availableVehicles, unavailableVehicles);
    });

    getAllEqu().then((data) => {
        $(".equipment-count h1").text(data.length);

        // Calculate available and unavailable equipment based on 'status'
        const availableEquipment = data.filter((equipment) => equipment.status === "Available").length;
        const unavailableEquipment = data.length - availableEquipment;

        // Initialize the donut chart with dynamic equipment data
        initDonutChart(availableEquipment, unavailableEquipment);
    });
});

// Function to initialize the pie chart for vehicles
function initPieChart(availableCount, unavailableCount) {
    const ctx = document.getElementById("vehicleChart").getContext("2d");

    const data = {
        labels: ["Available Vehicles", "Unavailable Vehicles"],
        datasets: [
            {
                data: [availableCount, unavailableCount],
                backgroundColor: ["rgba(116,234,120,0.8)", "rgba(248,120,131,0.8)"],
                borderColor: ["rgba(116,234,120,1)", "rgba(248,120,131,1)"],
                borderWidth: 1,
            },
        ],
    };

    new Chart(ctx, {
        type: "pie",
        data: data,
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: "top",
                },
                tooltip: {
                    callbacks: {
                        label: function (tooltipItem) {
                            const label = tooltipItem.label || "";
                            const value = tooltipItem.raw;
                            const total = tooltipItem.dataset.data.reduce((acc, val) => acc + val, 0);
                            const percentage = ((value / total) * 100).toFixed(2);
                            return `${label}: ${value} (${percentage}%)`;
                        },
                    },
                },
            },
        },
    });
}

// Function to initialize the donut chart for equipment
function initDonutChart(availableCount, unavailableCount) {
    const ctx = document.getElementById("equipmentChart").getContext("2d");

    const data = {
        labels: ["Available Equipment", "Unavailable Equipment"],
        datasets: [
            {
                data: [availableCount, unavailableCount],
                backgroundColor: ["rgba(116,234,120,0.8)", "rgba(248,120,131,0.8)"],
                borderColor: ["rgba(116,234,120,0.8)", "rgba(248,120,131,0.8)"],
                borderWidth: 1,
            },
        ],
    };

    new Chart(ctx, {
        type: "doughnut",
        data: data,
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: "top",
                },
                tooltip: {
                    callbacks: {
                        label: function (tooltipItem) {
                            const label = tooltipItem.label || "";
                            const value = tooltipItem.raw;
                            const total = tooltipItem.dataset.data.reduce((acc, val) => acc + val, 0);
                            const percentage = ((value / total) * 100).toFixed(2);
                            return `${label}: ${value} (${percentage}%)`;
                        },
                    },
                },
            },
        },
    });
}
