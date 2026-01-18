import hibernateClasses.Writers;
import hibernateClasses.Works;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        // Hibernate configuration
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        configuration.addResource("Classes/writers.hbm.xml");
        configuration.addResource("Classes/works.hbm.xml");

        ServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

        SessionFactory sessionFactory = configuration.buildSessionFactory(registry);
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        // Load writers from CSV
        session.createNativeQuery("TRUNCATE TABLE writers").executeUpdate();
        loadWritersFromCSV(session, "writers.csv");

        // Load works from CSV
        session.createNativeQuery("TRUNCATE TABLE works").executeUpdate();
        loadWorksFromCSV(session, "works.csv");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a number of years between 1 and 25:");
        int numberOfYears = scanner.nextInt();

        System.out.println("Enter number of works to show:");
        int numberOfWorks = scanner.nextInt();

        List<Writers> writersList = session.createQuery("from Writers", Writers.class).list();
        List<Works> worksList = session.createQuery("from Works", Works.class).list();

        // Show writers and their works
        for (int i = 0; i < numberOfYears && i < writersList.size(); i++) {
            Writers writer = writersList.get(writersList.size() - 1 - i);
            List<Works> selectedWorks = new ArrayList<>();

            for (Works work : worksList) {
                if (work.getAuthorId() == writer.getId() && selectedWorks.size() < numberOfWorks) {
                    selectedWorks.add(work);
                }
            }

            System.out.println(writer.getAuthor() + " (" + writer.getAwardYear() + ")");
            System.out.println("Bio: " + writer.getBio());
            System.out.println("Reason: " + writer.getReason());
            System.out.print("Works: ");

            for (Works work : selectedWorks) {
                System.out.print(work.getTitle() + " (" + work.getYear() + ") ");
            }

            System.out.println("\n-------------------------");
        }

        scanner.nextLine(); // Clear buffer

        // Delete a work
        System.out.println("Delete a work, enter the author's name:");
        String authorName = scanner.nextLine();

        int authorId = getAuthorIdByName(writersList, authorName);

        if (authorId != -1) {
            printWorksByAuthor(worksList, authorId);

            System.out.println("Enter the title of the work to delete:");
            String workToDelete = scanner.nextLine();

            for (Works work : worksList) {
                if (work.getAuthorId() == authorId && work.getTitle().equals(workToDelete)) {
                    session.delete(work);
                }
            }
        }

        session.getTransaction().commit();
        session.close();
    }

    private static void loadWritersFromCSV(Session session, String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] data = line.split(";");
            Writers writer = new Writers(
                    data[0],
                    Integer.parseInt(data[1]),
                    data[2],
                    data[3]
            );
            session.save(writer);
        }
        reader.close();
    }

    private static void loadWorksFromCSV(Session session, String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] data = line.split(";");
            Works work = new Works(
                    Integer.parseInt(data[0]),
                    data[1],
                    Integer.parseInt(data[2])
            );
            session.save(work);
        }
        reader.close();
    }

    private static int getAuthorIdByName(List<Writers> writers, String name) {
        for (Writers writer : writers) {
            if (writer.getAuthor().equals(name)) {
                return writer.getId();
            }
        }
        return -1;
    }

    private static void printWorksByAuthor(List<Works> works, int authorId) {
        for (Works work : works) {
            if (work.getAuthorId() == authorId) {
                System.out.println(work.getTitle());
            }
        }
    }
}
