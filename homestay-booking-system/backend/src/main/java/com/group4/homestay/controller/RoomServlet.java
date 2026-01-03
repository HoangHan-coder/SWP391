package com.group4.homestay.controller;

import com.group4.homestay.dto.RoomRequestDTO;
import com.group4.homestay.dto.RoomUpdateDTO;
import com.group4.homestay.service.RoomServices;
import com.group4.homestay.ultil.JsonUtil;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet("/admin/room")
public class RoomServlet extends HttpServlet {

    private final RoomServices service = new RoomServices();

    // ================= CREATE =================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try {
            String json = req.getReader()
                    .lines()
                    .collect(Collectors.joining());

            RoomRequestDTO dto =
                    JsonUtil.toModel(json, RoomRequestDTO.class);

            service.create(dto);

            resp.setContentType("application/json");
            resp.getWriter().print("{\"message\":\"Create success\"}");

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().print(e.getMessage());
        }
    }

    // ================= UPDATE =================
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try {
            String json = req.getReader()
                    .lines()
                    .collect(Collectors.joining());

            RoomUpdateDTO dto =
                    JsonUtil.toModel(json, RoomUpdateDTO.class);

            service.update(dto);

            resp.setContentType("application/json");
            resp.getWriter().print("{\"message\":\"Update success\"}");

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().print(e.getMessage());
        }
    }

    // ================= DELETE =================
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try {
            int roomId = Integer.parseInt(req.getParameter("roomId"));
            service.delete(roomId);

            resp.setContentType("application/json");
            resp.getWriter().print("{\"message\":\"Delete success\"}");

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().print(e.getMessage());
        }
    }

}

