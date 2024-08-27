import axios from 'axios';

const API_URL = 'http://localhost:8080/api/keywords';

export const getKeywords = async (inputValue: string): Promise<string[]> => {
  try {
    const response = await axios.get(API_URL, {
      params: {
        query: inputValue,
      },
    });
    return response.data;
  } catch (error) {
    console.error('Error fetching keywords:', error);
    return [];
  }
};
