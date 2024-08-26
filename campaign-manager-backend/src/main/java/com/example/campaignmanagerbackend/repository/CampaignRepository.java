package com.example.campaignmanagerbackend.repository;

import com.example.campaignmanagerbackend.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    List<Campaign> findByStatus(Boolean status);
}
