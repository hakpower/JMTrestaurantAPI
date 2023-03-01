function add() {
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
    url: "http://localhost:8080/JMTrestaurantAPI/api/restaurant/add",
    dataType: "json",
    type: "post",
    data: {
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
