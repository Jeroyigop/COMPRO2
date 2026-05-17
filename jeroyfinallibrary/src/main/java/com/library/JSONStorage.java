import com.google.gson.Gson;
import java.io.FileWriter;

public class JSONStorage {

    public static void saveInventory(
        InventoryManager manager) {

        try {

            Gson gson = new Gson();

            FileWriter writer =
                new FileWriter("inventory.json");

            gson.toJson(manager.getProducts(), writer);

            writer.flush();
            writer.close();

            System.out.println("Inventory Saved");

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}