import React, { Component } from 'react';
import { Routes, Route, Link } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';

import AuthService from './services/auth.service';

import Login from './components/auth/login.component';
import Register from './components/auth/register.component';
import Home from './components/home/home.component';
import Profile from './components/profile/profile.component';
import BoardUser from './components/board-user.component';
import BoardAdmin from './components/board-admin.component';

// import AuthVerify from "./common/auth-verify";
import EventBus from './common/EventBus';
import Header from './components/header/header.component';
import Post from './components/post/post';
import Main from './components/main.component';
import CreateCourse from './components/course/course.create.component';
import Deadline from './components/post/posts.deadlines.component';
import Course from './course/page/course';

class App extends Component {
    constructor(props) {
        super(props);
        this.logOut = this.logOut.bind(this);

        this.state = {
            showTeacherBoard: false,
            currentUser: undefined
        };
    }

    componentDidMount() {
        const user = AuthService.getCurrentUser();

        if (user) {
            this.setState({
                currentUser: user,
                showTeacherBoard: user.roles.includes('ROLE_TEACHER')
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
            showTeacherBoard: false,
            currentUser: undefined
        });
    }

    render() {
        const { currentUser, showTeacherBoard } = this.state;

        return (
            <div>
                <Header />
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

                        {showTeacherBoard && (
                            <li className="nav-item">
                                <Link to={'/admin'} className="nav-link">
                                    Teacher Board
                                </Link>
                            </li>
                        )}

                        {currentUser && (
                            <li className="nav-item">
                                <Link to={'/courses'} className="nav-link">
                                    User
                                </Link>
                            </li>
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
                        <Route path="/user" element={<BoardUser />} />
                        <Route path="/admin" element={<BoardAdmin />} />
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
                            path="/posts/deadlines"
                            element={<Deadline />}
                        />
                    </Routes>
                </div>

                {/* <AuthVerify logOut={this.logOut}/> */}
            </div>
        );
    }
}

export default App;

// import { React } from 'react';
// import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
// import Course from './course/page/Course';
// import Post from './course/post/Post';
// import Header from './header/Header';
// import Home from './home/Home';
// import CreateCourse from './main/CreateCourse';
// import Main from './main/Main';
// import Deadline from './deadline/Deadline';

// export default function App() {
//     return (
//         <div>
//             <Router>
//                 <Routes>

//                 </Routes>
//             </Router>
//         </div>
//     );
// }
