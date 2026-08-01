package library_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import library_management.entity.Publisher;
import library_management.service.PublisherService;

@Controller
@RequestMapping("/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public String publishers(Model model) {
        model.addAttribute("publishers", publisherService.getAllPublishers());
        return "publishers";
    }

    @GetMapping("/create")
    public String showCreateForm(@RequestParam(required = false, defaultValue = "/publishers") String returnTo,
            Model model) {
        model.addAttribute("publisher", new Publisher());
        model.addAttribute("returnTo", returnTo);
        return "publisher-form";
    }

    @PostMapping("/save")
    public String savePublisher(
            @ModelAttribute("publisher") Publisher publisher,
            @RequestParam(required = false) String returnTo,
            RedirectAttributes redirectAttributes, Model model) {

        boolean isNew = publisher.getId() == null;

        try {
            publisherService.savePublisher(publisher);
            if (isNew) {
                redirectAttributes.addFlashAttribute(
                        "success",
                        "Publisher created successfully.");
            } else {
                redirectAttributes.addFlashAttribute(
                        "success",
                        "Publisher updated successfully.");
            }
            if (returnTo != null && !returnTo.isBlank()) {
                return "redirect:" + returnTo;
            }

            return "redirect:/publishers";

        } catch (IllegalArgumentException e) {
            model.addAttribute(
                    "error",
                    e.getMessage());

            model.addAttribute(
                    "returnTo",
                    returnTo);

            return "publisher-form";
        }

    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id,
            @RequestParam(required = false) String returnTo,
            Model model) {

        model.addAttribute(
                "publisher",
                publisherService.getPublisherById(id));
        model.addAttribute("returnTo", returnTo);

        return "publisher-form";
    }

    @GetMapping("/delete/{id}")
    public String deletePublisher(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        try {

            publisherService.deletePublisher(id);

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Publisher deleted successfully.");

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Cannot delete publisher because it is used by one or more books.");
        }

        return "redirect:/publishers";
    }
}