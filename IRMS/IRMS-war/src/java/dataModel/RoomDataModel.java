/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataModel;

import entity.Room;
import java.io.Serializable;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author Yang Zhennan
 */
public class RoomDataModel extends ListDataModel<Room> implements SelectableDataModel<Room>, Serializable {

    public RoomDataModel() {
    }

    public RoomDataModel(List<Room> data) {
        super(data);
    }

    @Override
    public Room getRowData(String rowKey) {
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  

        List<Room> rooms = (List<Room>) getWrappedData();

        for (Room room : rooms) {
            if (room.getRoomNumber().toString().equals(rowKey)) {
                return room;
            }
        }

        return null;
    }

    @Override
    public Object getRowKey(Room room) {
        return room.getRoomNumber();
    }
}
