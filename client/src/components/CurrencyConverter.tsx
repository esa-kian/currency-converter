import React, { useEffect, useState } from "react";
import { TextField, Button, MenuItem, Container, Typography } from "@mui/material";
import { getCurrencies, convertCurrency } from "../services/currencyService";

interface Currency {
    code: string;
    name: string;
}

const CurrencyConverter: React.FC = () => {
    const [currencies, setCurrencies] = useState<Currency[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const [sourceCurrency, setSourceCurrency] = useState("EUR");
    const [targetCurrency, setTargetCurrency] = useState("USD");
    const [amount, setAmount] = useState<string>("");
    const [convertedAmount, setConvertedAmount] = useState<number | null>(null);

    useEffect(() => {
        const fetchCurrencies = async () => {
            try {
                const data = await getCurrencies();
                setCurrencies(data);

                setLoading(false);
            } catch (err) {
                setError("Failed to load currencies.");
                setLoading(false);
            }
        };

        fetchCurrencies();
    }, []);

    const calculateCurrency = async () => {
        setLoading(true);

        try {
            const data = await convertCurrency(sourceCurrency, targetCurrency, amount);
            setConvertedAmount(data);

            setLoading(false);
        } catch (err) {
            setError("Failed to load conversion.");
            setLoading(false);
        }

    }

    const handleConvert = (e: React.FormEvent) => {
        e.preventDefault();

        calculateCurrency()
        console.log("Converting:", amount, sourceCurrency, "to", targetCurrency);
    };


    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>{error}</div>;
    }

    return (
        <Container maxWidth="sm">
            <Typography variant="h4" gutterBottom>
                Currency Converter
            </Typography>
            <form onSubmit={handleConvert}>
                <TextField
                    select
                    label="From"
                    value={sourceCurrency}
                    onChange={(e) => setSourceCurrency(e.target.value)}
                    fullWidth
                    margin="normal"
                >
                    {currencies.length > 0 ? currencies.map((currency) => (
                        <MenuItem key={currency.code} value={currency.code}>
                            {currency.name}
                        </MenuItem>

                    )) : ""}
                </TextField>

                <TextField
                    select
                    label="To"
                    value={targetCurrency}
                    onChange={(e) => setTargetCurrency(e.target.value)}
                    fullWidth
                    margin="normal"
                >
                    {currencies.length > 0 ? currencies.map((currency) => (
                        <MenuItem key={currency.code} value={currency.code}>
                            {currency.name}
                        </MenuItem>
                    )) : ""}
                </TextField>

                <TextField
                    label="Amount"
                    type="number"
                    value={amount}
                    onChange={(e) => setAmount(e.target.value)}
                    fullWidth
                    margin="normal"
                    required
                />

                <Button type="submit" variant="contained" color="primary" fullWidth>
                    Convert
                </Button>
            </form>

            {convertedAmount !== null && (
                <Typography variant="h6" marginTop={2}>
                    Converted Amount: {convertedAmount}
                </Typography>
            )}
        </Container>
    );
};

export default CurrencyConverter;
