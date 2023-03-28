package com.droame.login;


import com.droame.dao.CustomerInfoDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


public class LoginServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        try {
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            if (new CustomerInfoDao().toCheckLogin(username, password)) {
                out.println("login successful");
            } else {
                out.print(" UserName or Password Incorrect");
                RequestDispatcher rd = request.getRequestDispatcher("/Login.html");
                rd.include(request, response);
            }
        }catch (Exception e){
            System.out.println("Exception");
        }
    }
}
