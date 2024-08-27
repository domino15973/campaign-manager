import React from 'react';
import { Campaign } from '../services/campaignService';

interface CampaignItemProps {
  campaign: Campaign;
  onEdit: () => void;
  onDelete: () => void;
}

const CampaignItem: React.FC<CampaignItemProps> = ({ campaign, onEdit, onDelete }) => {
  return (
    <li>
      <h3>{campaign.name}</h3>
      <p><strong>Keywords:</strong> {campaign.keywords.join(', ')}</p>
      <p><strong>Bid Amount:</strong> ${campaign.bidAmount.toFixed(2)}</p>
      <p><strong>Campaign Fund:</strong> ${campaign.campaignFund.toFixed(2)}</p>
      <p><strong>Status:</strong> {campaign.status ? 'On' : 'Off'}</p>
      <p><strong>Town:</strong> {campaign.town}</p>
      <p><strong>Radius:</strong> {campaign.radius} km</p>
      <button onClick={onEdit}>Edit</button>
      <button onClick={onDelete}>Delete</button>
    </li>
  );
};

export default CampaignItem;
