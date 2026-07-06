package com.example.canteen;

import com.example.canteen.model.Canteen;
import com.example.canteen.model.User;
import com.example.canteen.repository.CanteenRepo;
import com.example.canteen.repository.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CanteenBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CanteenBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner seed(UserRepo users, CanteenRepo canteens, PasswordEncoder enc) {
        return args -> {

            System.out.println("🧹 Clearing old database data...");
            users.deleteAll();
            canteens.deleteAll();

            System.out.println("🌱 Seeding fresh data...");

            // Create sample canteens
            Canteen c1 = canteens.save(new Canteen(null, "canteen1",""));
            Canteen c2 = canteens.save(new Canteen(null, "canteen2", ""));
            

            // Create global manager (can manage all)
            User globalManager = new User();
            globalManager.setUsername("manager1");
            globalManager.setPassword(enc.encode("admin123"));
            globalManager.setRole("MANAGER");
            globalManager.setEmail("manager1@canteen.com");
            globalManager.setCanteenId(null);
            users.save(globalManager);

            // Create a manager for each canteen (optional)
            User alphaMgr = new User();
            alphaMgr.setUsername("canteen1_mgr");
            alphaMgr.setPassword(enc.encode("pass123"));
            alphaMgr.setRole("MANAGER");
            alphaMgr.setEmail("canteen1_mgr@canteen.com");
            alphaMgr.setCanteenId(c1.getId());
            users.save(alphaMgr);

            User betaMgr = new User();
            betaMgr.setUsername("canteen2_mgr");
            betaMgr.setPassword(enc.encode("pass123"));
            betaMgr.setRole("MANAGER");
            betaMgr.setEmail("canteen2_mgr@canteen.com");
            betaMgr.setCanteenId(c2.getId());
            users.save(betaMgr);

            // Create a sample customer
            User customer = new User();
            customer.setUsername("customer1");
            customer.setPassword(enc.encode("pass123"));
            customer.setRole("CUSTOMER");
            customer.setEmail("customer1@canteen.com");
            customer.setCanteenId(null);
            users.save(customer);

            System.out.println("✅ Database reseeded successfully.");
            System.out.println("🧑‍💼 Global Manager: manager1 / admin123");
            System.out.println("🏫 Alpha Manager: alpha_mgr / pass123");
            System.out.println("🏫 Beta Manager: beta_mgr / pass123");
            System.out.println("👤 Customer: customer1 / pass123");
        };
    }
}
