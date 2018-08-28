package controller;

import java.io.IOException;
import java.nio.file.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UploadController {

	 public static Connection con=null;
	 public static ResultSet  rs =null;
	 public static PreparedStatement pst=null;
	 public static Statement st=null;
	
	
    //private int maxUploadSizeInMb = 100 * 1024 * 1024; // 10 MB     maximum size i defined in xml

   // Path for saving the images
	private static String UPLOADED_FOLDER = "C://Users//Johny Dev PC//workspace//ImageUploadUsingSpring//src//main//webapp//static//images//";
    private String image;


    @GetMapping("/")
    public String index() 
    {
        return "index";
    }

    @GetMapping("/images")
    public String images() 
    {  
    	return "imagess";  
   	}

    @PostMapping("/upload")
    public ModelAndView singleFileUpload(@RequestParam("file") MultipartFile file) throws Exception 
    {
        ModelAndView mv = new ModelAndView("index");
            // Get the image and attempt to save it on the disk
        	
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            image = file.getOriginalFilename();
            
             Class.forName("com.mysql.jdbc.Driver");
	    	 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diji-info-database","root","");
	    	 pst = con.prepareStatement("insert into blog_categories(c_pictuires) VALUES(?)");
			 pst.setString(1,image);
			 pst.execute();
			 pst.close();
			 pst = con.prepareStatement("select c_pictuires from blog_categories where c_pictuires =?");
			 pst.setString(1,image);
			 rs = pst.executeQuery();
			 
			 while(rs.next())
			 {
				 System.out.println("Image : "+rs.getString(1));
			 }
		   mv.addObject("image", image);
		   return mv;
         
    }
}
















