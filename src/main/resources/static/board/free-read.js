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

    $('.deleteFree').click(function (){
       let id = $(this).val();
       if (confirm("삭제 하겠습니까?")){
           $.ajax({
               type: 'DELETE',
               URL:'/board/free/read/'+id,
               dataType: 'json',
           }).done(function (){
               alert("삭제 완료 했습니다.");
               window.location.href='/board/free';
           }).fail(function (error){
               alert(JSON.stringify(error));
           })
       }else {

       }
    });
});