import { getCookie, saveCookie, tokenRefresh } from "../service/ToeknService.js";

$(document).ready(function () {
    changeButtonColor("dashboard-icon");
});

$(".log-out-btn").click(function (){
  //alert("awaa")
  saveCookie("authToken", "");
  window.location.href = "../index.html";
})

setInterval(refreshToken, 5000);

$(".dashboard-icon").click(function () {
  changeButtonColor("dashboard-icon");
  $(".main-container .main-wall").attr("src", "/pages/wall/HomeWall.html");
});

$(".staff-icon").click(function () {
  changeButtonColor("staff-icon");
  $(".main-container .main-wall").attr("src", "/pages/wall/StaffWall.html");
});

$(".vehicle").click(function () {
  changeButtonColor("vehicle");
  $(".main-container .main-wall").attr("src", "/pages/wall/VehicleWall.html");
});

$(".field-icon").click(function () {
  changeButtonColor("field-icon");
  $(".main-container .main-wall").attr("src", "/pages/wall/FieldWall.html");
});

$(".rqu_icon").click(function () {
  changeButtonColor("rqu_icon");
  $(".main-container .main-wall").attr("src", "/pages/wall/EquWall.html");
});

$(".crop_icon").click(function () {
  changeButtonColor("crop_icon");
  $(".main-container .main-wall").attr("src", "/pages/wall/CropWall.html");
});

$(".monitor_log").click(function () {
  changeButtonColor("monitor_log");
  $(".main-container .main-wall").attr(
    "src",
    "/pages/wall/CropDetailsWall.html"
  );
});

function changeButtonColor(button) {
  $(".nav-bar div img").each(function () {
    const className = $(this).attr("class");
    const imageName = className.replace(/-/g, "_");
    if (button === className) {
      $(".nav-bar ." + className).attr(
        "src",
        `/assets/icon/hover/${imageName}.svg`
      );
    } else {
      $(".nav-bar ." + className).attr("src", `/assets/icon/${imageName}.svg`);
    }
  });
}

function refreshToken() {
  tokenRefresh()
      .then((response) => {
        const token = response.token;
        saveCookie("authToken", token);
        console.log("Token refreshed successfully:", getCookie("authToken"));
      })
      .catch((error) => {
        console.error("Token refresh failed:", error);
      });
}

export function showAlerts(meessage , type){
  //alert("awaa alert")
  const notyf = new Notyf({
    duration: 3000,
    position: {
      x: "right",
      y: "top",
    },
    types: [
      {
        background: "orange",
        icon: {
          className: "material-icons",
          tagName: "i",
          text: type,
        },
      },
    ],
  });

  if(type === "success"){
    notyf.success(meessage);
  }
  if(type === "error"){
    notyf.error(meessage);
  }
  if(type === "warning"){
    notyf.warning(meessage);
  }
}
