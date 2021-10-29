package nl.bvsit.fileindatabaseexample.controller;

import nl.bvsit.fileindatabaseexample.model.Image;
import nl.bvsit.fileindatabaseexample.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("img")
public class ImageController {
    @Autowired
    ImageRepository imageRepository;

    @GetMapping("/all")
    public ResponseEntity<Object> getImages(){
        return ResponseEntity.ok().body(imageRepository.findAll());
    }

    //https://stackoverflow.com/questions/40557637/how-to-return-an-image-in-spring-boot-controller-and-serve-like-a-file-system
    @GetMapping("showfirst")
    public ResponseEntity<byte[]> getImage() throws IOException {
        Image image = imageRepository.findAll().get(0);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Filename",image.getFileName());
        responseHeaders.set("FileId",String.valueOf(image.getId()));

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .contentType(MediaType.valueOf("image/"+image.getType())) //i.e. "image/png"
                //.contentType(MediaType.valueOf("application/octet-stream")) //downloadable
                .body(image.getFile());
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") long id ) throws IOException {
        if (imageRepository.existsById(id)){
            Image image = imageRepository.findById(id).get();
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Filename",image.getFileName());
            responseHeaders.set("FileId",String.valueOf(image.getId()));
            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .contentType(MediaType.valueOf("image/"+image.getType())) //i.e. "image/png"
                    //.contentType(MediaType.valueOf("application/octet-stream")) //downloadable
                    .body(image.getFile());
        }
        return ResponseEntity.badRequest().build();
    }

}
