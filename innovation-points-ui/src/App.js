import React, { useState } from 'react';
import axios from 'axios';

function App() {
  const [employeeId, setEmployeeId] = useState('');
  const [points, setPoints] = useState(null);
  const [allEmployeePoints, setAllEmployeePoints] = useState([]);
  const [error, setError] = useState(null);

  // Einzelne Mitarbeiterpunkte abrufen
  const fetchPoints = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/points/get/${employeeId}`);
      setPoints(response.data);
      setError(null); // clear error if successful
    } catch (err) {
      setError('Mitarbeiterpunkte konnten nicht abgerufen werden.');
      setPoints(null);
    }
  };

  // Alle Mitarbeiterpunkte abrufen
  const fetchAllEmployeePoints = async () => {
    try {
      const response = await axios.get('http://localhost:8080/points/all');
      setAllEmployeePoints(response.data);
      setError(null); // clear error if successful
    } catch (err) {
      setError('Mitarbeiterliste konnte nicht abgerufen werden.');
      setAllEmployeePoints([]);
    }
  };

  return (
    <div className="App">
      <h1>Punktesystem für Innovationen</h1>
      
      <div>
        <label>Mitarbeiter-ID: </label>
        <input
          type="text"
          value={employeeId}
          onChange={(e) => setEmployeeId(e.target.value)}
        />
        <button onClick={fetchPoints}>Punkte abrufen</button>
      </div>

      {points !== null && (
        <div>
          <h2>Punkte für Mitarbeiter {employeeId}: {points}</h2>
        </div>
      )}

      {error && <div style={{ color: 'red' }}>{error}</div>}

      <hr />

      <div>
        <button onClick={fetchAllEmployeePoints}>Alle Mitarbeiterpunkte anzeigen</button>
        {allEmployeePoints.length > 0 && (
          <div>
            <h2>Alle Mitarbeiterpunkte:</h2>
            <ul>
              {allEmployeePoints.map((employee) => (
                <li key={employee.id}>
                  Mitarbeiter ID: {employee.employeeId}, Punkte: {employee.points}
                </li>
              ))}
            </ul>
          </div>
        )}
      </div>
    </div>
  );
}

export default App;
