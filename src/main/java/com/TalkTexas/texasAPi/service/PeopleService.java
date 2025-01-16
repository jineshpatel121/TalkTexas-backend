package com.TalkTexas.texasAPi.service;

import com.TalkTexas.texasAPi.model.Person;
import com.TalkTexas.texasAPi.model.PersonDTO;
import com.TalkTexas.texasAPi.repository.PersonRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Service
public class PeopleService {

    @Autowired
    private PersonRepository personRepository;

    private final String apiKey = "134294ae-a2a7-49c8-b3ea-b6f10f84c703";
    private final String urlString = "https://v3.openstates.org/people";
    private final RestTemplate restTemplate = new RestTemplate();

    public List<PersonDTO> getTexasPeople() {
        List<PersonDTO> texasPeople = new ArrayList<>();
        int page = 1;
        int maxPages = 4;

        try {
            while (page <= maxPages) {
                String paginatedUrl = String.format(
                    "%s?jurisdiction=Texas&include=offices&page=%d&per_page=50", 
                    urlString, 
                    page
                );

                HttpHeaders headers = new HttpHeaders();
                headers.set("X-API-KEY", apiKey);
                HttpEntity<String> entity = new HttpEntity<>(headers);

                ResponseEntity<String> response;
                try {
                    response = restTemplate.exchange(paginatedUrl, HttpMethod.GET, entity, String.class);
                } catch (HttpClientErrorException.TooManyRequests e) {
                    System.out.println("Rate limit exceeded. Sleeping for 60 seconds.");
                    Thread.sleep(60000);
                    continue;
                }

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root = objectMapper.readTree(response.getBody());
                JsonNode results = root.path("results");

                if (results.isMissingNode() || results.size() == 0) {
                    break;
                }

                for (JsonNode result : results) {
                    JsonNode jurisdiction = result.path("jurisdiction");
                    String jurisdictionId = jurisdiction.path("id").asText();

                    if (jurisdictionId.contains("state:tx")) {
                        String name = result.path("name").asText();
                        String party = result.path("party").asText();
                        String email = result.path("email").asText();
                        JsonNode currentRole = result.path("current_role");
                        String title = currentRole.path("title").asText();
                        String orgClass = currentRole.path("org_classification").asText();
                        String district = currentRole.path("district").asText();
                        JsonNode offices = result.path("offices");
                        String number = offices.isArray() && offices.size() > 0
                             ? offices.get(0).path("voice").asText()
                            : "Phone: N/A";
                        String imageUrl = result.path("image").asText();
                        String moreUrl = result.path("openstates_url").asText();

                        texasPeople.add(new PersonDTO(name, party, email, title, orgClass, district, number, imageUrl, moreUrl));
                    }
                }

                page++;
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }

        return texasPeople;
    }

    public List<PersonDTO> saveTexasPeople(List<PersonDTO> texasPeople) {
        List<Person> savedPeople = new ArrayList<>();
        for (PersonDTO personDTO : texasPeople) {
            Person person = new Person(
                personDTO.getName(),
                personDTO.getParty(),
                personDTO.getEmail(),
                personDTO.getTitle(),
                personDTO.getOrgClass(),
                personDTO.getDistrict(),
                personDTO.getNumber(),
                personDTO.getImageUrl(),
                personDTO.getMoreUrl()
            );

            savedPeople.add(personRepository.save(person));
        }

        List<PersonDTO> savedPersonDTOs = new ArrayList<>();
        for (Person person : savedPeople) {
            savedPersonDTOs.add(new PersonDTO(
                person.getName(),
                person.getParty(),
                person.getEmail(),
                person.getTitle(),
                person.getOrgClass(),
                person.getDistrict(),
                person.getNumber(),
                person.getImageUrl(),
                person.getMoreUrl()
            ));
        }

        return savedPersonDTOs;
    }

    public List<PersonDTO> getSavedRepresentatives() {
        List<Person> savedPeople = personRepository.findAll();
        List<PersonDTO> savedPersonDTOs = new ArrayList<>();

        for (Person person : savedPeople) {
            savedPersonDTOs.add(new PersonDTO(
                person.getName(),
                person.getParty(),
                person.getEmail(),
                person.getTitle(),
                person.getOrgClass(),
                person.getDistrict(),
                person.getNumber(),
                person.getImageUrl(),
                person.getMoreUrl()
            ));
        }

        return savedPersonDTOs;
    }
}
