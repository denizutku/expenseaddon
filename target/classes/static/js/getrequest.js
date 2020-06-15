$( document ).ready(function() {

    // GET REQUEST
    $("#getExpenses").click(function(event){
        event.preventDefault();
        ajaxGet();
    });

    // DO GET
    function ajaxGet(){
        $.ajax({
            type : "GET",
            url : "api/expense/all",
            headers: { 'Authorization': "JWT " + document.getElementById("token").value },
            success: function(result){
                if(result.status == "Done"){
                    $('#getResultDiv ul').empty();
                    $.each(result.data, function(i, expense){
                        var expense = '<tr> <td> ' + i + ')   ' + '</td> <td>'   + expense.description + '</td> <td>' + expense.amount + ' </td><td> <img src="tmp/' +expense.filename +'" style="width:50px;height:50px;" alt="Image not found"></td> <td><button class="delete-expense" value="' +expense.id+'">Delete</button></td> <td><form><button class="edit-expense" value="' +expense.id+'">Edit</button>  </td> </tr>';
                        $('#getResultDiv .list-group').append(expense)
                    });
                    console.log("Success: ", result);
                }else{
                    $("#getResultDiv").html("<strong>Error</strong>");
                    console.log("Fail: ", result);
                }
            },
            error : function(e) {
                $("#getResultDiv").html("<strong>Error</strong>");
                console.log("ERROR: ", e);
            }
        });
    }
})