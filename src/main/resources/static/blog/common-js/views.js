function pageLoaded(id) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    
    $.ajax({
        type: 'POST',
        url: '/api/viewed/' + id,
        contentType: "application/json",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        dataType: 'json',
        success: function() {
            window.location.reload(false);
        }
    })
}