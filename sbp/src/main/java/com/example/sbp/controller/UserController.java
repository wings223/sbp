package com.example.sbp.controller;

import com.example.sbp.model.User;
import com.example.sbp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/user")
    @ResponseBody
    public String user1(Long id){
        return userService.listUser(id).toString();
    }

    @RequestMapping("/list")
    @ResponseBody
    public String list(String ip){
        return ip;
    }

    @RequestMapping("/upload")
    @ResponseBody
    public List<User> upload(HttpServletRequest req,
                             MultipartHttpServletRequest multiReq){
        MultipartFile f= multiReq.getFile("file1");

        return userService.inputUser(f);

    }

    @RequestMapping("/download")
    public void download(HttpServletResponse res){
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + "abc.xlsx");
        try {

            byte[] bytes=userService.getByte();
            OutputStream os=res.getOutputStream();
            os.write(bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
