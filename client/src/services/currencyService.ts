import axios from "axios";

const API_URL = 'http://localhost:8080/api';

export const getCurrencies = async () => {

    try {
        const response = await axios.get(API_URL + "/currencies");

        return response.data;
    } catch (error) {
        console.error("Error fetching currencies", error);
        throw error;
    }
};

export const convertCurrency = async (source: string, target: string, amount: string) => {

    try {
        let data = {
            source: source,
            target: target,
            amount: amount
        }
        const response = await axios.post(API_URL + "/currencies/convert", data);

        return response.data.convertedAmount;
    } catch (error) {
        console.error("Error fetching currencies", error);
        throw error;
    }
};
