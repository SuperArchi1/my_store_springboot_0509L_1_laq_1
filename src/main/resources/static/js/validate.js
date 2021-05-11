/**
 * 检查input中邮箱格式是否正确
 * @param name input的id值
 * @param msg 提示信息
 * @returns {boolean} true-格式正确 false-格式不正确
 */
function checkEmail(name,msg){
    if(!/^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/.test(
        $("#"+name).val())){ // 邮箱格式不正确
        $("#"+name).parents(".form-group").addClass("has-error").removeClass("has-success");
        // 找到input相邻的span，在其中添加错误提示信息
        $("#"+name).next("span").text(msg);
        return false;
    }else{ // 邮箱格式正确
        $("#"+name).parents(".form-group").removeClass("has-error").addClass("has-success");
        // 找到input相邻的span，在其中添加错误提示信息
        $("#"+name).next("span").text("");
        return true;
    }
}


/**
 * 检查input中是否为有效手机号
 * @param name input的id值
 * @param msg 提示信息
 * @returns {boolean} true-格式正确 false-格式不正确
 */
function checkPhone(name,msg){
    if(!/^(0|86|17951)?(13[0-9]|15[012356789]|166|17[3678]|18[0-9]|14[57])[0-9]{8}$/.test(
        $("#"+name).val())){ // 手机号不正确
        $("#"+name).parents(".form-group").addClass("has-error").removeClass("has-success");
        // 找到input相邻的span，在其中添加错误提示信息
        $("#"+name).next("span").text(msg);
        return false;
    }else{ // 手机号格式不正确
        $("#"+name).parents(".form-group").removeClass("has-error").addClass("has-success");
        // 找到input相邻的span，在其中添加错误提示信息
        $("#"+name).next("span").text("");
        return true;
    }
}

/**
 * 验证input长度是否在范围内的方法
 * @param name input的id值
 * @param minLength 最小长度，包含
 * @param maxLength 最大长度，包含
 * @returns {boolean} true-在范围内 false-不在范围内
 */
function checkLength(name,minLength,maxLength){
    if($("#"+name).val().length<minLength){ // 小于长度
        $("#"+name).parents(".form-group").addClass("has-error").removeClass("has-success");
        // 找到input相邻的span，在其中添加错误提示信息
        $("#"+name).next("span").text("长度不能小于"+minLength);
        return false;
    }else if($("#"+name).val().length>maxLength){
        $("#"+name).parents(".form-group").addClass("has-error").removeClass("has-success");
        // 找到input相邻的span，在其中添加错误提示信息
        $("#"+name).next("span").text("长度不能大于"+maxLength);
        return false;
    }else{ // 长度合适
        $("#"+name).parents(".form-group").removeClass("has-error").addClass("has-success");
        // 找到input相邻的span，在其中添加错误提示信息
        $("#"+name).next("span").text("");
        return true;
    }
}

/**
 * 检查input内容是否为空的方法
 * @param name input的id值
 * @param msg 提示信息
 * @returns {boolean} true-值不为空 false-值为空
 */
function checkEmpty(name,msg){
    if($("#"+name).val()==""){ // 为空
        $("#"+name).parents(".form-group").addClass("has-error").removeClass("has-success");
        // 找到input相邻的span，在其中添加错误提示信息
        $("#"+name).next("span").text(msg);
        return false;
    }else{ // 不为空
        $("#"+name).parents(".form-group").removeClass("has-error").addClass("has-success");
        // 找到input相邻的span，在其中添加错误提示信息
        $("#"+name).next("span").text("");
        return true;
    }
}

/**
 * 检查密码和确认密码是否一致的方法
 * @param name 密码input的id值
 * @param name2 确认密码input的id值
 * @param msg 提示信息
 * @returns {boolean} true-两次密码一致 false-两次密码不一致
 */
function checkRePwd(name,name2,msg){
    if($("#"+name).val()==""){ // 为空则不进行一致验证
        return false;
    }

    if($("#"+name).val()!=$("#"+name2).val()){ // 两次密码不一致
        $("#"+name2).parents(".form-group").addClass("has-error").removeClass("has-success");
        // 找到input相邻的span，在其中添加错误提示信息
        $("#"+name2).next("span").text(msg);
        return false;
    }else{ // 不为空
        $("#"+name2).parents(".form-group").removeClass("has-error").addClass("has-success");
        // 找到input相邻的span，在其中添加错误提示信息
        $("#"+name2).next("span").text("");
        return true;
    }
}

/**
 * 检查两个input输入的内容不一致
 * @param name 第一个input的id值
 * @param name2 第二个input的id值
 * @param msg 提示信息
 * @returns {boolean} true-两个内容不一致 false-两个内容一致
 */
function checkNotSame(name,name2,msg){
    if($("#"+name).val()==""){ // 为空则不进行一致验证
        return false;
    }

    if($("#"+name).val()==$("#"+name2).val()){ // 两个内容一致
        $("#"+name2).parents(".form-group").addClass("has-error").removeClass("has-success");
        // 找到input相邻的span，在其中添加错误提示信息
        $("#"+name2).next("span").text(msg);
        return false;
    }else{ // 两个内容不一致
        $("#"+name2).parents(".form-group").removeClass("has-error").addClass("has-success");
        // 找到input相邻的span，在其中添加错误提示信息
        $("#"+name2).next("span").text("");
        return true;
    }
}

/*
var validateConfig={
    username:[
        {
            type:checkEmpty,
            method:blur,
            msg:"用户名不能为空"
        },{
            type:checkLength,
            method:blur,
            minLength:6,
            maxLength:12
        },{
            type:checkLength,
            method:input,
            minLength:6,
            maxLength:12
        }
    ],
    password:[
        {
            type:checkEmpty,
            method:blur,
            msg:"密码不能为空"
        },{
            type:checkRePwd,
            method:blur,
            name:rePwd,
            msg:"两次密码不一致"
        }
    ]
}





function MyValidator(validateConfig){
    var validate=function() {
        // 遍历验证配置中所有的验证项名称，即被验证的input的id值
        for(var key in validateConfig){
            // 获取一个input被配置的所有验证内容
            var array=validateConfig[key];
            for(var index in array){
                // 获取一个验证内容
                var vali=array[index];
                switch (vali.type) {
                    case checkEmpty:
                        $("#"+key).bind(vali.method,);
                        break;
                    case checkLength:

                        break;
                    case checkRePwd:

                        break;
                }
            }
        }
    }
}
*/