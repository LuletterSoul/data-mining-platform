$.isSubmit = true;	// 是否可提交
$.verifyForm = function (eml, isEmpty) {
    eml.removeClass('empty');
    if (!isEmpty) return eml.val();
    var val = eml.val();
    if (val < 1 || val.length < 1) {
        $.isSubmit = false;
        eml.addClass('empty');
    }
    return val;
};