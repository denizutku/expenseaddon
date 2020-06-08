$( document ).ready(function() {

    // SUBMIT FORM
    $("#expenseForm").submit(function(event) {
        // Prevent the form from submitting via the browser.
        event.preventDefault();
        ajaxPost();
    });

    function ajaxPost(){

        // PREPARE FORM DATA
        var formData = new FormData();
        formData.append('description', $("#description").val());
        formData.append('amount', $("#amount").val());
        // Attach file
        formData.append('file', $('input[type=file]')[0].files[0]);
        formData.append('filename', $('input[type=file]')[0].files[0].name);

        // DO POST
        $.ajax({
            type : "POST",
            contentType: false, // NEEDED, DON'T OMIT THIS (requires jQuery 1.6+)
            processData: false,
            url :"api/expense/save",
            data : formData,
            headers: { 'Authorization': "JWT " + document.getElementById("token").value },
            success : function(result) {
                if(result.status == "Done"){
                    $("#postResultDiv").html("<p style='background-color:#7FA7B0; color:white; padding:20px 20px 20px 20px'>" +
                        "Post Successfully! <br>");
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

        // Reset FormData after Posting
        resetData();

    }

    function resetData(){
        $("#description").val("");
        $("#amount").val("");
        $("#file").val("");
    }
})