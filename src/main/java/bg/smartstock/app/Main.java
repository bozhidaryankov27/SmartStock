package bg.smartstock.app;

import bg.smartstock.repository.ProductRepository;
import bg.smartstock.repository.SQLProductRepository;
import bg.smartstock.service.InventoryService;

public class Main {
    public static void main(String[] args) {
        ProductRepository repository = new SQLProductRepository();
        InventoryService service = new InventoryService(repository);

        ConsoleApp app = new ConsoleApp(service);
        app.start();
    }
}
