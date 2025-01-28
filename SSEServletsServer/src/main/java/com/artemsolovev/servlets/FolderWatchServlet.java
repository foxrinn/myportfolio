package com.artemsolovev.servlets;

import com.artemsolovev.repository.SSEEmittersRepository;
import com.artemsolovev.service.FolderWatchService;

import javax.servlet.AsyncContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/sse/folder-watch", asyncSupported = true)
public class FolderWatchServlet extends HttpServlet {
    private SSEEmittersRepository emitters = new SSEEmittersRepository();
    private FolderWatchService service;


    @Override
    public void init() {
        this.service = new FolderWatchService("C:\\users_files", this.emitters);
    }

    @Override
    public void destroy() {
        this.service.stop();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getHeader("Accept").equals("text/event-stream")) {

            resp.setContentType("text/event-stream");
            resp.setHeader("Connection", "keep-alive");
            resp.setCharacterEncoding("UTF-8");

            AsyncContext asyncContext = req.startAsync();
            asyncContext.setTimeout(60000L);
            this.emitters.add(asyncContext);
        }
    }
}
