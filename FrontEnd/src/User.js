import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { getDistance } from 'geolib';
import Feedback from './FeedBack';
import './User.css';

function User() {
  const [userLocationInMeters, setUserLocationInMeters] = useState(null);
  const [hotels, setHotels] = useState([]);
  const [radiusInKilometers, setRadiusInKilometers] = useState(0);
  const [selectedHotel, setSelectedHotel] = useState(null);
  const [rooms, setRooms] = useState([]);
  const [showFeedbackForm, setShowFeedbackForm] = useState(false);
  const [feedbackHotelId, setFeedbackHotelId] = useState(null);
  const [feedbackFormKey, setFeedbackFormKey] = useState(0);
  const [userReservationsWithHotelName, setUserReservationsWithHotelName] = useState([]);
  const [showUserReservations, setShowUserReservations] = useState(false);
  const [availableRooms, setAvailableRooms] = useState([]);
  const [reservationIdToModify, setReservationIdToModify] = useState(null);
  const [selectedDateTime, setSelectedDateTime] = useState(''); 

  useEffect(() => {
    function getUserLocation() {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
          position => {
            const latitudeInMeters = position.coords.latitude * 111132.92;
            const longitudeInMeters = position.coords.longitude * (111412.84 * Math.cos(position.coords.latitude * Math.PI / 180));
            setUserLocationInMeters({ latitude: latitudeInMeters, longitude: longitudeInMeters });
          },
          error => {
            console.error('Error getting user position:', error);
          }
        );
      } else {
        console.error('Geolocation is not supported by this browser.');
      }
    }

    async function fetchHotels() {
      try {
        const response = await axios.get('http://localhost:8085/hotel/api/getAllHotelsWithCoordinatesInMeters');
        const radiusInMeters = radiusInKilometers * 1000;
        const filteredHotels = response.data.filter(hotel =>
          getDistance(
            { latitude: userLocationInMeters.latitude, longitude: userLocationInMeters.longitude },
            { latitude: hotel.latitudeInMeters, longitude: hotel.longitudeInMeters }
          ) <= radiusInMeters
        );
        setHotels(filteredHotels);
        console.log(filteredHotels);
      } catch (error) {
        console.error('Error fetching hotels:', error);
      }
    }

    getUserLocation();
    fetchHotels();
  }, [radiusInKilometers]);

  async function handleDetailClick(hotel) {
    setSelectedHotel(hotel);
    try {
      const response = await axios.post(`http://localhost:8085/room/getAllbyHotel?idHotel=${hotel.id}`);
      setRooms(response.data);
      console.log('Details of rooms in', hotel.name, 'have been loaded:', response.data);
    } catch (error) {
      console.error('Error fetching rooms:', error);
    }
  }

  async function handleReservation(roomId) {
    try {
      await axios.put(`http://localhost:8085/room/reserve/${roomId}`);
  
      function convertToDateTime(dateTimeString) {
        const date = new Date(dateTimeString);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');
        const seconds = String(date.getSeconds()).padStart(2, '0');
  
        return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}`;
      }
  
      const dateTime = convertToDateTime(selectedDateTime);
  
      await axios.post('http://localhost:8085/reservation/add', {
        roomId: roomId,
        userId: 1,
        date: dateTime
      });
  
      const updatedRooms = rooms.map(room => {
        if (room.id === roomId) {
          return { ...room, available: false };
        }
        return room;
      });
      setRooms(updatedRooms);
      console.log('Room with id', roomId, 'has been reserved.');
  
      await fetchUserReservations();
    } catch (error) {
      console.error('Error reserving room:', error);
    }
  }
  

  async function updatedReservations(roomId, reservationId) {
    try {
      const reservationToUpdate = userReservationsWithHotelName.find(reservation => reservation.id === reservationId);
      if (reservationToUpdate) {
        const reservationDate = new Date(reservationToUpdate.date);
        const currentDate = new Date();
        const timeDifference = currentDate.getTime() - reservationDate.getTime();
        const hoursDifference = Math.abs(timeDifference / (1000 * 60 * 60));
  
        if (hoursDifference < 2) {
          alert("Nu puteți actualiza această rezervare, deoarece diferența față de data curentă este mai mică de 2 ore.");
          return;
        }
      }
  
      await axios.post(`http://localhost:8085/room/setRoomAvailable/${reservationId}`);
      
      try {
        await axios.put(`http://localhost:8085/reservation/updateReservationRoom/${reservationId}?roomId=${roomId}`);
      } catch (error) {
        console.error('Error updating reservation room:', error);
      }
      await axios.post(`http://localhost:8085/room/setRoomUnAvailable/${reservationId}`);
  
  
      const updatedReservations = userReservationsWithHotelName.map(reservation => {
        if (reservation.id === reservationId) {
          return { ...reservation, roomId: roomId };
        }
        return reservation;
      });
      setUserReservationsWithHotelName(updatedReservations);
      console.log('Room with id', roomId, 'has been updated for reservation with id', reservationId);
      window.location.reload();

    } catch (error) {
      console.error('Error updating reservation room:', error);
    }
  }
  
  
  async function fetchUserReservations() {
    try {
      
      const response = await axios.get('http://localhost:8085/reservation/reservation/getUserReservations?userId=1');
      const reservationsWithHotelName = await Promise.all(response.data.map(async reservation => {
        try {
          const hotelResponse = await axios.get(`http://localhost:8085/hotel/api/getHotelById/${reservation.hotelId}`);
          return { ...reservation, hotelName: hotelResponse.data.name };
        } catch (error) {
          console.error('Error fetching hotel details:', error);
          return reservation;
        }
      }));
      setUserReservationsWithHotelName(reservationsWithHotelName);
      console.log('User reservations with hotel name:', reservationsWithHotelName);
    } catch (error) {
      console.error('Error fetching user reservations:', error);
    }
  }

  useEffect(() => {
    fetchUserReservations();
  }, []);

  async function cancelReservation(reservationId) {
    try {
      const canceledReservation = userReservationsWithHotelName.find(reservation => reservation.id === reservationId);
      if (canceledReservation) {
        const reservationDate = new Date(canceledReservation.date);
        const currentDate = new Date();
        const timeDifference = currentDate.getTime() - reservationDate.getTime();
        const hoursDifference = Math.abs(timeDifference / (1000 * 60 * 60));
  
        if (hoursDifference < 2) {
          alert("Nu puteți anula această rezervare, deoarece diferența față de data curentă este mai mică de 2 ore.");
          return;
        }
        
        await axios.post(`http://localhost:8085/room/setRoomAvailable/${reservationId}`);
      }
  
      await axios.delete(`http://localhost:8085/reservation/deleteReservation/${reservationId}`);
  
      const updatedReservations = userReservationsWithHotelName.filter(reservation => reservation.id !== reservationId);
      setUserReservationsWithHotelName(updatedReservations);
      console.log('Reservation with id', reservationId, 'has been canceled.');
    } catch (error) {
      console.error('Error canceling reservation:', error);
    }
  }
  

  function handleFeedbackClick(hotelId) {
    setFeedbackFormKey(prevKey => prevKey + 1);
    setFeedbackHotelId(hotelId);
    setShowFeedbackForm(true);
  }

  function handleSeeReservationsClick() {
    setShowUserReservations(true);
  }

  async function handleModifyReservationClick(reservation) {
    try {
      if (reservation && reservation.hotelName) {
        console.log("Hotel Name:", reservation.hotelName);
        const response = await axios.get(`http://localhost:8085/room/room/getAvailableRoomsByHotelName/${reservation.hotelName}`);
        console.log("Response from server:", response.data);
  
        setAvailableRooms(response.data);
        setReservationIdToModify(reservation.id);
      } else {
        console.warn('No reservation selected to modify or hotel name is missing.');
      }
    } catch (error) {
      console.error('Error modifying reservation:', error);
    }
  }

  return (
    <div>
      <h2>User Location (in meters)</h2>
      {userLocationInMeters ? (
        <div>
          <p>Latitude: {userLocationInMeters.latitude}</p>
          <p>Longitude: {userLocationInMeters.longitude}</p>
          <h2>Hotels within {radiusInKilometers} kilometers</h2>
          <input
            type="intiger"
            value={radiusInKilometers}
            onChange={e => setRadiusInKilometers(Number(e.target.value))}
            placeholder="Enter radius in kilometers"
          />
          <table>
            <thead>
              <tr>
                <th>Name</th>
                <th>Distance to User</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {hotels.map(hotel => (
                <tr key={hotel.id}>
                  <td>{hotel.name}</td>
                  <td>{getDistance(
                    { latitude: userLocationInMeters.latitude, longitude: userLocationInMeters.longitude },
                    { latitude: hotel.latitudeInMeters, longitude: hotel.longitudeInMeters }
                  )} meters</td>
                  <td>
                    <button onClick={() => handleDetailClick(hotel)}>Details</button>
                    <button onClick={() => handleFeedbackClick(hotel.id)}>Feedback</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      ) : (
        <p>Loading user location...</p>
      )}
      {selectedHotel && (
        <div>
          <h2>Rooms in {selectedHotel.name}</h2>
          <table>
            <thead>
              <tr>
                <th>Room Number</th>
                <th>Type</th>
                <th>Price</th>
                <th>Available</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {rooms.map(room => (
                <tr key={room.id}>
                  <td>{room.roomNumber}</td>
                  <td>{room.type}</td>
                  <td>{room.price}</td>
                  <td>{room.available ? 'Yes' : 'No'}</td>
                  <td>
                    {room.available && (
                      <>
                        <input
                          type="datetime-local"
                          value={selectedDateTime}
                          onChange={e => setSelectedDateTime(e.target.value)}
                        />
                        <button onClick={() => handleReservation(room.id)}>Reservation</button>
                      </>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
     
      {showFeedbackForm && <Feedback key={feedbackFormKey} hotelId={feedbackHotelId} />}
      <button onClick={handleSeeReservationsClick}>See Your Reservations</button>
      {showUserReservations && (
        <div>
          <h2>User Reservations</h2>
          <table>
            <thead>
              <tr>
                <th>Hotel Name</th>
                <th>Date</th>
                <th>Room Number</th>
                <th>Room Type</th>
                <th>Room Available</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {userReservationsWithHotelName.map(reservation => (
                <tr key={reservation.id}>
                  <td>{reservation.hotelName}</td>
                  <td>{reservation.date}</td>
                  <td>{reservation.roomNumber}</td>
                  <td>{reservation.roomType}</td>
                  <td>{reservation.roomAvailable ? 'Yes' : 'No'}</td>
                  <td>
                    <button onClick={() => cancelReservation(reservation.id)}>Cancel</button>
                    <button onClick={() => handleModifyReservationClick(reservation)}>Modify Reservation</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
          {availableRooms.length > 0 && (
            <div>
              <h2>Available Rooms for Modification</h2>
              <table>
                <thead>
                  <tr>
                    <th>Room Number</th>
                    <th>Type</th>
                    <th>Price</th>
                    <th>Actions</th>
                  </tr>
                </thead>
                <tbody>
                  {availableRooms.map(room => (
                    <tr key={room.id}>
                      <td>{room.roomNumber}</td>
                      <td>{room.type}</td>
                      <td>{room.price}</td>
                      <td>
                        <button onClick={() => updatedReservations(room.id, reservationIdToModify)}>Change the Room</button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </div>
      )}
    </div>
  );
}
export default User;