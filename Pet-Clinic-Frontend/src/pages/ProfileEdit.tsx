import React, { useState } from 'react';
import "./ProfileEdit.css";

const backendUrl = import.meta.env.VITE_BACKEND_URL || 'http://localhost:8080';

const ProfileEdit = () => {

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState<string | null>(null);


  const sendToBackend = async (email: string, password: string) => {
    try {
      const response = await fetch(`${backendUrl}/profile-edit`, { // what endpoint is in backEnd??
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, password }),
      });

      if (!response.status.toString().startsWith('2')) {
        setError('Failed to update profile. Please try again.');
      } else {
        setSuccess('Profile updated successfully!');
      }

    } catch (error) {
      console.error('Error:', error);
      setError('An error occurred. Please try again later.');
    }
  };


  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setError(null);
    setSuccess(null);

    if (!email || !password) {
      setError('Email and password are required.');
      return;
    }


    sendToBackend(email, password);
  };

  return (
    <div className='formDiv'>
      <h1 className='rf-title'>Edit Profile</h1>
      <form onSubmit={handleSubmit}>
        {error && <div className="error-message">{error}</div>}
        {success && <div className="success-message">{success}</div>}

        <div>
          <label className='label' htmlFor="email">Enter a new Email:</label>
          <input
            className='input'
            type="email"
            id="email"
            name="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </div>

        <div>
          <label className='label' htmlFor="password">Enter a new Password:</label>
          <input
            className='input'
            type="password"
            id="password"
            name="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>

        <button className='btn' type="submit">Save Changes</button>
      </form>
    </div>
  );
};

export default ProfileEdit;