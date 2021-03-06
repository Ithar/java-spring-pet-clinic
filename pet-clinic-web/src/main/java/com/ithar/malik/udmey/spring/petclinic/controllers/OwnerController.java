package com.ithar.malik.udmey.spring.petclinic.controllers;

import com.ithar.malik.udmey.spring.petclinic.dto.OwnerDTO;
import com.ithar.malik.udmey.spring.petclinic.model.Owner;
import com.ithar.malik.udmey.spring.petclinic.service.OwnerService;
import java.util.Set;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

@Slf4j
@RequestMapping("/owners")
@Controller
public class OwnerController {

    private static final String OWNER_LIST = "owners/list";
    private static final String OWNER_VIEW = "owners/view";
    private static final String OWNER_FORM = "owners/form";
    private static final String OWNER_FIND = "owners/find";

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    // List Owners
    @GetMapping({"", "/", "/index", "/index.html", "/list"})
    public String list(Model model) {
        return listOwners(model, ownerService.findAll());
    }

    private String listOwners(Model model, Set<Owner> owners) {
        model.addAttribute("owners", owners);
        return OWNER_LIST;
    }

    // View Owners
    @GetMapping("/{id}")
    public ModelAndView viewOwner(@PathVariable long id) {

        log.info("View owner [id={}]", id);

        ModelAndView mav = new ModelAndView(OWNER_VIEW);
        mav.addObject("owner", ownerService.mapToDTO(ownerService.findById(id)));
        return mav;
    }

    // Create owner
    @GetMapping("/new")
    public ModelAndView createOwnerForm() {

        log.info("New owner form");

        ModelAndView mav = new ModelAndView(OWNER_FORM);
        mav.addObject("owner", new OwnerDTO());
        return mav;
    }

    @PostMapping("/new")
    public String createOwnerFormProcess(Model model, @Valid OwnerDTO ownerDTO, BindingResult result) {

        log.info("Creating new owner");

        if (result.hasErrors()) {
            model.addAttribute("owner", ownerDTO);
            return OWNER_FORM;
        }

        Owner owner = ownerService.save(ownerDTO);
        return "redirect:/owners/" + owner.getId();
    }

    // Edit owner
    @GetMapping("/{id}/edit")
    public ModelAndView editOwnerForm(@PathVariable long id) {

        log.info("Editing owner with id {}", id);

        Owner owner = ownerService.findById(id);

        ModelAndView mav = new ModelAndView(OWNER_FORM);
        mav.addObject("owner", ownerService.mapToDTO(owner));
        return mav;
    }

    @PostMapping("/{id}/edit")
    public String editOwnerFormProcess(Model model, @PathVariable long id,
        @Valid OwnerDTO ownerDTO, BindingResult result) {

        log.info("Processing owner edit with id {}", id);

        Owner owner = ownerService.findById(id);

        // TODO [IM 19-08-10] - Move these to validation class
        if (id != ownerDTO.getId()) {
            result.reject("Failed to edit owner");
        }

        if (owner == null) {
            result.reject("Failed to edit owner");
        }

        if (result.hasErrors()) {
            model.addAttribute("owner", ownerDTO);
            return OWNER_FORM;
        }

        ownerDTO.setPets(owner.getPets());
        ownerService.save(ownerDTO);
        return "redirect:/owners/" + ownerDTO.getId();
    }

    // Find Owners
    @GetMapping("find")
    public ModelAndView findOwner() {
        ModelAndView mav = new ModelAndView(OWNER_FIND);
        mav.addObject("owner", new OwnerDTO());
        return mav;
    }

    @PostMapping("find")
    public String findOwnerProcess(Model model, Owner owner, BindingResult result) {

        if (StringUtils.isEmpty(owner.getLastName())) {
            result.rejectValue("lastName", "notFound", "not found");
            log.warn("Cannot find owner with empty last name:" + owner.getLastName());
            return OWNER_FIND;
        }

        if (result.hasErrors()) {
            model.addAttribute("owner", ownerService.mapToDTO(owner));
            return "owners/form";
        }

        String lastName = owner.getLastName();
        Set<Owner> owners = ownerService.findByLastNameLike(lastName);

        if (owners.isEmpty()) {
            result.rejectValue("lastName", "notFound", "No owner with last name '" + lastName + "' found.");
            return OWNER_FIND;
        } else if (owners.size() == 1) {
            return "redirect:/owners/" + owners.iterator().next().getId();
        }

        return listOwners(model, owners);
    }
}
