$(function () {
    $.get("/route/queryDetails" + location.search, {}, function (data) {
        if (data["flag"]) {
            data = data["data"];
            var seller = data["seller"]
            var routeImgList = data["routeImgList"]

            //设置标题和价格
            $(".pros_title").text(data["rname"]);
            $(".hot").text(data["routeIntroduce"]);
            $(".price > strong").text(data["price"]);

            //设置商家信息
            $("#sname").text(seller["sname"]);
            $("#consphone").text(seller["consphone"]);
            $("#address").text(seller["address"]);

            //设置图片
            var head_img = $(".little_img");
            var imgList = [];
            for (var index in routeImgList) {
                let clone = head_img.clone()
                $(clone).attr("data-bigpic", routeImgList[index]["bigPic"])
                $(clone).children("img").prop("src", routeImgList[index]["smallPic"])
                imgList.push(clone);
            }
            head_img.after(imgList);
            head_img.remove();

            //设置收藏数量
            $("#count").text(data["count"]);

            //绑定事件
            bindEvent();
        } else {
            $("html").html("<h1 align='center'>警告你别给我整这些花里胡哨的</h1>")
        }
    }, "json");

    //查询收藏状态
    $.post("/route/favoriteStatus", {
        rid: new URLSearchParams(location.search).get("rid")
    }, function (data) {
        if (data["flag"]) {
            if (!data["data"]) {
                $("#favorite").show();
            } else {
                $("#unfavorite").show();
            }
        }else{
            $("#favorite").attr('disabled',"true");
            $("#favorite").show();
        }
    }, "json");

    $("#favorite").click(function () {
        $.post("/route/favorite", {
            flag: "true",
            rid: new URLSearchParams(location.search).get("rid")
        }, function (data) {
            if (data["flag"]) {
                $("#favorite").hide();
                $("#unfavorite").show();
            } else {
                if (confirm("您还未登录，请登录后再执行操作！是否要为您跳转登录页面？"))
                    location.href = "login.html";
            }
        }, "json")
    })

    $("#unfavorite").click(function () {
        $.post("/route/favorite", {
            flag: "false",
            rid: new URLSearchParams(location.search).get("rid")
        }, function (data) {
            if (data["flag"]) {
                $("#favorite").show();
                $("#unfavorite").hide();
            }else {
                if (confirm("您还未登录，请登录后再执行操作！是否要为您跳转登录页面？"))
                    location.href = "login.html";
            }
        }, "json")
    })
});