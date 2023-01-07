import React, { Component } from 'react';
import { Routes, Route, Link } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';

import AuthService from './services/auth.service';

import Login from './components/auth/login.component';
import Register from './components/auth/register.component';
import Home from './components/home/home.component';
import Profile from './components/profile/profile.component';

import EventBus from './common/EventBus';
import Post from './components/post/post';
import Main from './components/main/main.component';
import CreateCourse from './components/course/course.create.component';
import Deadline from './components/post/posts.deadlines.component';
import Course from './components/page/course';
import Journal from './components/student/journal.component';

class App extends Component {
    constructor(props) {
        super(props);
        this.logOut = this.logOut.bind(this);

        this.state = {
            currentUser: undefined
        };
    }

    componentDidMount() {
        const user = AuthService.getCurrentUser();

        if (user) {
            this.setState({
                currentUser: user
            });
        }

        EventBus.on('logout', () => {
            this.logOut();
        });
    }

    componentWillUnmount() {
        EventBus.remove('logout');
    }

    logOut() {
        AuthService.logout();
        this.setState({
            currentUser: undefined
        });
    }

    render() {
        const { currentUser } = this.state;

        return (
            <div>
                <nav className="navbar navbar-expand navbar-dark bg-dark">
                    <Link to={'/'} className="navbar-brand">
                        classroomy
                    </Link>
                    <div className="navbar-nav mr-auto">
                        <li className="nav-item">
                            <Link to={'/home'} className="nav-link">
                                Home
                            </Link>
                        </li>

                        {currentUser && (
                            <div className="navbar-nav ml-auto">
                                <li className="nav-item">
                                    <Link to={'/courses'} className="nav-link">
                                        My Courses
                                    </Link>
                                </li>
                                <li className="nav-item">
                                    <Link
                                        to={'/posts/deadlines'}
                                        className="nav-link"
                                    >
                                        My Homeworks
                                    </Link>
                                </li>
                            </div>
                        )}
                    </div>

                    {currentUser ? (
                        <div className="navbar-nav ml-auto">
                            <li className="nav-item">
                                <Link to={'/profile'} className="nav-link">
                                    {currentUser.username}
                                </Link>
                            </li>
                            <li className="nav-item">
                                <a
                                    href="/login"
                                    className="nav-link"
                                    onClick={this.logOut}
                                >
                                    LogOut
                                </a>
                            </li>
                        </div>
                    ) : (
                        <div className="navbar-nav ml-auto">
                            <li className="nav-item">
                                <Link to={'/login'} className="nav-link">
                                    Login
                                </Link>
                            </li>

                            <li className="nav-item">
                                <Link to={'/register'} className="nav-link">
                                    Sign Up
                                </Link>
                            </li>
                        </div>
                    )}
                </nav>

                <div className="container mt-3">
                    <Routes>
                        <Route path="/" element={<Home />} />
                        <Route path="/home" element={<Home />} />
                        <Route path="/login" element={<Login />} />
                        <Route path="/register" element={<Register />} />
                        <Route path="/profile" element={<Profile />} />
                        <Route exact path="/courses" element={<Main />} />
                        <Route
                            exact
                            path="/students/:id"
                            element={<Journal />}
                        />
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
                            path="/posts/deadlines"
                            element={<Deadline />}
                        />
                    </Routes>
                </div>
            </div>
        );
    }
}

export default App;
