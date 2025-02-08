import axios from "axios";

const API_URL = 'http://localhost:8080/api';

export const getCurrencies = async () => {

    try {
        const response = await axios.get(API_URL + "/currencies");

        return response.data;
    } catch (error: any) {
        console.error("Error fetching currencies", error);
        throw error.response?.data?.message || "An unknown error occurred";
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
    } catch (error: any) {
        if (error.response?.data) {
            const errors = error.response.data;

            return Promise.reject(
                Object.values(errors).join(" | ") || "An unknown error occurred"
            );
        }

        return Promise.reject("An unknown error occurred");

    }
};
