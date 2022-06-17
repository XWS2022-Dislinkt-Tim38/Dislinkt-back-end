package com.example.joboffersevice.controller;

import com.example.joboffersevice.dto.JobOfferDTO;
import com.example.joboffersevice.service.JobOfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4201")
@RequestMapping(value = "/jobOffer")
public class JobOfferController {

    Logger logger = LoggerFactory.getLogger((JobOfferController.class));
    @Autowired
    private JobOfferService jobOfferService;

    @GetMapping
    public ResponseEntity<List<JobOfferDTO>> getAllJobOffers() {
        logger.info("GET REQUEST /jobOffer");
        List<JobOfferDTO> jobOffers = new ArrayList<JobOfferDTO>();

        jobOffers = jobOfferService.getAllJobOffers();

        return new ResponseEntity<>(jobOffers, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteJobOffer(@PathVariable String id){
        logger.info("DELETE REQUEST /jobOffer/{id}");
        boolean status = jobOfferService.deleteJobOffer(id);
        if(status)
            return new ResponseEntity<>("Job offer successfully deleted!", HttpStatus.OK);
        else
            return new ResponseEntity<>("Job offer not found!", HttpStatus.NOT_FOUND);

    }

    @GetMapping(value = "/{idJobOffer}")
    public ResponseEntity<Object> getJobOfferById(@PathVariable String idJobOffer){
        logger.info("GET REQUEST /jobOffer/{idJobOffer}");
        JobOfferDTO jobOfferDTO = jobOfferService.getJobOfferById(idJobOffer);
        if(jobOfferDTO != null)
            return new ResponseEntity<>(jobOfferDTO, HttpStatus.OK);
        else
            return new ResponseEntity<>("Job offer not found!", HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/title")
    public ResponseEntity<Object> getJobOfferByTitle(@RequestParam String title) {
        logger.info("GET REQUEST /jobOffer/title");
        JobOfferDTO jobOfferDTO = jobOfferService.getJobOfferByTitle(title);
        if(jobOfferDTO != null)
            return new ResponseEntity<>(jobOfferDTO, HttpStatus.OK);
        else
            return new ResponseEntity<>("Job offer not found!", HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/industry")
    public ResponseEntity<Object> getJobOfferByIndustry(@RequestParam String industry) {
        logger.info("GET REQUEST /jobOffer/industry");
        JobOfferDTO jobOfferDTO = jobOfferService.getJobOfferByIndustry(industry);
        if(jobOfferDTO != null)
            return new ResponseEntity<>(jobOfferDTO, HttpStatus.OK);
        else
            return new ResponseEntity<>("Job offer not found!", HttpStatus.NOT_FOUND);
    }
    @GetMapping(value = "/seniority")
    public ResponseEntity<Object> getJobOfferBySeniority(@RequestParam String seniority) {
        logger.info("GET REQUEST /jobOffer/seniority");
        JobOfferDTO jobOfferDTO = jobOfferService.getJobOfferBySeniority(seniority);
        if(jobOfferDTO != null)
            return new ResponseEntity<>(jobOfferDTO, HttpStatus.OK);
        else
            return new ResponseEntity<>("Job offer not found!", HttpStatus.NOT_FOUND);
    }
    @GetMapping(value = "/workType")
    public ResponseEntity<Object> getJobOfferByWorkType(@RequestParam String workType) {
        logger.info("GET REQUEST /jobOffer/workType");
        JobOfferDTO jobOfferDTO = jobOfferService.getJobOfferByWorkType(workType);
        if(jobOfferDTO != null)
            return new ResponseEntity<>(jobOfferDTO, HttpStatus.OK);
        else
            return new ResponseEntity<>("Job offer not found!", HttpStatus.NOT_FOUND);
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<Object> createJobOffer(@RequestBody JobOfferDTO newJobOfferDTO){
        logger.info("POST REQUEST /jobOffer");
        JobOfferDTO jobOfferDTO = jobOfferService.createJobOffer(newJobOfferDTO);
        return new ResponseEntity<>(jobOfferDTO, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/import/{key}")
    public ResponseEntity<Object> importJobOffer(@RequestBody JobOfferDTO newJobOfferDTO,
                                                 @PathVariable String key){
        logger.info("POST REQUEST /jobOffer/import/{key}");
        JobOfferDTO jobOfferDTO = jobOfferService.importJobOffer(newJobOfferDTO, key);
        return new ResponseEntity<>(jobOfferDTO, HttpStatus.OK);
    }
    
}
