import { React } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Course from './course/page/Course';
import Post from './course/post/Post';
import Header from './header/Header';
import Home from './home/Home';
import CreateCourse from './main/CreateCourse';
import Main from './main/Main';
import Deadline from './deadline/Deadline';

export default function App() {
    return (
        <div>
            <Router>
                <Header />
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route exact path="/courses" element={<Main />} />
                    <Route exact path="/courses/:id" element={<Course />} />
                    <Route
                        exact
                        path="/courses/:id/posts/:id"
                        element={<Post />}
                    />
                    <Route
                        exact
                        path="/courses/create"
                        element={<CreateCourse />}
                    />
                    <Route
                        exact
                        path="posts/deadlines"
                        element={<Deadline />}
                    />
                </Routes>
            </Router>
        </div>
    );
}
