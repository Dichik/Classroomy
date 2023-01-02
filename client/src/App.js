import { React } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Course from './course/page/Course';
import Post from './course/post/Post';
import Header from './header/Header';
import Home from './home/Home';
import Main from './main/Main';

const App = () => {
    return (
        <div>
            <Router>
                <Header />
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/courses" element={<Main />}></Route>
                    <Route exect path="/courses/:id" element={<Course />} />
                    <Route exect path="/courses/:id/posts/:id" element={<Post />} />
                </Routes>
            </Router>
        </div>
    );
};

export default App;
