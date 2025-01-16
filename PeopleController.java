package com.TalkTexas.texasAPi.controller;

import com.TalkTexas.texasAPi.service.PeopleService;
import com.TalkTexas.texasAPi.model.PersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "https://talk-texas.vercel.app") // Allow React frontend to access this API
@RestController
public class PeopleController {

    @Autowired
    private PeopleService peopleService;

    // Endpoint to fetch and save data from external API
    @GetMapping("/representatives")
    public List<PersonDTO> fetchAndSaveTexasPeople() {
        List<PersonDTO> texasPeople = peopleService.getTexasPeople();
        return peopleService.saveTexasPeople(texasPeople); // Save data to the database
    }

    
}
