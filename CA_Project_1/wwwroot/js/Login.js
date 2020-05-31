
$(document).ready(function () {

    $("#loginBtn").on("click", function (e) {
        e.preventDefault();
        var username = $("#uname").val();
        var password = $("#pwd").val();
        $("#hashPwd").val(CryptoJS.SHA256(password).toString());
        var hashPwd = $("#hashPwd").val();


        if (username.length == 0 || password.length == 0) {
            $("#errmsg").html("Both Username and Password are required.");
            return;
        }
        var $btn = $(this);
        $btn.attr("class", "btn btn-primary disabled");
        $btn.text("verifying...");
        Post("/Login/Verify", { Username: username, Password: hashPwd })
            .then(function (response) {
                var result = JSON.parse(response);
                console.log(result);
                $btn.attr("class", "btn btn-success");
                $btn.text("Login");
                if (result.ErrMsg == null) {
                    window.location.replace(result.Value);
                } else {
                    $("#errmsg").html(result.ErrMsg);
                }
            })
            .catch(function (err) {
                console.log("err", err);
                $btn.attr("class", "btn btn-success");
                $btn.text("Login");
            })
    });
});

