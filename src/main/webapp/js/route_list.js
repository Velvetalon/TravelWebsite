function load() {
    $.get("/route/queryPage" + location.search, {}, function (data) {
        if (data["flag"]) {
            data = data["data"];
            $("#search_input").val(data["keyword"])
            //拼接旅游线路列表
            var html = "";
            var dataList = data["dataList"]
            for (var index in dataList) {
                html += `<li>
                            <div class="img"><img src="${dataList[index]["rimage"]}" style="width: 299px; height: 169px"></div>
                            <div class="text1">
                                <p>${dataList[index]["rname"]}</p>
                                <br/>
                                <p>${dataList[index]["routeIntroduce"]}</p>
                            </div>
                            <div class="price">
                                <p class="price_num">
                                    <span>&yen;</span>
                                    <span>${dataList[index]["price"]}</span>
                                    <span>起</span>
                                </p>
                                <p><a href="route_detail.html">查看详情</a></p>
                            </div>
                        </li>`
            }
            $("#route_list").html(html);

            //生成统计信息
            $("#total_page").text(data["totalPage"]);
            $("#total_count").text(data["totalCount"]);

            //生成分页栏
            var currentPage = data["currentPage"];
            var totalPage = data["totalPage"];

            var start = currentPage - 5 > 0 ? currentPage - 5 : 1;
            var end = (start + 9) > totalPage ? totalPage : start + 9;

            html = `<li><a href="javascript:go(1);">首页</a></li>`
            html += `<li class="threeword"><a href="javascript:go(${currentPage - 1 > 0 ? currentPage - 1 : 1});">上一页</a></li>`

            for (var i = start; i < end + 1; i++) {
                html += `<li><a href="javascript:go(${i});">${i}</a></li>`
            }

            html += `<li class="threeword"><a href="javascript:go(${currentPage + 1 > totalPage ? totalPage : currentPage + 1});">下一页</a></li>`
            html += `<li class="threeword"><a href="javascript:go(${totalPage});">末页</a></li>`
        }
        $("#page_bar").html(html);
    }, "json");
}

function getCid() {
    return new URLSearchParams(location.search).get("cid");
}

function getURL() {
    return location.protocol + "//" + location.hostname + location.pathname
}

function go(page){
    var url = new URL(location.href);
    url.searchParams.set("currentPage",page);
    location.href = url.href;
}

//网页加载完成后加载数据
$(function () {
    load()
});
