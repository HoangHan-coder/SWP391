package com.group4.homestay.repository;

import com.group4.homestay.model.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    public String getMaxRoomNumber() throws Exception {
        String sql = """
            SELECT TOP 1 room_number
            FROM Room
            WHERE room_number LIKE 'R%'
            ORDER BY room_number DESC
        """;

        try (Connection c = DBconnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getString("room_number");
            }
        }
        return null;
    }

    public void insert(Room room) throws Exception {
        String sql = """
            INSERT INTO Room
            (homestay_id, room_number, room_type, price_per_hour,
             max_guests, area_m2, description, status, quantity_room, is_delete)
            VALUES (?, ?, ?, ?, ?, ?, ?,?, ?, 0)
        """;

        try (Connection c = DBconnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, room.getHomestayId());
            ps.setString(2, room.getRoomNumber());
            ps.setString(3, room.getRoomType());
            ps.setBigDecimal(4, room.getPricePerHour());
            ps.setInt(5, room.getMaxGuests());
            ps.setObject(6, room.getAreaM2());
            ps.setString(7, room.getDescription());
            ps.setString(8, room.getStatus());
            ps.setInt(9, room.getQuantity());
            ps.executeUpdate();
        }
    }

    // UPDATE
    public void update(Room room) throws Exception {

        String sql = """
        UPDATE Room
        SET
            room_type = ?,
            price_per_hour = ?,
            max_guests = ?,
            area_m2 = ?,
            description = ?,
            status = ?
        WHERE room_id = ?
          AND is_delete = 0
    """;

        try (Connection c = DBconnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, room.getRoomType());
            ps.setBigDecimal(2, room.getPricePerHour());
            ps.setInt(3, room.getMaxGuests());
            ps.setInt(4, room.getAreaM2());
            ps.setString(5, room.getDescription());
            ps.setString(6, room.getStatus());
            ps.setInt(7, room.getRoomId());

            int rows = ps.executeUpdate();

            // 🔥 CỰC KỲ QUAN TRỌNG
            if (rows == 0) {
                throw new RuntimeException("Update failed: room not found or already deleted");
            }
        }
    }


    // DELETE (soft delete)
    public void delete(int roomId) throws Exception {
        String sql = "UPDATE Room SET is_delete = 1 WHERE room_id = ?";

        try (Connection c = DBconnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, roomId);
            ps.executeUpdate();
        }
    }

    public List<Room> findAll() throws Exception {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT * FROM Room WHERE is_delete = 0";

        try (Connection c = DBconnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Room r = new Room();
                r.setRoomId(rs.getInt("room_id"));
                r.setRoomNumber(rs.getString("room_number"));
                r.setRoomType(rs.getString("room_type"));
                r.setPricePerHour(rs.getBigDecimal("price_per_hour"));
                r.setMaxGuests(rs.getInt("max_guests"));
                r.setStatus(rs.getString("status"));
                list.add(r);
            }
        }
        return list;
    }
}
