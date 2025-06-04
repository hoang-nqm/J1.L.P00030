package Utils;

import Model.Room;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileUltils {

    private static List<Room> roomList = new ArrayList<>();
    private static Set<String> roomIDs = new HashSet<>();

    public static void importRoomData(String filePath) {
        int successCount = 0;
        int failedCount = 0;

        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File not found: " + filePath);
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length != 6) {
                    failedCount++;
                    continue;
                }

                String roomID = parts[0].trim();
                String roomName = parts[1].trim();
                String roomType = parts[2].trim();
                String rateStr = parts[3].trim();
                String capacityStr = parts[4].trim();
                String furniture = parts[5].trim();


                if (roomIDs.contains(roomID)) {
                    failedCount++;
                    continue;
                }

                try {
                    double dailyRate = Double.parseDouble(rateStr);
                    int capacity = Integer.parseInt(capacityStr);

                    if (dailyRate <= 0 || capacity <= 0) {
                        failedCount++;
                        continue;
                    }

                    Room room = new Room(roomID, roomName, roomType, dailyRate, capacity, furniture);
                    roomList.add(room);
                    roomIDs.add(roomID);
                    successCount++;
                } catch (NumberFormatException e) {
                    failedCount++;
                }
            }

            System.out.println(successCount + " rooms successfully loaded.");
            System.out.println(failedCount + " entries failed.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public static List<Room> loadRoomsFromFile(String filePath) {
        List<Room> roomList = new ArrayList<>();
        Set<String> roomIDs = new HashSet<>();
        int successCount = 0;
        int failedCount = 0;

        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("❌ File not found: " + filePath);
            return roomList;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length != 6) {
                    failedCount++;
                    continue;
                }

                String roomID = parts[0].trim();
                String roomName = parts[1].trim();
                String roomType = parts[2].trim();
                String rateStr = parts[3].trim();
                String capacityStr = parts[4].trim();
                String furniture = parts[5].trim();


                if (roomIDs.contains(roomID)) {
                    failedCount++;
                    continue;
                }

                try {
                    double dailyRate = Double.parseDouble(rateStr);
                    int capacity = Integer.parseInt(capacityStr);


                    if (dailyRate <= 0 || capacity <= 0) {
                        failedCount++;
                        continue;
                    }

                    Room room = new Room(roomID, roomName, roomType, dailyRate, capacity, furniture);
                    roomList.add(room);
                    roomIDs.add(roomID);
                    successCount++;
                } catch (NumberFormatException e) {
                    failedCount++;
                }
            }

        } catch (IOException e) {
            System.out.println("❌ Error reading file: " + e.getMessage());
        }

        return roomList;
    }
}
