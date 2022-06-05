package com.example.joboffersevice.controller;

import com.example.joboffersevice.dto.JobOfferDTO;
import com.example.joboffersevice.service.JobOfferService;
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

    @Autowired
    private JobOfferService jobOfferService;

    @GetMapping
    public ResponseEntity<List<JobOfferDTO>> getAllJobOffers() {

        List<JobOfferDTO> jobOffers = new ArrayList<JobOfferDTO>();

        jobOffers = jobOfferService.getAllJobOffers();

        return new ResponseEntity<>(jobOffers, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteJobOffer(@PathVariable String id){
        boolean status = jobOfferService.deleteJobOffer(id);
        if(status)
            return new ResponseEntity<>("Job offer successfully deleted!", HttpStatus.OK);
        else
            return new ResponseEntity<>("Job offer not found!", HttpStatus.NOT_FOUND);

    }

    @GetMapping(value = "/{idJobOffer}")
    public ResponseEntity<Object> getJobOfferById(@PathVariable String idJobOffer){

        JobOfferDTO jobOfferDTO = jobOfferService.getJobOfferById(idJobOffer);
        if(jobOfferDTO != null)
            return new ResponseEntity<>(jobOfferDTO, HttpStatus.OK);
        else
            return new ResponseEntity<>("Job offer not found!", HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/title")
    public ResponseEntity<Object> getJobOfferByTitle(@RequestParam String title) {
        JobOfferDTO jobOfferDTO = jobOfferService.getJobOfferByTitle(title);
        if(jobOfferDTO != null)
            return new ResponseEntity<>(jobOfferDTO, HttpStatus.OK);
        else
            return new ResponseEntity<>("Job offer not found!", HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/industry")
    public ResponseEntity<Object> getJobOfferByIndustry(@RequestParam String industry) {
        JobOfferDTO jobOfferDTO = jobOfferService.getJobOfferByIndustry(industry);
        if(jobOfferDTO != null)
            return new ResponseEntity<>(jobOfferDTO, HttpStatus.OK);
        else
            return new ResponseEntity<>("Job offer not found!", HttpStatus.NOT_FOUND);
    }
    @GetMapping(value = "/seniority")
    public ResponseEntity<Object> getJobOfferBySeniority(@RequestParam String seniority) {
        JobOfferDTO jobOfferDTO = jobOfferService.getJobOfferBySeniority(seniority);
        if(jobOfferDTO != null)
            return new ResponseEntity<>(jobOfferDTO, HttpStatus.OK);
        else
            return new ResponseEntity<>("Job offer not found!", HttpStatus.NOT_FOUND);
    }
    @GetMapping(value = "/workType")
    public ResponseEntity<Object> getJobOfferByWorkType(@RequestParam String workType) {
        JobOfferDTO jobOfferDTO = jobOfferService.getJobOfferByWorkType(workType);
        if(jobOfferDTO != null)
            return new ResponseEntity<>(jobOfferDTO, HttpStatus.OK);
        else
            return new ResponseEntity<>("Job offer not found!", HttpStatus.NOT_FOUND);
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<Object> createJobOffer(@RequestBody JobOfferDTO newJobOfferDTO){
        JobOfferDTO jobOfferDTO = jobOfferService.createJobOffer(newJobOfferDTO);
        return new ResponseEntity<>(jobOfferDTO, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/import/{key}")
    public ResponseEntity<Object> importJobOffer(@RequestBody JobOfferDTO newJobOfferDTO,
                                                 @PathVariable String key){
        JobOfferDTO jobOfferDTO = jobOfferService.importJobOffer(newJobOfferDTO, key);
        return new ResponseEntity<>(jobOfferDTO, HttpStatus.OK);
    }
    
}
