var r_id = 0;

$(function () {
  var param = getParameters();
  r_id = param["r_id"];

  getDetail();
});

function getDetail() {
  $.ajax({
    url: "http://localhost:8080/JMTrestaurantAPI/api/restaurant/detail",
    dataType: "json",
    type: "post",
    data: {
      r_id: r_id,
    },
    success: function (result) {
      if (result.status == "success") {
        var data = result.data;

        var m_id = data.m_id;
        var name = data.name;
        var addr = data.addr;
        var content = data.content;

        $("#name").val(name);
        $("#addr").val(addr);
        $("#content").val(content);

        if (localStorage.getItem("auth_token")) {
          $.ajax({
            url: "http://localhost:8080/JMTrestaurantAPI/api/member/detail",
            dataType: "json",
            type: "post",
            headers: {
              auth_check: "yes",
              auth_token: localStorage.getItem("auth_token"),
            },
            success: function (result) {
              if (result.status == "success") {
                var check_data = result.data;

                var check_m_id = check_data.m_id;

                if (check_m_id == m_id) {
                  $("#editBtn").css("display", "inline-block");
                  $("#removeBtn").css("display", "inline-block");
                }
              }
            },
          });
        }
      }
    },
  });
}

function edit() {
  var name = $("#name").val();
  var addr = $("#addr").val();
  var content = $("#content").val();

  if (!name || name == "") {
    $("#name").focus();
    return false;
  }
  if (!addr || addr == "") {
    $("#addr").focus();
    return false;
  }
  if (!content || content == "") {
    $("#content").focus();
    return false;
  }

  $.ajax({
    url: "http://localhost:8080/JMTrestaurantAPI/api/restaurant/edit",
    dataType: "json",
    type: "post",
    data: {
      r_id: r_id,
      name: name,
      addr: addr,
      content: content,
    },
    headers: {
      auth_check: "yes",
      auth_token: localStorage.getItem("auth_token"),
    },
    success: function (result) {
      if (result.status == "success") {
        if (result.resultCode) {
          location.replace("./index.html");
        }
      }
    },
  });
}

function remove() {
  $.ajax({
    url: "http://localhost:8080/JMTrestaurantAPI/api/restaurant/remove",
    dataType: "json",
    type: "post",
    data: {
      r_id: r_id,
    },
    headers: {
      auth_check: "yes",
      auth_token: localStorage.getItem("auth_token"),
    },
    success: function (result) {
      if (result.status == "success") {
        if (result.resultCode) {
          location.replace("./index.html");
        }
      }
    },
  });
}

function getParameters() {
  var url = location.search.substring(1);

  var parameterArr = url.split("&");
  var parameterObj = {};
  for (var value of parameterArr) {
    var obj = value.split("=");
    parameterObj[obj[0]] = obj[1];
  }

  return parameterObj;
}
