import axios from "axios";

const API_URL = 'http://localhost:8080/api';

export const getCurrencies = async () => {
    console.log(API_URL, 'api url');
    
    try {
        const response = await axios.get(API_URL+"/currencies");
        console.log(response, 'server resp');
        
        return response.data;
    } catch (error) {
        console.error("Error fetching currencies", error);
        throw error;
    }
};
