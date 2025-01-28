let array;
let arrayCards;
function showCategories() {
    $("#category_table tbody").html("");
    $.ajax({
        type: "GET",
        url: 'category/1',
        success: [function (result) {
            let categories = result.data;
            array = categories;
            for (let i = 0; i < categories.length; i++) {
                let markup = "<tr>" +
                    "<td>" + categories[i].name + "</td>"
                    + `<td style="text-align: center"><a href="#" id="change_${categories[i].id}"><i class="fa fa-edit" style="font-size:20px"></i></a></td>`
                    + `<td style="text-align: center"><a href="#" id="delete_${categories[i].id}"><i class="fa fa-trash" style="font-size:20px"></i></a></td>`
                    + `<td style="text-align: center"><button type="button" class="btn btn-primary" id="show_${categories[i].id}">Show</button>`;
                $("#category_table tbody").append(markup);
            }
        }],
        error: [function (e) {
            alert("error");
            alert(JSON.stringify(e));
        }]
    });
}

$(document).ready(function () {
    $('#add_category_button').click(function () {
        let name = $('#add_category_name').val();
        let category = JSON.stringify({
            'name': name
        });
        $.ajax({
            type: "POST",
            url: 'category/1',
            contentType: 'application/json',
            data: category,
            success: [function (result) {
                $('#add_category_name').val('');
                showCategories();
            }],
            error: [function () {
                alert("error");
                $('#add_category_name').val('');
            }]
        });
    });

    $('#category_table tbody').on("click", "a", function () {
        let arr = $(this).attr('id').split('_');
        let id = parseInt(arr[1]);
        let value = arr[0];
        if (value === "change") {
            let obj = array.find(x => x.id === id);
            $('#modalChangeCategory').modal('show');
            $('#change_category_id').val(id);
            $('#change_category_name').val(obj.name);
        } else {
            deleteCategory(id);
        }
    });

    $('#category_table tbody').on("click", "button", function () {
        let arr = $(this).attr('id').split('_');
        let id = parseInt(arr[1]);
        $('#add_card_idCategory').val(id);
        showCards(id);
    });


    $('#change_category_button').click(function () {
        let id = $('#change_category_id').val();
        let name = $('#change_category_name').val();
        let category = JSON.stringify({
            'id': id,
            'name': name
        });
        $.ajax({
            type: "PUT",
            url: `category`,
            contentType: 'application/json',
            data: category,
            success: [function (result) {
                $('#change_category_name').val('');
                showCategories();
            }],
            error: [function (e) {
                alert("error");
                alert(JSON.stringify(e));
            }]
        });
    });

    $('#add_card_button').click(function () {
        let question = $('#add_card_question').val();
        let answer = $('#add_card_answer').val();
        let idCategory = $('#add_card_idCategory').val();
        let card = JSON.stringify({
            'question': question,
            'answer': answer,
            'idCategory': idCategory
        });
        $.ajax({
            type: "POST",
            url: `card/${idCategory}`,
            contentType: 'application/json',
            data: card,
            success: [function (result) {
                $('#add_card_question').val('');
                $('#add_card_answer').val('');
                showCards(idCategory);
            }],
            error: [function () {
                alert("error");
                $('#add_card_question').val('');
                $('#add_card_answer').val('');
            }]
        });
    });

    $('#card_table tbody').on("click", "a", function () {
        let arr = $(this).attr('id').split('_');
        let id = parseInt(arr[1]);
        let value = arr[0];
        if (value === "change") {
            let obj = arrayCards.find(x => x.id === id);
            $('#modalChangeCard').modal('show');
            $('#change_card_id').val(id);
            $('#change_card_question').val(obj.question);
            $('#change_card_answer').val(obj.answer);
        } else {
            deleteCard(id);
        }
    });

    $('#change_card_button').click(function () {
        let id = $('#change_card_id').val();
        let question = $('#change_card_question').val();
        let answer = $('#change_card_answer').val();
        let idCategory = $('#add_card_idCategory').val();
        let card = JSON.stringify({
            'id': id,
            'question': question,
            'answer' : answer
        });
        $.ajax({
            type: "PUT",
            url: `card`,
            contentType: 'application/json',
            data: card,
            success: [function (result) {
                $('#change_category_name').val('');
                showCards(idCategory);
            }],
            error: [function (e) {
                alert("error");
                alert(JSON.stringify(e));
            }]
        });
    });
});

function showCards(id) {
    $("#card_table tbody").html("");
    $.ajax({
        type: "GET",
        url: `card/idCategory/${id}`,
        success: [function (result) {
            let cards = result.data;
            arrayCards = cards;
            for (let i = 0; i < cards.length; i++) {
                let markup = "<tr>" +
                    "<td>" + cards[i].question + "</td>" +
                    "<td>" + cards[i].answer + "</td>" +
                    "<td>" + cards[i].creationDate + "</td>"
                    + `<td style="text-align: center"><a href="#" id="change_${cards[i].id}"><i class="fa fa-edit" style="font-size:20px"></i></a></td>`
                    + `<td style="text-align: center"><a href="#" id="delete_${cards[i].id}"><i class="fa fa-trash" style="font-size:20px"></i></a></td>`;
                $("#card_table tbody").append(markup);
            }
        }],
        error: [function (e) {
            alert("error");
            alert(JSON.stringify(e));
        }]
    });
}
function deleteCategory(id) {
    $.ajax({
        type: "DELETE",
        url: `category/${id}`,
        success: [function (result) {
            showCategories();
        }],
        error: [function (e) {
            alert(JSON.stringify(e));
        }]
    });
}

function deleteCard(id) {
    let idCategory = $('#add_card_idCategory').val();
    $.ajax({
        type: "DELETE",
        url: `card/${id}`,
        success: [function (result) {
            showCards(idCategory);
        }],
        error: [function (e) {
            alert(JSON.stringify(e));
        }]
    });
}