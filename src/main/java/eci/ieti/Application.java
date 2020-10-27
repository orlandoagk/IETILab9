package eci.ieti;

import eci.ieti.config.AppConfiguration;
import eci.ieti.data.CustomerRepository;
import eci.ieti.data.ProductRepository;
import eci.ieti.data.TodoRepository;
import eci.ieti.data.UserRepository;
import eci.ieti.data.model.Customer;
import eci.ieti.data.model.Product;

import eci.ieti.data.model.Todo;
import eci.ieti.data.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


import java.util.Date;


@SpringBootApplication
public class Application implements CommandLineRunner {


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        MongoOperations mongoOperation = (MongoOperations) applicationContext.getBean("mongoTemplate");

        customerRepository.deleteAll();

        customerRepository.save(new Customer("Alice", "Smith"));
        customerRepository.save(new Customer("Bob", "Marley"));
        customerRepository.save(new Customer("Jimmy", "Page"));
        customerRepository.save(new Customer("Freddy", "Mercury"));
        customerRepository.save(new Customer("Michael", "Jackson"));

        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        
        customerRepository.findAll().stream().forEach(System.out::println);
        System.out.println();
        
        productRepository.deleteAll();

        productRepository.save(new Product(1L, "Samsung S8", "All new mobile phone Samsung S8"));
        productRepository.save(new Product(2L, "Samsung S8 plus", "All new mobile phone Samsung S8 plus"));
        productRepository.save(new Product(3L, "Samsung S9", "All new mobile phone Samsung S9"));
        productRepository.save(new Product(4L, "Samsung S9 plus", "All new mobile phone Samsung S9 plus"));
        productRepository.save(new Product(5L, "Samsung S10", "All new mobile phone Samsung S10"));
        productRepository.save(new Product(6L, "Samsung S10 plus", "All new mobile phone Samsung S10 plus"));
        productRepository.save(new Product(7L, "Samsung S20", "All new mobile phone Samsung S20"));
        productRepository.save(new Product(8L, "Samsung S20 plus", "All new mobile phone Samsung S20 plus"));
        productRepository.save(new Product(9L, "Samsung S20 ultra", "All new mobile phone Samsung S20 ultra"));
        
        System.out.println("Paginated search of products by criteria:");
        System.out.println("-------------------------------");
        
        productRepository.findByDescriptionContaining("plus", PageRequest.of(0, 2)).stream()
        	.forEach(System.out::println);
   
        System.out.println();

        todoRepository.deleteAll();

        todoRepository.save(new Todo("travel to Galapagos", new Date(1999,3,3),"charles@natural.com","pending",10));
        todoRepository.save(new Todo("travel to Montería", new Date(2020,8,5),"user1@mail.com","ready",9));
        todoRepository.save(new Todo("travel to Miami", new Date(2020,3,2),"user2@natural.com","pending",10));
        todoRepository.save(new Todo("travel to Bogotá", new Date(2021,2,5),"user3@natural.com","pending",2));
        todoRepository.save(new Todo("travel to Pereira", new Date(2010,3,9),"user4@natural.com","pending",3));
        todoRepository.save(new Todo("travel to Cartagena", new Date(2020,12,14),"user5@natural.com","pending",10));
        todoRepository.save(new Todo("travel to Tolúasdadfdssfgsfgsdfgfgsgjfhjfhjfhjfhjfhjfhjfhjfhj", new Date(2021,5,6),"user6@natural.com","in progress",4));
        todoRepository.save(new Todo("travel to Platanal", new Date(2019,1,9),"user7@natural.com","pending",9));
        todoRepository.save(new Todo("travel to Macondo", new Date(2020,2,5),"user8@natural.com","pending",6));
        todoRepository.save(new Todo("travel to Runaterra", new Date(2023,4,2),"user9@natural.com","pending",10));
        todoRepository.save(new Todo("travel to Cartacho", new Date(2021,11,5),"user10@natural.com","pending",10));
        todoRepository.save(new Todo("travel to Madrid", new Date(2020,8,2),"user11@natural.com","pending",5));
        todoRepository.save(new Todo("travel to Barcelona", new Date(2027,12,17),"user12@natural.com","pending",10));
        for(int i = 13;i<30;i++){
            todoRepository.save(new Todo("travel to Bogotá", new Date(2020,1,i),"user"+i+"@natural.com","pending",i%10));
        }
        System.out.println("Paginated search of Todo");
        System.out.println("-------------------------------");

        todoRepository.findByResponsible("charles@natural.com", PageRequest.of(0,2)).stream().forEach(System.out::println);


        System.out.println();

        userRepository.deleteAll();

        for(int i = 0;i<10;i++) {
            userRepository.save(new User(Integer.toString(i), "username"+i,"user"+i+"@mail.com"));
        }

        //First query expired date
        System.out.println("-------------First query, expired date---------------------");
        Query queryExpiredDueDate = new Query();
        queryExpiredDueDate.addCriteria(Criteria.where("dueDate").lt(new Date(2020,10,26)));
        mongoOperation.find(queryExpiredDueDate, Todo.class).stream().forEach(System.out::println);

        //Second query get todo by grater priority and user
        System.out.println("-------------Second Query, grated priority and user---------------------");
        Query query2 = new Query();
        query2.addCriteria(Criteria.where("priority").gte(5).and("responsible").is("user8@natural.com"));
        mongoOperation.find(query2, Todo.class).stream().forEach(System.out::println);

        //Third query - Users that have assigned more than 2 Todos.
        System.out.println("-------------Third query, Users that have assigned more than 2 Todos.---------------------");
        System.out.println("This can't be implemented");

        //Fourth query - Todos that contains a description with a length greater than 30 characters
        System.out.println("-------------Fourth query - Todos that contains a description with a length greater than 30 characters---------------------");
        Query query3 = new Query();
        query3.addCriteria(Criteria.where("description").regex("[a-z,A-Z,0-9,' ']{30,}"));
        mongoOperation.find(query3, Todo.class).stream().forEach(System.out::println);

    }

}