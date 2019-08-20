$(function () {
    function checkusername() {
        var username = $("#username").val();
        var reg_username = /^\w{8,20}$/;
        var flag = reg_username.test(username);
        return flag;
    }

    function checkpassword() {
        var password = $("#password").val();
        var reg_password = /^\w{8,20}$/;
        var flag = reg_password.test(password);
        return flag;
    }

    function checkemail() {
        var email = $("#email").val();
        var reg_email = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
        var flag = reg_email.test(email);
        return flag;
    }

    function checkname() {
        var name = $("#name").val();
        var reg_name = /\S/;
        var flag = reg_name.test(name);
        return flag;
    }

    function checkbirthday() {
        var birthday = $("#birthday").val();
        var reg_birthday = /\S/;
        var flag = reg_birthday.test(birthday);
        return flag;
    }

    function checkcheck() {
        var check = $("#check").val();
        var reg_check = /\S/;
        var flag = reg_check.test(check);
        return flag;
    }

    function checktelephone() {
        var telephone = $("#telephone").val();
        var reg_telephone = /^1[0-9]{10}$/;
        var flag = reg_telephone.test(telephone);
        return flag;
    }

    //检测用户名格式，并通过ajax去重
    $("#username").blur(function () {
        if (checkusername()) {
            $.post("./registerServlet", {
                action: "checkRepeat",
                username: $("#username").val()
            }, function (data) {
                if (data["flag"]) {
                    $("#msg").text("");
                    $("#username").css("border", "1px solid green")
                } else {
                    $("#msg").text(data["errorMsg"]);
                    $("#username").css("border", "1px solid red")
                }
            }, "json");
        } else {
            $("#username").css("border", "1px solid red")
        }
    })

    $("#password").blur(function () {
        if (checkpassword()) {
            $("#password").css("border", "1px solid green")
        } else {
            $("#password").css("border", "1px solid red")
        }
    })

    $("#email").blur(function () {
        if (checkemail()) {
            $("#email").css("border", "1px solid green")
        } else {
            $("#email").css("border", "1px solid red")

        }
    })
    $("#name").blur(function () {
        if (checkname()) {
            $("#name").css("border", "1px solid green")
        } else {
            $("#name").css("border", "1px solid red")

        }
    })
    $("#telephone").blur(function () {
        if (checktelephone()) {
            $("#telephone").css("border", "1px solid green")
        } else {
            $("#telephone").css("border", "1px solid red")

        }
    })
    $("#birthday").blur(function () {
        if (checkbirthday()) {
            $("#birthday").css("border", "1px solid green")
        } else {
            $("#birthday").css("border", "1px solid red")

        }
    });
    $("#check").blur(function () {
        if (checkcheck()) {
            $("#check").css("border", "1px solid green")
        } else {
            $("#check").css("border", "1px solid red")

        }
    })
    $("#registerForm").submit(function () {
        if (checkusername() && checkbirthday() && checkcheck() && checkemail() && checkname() && checkpassword() && checktelephone()) {
            $.post("user/register", $(this).serialize(), function (obj) {
                if (obj["flag"]) {
                    location.href = "register_ok.html";
                } else {
                    $("#msg").html(obj["errorMsg"]);
                }
            }, "json")
        }else{
            $("#msg").html("您有必填项填写错误或未填写");
        }
        return false;
    })
});