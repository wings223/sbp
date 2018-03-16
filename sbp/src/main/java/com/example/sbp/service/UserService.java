package com.example.sbp.service;

import com.example.sbp.model.User;
import com.example.sbp.repository.Testdao;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.nio.ch.IOUtil;

import javax.transaction.Transactional;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private Testdao testdao;
    public User listUser(Long id){
        User user =testdao.findById(id).get();
        return user;

    }
    @Transactional
    public List<User> inputUser(MultipartFile file){
        try {
            String filename=file.getOriginalFilename();
            BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream("f:\\excel\\"+filename));
            IOUtils.copy(file.getInputStream(),bos );
            HSSFWorkbook wb=new HSSFWorkbook(file.getInputStream());
            HSSFSheet sheet=wb.getSheetAt(0);
            List<User> users=new ArrayList<User>();
            for(int i=1;i<=sheet.getLastRowNum();i++){
                HSSFRow row=sheet.getRow(i);
                User user=new User();
                String username=row.getCell(0).getStringCellValue();
                String password=row.getCell(1).getStringCellValue();
                Date date=row.getCell(2)==null?null:row.getCell(2).getDateCellValue();
                user.setUserName(username);
                user.setPassword(password);
                user.setCreateTime(date);
                users.add(user);
            }
            testdao.saveAll(users);
            bos.close();
            return users;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public byte[] getByte(){
        XSSFWorkbook wb=new XSSFWorkbook();
        XSSFSheet sheet=wb.createSheet();
        List<User> users=testdao.findAll();
        System.out.println();
        int rowNum=1;
        for(User user:users){
            XSSFRow row=sheet.createRow(rowNum);
            row.createCell(0).setCellValue(user.getUserName());
            row.createCell(1).setCellValue(user.getPassword());
            row.createCell(2).setCellValue(user.getCreateTime()+"");
            rowNum++;
        }
        ByteArrayOutputStream os=new ByteArrayOutputStream();
        ByteArrayOutputStream oo = new ByteArrayOutputStream();


        try {
            wb.write(os);
            byte[] bytes=os.toByteArray();
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
