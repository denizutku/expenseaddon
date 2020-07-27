let url;

$( document ).ready(function() {

    $("#expenseForm").submit(function(event) {
        event.preventDefault();
        ajaxPost();
    });

    function ajaxPost(){

        var fd = new FormData();
        var files = $('#file')[0].files[0];
        fd.append('image',files)

        $.ajax({
            url: 'https://api.imgur.com/3/image',
            headers: {
                'Authorization': 'Client-ID 43b4dfd081c3806'
            },
            type: 'POST',
            data: fd,
            async: false,
            processData: false,
            contentType: false,
            success: function(response) {
                url = response.data.link;
            }
        });
    }
})

$( document ).ready(function() {

    $("#expenseForm").submit(function(event) {
        event.preventDefault();
        ajaxPost();
    });

    function ajaxPost(){

        var formData = new FormData();
        formData.append('description', $("#description").val());
        formData.append('amount', $("#amount").val());
        formData.append('file', url);
        $.ajax({
            type : "POST",
            contentType: false,
            processData: false,
            url :"api/expense/save",
            data : formData,
            headers: { 'Authorization': "JWT " + document.getElementById("token").value },
            success : function(result) {
                if(result.status == "Done"){
                    $("#postResultDiv").html("<p style='background-color:#0052cc; color:white; padding:20px 20px 20px 20px'>" +
                        "Expense added! <br>");
                }else{
                    $("#postResultDiv").html("<strong>Error</strong>");
                }
                console.log(result);
            },
            error : function(e) {
                alert("Error!")
                console.log("ERROR: ", e);
            }
        });
        resetData();
    }

    function resetData(){
        $("#description").val("");
        $("#amount").val("");
        $("#file").val("");
    }
})