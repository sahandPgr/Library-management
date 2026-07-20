document.addEventListener("DOMContentLoaded", function () {

    setTimeout(function () {

        document.querySelectorAll(".auto-dismiss").forEach(function (alert) {

            let bsAlert = bootstrap.Alert.getOrCreateInstance(alert);

            bsAlert.close();

        });

    }, 5000);

});