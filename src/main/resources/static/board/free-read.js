$(function () {
    $(".like").click(function () { // 좋아요 하는 기능
        let id = $(this).val();

        $.ajax({
            type:'POST',
            url:'/board/like',
            data:{
                id: id
            },
            dataType: 'json',

        }).done(function (data){
            if (data.result == "좋아요 성공"){
                alert(data.result);
                $("#"+id).attr("src","/images/like_red.svg");
                $('#likeNum' + id).html(data.likeCount);
            }else if (data.result == "좋아요 취소"){
                alert(data.result);
                $("#"+id).attr("src","/images/like.svg");
                $('#likeNum' + id).html(data.likeCount);
            }else {
                alert(data.result);
            }
        }).fail(function (){
          alert("error");

        })

    });
});