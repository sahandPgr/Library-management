package library_management.controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import library_management.entity.Publisher;
import library_management.service.PublisherService;

@RestController
@RequestMapping("/api/publishers")
public class PublisherApiController {


    private final PublisherService publisherService;


    public PublisherApiController(
            PublisherService publisherService) {

        this.publisherService = publisherService;

    }



    @PostMapping
    public Publisher createPublisher(
            @RequestBody Publisher publisher) {


        return publisherService.savePublisher(publisher);

    }


}