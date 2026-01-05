package com.group4.homestay.service;

import com.group4.homestay.dto.RoomRequestDTO;
import com.group4.homestay.dto.RoomUpdateDTO;
import com.group4.homestay.model.Room;
import com.group4.homestay.repository.RoomDAO;

public class RoomServices {

    private final RoomDAO dao = new RoomDAO();

    // CREATE (tạo nhiều phòng)
    public void create(RoomRequestDTO dto) throws Exception {

        String maxRoom = dao.getMaxRoomNumber();
        int startNumber = 1;

        if (maxRoom != null) {
            startNumber = Integer.parseInt(maxRoom.substring(1)) + 1;
        }

        int quantity = dto.getQuantity() != null ? dto.getQuantity() : 1;

        for (int i = 0; i < quantity; i++) {

            String roomNumber = String.format("R%03d", startNumber + i);

            Room room = new Room();
            room.setHomestayId(dto.getHomestayId());
            room.setRoomNumber(roomNumber);
            room.setRoomType(dto.getRoomType());
            room.setPricePerHour(dto.getPricePerHour());
            room.setMaxGuests(dto.getMaxGuests());
            room.setAreaM2(dto.getAreaM2());
            room.setDescription(dto.getDescription());
            room.setQuantity(dto.getQuantity());
            room.setStatus(dto.getStatus());

            dao.insert(room);
        }
    }

    // UPDATE
    public void update(RoomUpdateDTO dto) throws Exception {

        Room room = new Room();
        room.setRoomId(dto.getRoomId());
        room.setRoomType(dto.getRoomType());
        room.setPricePerHour(dto.getPricePerHour());
        room.setMaxGuests(dto.getMaxGuests());
        room.setAreaM2(dto.getAreaM2());
        room.setDescription(dto.getDescription());
        room.setStatus(dto.getStatus());

        System.out.println("AREA = " + room.getAreaM2());
        System.out.println("DESC = " + room.getDescription());

        dao.update(room);
    }

    // DELETE
    public void delete(int roomId) throws Exception {
        dao.delete(roomId);
    }
}
