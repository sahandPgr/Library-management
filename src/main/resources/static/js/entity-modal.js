const entityConfig = {

    category: {
        url: "/api/categories",
        select: "#categorySelect",
        label: "name"
    },

    publisher: {
        url: "/api/publishers",
        select: "#publisherSelect",
        label: "name"
    },

    author: {
        url: "/api/authors",
        select: "#authorSelect",
        label: "fullName"
    }

};
document.addEventListener(
    "DOMContentLoaded",
    function () {
        const modalElement =
            document.getElementById("entityModal");

        const modal =
            new bootstrap.Modal(modalElement);

        const saveBtn =
            document.getElementById("saveEntityBtn");


        const entityType =
            document.getElementById("entityType");


        document.querySelectorAll(".add-entity-btn")
            .forEach(btn => {


                btn.addEventListener(
                    "click",
                    function () {


                        let type =
                            this.dataset.type;
                        document.getElementById(
                            "entityType"
                        ).value = type;
                        document.getElementById(
                            "entityModalTitle"
                        ).innerText =
                            "Add " + type;



                        loadEntityForm(type);


                        modal.show();


                    });


            });
        saveBtn.addEventListener("click", function () {
            let type =
                entityType.value;

            if (type === "category" || type === "publisher") {


                let name =
                    document.getElementById("name")
                        .value
                        .trim();


                if (name === "") {

                    alert("Name is required");
                    return;

                }

            }



            if (type === "author") {


                let fullName =
                    document.getElementById("fullName")
                        .value
                        .trim();


                if (fullName === "") {

                    alert("Full name is required");
                    return;

                }

            }

            let config = entityConfig[type];

            let url = config.url;


            const csrfToken =
                document.querySelector('meta[name="_csrf"]').content;


            const csrfHeader =
                document.querySelector('meta[name="_csrf_header"]').content;
            let data =
                createEntityObject(type);
            fetch(url, {


                method: "POST",


                headers: {

                    "Content-Type": "application/json",

                    [csrfHeader]: csrfToken

                },


                body: JSON.stringify(data)


            })



                .then(response => {


                    if (!response.ok) {

                        throw new Error("Save failed");

                    }


                    return response.json();


                })



                .then(data => {


                    addOptionToSelect(
                        type,
                        data
                    );


                    modal.hide();


                })



                .catch(error => {


                    console.log(error);

                    alert("Something went wrong");


                });



        });

    });



function addOptionToSelect(type, data) {


    let config = entityConfig[type];


    let text =
        data[config.label];


    let option =
        new Option(
            text,
            data.id,
            true,
            true
        );



    $(config.select)
        .append(option)
        .trigger("change");


}

function loadEntityForm(type) {
    const container =
        document.getElementById("entityFields");
    container.innerHTML = "";

    if (type === "category") {


        container.innerHTML = `

<label>Name</label>

<input id="name"
class="form-control">


`;


    }


    if (type === "publisher") {


        container.innerHTML = `

<label>Name</label>

<input id="name"
class="form-control mb-3">


<label>Address</label>

<input id="address"
class="form-control mb-3">


<label>Phone</label>

<input id="phone"
class="form-control">


`;


    }
    if (type === "author") {


        container.innerHTML = `


<label>Full Name</label>

<input id="fullName"
       class="form-control mb-3">



<label>Nationality</label>

<input id="nationality"
       class="form-control mb-3">



<label>Birth Date</label>

<input type="date"
       id="birthDate"
       class="form-control">


`;

    }


}

function createEntityObject(type) {


    if (type === "category") {

        return {

            name:
                document.getElementById("name").value

        };

    }



    if (type === "publisher") {

        return {


            name:
                document.getElementById("name").value,


            address:
                document.getElementById("address").value,


            phone:
                document.getElementById("phone").value


        };


    }

    if (type === "author") {


        return {


            fullName:
                document.getElementById("fullName").value,


            nationality:
                document.getElementById("nationality").value,


            birthDate:
                document.getElementById("birthDate").value


        };


    }


}