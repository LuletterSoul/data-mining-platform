var saltEntry={tmpId:null,disposableSalt:''};
var contextPath;
$("#form-signin").submit(function (e)
{
    e.preventDefault();
    console.log("submit login form");
    postLoginForm();
});
$(function()
{
    contextPath = getContextPath();
});

function getEncryption(password,rowKey)
{
    var key = CryptoJS.enc.Utf8.parse(rowKey);
    var submittedPlaitText = CryptoJS.enc.Utf8.parse(password);
    var encrypted = CryptoJS.AES.encrypt(submittedPlaitText, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});
    console.log("complete simple encrypt");
    return encrypted.toString();
}
function getDisposableSalt()
{
    var preSaltId = saltEntry.tmpId;
    $.ajax
    (
        {
            type :'POST',
            url : contextPath+'/disposableSalt.json',
            data :  JSON.stringify(preSaltId),
            dataType : 'json',
            contentType: 'application/json;charset=utf-8',
            async:false,
            success : function (data)
            {
                saltEntry = eval(data);
                // alert(saltEntry.tmpId);
                // alert(saltEntry.disposableSalt);
            }
        }
    );
    return saltEntry;
}

function postLoginForm()
{
    getDisposableSalt();
    var username = $('#username').val();
    var password = $('#password').val();
    var passwordEncryption = getEncryption(password,saltEntry.disposableSalt);
    var loginInfo =
        {
            'user':
                {
                    'userName': username,
                    'password': passwordEncryption
                },
            'entry':
                {
                    'tmpId': saltEntry.tmpId,
                    'disposableSalt': saltEntry.disposableSalt
                }
        };
    $.ajax(
        {
            type: 'POST',
            url :contextPath+'/login',
            data: JSON.stringify(loginInfo),
            dataType: 'json',
            contentType: 'application/json;charset=utf-8',
            success :function (data)
            {
                // alert(data.userName);
                window.location.href=contextPath+'/manager/data_sets';
            },
            async:true
        }
    );
}

// function testShA256()
// {
//     var hmac = '123';
//     var key = '3R0j9gZqjXA2HBDi9kv+Xg==zhang';
//     for(var i=0;i<500000;i++)
//     {
//         hmac = CryptoJS.HmacSHA256(hmac, key);
//                CryptoJS.HmacSHA256(hmac)
//     }
//     alert(hmac);
// }

function getContextPath()
{
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0, index + 1);
    return result;
};