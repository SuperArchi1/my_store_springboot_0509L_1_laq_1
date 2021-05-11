package com.tedu.controller;

import com.tedu.entity.User;
import com.tedu.exception.UserException;
import com.tedu.service.IUserService;
import com.tedu.util.CodeUtil;
import com.tedu.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.lang.model.element.NestingKind;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserService userService;
    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult regist(User user) {
        userService.regist(user);
        return JsonResult.getSuccessJR();
    }
    //检查用户名是否存在
    @RequestMapping(value = "/checkUsername", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult checkUsername(String username) {
        Boolean count = userService.checkUsername(username);
        JsonResult jr = new JsonResult();
        //如果存在
        if (count) {
            jr.setMsg("true");
            jr.setState(1000);
        }
        //如果不存在
        else {
            jr.setMsg("false");
            jr.setState(1000);
        }
        return jr;
    }
    //用户登录
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult login(String username, String password, HttpSession session) {
        JsonResult jr = new JsonResult();
        try {
            User loginUser = userService.login(username, password);
            session.setAttribute("loginUser", loginUser);
            jr.setState(1000);
        } catch (UserException e) {
            jr.setMsg(e.getMessage());
        }
        return  jr;
    }

    //修改密码
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult changePassword(String oldPassword, String newPassword,
                                     HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if(user == null) {
            return new JsonResult(500, "登录状态异常,请重新登录");
        }
       /* if(!CodeUtil.getMD5Encoding(oldPassword).equals(user.getPassword())) {
            throw new UserException("密码错误");
        }*/

        String password = CodeUtil.getMD5Encoding(newPassword);
        user.setPassword(password);
        userService.changePassword(user);
        return JsonResult.getSuccessJR();
    }

    //回显用户信息
    @RequestMapping(value = "/findUserInfo", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult findUserInfo(HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if(user == null) {
            return new JsonResult(500, "登录状态异常,请重新登录");
        }

        return JsonResult.getSuccessJR(user);
    }

    //更新用户信息
    @RequestMapping(value = "/changeUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult changeUserInfo(String username, String email, String phone,Integer gender,HttpSession session) {
        User userdata = (User) session.getAttribute("loginUser");
        if(userdata == null) {
            return new JsonResult(500, "登录状态异常,请重新登录");
        }
        userdata.setUsername(username);
        userdata.setEmail(email);
        userdata.setPhone(phone);
        userdata.setGender(gender);
        userService.updateUserInfo(userdata);
        System.out.println("----------------------controller"+username);
        System.out.println("----------------------controller"+email);
        System.out.println("----------------------controller"+phone);

        return JsonResult.getSuccessJR();
    }

    private static final long AVATAR_MAX_SIZE = 600 * 1024;
    private static final List<String> AVATAR_TYPES = new ArrayList<String>();
    @Value("${avatar.upload.path}")
    String avatarBasePath;

    // 静态初始化器：用于初始化本类的静态成员
    static {
        AVATAR_TYPES.add("image/jpeg");
        AVATAR_TYPES.add("image/png");
    }

    @PostMapping("/changeAvatar")
    public JsonResult<String> changeAvatar(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpSession session) throws Exception {
        // 空文件验证
        if (file.isEmpty()) {
            throw new Exception("文件上传异常！文件不能为空");
        }
        // 文件大小验证
        long fileSize = file.getSize();
        if (fileSize > AVATAR_MAX_SIZE) {
            throw new Exception("文件上传异常！文件大小超过上限:" + (AVATAR_MAX_SIZE / 1024) + "kb");
        }
        // 文件类型验证
        if (!AVATAR_TYPES.contains(file.getContentType())) {
            throw new Exception("文件上传异常！文件类型不正确，允许的类型 有：" + AVATAR_TYPES);
        }
        // 生成文件名-> 直接使用uuid+原文件名后缀
        String oFilename = file.getOriginalFilename();
        Integer index = oFilename.lastIndexOf(".");
        String suffix = "";
        if (index != -1) {
            suffix = oFilename.substring(index);
        }
        String filename = UUID.randomUUID().toString() + suffix;
        // 生成目标路径
        // String filePath=request.getServletContext().getRealPath("upload");
        String filePath = avatarBasePath + "/upload";
        File parent = new File(new File(filePath).getAbsolutePath());
        if (!parent.exists()) {
            parent.mkdirs();//创建对应的目录
        }
        File dest = new File(parent, filename);
        // 将用户上传的头像保存到服务器上
        try {
            file.transferTo(dest);
        } catch (IllegalStateException e) {
            throw new Exception("文件上传异常！" + e.getMessage());
            // throw new FileStateException("文件上传异常！"+e.getMessage(),e);
        } catch (IOException e) {
            throw new Exception("文件上传异常！" + e.getMessage());
        }
        // 将头像在服务器的路径保存到数据库
        String avatar = "/upload/" + filename;
        User user = (User) session.getAttribute("user");
        Integer uid = user.getUid();
        String username = user.getUsername();
        user.setAvatar(avatar);
        userService.changeAvatar(user);
        return new JsonResult<String>(1000, "OK", avatar);
    }

}
