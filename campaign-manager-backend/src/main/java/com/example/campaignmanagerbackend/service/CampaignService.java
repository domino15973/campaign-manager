package com.example.campaignmanagerbackend.service;

import com.example.campaignmanagerbackend.exception.CampaignNotFoundException;
import com.example.campaignmanagerbackend.model.Campaign;
import com.example.campaignmanagerbackend.repository.CampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final BalanceService balanceService;

    public List<Campaign> getAllCampaigns() {
        return campaignRepository.findAll();
    }

    public Campaign getCampaignById(Long id) {
    return campaignRepository.findById(id)
            .orElseThrow(() -> new CampaignNotFoundException("Campaign not found"));
}

    public Campaign createCampaign(Campaign campaign) {
        balanceService.deductBalance(campaign.getCampaignFund());
        return campaignRepository.save(campaign);
    }

    public Campaign updateCampaign(Long id, Campaign updatedCampaign) {
        Campaign campaign = getCampaignById(id);

        balanceService.addBalance(campaign.getCampaignFund());

        balanceService.deductBalance(updatedCampaign.getCampaignFund());

        campaign.setName(updatedCampaign.getName());
        campaign.setKeywords(updatedCampaign.getKeywords());
        campaign.setBidAmount(updatedCampaign.getBidAmount());
        campaign.setCampaignFund(updatedCampaign.getCampaignFund());
        campaign.setStatus(updatedCampaign.getStatus());
        campaign.setTown(updatedCampaign.getTown());
        campaign.setRadius(updatedCampaign.getRadius());

        return campaignRepository.save(campaign);
    }

    public void deleteCampaign(Long id) {
        Campaign campaign = getCampaignById(id);
        balanceService.addBalance(campaign.getCampaignFund());
        campaignRepository.deleteById(id);
    }
}
