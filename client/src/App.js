import { React } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Course from './course/page/Course';
import Header from './header/Header';
import Footer from './footer/Footer';
import Main from './main/Main';

const App = () => {
    return (
        <div>
            <Router>
                <Header />
                <Routes>
                    <Route path="/" element={<Main />} />
                    <Route exect path="/courses/:id" element={<Course />} />
                </Routes>
                <Footer />
            </Router>
        </div>
    );
};

export default App;
