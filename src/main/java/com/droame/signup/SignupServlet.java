package com.droame.signup;

import com.droame.dao.CustomerInfoDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

public class SignupServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out=response.getWriter();
        String userId=request.getParameter("userId");
        String password=request.getParameter("password");
        String name=request.getParameter("name");
        String mobilenumber=request.getParameter("mobilenumber");
        SignupDetails newuser=new SignupDetails();
        RequestDispatcher rd;

        /**
         * Validate email input
         */
        if(Pattern.matches("[a-zA-Z][a-zA-Z0-9-.]*@[a-zA-Z0-9]+([.]com)",userId)) {
            newuser.setUserId(userId);
        }else {
            response.setContentType("text/html");
            out.print(" Enter a valid userId");
            rd=request.getRequestDispatcher("/Signup.html");
            rd.include(request, response);
            return;
        }


        /**
         * validate username
         */
        if(Pattern.matches("[a-z A-Z]{3,20}",name)) {
            newuser.setName(name);
        }else {
            response.setContentType("text/html");
            out.print("Enter a valid name");
            rd=request.getRequestDispatcher("/Signup.html");
            rd.include(request, response);
            return;
        }

        /**
         * password pattern check
         */
        if(Pattern.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\\\S+$).{8,20}",password)) {
            newuser.setPassword(password);
        }else {
            response.setContentType("text/html");
            out.print("password must contain\\n * Atleast 1 Upper Case Character\\n *Atleast 1 Lower Case Character\\n*Atleast 1 number\\n *Atleast 1 Special Character\\nPassword must contain 8-20 characters\"");

            rd=request.getRequestDispatcher("/Signup.html");
            rd.include(request, response);
            return;
        }

        /**
         * Validate mobile number
         */

        if(Pattern.matches("[6-9][0-9]{9}",mobilenumber)) {
            newuser.setMobileNum(mobilenumber);
        }else {
            response.setContentType("text/html");
            out.print("Enter a valid mobilenumber");

            rd=request.getRequestDispatcher("/Signup.html");
            rd.include(request, response);
            return;
        }

        /**
         * Redirect to  login page if user exist
         */
        try {
            if(!new CustomerInfoDao().toRegisterNewUser(newuser))
            {response.setContentType("text/html");

                out.print("UserId already exsist");

                rd=request.getRequestDispatcher("/Login.html");
                rd.include(request, response);
                return;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        rd=request.getRequestDispatcher("/Login.html");
        rd.include(request,response);


    }
}
