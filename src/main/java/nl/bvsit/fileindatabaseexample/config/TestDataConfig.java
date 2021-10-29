package nl.bvsit.fileindatabaseexample.config;

import nl.bvsit.fileindatabaseexample.model.Image;
import nl.bvsit.fileindatabaseexample.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Order(0)
@Component
public class TestDataConfig implements CommandLineRunner {
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    ConfigDatasource configDatasource;
    @Autowired
    ImageRepository imageRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Running "+this.getClass().getSimpleName());
        addData();
    }

    void addData() throws IOException {
        List<String> filenames = new ArrayList<>();
        filenames.add("static/pulp_fiction.png");
        filenames.add("static/kill_bill.png");
        filenames.add("static/cafe_nuage.jpg");

        for(String filename: filenames){
            Image image = saveResourceImageInDatabase(filename);
            System.out.println("Saved to database:" + image.getFileName());
        }

        /** TODO: *.jpg files were not found but caused error:
         *  java.io.FileNotFoundException: class path resource [cafe_nuage.jpg] cannot be resolved to URL because it does not exist
         *  When renamed to png or img OK??
         *  Solved suddenly for unknown reasons??
         */
    }

    private Image saveResourceImageInDatabase(String pathFile) throws IOException {
        File file = getFileFromResource(pathFile);
        //File file = getFileFromResourceTest();
        if (file!=null){
            Image image = new Image(file.getName(), getExtension(file.getName()), Files.readAllBytes(Path.of(file.getPath())));
            return imageRepository.save(image);
        }
        return null;
    }

    private File getFileFromResource(String pathFile){
        //Gets file from /resources
        try {
            Resource resource = applicationContext.getResource("classpath:"+pathFile);
            return resource.getFile();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //https://www.baeldung.com/java-file-extension
    public String getExtension(String filename) {
        Optional<String> optionalExtension =
                Optional.ofNullable(filename)
                        .filter(f -> f.contains("."))
                        .map(f -> f.substring(filename.lastIndexOf(".") + 1));
        return optionalExtension.get();
    }

    private File getFileFromResourceTest() throws IOException {
        //Gets file from /resources
        String pathFile = "static/cafe_nuage.jpg";

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(pathFile);
        byte[] bytes = is.readAllBytes();




        try {
            Resource resource = applicationContext.getResource("classpath:"+pathFile);
            return resource.getFile();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
