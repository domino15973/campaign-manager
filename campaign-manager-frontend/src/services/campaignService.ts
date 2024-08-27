import axios from 'axios';

export interface Campaign {
  id?: number;
  name: string;
  keywords: string[];
  bidAmount: number;
  campaignFund: number;
  status: boolean;
  town: string;
  radius: number;
}

const API_URL = 'http://localhost:8080/api/campaigns';

export const getAllCampaigns = async (): Promise<Campaign[]> => {
  const response = await axios.get(API_URL);
  return response.data;
};

export const createCampaign = async (campaign: Campaign): Promise<Campaign> => {
  const response = await axios.post(API_URL, campaign);
  return response.data;
};

export const updateCampaign = async (id: number, campaign: Campaign): Promise<Campaign> => {
  const response = await axios.put(`${API_URL}/${id}`, campaign);
  return response.data;
};

export const deleteCampaign = async (id: number): Promise<void> => {
  await axios.delete(`${API_URL}/${id}`);
};
