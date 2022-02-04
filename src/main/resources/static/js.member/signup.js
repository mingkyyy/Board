$(function () {
    $('#signup').click(function () {
        if ($('#hidden').val() == "aa"){
            alert("핸드폰 인증에 성공하셔야 합니다.");
            return false;
        }

        if ($('#name').val()==''){
            alert("이름을 입력하세요");
            $('#name').focus();
            return false;
        }

        if ($('#nickname').val()==''){
            alert("닉네임을 입력하세요");
            $('#nickname').focus();
            return false;
        }

        if ($('#password').val()==''){
            alert("비밀번호를 입력하세요");
            $('#password').focus();
            return false;
        }

        if ($('#repassword').val()==''){
            alert("비밀번호를 다시 한번 더 입력하세요");
            $('#repassword').focus();
            return false;
        }

        if ($('#email').val()==''){
            alert("이메일을 입력하세요");
            $('#email').focus();
            return false;
        }

        if ($('#repassword').val()!=$('#password').val()){
            alert("비밀번호를 둘다 동일하게 입력하세요");
            $('#repassword').focus();
            return false;
        }
        var emailRegex = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
        if (!emailRegex.test($('#email').val())){
            alert("이메일 형식이 아닙니다");
            $('#email').focus();
            return false;
        }

        var passwordReges=/^[A-Za-z0-9]{6,12}$/;
        if (passwordReges.test($('#password').val())){
            alert("비밀번호는 숫자와 문자 포함 형태의 6~12자 이여야 합니다.");
            $('#password').focus();
            return false;

        }
            let data = {
                name: $('#name').val(),
                nickname: $('#nickname').val(),
                password: $('#password').val(),
                email: $('#email').val(),
                tel : $('#tel').val()
            };

            $.ajax({
                type: 'POST',
                url: '/signup',
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function () {
                alert('회원가입이 완료되었습니다.');
                window.location.href = '/';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        });

    $('#sendPhoneNumber').click(function(){
        let phoneNumber = $('#tel').val();
        if (phoneNumber == null){
            alert("핸드폰 번호를 입력해주세요")
            return;
        }
        alert("인증번호가 전송되었습니다");
        $.ajax({
            type: "GET",
            url: "/check/sendSMS",
            data: {
                "phoneNumber" : phoneNumber
            },
            success: function(res){
                $('#checkBtn').click(function(){
                    if($.trim(res) ==$('#inputCertifiedNumber').val()){
                        $('#hidden').val("zzz");
                        alert("핸드폰 인증에 성공하였습니다.");
                    }else{
                        alert("인증번호가 다릅니다.");
                    }
                })


            }
        })
    });
});