import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Feedback from './FeedBack';
import User from './User';

function App() {
  return (
    <Router>
      <Routes>
        <Route path='/user' element={<User />} />
        <Route path='/feedback' element={<Feedback />} />

      </Routes>
    </Router>
  );
}

export default App;
