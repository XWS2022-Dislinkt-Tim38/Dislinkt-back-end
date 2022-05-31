package com.example.userservice.controller;

import com.example.userservice.dto.JobOfferDTO;
import com.example.userservice.model.JobOffer;
import com.example.userservice.service.JobOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
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

    @GetMapping(value = "/position")
    public ResponseEntity<Object> getJobOfferByPosition(@RequestParam String position){

        JobOffer jobOffer = jobOfferService.getJobOfferByPosition(position);
        if(jobOffer != null)
            return new ResponseEntity<>(jobOffer, HttpStatus.OK);
        else
            return new ResponseEntity<>("Job offer not found!", HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getJobOfferById(@PathVariable String id){

        JobOfferDTO jobOfferDTO = jobOfferService.getJobOfferById(id);
        if(jobOfferDTO != null)
            return new ResponseEntity<>(jobOfferDTO, HttpStatus.OK);
        else
            return new ResponseEntity<>("Job offer not found!", HttpStatus.NOT_FOUND);
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<Object> createJobOffer(@RequestBody JobOfferDTO newJobOfferDTO){
        JobOfferDTO jobOffer = jobOfferService.createJobOffer(newJobOfferDTO);
        if(jobOffer != null)
            return new ResponseEntity<>(jobOffer, HttpStatus.OK);
        else
            return new ResponseEntity<>("Something is wrong!", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteJobOffer(@PathVariable String id){
        boolean status = jobOfferService.deleteJobOffer(id);
        if(status)
            return new ResponseEntity<>("Job offer successfully deleted!", HttpStatus.OK);
        else
            return new ResponseEntity<>("Job offer not found!", HttpStatus.NOT_FOUND);

    }
}
