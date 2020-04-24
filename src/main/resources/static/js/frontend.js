var entry_point = "http://localhost:8080/api";
var pageAndSort = "?page=0&size=5&sort=description,desc"
var items = [];
var presentSort = "description,desc";
var presentPageNumber = 0;
var presentPageSize = 2;
var presentSearchParameter = null;

$(document).ready(function () {
    // fetchAllItems();

    $("#first-drop-down-id").change(function () {
        var currentSort = $(this).children("option:selected").val();
        presentSort = currentSort;
        presentPageNumber=0;
    });

    $("#second-drop-down-id").change(function () {
        var recentSort = $(this).children("option:selected").val();
        presentPageSize = parseInt(recentSort);
        presentPageNumber=0;
    });

    $("#search-button").click(function () {
        presentPageNumber=0;
        presentSearchParameter = $("#search-id").val();
        if(presentSearchParameter==null || presentSearchParameter==""){
            presentSearchParameter="*";
        }
        getSearchResult();
    });

    $("#next-button-id").click(function () {
        presentPageNumber++;
        getSearchResult();
    });

    $("#previous-button-id").click(function () {
        presentPageNumber--;
        getSearchResult();
    });

    $("#item-list-id").change(function () {
        var selectValues = $(this).children("option:selected").val();
        getItemDetails(selectValues);
    });
    $("#create-button-id").click(function () {

    });

    $("#clear-button-task").click(function () {
        $("#quantity-id").val("");
        $("#id-id").val("");
        $("#name-id").val("");
        $("#price-id").val("");
        $("#description-id").val("");
    });

    $("#create-button-id").click(function () {
        var itemName = $("#name-id").val();
        var itemDescription = $("#description-id").val();
        var itemQuantity = $("#quantity-id").val();
        var itemPrice = $("#price-id").val();
        var creation = {itemName: itemName, itemPrice:itemPrice,description:itemDescription};
        createNewItem(creation, itemQuantity);
    })


    $("#update-button-id").click(function () {
        var itemName = $("#name-id").val();
        var itemId = $("#id-id").val();
        var itemDescription = $("#description-id").val();
        var itemQuantity = $("#quantity-id").val();
        var itemPrice = $("#price-id").val();
        var update = {itemName: itemName, itemPrice:itemPrice,description:itemDescription, itemId: itemId};
        updateItem(update, itemQuantity);
    })

});

function getSearchResult() {
    pageAndSort = "?page=" + presentPageNumber + "&size=" + presentPageSize + "&sort=" + presentSort;
    var url = entry_point + "/keyword-search/" + presentSearchParameter + pageAndSort;
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json', // added data type
        success: function (res) {
            $("#item-list-id").empty();
            console.log(res);
            $("#search-result-selector").empty();
            $.each(res.content, function (index, value) {
                var optionString = `<option value="${value.itemId}"> ${value.itemName} : ${value.itemDescription} </option>`;
                $("#item-list-id").append(optionString);
            });
            var pageCount = "page  " + (parseInt(res.currentPage) + 1) + " of " + res.pageCount;

            document.getElementById("span-text-id").innerHTML = pageCount;
            //alert(res);
            var initialPage = res.firstPage;
            var finalPage = res.lastPage;

            if(res.pageCount == 0) {
                document.getElementById("previous-button-id").style.visibility = "hidden";
                document.getElementById("next-button-id").style.visibility="hidden";
                document.getElementById("span-text-id").style.visibility="hidden";
            }

            else if(initialPage) {
                document.getElementById("previous-button-id").style.visibility = "hidden";
                document.getElementById("next-button-id").style.visibility="visible";
                document.getElementById("span-text-id").style.visibility="visible";
            }
            else if(finalPage) {
                document.getElementById("next-button-id").style.visibility="hidden";
                document.getElementById("previous-button-id").style.visibility = "visible";
                document.getElementById("span-text-id").style.visibility="visible";
            }
            else if(finalPage && initialPage) {
                document.getElementById("next-button-id").style.visibility="hidden";
                document.getElementById("previous-button-id").style.visibility = "hidden";
                document.getElementById("span-text-id").style.visibility="visible";
            }
            else {
                document.getElementById("next-button-id").style.visibility="visible";
                document.getElementById("previous-button-id").style.visibility = "visible";
                document.getElementById("span-text-id").style.visibility="visible";
            }

        },
        error: function (jqXHR, status, err) {
            alert("Local error callback.");
        }
    });


    function fetchAllItems() {
        var url = entry_point + "/keyword-search/*" + pageAndSort;
        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'json', // added data type
            success: function (res) {
                $("#item-list-id").empty();
                console.log(res);
                $("#search-result-selector").empty();
                $.each(res.content, function (index, value) {
                    var optionString = `<option value="${value.itemId}"> ${value.itemName} : ${value.itemDescription} </option>`;
                    $("#item-list-id").append(optionString);
                });

                //alert(res);
            },
            error: function (jqXHR, status, err) {
                alert("Local error callback.");
            }
        });
    }
}


function getItemDetails(itemId){
    $.ajax({
        url: entry_point+"/get-item-by-id/"+itemId,
        type: 'GET',
        dataType: 'json', // added data type
        success: function(res) {
            console.log(res);
            var itemDetailsFetched = res;
            $("#id-id").val(itemDetailsFetched.itemId);
            $("#name-id").val(itemDetailsFetched.itemName);
            $("#description-id").val(itemDetailsFetched.description);
            $("#price-id").val(itemDetailsFetched.itemPrice);
            fetchSelectedItemQuantity(itemId);

        },
        error: function (jqXHR, status, err) {
            if(jqXHR.responseJSON == null){
                alert("Unable to connect to service.")
            }
            else {
                var errorResponse = jqXHR.responseJSON;
                alert("ERROR : " + errorResponse.error + ". MESSAGE : " + errorResponse.message);
            }
        }
    });
}

function fetchSelectedItemQuantity(itemId){
    $.ajax({
        url: entry_point+"/stock/get-stock-by-item-id/"+itemId,
        type: 'GET',
        dataType: 'json', // added data type
        success: function(res) {
            console.log(res);
            var fetchedItemStock = res;
            $("#quantity-id").val(fetchedItemStock.quantity);

        },
        error: function (jqXHR, status, err) {
            $("#quantity-id").val("");
            if(jqXHR.responseJSON == null){
                alert("Error connecting...")
            }
            else {
                var errorResponse = jqXHR.responseJSON;
                alert("Notice : " + errorResponse.error + ". Message : " + errorResponse.message);
            }
        }
    });
}


function createNewItem(item, quantity){
    item = JSON.stringify(item);
    $.ajax({
        url: entry_point+"/add-item/",
        type: 'POST',
        contentType: "application/json; charset=utf-8",
        data:item,
        dataType: 'json', // added data type
        success: function(res) {
            console.log(res);
            $('#id-id').val(res.itemId);
            var stockJsonToBeCreated = {quantity: quantity, item: {itemId: res.itemId}};
            createNewStock(stockJsonToBeCreated);
            //TODO: RETRIEVE QUANTITY VALUE AND CREATE NEW QUANTITY FOR THIS ITEM.

        },
        error: function (jqXHR, status, err) {
            $("#quantity-id").val("");
            if(jqXHR.responseJSON == null){
                alert("Error connecting...")
            }
            else {
                var errorResponse = jqXHR.responseJSON;
                alert("Notice : " + errorResponse.error + ". Message : " + errorResponse.message);
            }
        }
    });
}


function createNewStock(stock){
    stock = JSON.stringify(stock);
    $.ajax({
        url: entry_point+"/stock/add-stock/",
        type: 'POST',
        contentType: "application/json; charset=utf-8",
        data:stock,
        dataType: 'json', // added data type
        success: function(res) {
            console.log(res);
            alert("CREATED SUCCESSFULLY");
        },
        error: function (jqXHR, status, err) {
            $("#quantity-id").val("");
            if(jqXHR.responseJSON == null){
                alert("Error connecting...")
            }
            else {
                var errorResponse = jqXHR.responseJSON;
                alert("Notice : " + errorResponse.error + ". Message : " + errorResponse.message);
            }
        }
    });
}


function updateItem(item, quantity){
    item = JSON.stringify(item);
    $.ajax({
        url: entry_point+"/update-item/",
        type: 'PUT',
        contentType: "application/json; charset=utf-8",
        data:item,
        dataType: 'json', // added data type
        success: function(res) {
            console.log(res);
            $('#id-id').val(res.itemId);
            var stockJsonToBeUpdated = {quantity: quantity, item: {itemId: res.itemId}};
            updateStock(stockJsonToBeUpdated);
        },
        error: function (jqXHR, status, err) {
            $("#quantity-id").val("");
            if(jqXHR.responseJSON == null){
                alert("Error connecting...")
            }
            else {
                var errorResponse = jqXHR.responseJSON;
                alert("Notice : " + errorResponse.error + ". Message : " + errorResponse.message);
            }
        }
    });
}

function updateStock(stock){
    stock = JSON.stringify(stock);
    $.ajax({
        url: entry_point+"/stock/update-stock",
        type: 'PUT',
        contentType: "application/json; charset=utf-8",
        data:stock,
        dataType: 'json', // added data type
        success: function(res) {
            console.log(res);
            alert("UPDATED SUCCESSFULLY");
        },
        error: function (jqXHR, status, err) {
            $("#quantity-id").val("");
            if(jqXHR.responseJSON == null){
                alert("Error connecting...")
            }
            else {
                var errorResponse = jqXHR.responseJSON;
                alert("Notice : " + errorResponse.error + ". Message : " + errorResponse.message);
            }
        }
    });
}
