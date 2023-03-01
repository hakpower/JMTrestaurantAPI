async function join() {
  var m_id = $("#m_id").val();
  var password = $("#password").val();
  var password_re = $("#password_re").val();
  var name = $("#name").val();
  var email = $("#email").val();

  if (!m_id || m_id == "") {
    $("#m_id").focus();
    return false;
  }
  if (!password || password == "") {
    $("#password").focus();
    return false;
  }
  if (!password_re || password_re == "") {
    $("#password_re").focus();
    return false;
  }
  if (password != password_re) {
    $("#password").focus();
    return false;
  }
  if (!name || name == "") {
    $("#name").focus();
    return false;
  }
  if (!email || email == "") {
    $("#email").focus();
    return false;
  }

  var check = await isExists(m_id).then(function (value) {
    return value;
  });

  if (check) {
    return false;
  }

  $.ajax({
    url: "http://localhost:8080/JMTrestaurantAPI/api/member/add",
    dataType: "json",
    type: "post",
    data: {
      m_id: m_id,
      password: password,
      name: name,
      email: email,
      age: 30,
      gender: "M",
    },
    success: function (result) {
      if (result.status == "success") {
        if (result.resultCode) {
          location.replace("./login.html");
        }
      }
    },
  });
}

async function isExists(m_id) {
  var check = false;

  await $.ajax({
    url: "http://localhost:8080/JMTrestaurantAPI/api/member/detail",
    dataType: "json",
    type: "post",
    data: {
      m_id: m_id,
    },
    success: function (result) {
      if (result.status == "success") {
        if (result.data) {
          check = true;
        } else {
          check = false;
        }
      }
    },
    error: function () {
      check = true;
    },
  });

  return check;
}
