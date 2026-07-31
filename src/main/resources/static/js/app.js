document.addEventListener("DOMContentLoaded", function () {

    setTimeout(function () {

        document.querySelectorAll(".auto-dismiss").forEach(function (alert) {

            let bsAlert = bootstrap.Alert.getOrCreateInstance(alert);

            bsAlert.close();

        });

    }, 5000);

});

const borrowDate = document.getElementById("borrowDate");
const dueDate = document.getElementById("dueDate");

function validateDates() {

    if (
        borrowDate.value &&
        dueDate.value &&
        dueDate.value < borrowDate.value
    ) {

        dueDate.setCustomValidity(
            "Due date cannot be before borrow date."
        );

    } else {

        dueDate.setCustomValidity("");

    }

}

borrowDate.addEventListener("change", validateDates);
dueDate.addEventListener("change", validateDates);
borrowDate.addEventListener("change", function () {

    dueDate.min = this.value;

    if (dueDate.value && dueDate.value < this.value) {
        dueDate.value = "";
    }

});