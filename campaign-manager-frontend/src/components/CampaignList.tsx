import React, { useState, useEffect } from 'react';
import CampaignItem from './CampaignItem';
import CampaignForm from './CampaignForm';
import Modal from './Modal';
import { getAllCampaigns, deleteCampaign, Campaign } from '../services/campaignService';
import { getBalance } from '../services/balanceService';

const CampaignList: React.FC = () => {
  const [campaigns, setCampaigns] = useState<Campaign[]>([]);
  const [selectedCampaign, setSelectedCampaign] = useState<Campaign | null>(null);
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
  const [balance, setBalance] = useState<number>(0);

  useEffect(() => {
    fetchCampaigns().then();
    fetchBalance().then();
  }, []);

  const fetchCampaigns = async () => {
    const response = await getAllCampaigns();
    setCampaigns(response);
  };

  const fetchBalance = async () => {
    const currentBalance = await getBalance();
    setBalance(currentBalance);
  };

  const handleDelete = async (id: number) => {
    await deleteCampaign(id);
    await fetchCampaigns();
    await fetchBalance();
  };

  const openModal = (campaign: Campaign | null) => {
    setSelectedCampaign(campaign);
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setSelectedCampaign(null);
    setIsModalOpen(false);
    fetchCampaigns().then();
    fetchBalance().then();
  };

  return (
    <div className="container">
      <div className="balance">Balance: ${balance.toFixed(2)}</div>
      <button className="create-button" onClick={() => openModal(null)}>Create Campaign</button>
      <ul>
        {campaigns.map(campaign => (
          <CampaignItem
            key={campaign.id}
            campaign={campaign}
            onEdit={() => openModal(campaign)}
            onDelete={() => campaign.id !== undefined && handleDelete(campaign.id)}
          />
        ))}
      </ul>
      {isModalOpen && (
        <Modal>
          <CampaignForm campaign={selectedCampaign} onClose={closeModal} />
        </Modal>
      )}
    </div>
  );
};

export default CampaignList;
