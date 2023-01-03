import { React } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Course from './course/page/Course';
import Post from './course/post/Post';
import Header from './header/Header';
import Home from './home/Home';
import CreateCourse from './main/CreateCourse';
import Main from './main/Main';

export default function App() {
    return (
        <div>
            <Router>
                <Header />
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/courses" element={<Main />} />
                    <Route exact path="/courses/:id" element={<Course />} />
                    <Route
                        exact
                        path="/courses/:id/posts/:id"
                        element={<Post />}
                    />
                    <Route
                        exect
                        path="courses/create"
                        element={<CreateCourse />}
                    />
                </Routes>
            </Router>
        </div>
    );
}
