package com.example.userservice.service;

import com.example.userservice.dto.JobOfferDTO;
import com.example.userservice.model.JobOffer;
import com.example.userservice.repository.JobOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobOfferService {

    @Autowired
    private JobOfferRepository jobOfferRepository;

   public List<JobOfferDTO> getAllJobOffers(){
        List<JobOffer> jobOffers = jobOfferRepository.findAll();
        List<JobOfferDTO> jobOffersDTO = new ArrayList<>();

        for(JobOffer jobOffer: jobOffers){
            jobOffersDTO.add(new JobOfferDTO(jobOffer));
        }
        return jobOffersDTO;
    }

    public JobOffer getJobOfferByPosition(String position) {
        JobOffer jobOffer = jobOfferRepository.findByPosition(position);
        if (jobOffer != null)
            return jobOffer;
        else
            return null;
    }

    public JobOfferDTO getJobOfferById(String id) {
        JobOffer jobOffer = jobOfferRepository.findById(id).orElse(null);
        if (jobOffer != null)
            return new JobOfferDTO(jobOffer);
        else
            return null;
    }

    public JobOfferDTO createJobOffer(JobOfferDTO newJobOfferDTO) {

        JobOffer jobOffer = new JobOffer(newJobOfferDTO);
        jobOfferRepository.save(jobOffer);

        return new JobOfferDTO(jobOffer);
    }

    public boolean deleteJobOffer(String id) {
        boolean status = jobOfferRepository.existsById(id);
        if (status)
            jobOfferRepository.deleteById(id);

        return status;

    }
}
