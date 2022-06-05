package com.example.joboffersevice.service;

import com.example.joboffersevice.dto.JobOfferDTO;
import com.example.joboffersevice.dto.UserDTO;
import com.example.joboffersevice.model.JobOffer;
import com.example.joboffersevice.repository.JobOfferRepository;
import com.example.joboffersevice.service.common.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobOfferService {
    @Autowired
    private JobOfferRepository jobOfferRepository;

    @Autowired
    private UserFeignClient userFeignClient;

    public List<JobOfferDTO> getAllJobOffers(){
        List<JobOffer> jobOffers = jobOfferRepository.findAll();
        List<JobOfferDTO> jobOffersDTO = new ArrayList<>();

        for(JobOffer jobOffer: jobOffers){
            jobOffersDTO.add(new JobOfferDTO(jobOffer));
        }
        return jobOffersDTO;
    }

    public boolean deleteJobOffer(String id) {
        boolean status = jobOfferRepository.existsById(id);
        if (status)
            jobOfferRepository.deleteById(id);

        return status;

    }

    public JobOfferDTO getJobOfferById(String idJobOffer) {
        JobOffer jobOffer = jobOfferRepository.findById(idJobOffer).orElse(null);
        if (jobOffer != null)
            return new JobOfferDTO(jobOffer);
        else
            return null;
    }

    public JobOfferDTO getJobOfferByTitle(String title) {
        JobOffer jobOffer = jobOfferRepository.findByTitle(title);
        if (jobOffer != null)
            return new JobOfferDTO(jobOffer);
        else
            return null;
    }
    public JobOfferDTO getJobOfferByIndustry(String industry) {
        JobOffer jobOffer = jobOfferRepository.findByIndustry(industry);
        if (jobOffer != null)
            return new JobOfferDTO(jobOffer);
        else
            return null;
    }
    public JobOfferDTO getJobOfferBySeniority(String seniority) {
        JobOffer jobOffer = jobOfferRepository.findBySeniority(seniority);
        if (jobOffer != null)
            return new JobOfferDTO(jobOffer);
        else
            return null;
    }
    public JobOfferDTO getJobOfferByWorkType(String workType) {
        JobOffer jobOffer = jobOfferRepository.findByWorkType(workType);
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

    public JobOfferDTO importJobOffer(JobOfferDTO newJobOfferDTO, String key) {
        UserDTO user = userFeignClient.getUser(key);
        JobOffer jobOffer = new JobOffer(newJobOfferDTO);
        jobOffer.idUSer = user.id;
        jobOfferRepository.save(jobOffer);
        return new JobOfferDTO(jobOffer);
    }
}
