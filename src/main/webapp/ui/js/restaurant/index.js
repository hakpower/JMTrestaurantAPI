var currentPageNum = 1;
var countDataInPage = 10;
var countInPageGroup = 5;
var searchColumn = "";
var searchValue = "";

$(function () {
  getList();
});

function searchResult() {
  currentPageNum = 1;
  searchColumn = document.getElementById("searchColumn").value;
  searchValue = document.getElementById("searchValue").value;

  $(".tableContainer table").css("opacity", "0");
  setTimeout(getList, 250);
}

function clickPage(PageNum) {
  currentPageNum = PageNum;

  $(".tableContainer table").css("opacity", "0");
  setTimeout(getList, 250);
}

function getList() {
  $.ajax({
    url: "http://localhost:8080/JMTrestaurantAPI/api/restaurant/list",
    type: "post",
    dataType: "json",
    data: {
      currentPageNum: currentPageNum,
      countDataInPage: countDataInPage,
      countInPageGroup: countInPageGroup,
      searchColumn: searchColumn,
      searchValue: searchValue,
    },
    headers: {
      auth_check: "no",
    },
    success: function (result) {
      if (result.status == "success") {
        var data = result.data;
        var totalDataCount = result.totalDataCount;

        tbodyHTML = "";
        for (value of data) {
          tbodyHTML += "<tr>";
          tbodyHTML += "<td>" + value.num + "</td>";
          tbodyHTML +=
            "<td><a href='./detail.html?r_id=" +
            value.r_id +
            "'>" +
            value.name +
            "</a></td>";
          tbodyHTML += "<td>" + value.addr + "</td>";
          tbodyHTML += "<td>" + value.m_id + "</td>";
          tbodyHTML += "<td>" + value.reg_date + "</td>";
          tbodyHTML += "</tr>";
        }
        $("#listContainer").html(tbodyHTML);

        $(".tableContainer table").css("opacity", "1");

        var countTotalPage = Math.ceil(totalDataCount / countDataInPage);
        var PageGroup = Math.floor(currentPageNum / countInPageGroup);
        if (
          PageGroup > 0 &&
          Math.floor(currentPageNum % countInPageGroup) == 0
        ) {
          PageGroup -= 1;
        }
        var startPageGroupNum = PageGroup * countInPageGroup + 1;
        var endPageGroupNum = startPageGroupNum + (countInPageGroup - 1);
        if (endPageGroupNum >= countTotalPage) endPageGroupNum = countTotalPage;
        var pageGroupHTML = "";

        if (endPageGroupNum > countInPageGroup)
          pageGroupHTML +=
            '<span class="active" onclick="clickPage(' +
            (startPageGroupNum - 2) +
            ')">이전</span>';
        console.log(
          "currentPageNum : " + currentPageNum,
          " PageGroup : " + PageGroup,
          " startPageGroupNum : " + startPageGroupNum,
          " endPageGroupNum : " + endPageGroupNum
        );
        var activeClass = "";
        for (var i = startPageGroupNum; i <= endPageGroupNum; i++) {
          activeClass = "";
          if (currentPageNum == i) activeClass = "active";
          pageGroupHTML +=
            '<span class="' +
            activeClass +
            '" onclick="clickPage(' +
            i +
            ')">' +
            i +
            "</span>";
        }
        if (endPageGroupNum < countTotalPage)
          pageGroupHTML +=
            '<span class="active" onclick="clickPage(' +
            (endPageGroupNum + 1) +
            ')">다음</span>';
        $("#pagination").html(pageGroupHTML);
      }
    },
  });
}
