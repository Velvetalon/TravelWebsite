$(function () {
    $("#loginForm").submit(function () {
        $.post("user/login", $("#loginForm").serialize(), function (data) {
            if (data["flag"]) {
                alert(data["errorMsg"])
                location.href = "./index.html";
            } else {
                $("#checkImg").click();
                $("input[name='check']").value("");
                $("#errorMsg").text(data["errorMsg"]);
                $("#errorMsg").show();
            }
        }, "json");
        return false;
    });

    $("input[type=text],input[type=password]").focus(function () {
        $("#errorMsg").hidden
    });


})