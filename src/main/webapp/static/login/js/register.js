function encryptPassword_SHA256(submittedPlaintText,username
)
{
    var str1 = $.sha256($.sha256(submittedPlaintText));
    return $.sha256(str1 + username);
}