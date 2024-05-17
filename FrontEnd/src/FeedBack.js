import React, { useState, useEffect } from 'react';
import axios from 'axios';

function Feedback({ hotelId }) {
  const [services, setServicesRating] = useState(1);
  const [cleanliness, setCleanlinessRating] = useState(1);
  const [food, setFoodRating] = useState(1);
  const [comment, setComment] = useState('');

  useEffect(() => {
    setServicesRating(1);
    setCleanlinessRating(1);
    setFoodRating(1);
    setComment('');
  }, [hotelId]);

  const handleSaveFeedback = async () => {
    try {
      const response = await axios.post('http://localhost:8085/feedback/addFeedback', {
        hotelId,
        services,
        cleanliness,
        food,
        comment
      });
      console.log('Feedback saved successfully:', response.data);
      alert("Feedback salvat cu succes");
    } catch (error) {
      console.error('Error saving feedback:', error);
    }
  };

  return (
    <div>
      <h2>Feedback Form</h2>
      <label>
        Services Rating (1-5):
        <input type="number" value={services} onChange={e => setServicesRating(Math.min(Math.max(Number(e.target.value), 1), 5))} min="1" max="5" />
      </label>
      <br />
      <label>
        Cleanliness Rating (1-5):
        <input type="number" value={cleanliness} onChange={e => setCleanlinessRating(Math.min(Math.max(Number(e.target.value), 1), 5))} min="1" max="5" />
      </label>
      <br />
      <label>
        Food Rating (1-5):
        <input type="number" value={food} onChange={e => setFoodRating(Math.min(Math.max(Number(e.target.value), 1), 5))} min="1" max="5" />
      </label>
      <br />
      <label>
        Comment:
        <textarea value={comment} onChange={e => setComment(e.target.value)} />
      </label>
      <br />
      <button onClick={handleSaveFeedback}>Save Feedback</button>
    </div>
  );
}

export default Feedback;
