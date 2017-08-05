function postLogout()
{
    $.ajax({
            type: 'POST',
            url :'/dm/logout',
            success :function (data)
             {
                 window.location.href = '/dm/login';
            }
        }
    );
}