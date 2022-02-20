$(function () {
    $('#nicknameUpdate').click(function () {

        let nickname = $('#nickname').val()

        let id = $('#memberId').val();


        if (nickname == '') {
            alert("변경햘 닉네임을 입력해주세요");
            return false;
        }

        $.ajax({
            type: 'PUT',
            url: '/nicknameChange/'+id,
            dataType: 'json',
            data: {
              nickname : nickname
            },
            success: function (data) {
                if (data.result == "닉네임 수정 완료되었습니다.") {
                    alert(data.result);
                    $('#nickname').html(data);
                } else if (data.result == "중복된 닉네임 입니다.") {
                    alert(data.result);
                }else {
                    alert(data.result);
                }
            }
        });
        });
    });