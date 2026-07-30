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
$("#userSelect").select2({

    placeholder:"Search user...",

    width:"100%",
    allowClear:true


});


$("#bookSelect").select2({

    placeholder:"Search available book...",

    width:"100%",
    allowClear:true


});
});