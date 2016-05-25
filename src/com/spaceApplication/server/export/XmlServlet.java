package com.spaceApplication.server.export;

/**
 * Created by Кристина on 16.05.2016.
 */
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

public class XmlServlet extends HttpServlet {

    public void doPost(HttpServletRequest req,HttpServletResponse res)
    {
        File uploadedFile;
        System.out.print("on server");
        try{
            // String filename =
            File uploadedFile1=new File("./img/"+"happybir.jpg" );
            String kk=uploadedFile1.getAbsolutePath();
            System.out.print(kk);
            File f=new File(kk);
            //System.out.println("foder is " + folder);
            res.setContentType("application/octet-stream");
            res.setHeader("Content-disposition", "attachment;filename=\"" +"happybir.jpg" + "\"");
            //res.setHeader("Content-Disposition","attachment; filename=;");
            ServletOutputStream stream = res.getOutputStream();
            BufferedInputStream fif = new BufferedInputStream(new FileInputStream(f));
            int data;
            while((data = fif.read()) != -1) {
                stream.write(data);
            }
            fif.close();
            stream.close();

        }catch(Exception e)
        {
            System.out.println("Exception Due to"+e);
            e.printStackTrace();
        }
    }
}