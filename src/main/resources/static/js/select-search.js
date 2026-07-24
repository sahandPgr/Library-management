$(document).ready(function () {


    $("#categorySelect").select2({

        placeholder:
            "Search category",

        width:
            "100%",
        allowClear:
            true,
    });

    $("#publisherSelect").select2({

    placeholder:"Search publisher",
    width:"100%",
    allowClear:true

});

   $("#authorSelect").select2({

    placeholder:"Search author",
    width:"100%",
    allowClear:true

});

});