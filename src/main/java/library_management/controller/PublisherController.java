package library_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import library_management.entity.Publisher;
import library_management.service.PublisherService;

@Controller
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }
    @GetMapping("/publishers")
    public String publishers(Model model) {
        model.addAttribute("publishers", publisherService.getAllPublishers());
        return "publishers";
    }

    @GetMapping("/publishers/new")
    public String showCreateForm(Model model) {
        model.addAttribute("publisher", new Publisher());
        return "publisher-form";
    }

    @PostMapping("/publishers")
    public String savePublisher(@ModelAttribute("publisher") Publisher publisher) {
        publisherService.savePublisher(publisher);
        return "redirect:/publishers";
    }

    @GetMapping("/publishers/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {

        model.addAttribute("publisher",
                publisherService.getPublisherById(id));

        return "publisher-form";
    }

  @GetMapping("/publishers/delete/{id}")
public String deletePublisher(
        @PathVariable Long id,
        RedirectAttributes redirectAttributes) {

    try {
        publisherService.deletePublisher(id);
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute(
                "error",
                "Cannot delete publisher because it is used by one or more books."
        );
    }

    return "redirect:/publishers";
}
}