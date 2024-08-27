import axios from 'axios';

const API_URL = 'http://localhost:8080/api/balance';

export const getBalance = async (): Promise<number> => {
  try {
    const response = await axios.get(API_URL);
    return response.data;
  } catch (error) {
    console.error('Error fetching balance:', error);
    return 0;
  }
};
