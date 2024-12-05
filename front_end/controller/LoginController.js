import { getCookie, saveCookie } from "../service/ToeknService.js";
import { login } from "../service/UserService.js";

$(document).ready(function () {
  $(".toggle-password").click(function () {
    const passwordInput = $(".password-input");
    const eyeIcon = $(this).find("i");

    if (passwordInput.attr("type") === "password") {
      passwordInput.attr("type", "text");
      eyeIcon.removeClass("bi-eye").addClass("bi-eye-slash");
    } else {
      passwordInput.attr("type", "password");
      eyeIcon.removeClass("bi-eye-slash").addClass("bi-eye");
    }
  });
});

$(".cssbuttons-io-button").click(function () {
  const email = $(".email-input").val();
  const password = $(".password-input").val();

  const notyf = new Notyf({
    duration: 1000,
    position: {
      x: "right",
      y: "top",
    },
    types: [
      {
        type: "warning",
        background: "orange",
        icon: {
          className: "material-icons",
          tagName: "i",
          text: "warning",
        },
      },
    ],
  });

  if ($("#checkbox").prop("checked")) {
    login(email, password)
      .then((response) => {
        //alert("awaaa");
        localStorage.setItem("userEmail", email);
        const token = response.token;
        saveCookie("authToken", token);
        console.log("Token saved as cookie:", getCookie("authToken") );
        window.location = "/pages/Dashboard.html";
      })
      .catch((error) => {
        console.log("Error:", error);
        notyf.error("Invalid email or password.");
      });
  } else {
    notyf.error("Please accept the terms and conditions.");
  }
});