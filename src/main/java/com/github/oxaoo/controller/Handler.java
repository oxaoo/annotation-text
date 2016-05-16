package com.github.oxaoo.controller;

import com.github.oxaoo.service.ServiceAnnotation;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

@WebServlet("/handler")
public class Handler extends HttpServlet {
    private final static Logger log = Logger.getLogger(Handler.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String text = req.getParameter("text");
        String method = req.getParameter("method").toLowerCase();
        log.info("Request content, text: " + text + ", method: " + method);

        String jresponse = ServiceAnnotation.execute(text, method);

//        Map<String, Object> response = new TreeMap<String, Object>();
//        response.put("annotation", annotation);
//        String jresponse = new Gson().toJson(response);

        log.info("Send response: " + jresponse);

        resp.setContentType("text/html; charset=utf-8");
        resp.getWriter().write(jresponse);
        resp.getWriter().close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
