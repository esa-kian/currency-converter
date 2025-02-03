import React, { useState } from "react";
import { TextField, Button, MenuItem, Container, Typography } from "@mui/material";

const currencies = ["USD", "EUR", "GBP", "JPY", "CAD"]; // Temporary list

const CurrencyConverter: React.FC = () => {
    const [sourceCurrency, setSourceCurrency] = useState("USD");
    const [targetCurrency, setTargetCurrency] = useState("EUR");
    const [amount, setAmount] = useState<string>("");
    const [convertedAmount, setConvertedAmount] = useState<number | null>(null);

    const handleConvert = (e: React.FormEvent) => {
        e.preventDefault();
        // TODO: Call the backend API to get the converted value
        console.log("Converting:", amount, sourceCurrency, "to", targetCurrency);
    };

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
                    {currencies.map((currency) => (
                        <MenuItem key={currency} value={currency}>
                            {currency}
                        </MenuItem>
                    ))}
                </TextField>

                <TextField
                    select
                    label="To"
                    value={targetCurrency}
                    onChange={(e) => setTargetCurrency(e.target.value)}
                    fullWidth
                    margin="normal"
                >
                    {currencies.map((currency) => (
                        <MenuItem key={currency} value={currency}>
                            {currency}
                        </MenuItem>
                    ))}
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
