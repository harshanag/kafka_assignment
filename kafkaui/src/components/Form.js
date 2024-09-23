import React, { useState } from 'react';
import axios from 'axios';

function Form() {
    const [person, setPerson] = useState({
        firstName: '',
        lastName: '',
        dateOfBirth: ''
    });

    const [message, setMessage] = useState('');

    // Handle form field changes
    const handleChange = (e) => {
        const { name, value } = e.target;
        setPerson(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    // Handle form submission
    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            // Send person data to the backend (Spring Boot API)
            const response = await axios.post(`${process.env.REACT_APP_API_URL}/person`, person, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            setMessage('Person data submitted successfully!');
        } catch (error) {
            setMessage('Error submitting data: ' + error.message);
        }
    };

    return (
        <div>
            <h1>Enter Person Data</h1>
            <form onSubmit={handleSubmit}>
                <label>
                    First Name:
                    <input
                        type="text"
                        name="firstName"
                        value={person.firstName}
                        onChange={handleChange}
                        required
                    />
                </label>
                <br />
                <label>
                    Last Name:
                    <input
                        type="text"
                        name="lastName"
                        value={person.lastName}
                        onChange={handleChange}
                        required
                    />
                </label>
                <br />
                <label>
                    Date of Birth:
                    <input
                        type="date"
                        name="dateOfBirth"
                        value={person.dateOfBirth}
                        onChange={handleChange}
                        required
                    />
                </label>
                <br />
                <button type="submit">Submit</button>
            </form>
            {message && <p>{message}</p>}
        </div>
    );
}

export default Form;
