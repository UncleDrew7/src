package excelBuild;

/**
 * Created by apple on 16/08/2017.
 */
public class Room {


    private int id;
    private String nameRoom;
    private int countRoom;

    public Room(int id, String nameRoom, int countRoom) {
        this.id = id;
        this.nameRoom = nameRoom;
        this.countRoom = countRoom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameRoom() {
        return nameRoom;
    }

    public void setNameRoom(String nameRoom) {
        this.nameRoom = nameRoom;
    }

    public int getCountRoom() {
        return countRoom;
    }

    public void setCountRoom(int countRoom) {
        this.countRoom = countRoom;
    }

    @Override
    public String toString() {
        return "id=" + id + ", name=" + nameRoom + ", count=" + countRoom;
    }

}
